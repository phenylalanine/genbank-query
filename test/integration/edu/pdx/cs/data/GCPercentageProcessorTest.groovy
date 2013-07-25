package edu.pdx.cs.data

import org.biojavax.bio.seq.RichSequence
import org.junit.Test
import static org.mockito.Mockito.*

/**
 * Created with IntelliJ IDEA.
 * User: Laura
 * Date: 7/6/13
 * Time: 10:19 AM
 * To change this template use File | Settings | File Templates.
 */
class GCPercentageProcessorTest {
    @Test
    void testProcessorCreatesCorrectDomainClass(){
        //create mocks of the objects we need for the test
        //and define their behavior
        def mockSequence = mock(RichSequence)

        when(mockSequence.identifier).thenReturn("1234")
        // string is me typing randomly
        when(mockSequence.seqString()).thenReturn("gattacattacccggatttacgataccgaaagtcgattcagatatagaaagccatcat")

        def processor = new GCPercentageProcessor()
        def gc = processor.process(mockSequence)

        // bit of BigDecimal math...
        def num = new BigDecimal("22")
        def denom = new BigDecimal("58")
        def scale = 10
        def temp = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
        def str = temp.movePointRight(2).toString()

        assert gc == str
    }
}
