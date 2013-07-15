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
    private final FTPClient ftp

    GenBankClient(String url) {
        ftp = getFtpClient(url)
    }

    def getCurrentGenBankReleaseVersion() {
        ftp.retrieveFileStream("/genbank/GB_Release_Number").withReader {
            return it.readLine().trim()
        }
    }

    def static getTaxonomyForId(int id) {
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
    def processGenBank(ProcessingPipeline processors) {
        def genBankFiles = getAllFilesInDirectory("genbank") { file ->
            file.name.endsWith("seq.gz")
        }

        genBankFiles.each { genBankFile ->
            try {
                def is = new GZIPInputStream(ftp.retrieveFileStream(genBankFile))
                def reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(is)))
                RichSequenceIterator sequences = RichSequence.IOTools.readGenbankDNA(reader, new SimpleNamespace("base-genbank"))
                processors.process(sequences)
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
}
