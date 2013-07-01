package edu.pdx.cs.data


import org.biojavax.bio.seq.RichSequence
import org.biojavax.bio.taxa.NCBITaxon
import org.junit.Test
import webapp.Organism

import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: Ryan
 * Date: 6/30/13
 * Time: 5:31 PM
 * To change this template use File | Settings | File Templates.
 */

class OrganismProcessorTest {

    @Test
    void testProcessorCreatesCorrectDomainClass(){
        //create mocks of the objects we need for the test
        //and define their behavior
        def mockSequence = mock(RichSequence)
        def mockTaxon = mock(NCBITaxon)

        when(mockTaxon.NCBITaxID).thenReturn(4321)
        when(mockTaxon.getNames("scientific name")).thenReturn(["homo sapien"] as Set)

        when(mockSequence.taxon)thenReturn(mockTaxon)
        when(mockSequence.identifier).thenReturn("1234")

        def processor = new OrganismProcessor(mockSequence)
        processor.process()

        def newlyCreatedOrganism = Organism.find{organismId == 1234}

        assert newlyCreatedOrganism.organismId == 1234
        assert newlyCreatedOrganism.scientificName == "homo sapien"
        assert newlyCreatedOrganism.taxonomyId == "4321"
        assert newlyCreatedOrganism.completeGenome == Boolean.FALSE

        //clean up the in-memory database in case another test uses it before we tear down
        newlyCreatedOrganism.delete(flush: true)
        }
}
