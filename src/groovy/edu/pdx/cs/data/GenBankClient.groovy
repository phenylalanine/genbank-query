package edu.pdx.cs.data

import org.apache.commons.logging.LogFactory
import org.apache.commons.net.ftp.FTP
import org.apache.commons.net.ftp.FTPClient
import org.biojavax.SimpleNamespace
import org.biojavax.bio.seq.RichSequence
import org.biojavax.bio.seq.RichSequenceIterator
import org.biojavax.bio.taxa.NCBITaxon
import org.biojavax.bio.taxa.SimpleNCBITaxon
import java.util.zip.GZIPInputStream

/**
 * Created with IntelliJ IDEA.
 * User: Ryan
 * Date: 7/6/13
 * Time: 5:18 PM
 * To change this template use File | Settings | File Templates.
 */

class GenBankClient {

    private static final log = LogFactory.getLog(this)
    public static final GENBANK_FTP_URL = "ftp.ncbi.nih.gov"
    def EukTaxonomyFileEnding = "ASSEMBLY_INFO"
    def EukSequenceFileEnding = ".fa.gz"
    def BacTaxonomyFileEnding = ".rpt"
    def BacSequenceFileEnding = ".fna"
    private final FTPClient ftp

    GenBankClient(String url) {
        ftp = getFtpClient(url)
    }

    def getCurrentGenBankReleaseVersion() {
        ftp.retrieveFileStream("/genbank/GB_Release_Number").withReader {
            return it.readLine().trim()
        }
    }

    static NCBITaxon getTaxonomyForId(int id) {
        try {
            def xml = new XmlSlurper()
                    .parse("http://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=taxonomy&id=${id}&retmode=xml")

            NCBITaxon ncbiTaxon = new SimpleNCBITaxon(id)

            ncbiTaxon.addName("scientific name", xml.Taxon.ScientificName.text())

            xml.Taxon.LineageEx.Taxon.each { taxon ->
                ncbiTaxon.addName(taxon.Rank.text(), taxon.ScientificName.text())
            }

            ncbiTaxon.geneticCode = xml.Taxon.GeneticCode.GCId.text() as Integer
            ncbiTaxon.mitoGeneticCode = xml.Taxon.MitoGeneticCode.MGCId.text() as Integer
            ncbiTaxon.parentNCBITaxID = xml.Taxon.ParentTaxId.text() as Integer

            return ncbiTaxon
        } catch (Exception e) {
            log.warn("Error retrieving taxonomy information for id: " + id, e)
        }
    }

