package webapp

import org.biojavax.bio.taxa.NCBITaxon
import org.junit.Test

/**
 * Created with IntelliJ IDEA.
 * User: Ryan
 * Date: 8/5/13
 * Time: 4:50 PM
 * To change this template use File | Settings | File Templates.
 */
class GenBankDynamicServiceTests {

    @Test
    void testClientRetrievesTaxonomyForTaxonomyId() {
        def genbankDynamicSerivce = new GenBankDynamicService()

        NCBITaxon taxon = genbankDynamicSerivce.getTaxonomyForId(1140)

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
}
