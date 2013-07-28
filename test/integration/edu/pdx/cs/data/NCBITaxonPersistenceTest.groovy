package edu.pdx.cs.data

import org.biojavax.bio.taxa.NCBITaxon
import org.biojavax.bio.taxa.SimpleNCBITaxon
import org.junit.Test

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue

/**
 * Created with IntelliJ IDEA.
 * User: Ryan
 * Date: 7/27/13
 * Time: 9:35 AM
 * To change this template use File | Settings | File Templates.
 */
class NCBITaxonPersistenceTest {

    @Test
    void testNCBITaxonCRUD() {
        //Create
        NCBITaxon taxon = GenBankClient.getTaxonomyForId(1140)
        taxon.save(flush: true)

        //Read
        NCBITaxon taxon2 = SimpleNCBITaxon.findAll().first()
        assertEquals(taxon, taxon2)

        //Update
        taxon.NCBITaxID = 1240
        taxon.save(flush: true)

        taxon2 = SimpleNCBITaxon.findAll().first()
        assertEquals(taxon, taxon2)

        //Delete
        taxon.delete(flush: true)
        assertTrue(SimpleNCBITaxon.findAll().empty)
    }
}