    /**
     * This will go out and get all of the sequences in the genbank directory
     * of the NCBI FTP site and run them through our processing pipeline to analyze
     * and persist the results
     */
    def processGenBank(persist = true) {
        def genBankFiles = getAllFilesInDirectory("genbank") { file ->
            file.name.endsWith("seq.gz")
        }

        genBankFiles.each { genBankFile ->
            try {
                def is = new GZIPInputStream(ftp.retrieveFileStream(genBankFile))
                def reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(is)))
                RichSequenceIterator sequences = RichSequence.IOTools.readGenbankDNA(reader, new SimpleNamespace("base-genbank"))
                OrganismProcessor processor = new OrganismProcessor(persist: persist)

                while (sequences.hasNext()) {
                    processor.process(sequences.nextRichSequence())
                }
            } catch (Exception e) {
                log.warn("Error processing GenBank file: " + genBankFile, e)
            }
        }
    }

    def getAllFilesInDirectory(String dir, Closure<Boolean> filter = { true }) {
        def ftp = getFtpClient(GENBANK_FTP_URL)
        return ftp.listFiles(dir).findAll { filter(it) }.collect { dir + "/" + it.name }
    }

    def recursivelyGetAllFilesInDirectory(String dir, Closure<Boolean> filter = { true }) {
        def files = []
        for (file in ftp.listFiles(dir)) {
            if (file.isFile()) {
                if (filter(file)) {
                    files.add(dir + "/" + file.name)
                }
            } else {
                files.addAll(recursivelyGetAllFilesInDirectory(dir + "/" + file.name, filter))
            }
        }
        return files
    }

    private getFtpClient(String url, String username = "anonymous", String password = "") {
        def ftp = new FTPClient()
        ftp.connect(url)
        ftp.login(username, password)
        ftp.setFileType(FTP.BINARY_FILE_TYPE)
        ftp.enterLocalPassiveMode()

        return ftp
    }

    def processAllGenomes() {

        getAllEukaryaGenomeFiles().each { assembly ->
            processGenomeAssembly(assembly)
        }

        getAllBacteriaGenomeFiles().each { assembly ->
            processGenomeAssembly(assembly)
        }
    }

    /** Relevant info here: ftp://ftp.ncbi.nlm.nih.gov/genomes/Bacteria/ReadMe.txt
     "The data for individual microbial genomes are contained in separate folders.
     Directory names approximate organism names (in taxonomy) but not always. For duplicate
     projects, project ID is appended to differentiate them."

     "Provided for each chromosome/plasmid:
     -Summary data (.rpt)
     -Genbank (.gbk), ASN1 (.asn), binary ASN1 (.val) and fasta (.fna) format sequences
     -Fasta format gene (.fnn) and protein (.faa) sequences
     -Gene and protein information (location, strand, product etc.) (.gff and .ptt).
     -GeneMark, GeneMarkHMM, Glimmer and Prodigal gene predictions
     -A list of updates since the last release (.rps)."

     For the purpose of this project, I decided to use .fna and .rpt files for Bacterial genomes. We could
     also use .gbk files, but they are 4 times larger and average to 8MB per file.
     */
    def List<GenomeAssembly> getAllBacteriaGenomeFiles() {

        def bac = "genbank/genomes/Bacteria"

        // visit folder and find all related files
        def fileList = recursivelyGetAllFilesInDirectory(bac) {
            it.name.endsWith(BacSequenceFileEnding) or it.name.endsWith(BacTaxonomyFileEnding)
        }

        return createGenomeAssemblyObjects(fileList, false)
    }

    /** Relevant info here: ftp://ftp.ncbi.nih.gov/genbank/genomes/README_ASSEMBLIES
     "The files provided include sequences for chromosomes and scaffolds in FASTA format,
     and AGP format files that describe how the chromosomes and scaffolds were assembled from
     the component sequences."

     "Each assembly directory will contain an ASSEMBLY_INFO file and one or
     more assembly-unit directories. Many assemblies consist of a single
     assembly-unit, the Primary Assembly; other assemblies may be comprised
     of multiple assembly-units."

     "Each assembly-unit directory will also contain one or more of the following directories
     (depending on the particular assembly):
     assembled_chromosomes/
     placed_scaffolds/
     unlocalized_scaffolds/
     unplaced_scaffolds/
     alt_scaffolds/ (only in alternate loci and patch assembly-units)
     pseudoautosomal_region/ (only for mammmals)"

     "The ASSEMBLY_INFO contains assembly meta data.
     The file structure is as in this example from the GRCh37 assembly.

     DATE:<tab>24-FEB-2009 (date assembly was submitted)
     ORGANISM:<tab>Homo sapiens
     TAXID:<tab>9606
     ASSEMBLY LONG NAME:<tab>Genome Reference Consortium Human Reference 37
     ASSEMBLY SHORT NAME:<tab>GRCh37
     ASSEMBLY SUBMITTER:<tab>Genome Reference Consortium
     ASSEMBLY TYPE:<tab>Haploid + alternate loci
     NUMBER OF ASSEMBLY-UNITS:<tab>10"
     */

    def List<GenomeAssembly> getAllEukaryaGenomeFiles() {
        // get genomes for all Eukaryotes
        return getEukaryaGenomeFiles("")
    }

    /*
        This method allows to limit the file search to a subgroup of Eukarya (more convenient for testing).
        Acceptable subgroups are: fungi, invertebrates, plants, protozoa, vertebrates_mammals, vertebrates_other.
        Method returns a list of files
    */

    def List<GenomeAssembly> getEukaryaGenomeFiles(String subgroup) {

        def euk = "genbank/genomes/Eukaryotes"

        if (!subgroup.isEmpty())
            euk += '/' + subgroup

        // recursively visit folder and find all 'ASSEMBLY_INFO' and associated FASTA files
        def List<String> fileList = recursivelyGetAllFilesInDirectory(euk) {
            it.name.endsWith(EukSequenceFileEnding) or it.name.endsWith(EukTaxonomyFileEnding)
        }

        return createGenomeAssemblyObjects(fileList, true)
    }

    /*
        Accepts a list of file names (either Bacteria or Eukarya), and creates a list of GenomeAssembly
        objects. Assumes that any file in the list that doesn't match the taxonomyFileEnding is a sequence file.
     */

    def List<GenomeAssembly> createGenomeAssemblyObjects(List<String> fileList, boolean isEukarya) {

        def List<GenomeAssembly> genomes = []
        def String taxonomyFileEnding = (isEukarya) ? EukTaxonomyFileEnding : BacTaxonomyFileEnding
        def String sequenceFileEnding = (isEukarya) ? EukSequenceFileEnding : BacSequenceFileEnding

        def taxonomyInfo = fileList.findAll { it.endsWith(taxonomyFileEnding) }
        def sequences = fileList - taxonomyInfo

        // assert that sequence file endings are correct
        try {
            sequences.each { assert it.endsWith(sequenceFileEnding) }
        } catch (Error e) {
            log.warn("Error creating GenomeAssembly: wrong sequence file extension")
        }

        taxonomyInfo.each { file ->
            def genome = new GenomeAssembly()

            genome.isEukarya = isEukarya
            genome.taxonomyFile = file

            def dir = file.substring(0, file.lastIndexOf(taxonomyFileEnding))
            def sequenceFiles = sequences.findAll { it.startsWith(dir) }
            sequences -= sequenceFiles

            // eliminate placed_scaffolds if assembled_chromosomes are present
            def placedScaffolds = sequenceFiles.findAll { it.contains("placed_scaffolds") }
            def assembled_chromosomes = sequenceFiles.findAll { it.contains("assembled_chromosomes") }

            if (!assembled_chromosomes.isEmpty())
                sequenceFiles = sequenceFiles - placedScaffolds

            genome.sequenceFiles = sequenceFiles
            genomes.add(genome)
        }

        try {
            assert sequences.isEmpty()
        } catch (Error e) {
            log.warn("Error creating GenomeAssembly: unused files in the list")
        }

        return genomes
    }

    /*
      This method is supposed to take a GenomeAssembly object, download sequences, and process extracted RichSequences.
     */

    def void processGenomeAssembly(GenomeAssembly genome) {

        def RichSequenceIterator iterator
        def id = getTaxonomyIDfromAssemblyInfo(genome.taxonomyFile)
        // def NCBITaxon taxon = getTaxonomyForId(id)

        genome.sequenceFiles.each { sequenceFile ->
            def ftp1 = getFtpClient(GENBANK_FTP_URL)
            try {

                def is
                if (genome.isEukarya)
                    is = new GZIPInputStream(ftp1.retrieveFileStream(sequenceFile))
                else
                    is = new BufferedInputStream(ftp1.retrieveFileStream(sequenceFile))

                def reader = new BufferedReader(new InputStreamReader(is))
                iterator = RichSequence.IOTools.readFastaDNA(reader, null)

                // todo: associate taxon with each sequence in the iterator? (.setTaxon(taxon))
                // (not sure if it's possible). Alternatively, the GI number that comes
                // with every FASTA file can be tracked down to a taxon in the future

                // here processing happens
                //TODO add persistence

                is.close()
                ftp1.disconnect()

            } catch (Exception e) {
                log.warn("Error processing GenBank file: " + sequenceFile, e)
            }
        }
    }

    def private getTaxonomyIDfromAssemblyInfo(String assemblyFileName) {

        def taxID
        def ftp = getFtpClient(GENBANK_FTP_URL)
        ftp.retrieveFileStream(assemblyFileName).withReader {
            taxID = (((it.readLines().find { it.toUpperCase().startsWith("TAXID") }).split('\\s+'))[1])
        }
        ftp.disconnect()
        return taxID.toInteger()
    }

    BufferedReader readRemoteFile(String remotePath) {
        InputStream is = new MaybeCompressedInputStream(ftp.retrieveFileStream(remotePath))
        return new BufferedReader(new InputStreamReader(is))
    }
}
