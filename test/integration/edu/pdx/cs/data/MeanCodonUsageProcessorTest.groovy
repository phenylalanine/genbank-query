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
        when(mockSequence.seqString()).thenReturn("gattacattacccggatttacgataccgaaagtcgattcagatatagaaagccatcat")

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

        assert createdEntry.distribution['gct'] == '0'
        assert createdEntry.distribution['gcc'] == '0'
        assert createdEntry.distribution['gca'] == '0'
        assert createdEntry.distribution['gcg'] == '0'

        assert createdEntry.distribution['cgt'] == zero.divide(four, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert createdEntry.distribution['cgc'] == zero.divide(four, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert createdEntry.distribution['cga'] == one.divide(four, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert createdEntry.distribution['cgg'] == one.divide(four, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert createdEntry.distribution['aga'] == two.divide(four, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert createdEntry.distribution['agg'] == zero.divide(four, scale, BigDecimal.ROUND_HALF_UP).toString()

        assert createdEntry.distribution['aat'] == '0'
        assert createdEntry.distribution['aac'] == '0'

        assert createdEntry.distribution['gat'] == two.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert createdEntry.distribution['gac'] == zero.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()

        assert createdEntry.distribution['tgt'] == '0'
        assert createdEntry.distribution['tgc'] == '0'

        assert createdEntry.distribution['caa'] == '0'
        assert createdEntry.distribution['cag'] == '0'

        assert createdEntry.distribution['gaa'] == one.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert createdEntry.distribution['gag'] == zero.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString()

        assert createdEntry.distribution['ggt'] == '0'
        assert createdEntry.distribution['ggc'] == '0'
        assert createdEntry.distribution['gga'] == '0'
        assert createdEntry.distribution['ggg'] == '0'

        assert createdEntry.distribution['cat'] == '0'
        assert createdEntry.distribution['cac'] == '0'

        assert createdEntry.distribution['att'] == two.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert createdEntry.distribution['atc'] == zero.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert createdEntry.distribution['ata'] == zero.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()

        assert createdEntry.distribution['tta'] == '0'
        assert createdEntry.distribution['ttg'] == '0'
        assert createdEntry.distribution['ctt'] == '0'
        assert createdEntry.distribution['ctc'] == '0'
        assert createdEntry.distribution['cta'] == '0'
        assert createdEntry.distribution['ctg'] == '0'

        assert createdEntry.distribution['aaa'] == zero.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert createdEntry.distribution['aag'] == one.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString()

        assert createdEntry.distribution['atg'] == '0'

        assert createdEntry.distribution['ttt'] == zero.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert createdEntry.distribution['ttc'] == one.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString()

        assert createdEntry.distribution['cct'] == zero.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert createdEntry.distribution['ccc'] == zero.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert createdEntry.distribution['cca'] == one.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert createdEntry.distribution['ccg'] == zero.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString()

        assert createdEntry.distribution['tct'] == zero.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert createdEntry.distribution['tcc'] == zero.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert createdEntry.distribution['tca'] == one.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert createdEntry.distribution['tcg'] == zero.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert createdEntry.distribution['agt'] == one.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert createdEntry.distribution['agc'] == zero.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()

        assert createdEntry.distribution['act'] == zero.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert createdEntry.distribution['acc'] == two.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert createdEntry.distribution['aca'] == zero.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert createdEntry.distribution['acg'] == zero.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()

        assert createdEntry.distribution['tgg'] == '0'

        assert createdEntry.distribution['tat'] == one.divide(three, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert createdEntry.distribution['tac'] == two.divide(three, scale, BigDecimal.ROUND_HALF_UP).toString()

        assert createdEntry.distribution['gtt'] == '0'
        assert createdEntry.distribution['gtc'] == '0'
        assert createdEntry.distribution['gta'] == '0'
        assert createdEntry.distribution['gtg'] == '0'

        assert createdEntry.distribution['taa'] == '0'
        assert createdEntry.distribution['tga'] == '0'
        assert createdEntry.distribution['tag'] == '0'
    }
}
