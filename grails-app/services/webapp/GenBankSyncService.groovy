package webapp

import edu.pdx.cs.data.DNAParser
import edu.pdx.cs.data.GenBankFTPClient
import edu.pdx.cs.data.GenomeAssembly
import edu.pdx.cs.data.OrganismProcessor
import org.apache.commons.lang.NotImplementedException
import org.apache.tools.tar.TarEntry
import org.apache.tools.tar.TarInputStream
import org.biojavax.SimpleNamespace
import org.biojavax.bio.seq.RichSequence
import org.biojavax.bio.seq.RichSequenceIterator
import org.biojavax.bio.taxa.io.SimpleNCBITaxonomyLoader

class GenBankSyncService {
    //we don't want to roll the whole thing back if something fails. Some data is
    //better than no data and this could be pretty finicky for a while
    static transactional = false

    /**
     * @return the current genbank release version
     */
    def getCurrentGenBankReleaseVersion() {
        def ftp = new GenBankFTPClient(GenBankFTPClient.GENBANK_FTP_URL)
        try {
            ftp.getRemoteStream("/genbank/GB_Release_Number").withReader {
                return it.readLine().trim()
            }
        } finally {
            ftp.close()
        }
    }

    /**
     * Get the entirety of the genbank directory as a stream
     * and persist the analysis of the sequences to our local
     * database
     */
    void syncGenbank() {
        GenBankFTPClient client = new GenBankFTPClient(GenBankFTPClient.GENBANK_FTP_URL)
        try {
            def genBankFiles = client.getAllFilesInDirectory("genbank") { file ->
                file.name.endsWith("seq.gz")
            }

            genBankFiles.each { genBankFile ->
                try {
                    def reader = client.readRemoteFile(genBankFile)
                    RichSequenceIterator sequences = RichSequence.IOTools.readGenbankDNA(reader, new SimpleNamespace("base-genbank"))
                    OrganismProcessor processor = new OrganismProcessor()

                    while (sequences.hasNext()) {
                        processor.process(sequences.nextRichSequence())
                    }
                } catch (Exception e) {
                    log.warn("Error processing GenBank file: " + genBankFile, e)
                }
            }
        } finally {
            client.close()
        }
    }

    /**
     * Download everything in the genbank/genomes directory
     * and the genomes directory
     */
    void syncGenbankCompleteGenome() {

        GenBankFTPClient client = new GenBankFTPClient(GenBankFTPClient.GENBANK_FTP_URL)
        try {

            client.getAllGenomeFiles().each { assembly ->
                processGenomeAssembly(assembly)
            }

        } finally {
            client.close()
        }

    }

    /*
     This method is supposed to take a GenomeAssembly object, download sequences, and process extracted RichSequences.
    */

    def void processGenomeAssembly(GenomeAssembly genome) {

        genome.sequenceFiles.each { sequenceFile ->

            def client = new GenBankFTPClient(GenBankFTPClient.GENBANK_FTP_URL)
            try {

                def reader = client.readRemoteFile(sequenceFile)
                RichSequenceIterator sequences = RichSequence.IOTools.readFastaDNA(reader, null)
                // todo [FEATURE REQUEST] : make parser work with reader instead OF InputStream:
                // RichSequenceIterator sequences = DNAParser.parseDNA(reader, null)

                OrganismProcessor processor = new OrganismProcessor()

                while (sequences.hasNext()) {
                    processor.process(sequences.nextRichSequence())
                }

                client.close()

            } catch (Exception e) {
                log.warn("Error processing GenBank file: " + sequenceFile, e)
            }
        }
    }

    /**
     * Download the Taxonomy dump from NCBI
     * and load it into our local database
     */
    void syncNCBITaxonomy() {
        downloadTaxonomyDump()
        readTaxonomyAndSave()
    }

    private void downloadTaxonomyDump() {
        GenBankFTPClient ftp = new GenBankFTPClient(GenBankFTPClient.GENBANK_FTP_URL)

        try {
            def taxonomyTar = new TarInputStream(ftp.getRemoteStream("/pub/taxonomy/taxdump.tar.gz"))
            try {
                for (TarEntry entry = taxonomyTar.nextEntry; entry != null;) {
                    if (entry.name == "names.dmp" || entry.name == "nodes.dmp") {
                        def bos = new BufferedOutputStream(new FileOutputStream("${System.getProperty("java.io.tmpdir")}/${entry.name}"))
                        try {
                            taxonomyTar.copyEntryContents(new BufferedOutputStream(bos))
                        } finally {//make sure we close the file output stream so we can interact with these files
                            bos.close()
                        }
                    }
                }
            } finally {//make sure we close the input stream no matter what
                taxonomyTar.close()
            }
        } finally {
            ftp.close()
        }
    }

    private void readTaxonomyAndSave() {
        def nodesFile = new File("${System.getProperty("java.io.tmpdir")}/nodes.dmp")
        def namesFile = new File("${System.getProperty("java.io.tmpdir")}/names.dmp")
        def nodes = new BufferedInputStream(new FileInputStream(nodesFile))
        def names = new BufferedInputStream(new FileInputStream(namesFile))

        try {
            nodes = new BufferedReader(new InputStreamReader(nodes))
            names = new BufferedReader(new InputStreamReader(names))

            SimpleNCBITaxonomyLoader taxonomyLoader = new SimpleNCBITaxonomyLoader()

            def node
            while ((node = taxonomyLoader.readNode(nodes))) {
                node.save(flush: true)
            }

            def name
            while ((name = taxonomyLoader.readName(names))) {
                name.save(flush: true)
            }

        } catch (Exception e) {
            log.warn("Error saving taxonomy data", e)
        }
        finally { //make sure we close these input streams so we can delete the files
            nodes.close()
            names.close()
            namesFile.delete()
            nodesFile.delete()
        }
    }
}
