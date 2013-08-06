package webapp

import org.biojavax.bio.taxa.SimpleNCBITaxon
import org.junit.Ignore
import org.junit.Test

import static org.junit.Assert.assertNotNull
import static org.junit.Assert.assertTrue

/**
 * Created with IntelliJ IDEA.
 * User: Ryan
 * Date: 8/5/13
 * Time: 4:50 PM
 * To change this template use File | Settings | File Templates.
 */
class GenBankSyncServiceTest {

    @Test
    void testGetCurrentGenBankVersion() {
        def genBankSyncService = new GenBankSyncService()
        assertNotNull(genBankSyncService.getCurrentGenBankReleaseVersion())
    }

    /*
     * The following tests are just for manual debugging. These
      * tests take way too long and there is no way to programatically
      * verify their correctness, other than the fact that they don't
      * throw an exception or something
     */
    @Ignore
    @Test
    void testSyncNodeFiles() {
        def genbankSyncService = new GenBankSyncService()
        genbankSyncService.syncNCBITaxonomy()
        assertTrue(SimpleNCBITaxon.count() > 100000)
    }

    @Ignore
    @Test
    void testSyncGenbank() {
        def genbankSyncService = new GenBankSyncService()
        genbankSyncService.syncGenbank()
    }

    @Ignore
    @Test
    void testSyncGenbankCompleteGenome() {
        def genbankSyncService = new GenBankSyncService()
        genbankSyncService.syncGenbankCompleteGenome()
    }
}
