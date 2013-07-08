package edu.pdx.cs.data

import org.biojavax.bio.taxa.NCBITaxon
import org.junit.Test

/**
 * Created with IntelliJ IDEA.
 * User: Ryan
 * Date: 7/6/13
 * Time: 5:31 PM
 * To change this template use File | Settings | File Templates.
 */
class GenBankClientTest {

    @Test
    void testClientRetrievesReleaseVersion() {
        def genbankClient = new GenBankClient(GenBankClient.GENBANK_FTP_URL)
        assert genbankClient.currentGenBankReleaseVersion != null
    }

    @Test
    void testClientRetrievesTaxonomyForTaxonomyId() {
        def genbankClient = new GenBankClient(GenBankClient.GENBANK_FTP_URL)
        NCBITaxon taxon = genbankClient.getTaxonomyForId(1140)

        assert taxon.containsName("no rank", "cellular organisms")
        assert taxon.containsName("superkingdom", "Bacteria")
        assert taxon.containsName("phylum", "Cyanobacteria")
        assert taxon.containsName("order", "Chroococcales")
        assert taxon.containsName("genus", "Synechococcus")
        assert taxon.containsName("species", "Synechococcus elongatus")

        assert taxon.parentNCBITaxID == 32046
        assert taxon.NCBITaxID == 1140
        assert taxon.geneticCode == 11
        assert taxon.mitoGeneticCode == 0
    }

    @Test
    void testGetAllFilesInDirectory() {
        def genbankClient = new GenBankClient(GenBankClient.GENBANK_FTP_URL)

        //check that we list the right # of dirs with no filter
        def fileList = genbankClient.getAllFilesInDirectory("genbank")
        assert fileList.size() == 1892

        //check that we list the right # of dirs with filter
        fileList = genbankClient.getAllFilesInDirectory("genbank") { file ->
            file.name.endsWith("Number")
        }

        assert fileList.size() == 1
        assert fileList[0] == "genbank/GB_Release_Number"
    }

    @Test
    void testGetAllFilesInDirectoryRecursively() {
        def genbankClient = new GenBankClient(GenBankClient.GENBANK_FTP_URL)

        //check that we list the right # of dirs with no filter
        def fileList = genbankClient
                .recursivelyGetAllFilesInDirectory("genbank/daily-nc")
        assert fileList.size() == 196

        //check that we list the right # of dirs with a filter
        fileList = genbankClient
                .recursivelyGetAllFilesInDirectory("genbank/daily-nc") {
            it.name.endsWith("flat.gz")
        }
        assert fileList.size() == 63
    }
}
