package edu.pdx.cs.data

import org.biojavax.bio.seq.RichSequence
import webapp.MeanCodonUsage
import org.junit.Test
import static org.mockito.Mockito.*

/**
 * Created with IntelliJ IDEA.
 * User: Laura
 * Date: 7/5/13
 * Time: 12:50 PM
 * To change this template use File | Settings | File Templates.
 */
class MeanCodonUsageProcessorTest {

    @Test
    void testProcessorCreatesCorrectDomainClass(){
        //create mocks of the objects we need for the test
        //and define their behavior
        def mockSequence = mock(RichSequence)

        when(mockSequence.identifier).thenReturn("1234")
        // string is me typing randomly
        when(mockSequence.seqString()).thenReturn("GATTACATTACCCGGATTTACGATACCGAAAGTCGATTCAGATATAGAAAGCCATCAT")

        def processor = new MeanCodonUsageProcessor()
        processor.process(mockSequence)

        def createdEntry = MeanCodonUsage.find{organismId == 1234}

        assert createdEntry.organismId == 1234
        assert createdEntry.distribution.size() == 64

        // and now the details...
        // using BigDecimal division to make sure things come out the same
        // counts were done manually
        def zero = new BigDecimal('0')
        def one = new BigDecimal('1')
        def two = new BigDecimal('2')
        def three = new BigDecimal('3')
        def four = new BigDecimal('4')
        def scale = 10

        assert createdEntry.distribution['GCT'] == '0'
        assert createdEntry.distribution['GCC'] == '0'
        assert createdEntry.distribution['GCA'] == '0'
        assert createdEntry.distribution['GCG'] == '0'

        assert createdEntry.distribution['CGT'] == zero.divide(four, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert createdEntry.distribution['CGC'] == zero.divide(four, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert createdEntry.distribution['CGA'] == one.divide(four, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert createdEntry.distribution['CGG'] == one.divide(four, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert createdEntry.distribution['AGA'] == two.divide(four, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert createdEntry.distribution['AGG'] == zero.divide(four, scale, BigDecimal.ROUND_HALF_UP).toString()

        assert createdEntry.distribution['AAT'] == '0'
        assert createdEntry.distribution['AAC'] == '0'

        assert createdEntry.distribution['GAT'] == two.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert createdEntry.distribution['GAC'] == zero.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()

        assert createdEntry.distribution['TGT'] == '0'
        assert createdEntry.distribution['TGC'] == '0'

        assert createdEntry.distribution['CAA'] == '0'
        assert createdEntry.distribution['CAG'] == '0'

        assert createdEntry.distribution['GAA'] == one.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert createdEntry.distribution['GAG'] == zero.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString()

        assert createdEntry.distribution['GGT'] == '0'
        assert createdEntry.distribution['GGC'] == '0'
        assert createdEntry.distribution['GGA'] == '0'
        assert createdEntry.distribution['GGG'] == '0'

        assert createdEntry.distribution['CAT'] == '0'
        assert createdEntry.distribution['CAC'] == '0'

        assert createdEntry.distribution['ATT'] == two.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert createdEntry.distribution['ATC'] == zero.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert createdEntry.distribution['ATA'] == zero.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()

        assert createdEntry.distribution['TTA'] == '0'
        assert createdEntry.distribution['TTG'] == '0'
        assert createdEntry.distribution['CTT'] == '0'
        assert createdEntry.distribution['CTC'] == '0'
        assert createdEntry.distribution['CTA'] == '0'
        assert createdEntry.distribution['CTG'] == '0'

        assert createdEntry.distribution['AAA'] == zero.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert createdEntry.distribution['AAG'] == one.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString()

        assert createdEntry.distribution['ATG'] == '0'

        assert createdEntry.distribution['TTT'] == zero.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert createdEntry.distribution['TTC'] == one.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString()

        assert createdEntry.distribution['CCT'] == zero.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert createdEntry.distribution['CCC'] == zero.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert createdEntry.distribution['CCA'] == one.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert createdEntry.distribution['CCG'] == zero.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString()

        assert createdEntry.distribution['TCT'] == zero.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert createdEntry.distribution['TCC'] == zero.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert createdEntry.distribution['TCA'] == one.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert createdEntry.distribution['TCG'] == zero.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert createdEntry.distribution['AGT'] == one.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert createdEntry.distribution['AGC'] == zero.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()

        assert createdEntry.distribution['ACT'] == zero.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert createdEntry.distribution['ACC'] == two.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert createdEntry.distribution['ACA'] == zero.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert createdEntry.distribution['ACG'] == zero.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()

        assert createdEntry.distribution['TGG'] == '0'

        assert createdEntry.distribution['TAT'] == one.divide(three, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert createdEntry.distribution['TAC'] == two.divide(three, scale, BigDecimal.ROUND_HALF_UP).toString()

        assert createdEntry.distribution['GTT'] == '0'
        assert createdEntry.distribution['GTC'] == '0'
        assert createdEntry.distribution['GTA'] == '0'
        assert createdEntry.distribution['GTG'] == '0'

        assert createdEntry.distribution['TAA'] == '0'
        assert createdEntry.distribution['TGA'] == '0'
        assert createdEntry.distribution['TAG'] == '0'
    }
}
