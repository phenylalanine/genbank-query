package edu.pdx.cs.data

import org.biojavax.bio.seq.RichSequence
import org.junit.Test
import webapp.MeanCodonUsage

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

/**
 * Created with IntelliJ IDEA.
 * User: Laura
 * Date: 7/5/13
 * Time: 12:50 PM
 * To change this template use File | Settings | File Templates.
 */
class MeanCodonUsageProcessorTest {

    @Test
    void testProcessorCreatesCorrectDomainClass() {
        //create mocks of the objects we need for the test
        //and define their behavior
        def mockSequence = mock(RichSequence)

        when(mockSequence.identifier).thenReturn("1234")
        // string is me typing randomly
        when(mockSequence.seqString()).thenReturn("gattacattacccggatttacgataccgaaagtcgattcagatatagaaagccatcat")

        def processor = new MeanCodonUsageProcessor()
        def processedDistribution = processor.process(mockSequence)

        assert processedDistribution.size() == 64

        // and now the details...
        // using BigDecimal division to make sure things come out the same
        // counts were done manually
        def zero = new BigDecimal('0')
        def one = new BigDecimal('1')
        def two = new BigDecimal('2')
        def three = new BigDecimal('3')
        def four = new BigDecimal('4')
        def scale = 10

        assert processedDistribution['gct'] == '0'
        assert processedDistribution['gcc'] == '0'
        assert processedDistribution['gca'] == '0'
        assert processedDistribution['gcg'] == '0'

        assert processedDistribution['cgt'] == zero.divide(four, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert processedDistribution['cgc'] == zero.divide(four, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert processedDistribution['cga'] == one.divide(four, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert processedDistribution['cgg'] == one.divide(four, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert processedDistribution['aga'] == two.divide(four, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert processedDistribution['agg'] == zero.divide(four, scale, BigDecimal.ROUND_HALF_UP).toString()

        assert processedDistribution['aat'] == '0'
        assert processedDistribution['aac'] == '0'

        assert processedDistribution['gat'] == two.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert processedDistribution['gac'] == zero.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()

        assert processedDistribution['tgt'] == '0'
        assert processedDistribution['tgc'] == '0'

        assert processedDistribution['caa'] == '0'
        assert processedDistribution['cag'] == '0'

        assert processedDistribution['gaa'] == one.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert processedDistribution['gag'] == zero.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString()

        assert processedDistribution['ggt'] == '0'
        assert processedDistribution['ggc'] == '0'
        assert processedDistribution['gga'] == '0'
        assert processedDistribution['ggg'] == '0'

        assert processedDistribution['cat'] == '0'
        assert processedDistribution['cac'] == '0'

        assert processedDistribution['att'] == two.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert processedDistribution['atc'] == zero.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert processedDistribution['ata'] == zero.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()

        assert processedDistribution['tta'] == '0'
        assert processedDistribution['ttg'] == '0'
        assert processedDistribution['ctt'] == '0'
        assert processedDistribution['ctc'] == '0'
        assert processedDistribution['cta'] == '0'
        assert processedDistribution['ctg'] == '0'

        assert processedDistribution['aaa'] == zero.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert processedDistribution['aag'] == one.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString()

        assert processedDistribution['atg'] == '0'

        assert processedDistribution['ttt'] == zero.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert processedDistribution['ttc'] == one.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString()

        assert processedDistribution['cct'] == zero.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert processedDistribution['ccc'] == zero.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert processedDistribution['cca'] == one.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert processedDistribution['ccg'] == zero.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString()

        assert processedDistribution['tct'] == zero.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert processedDistribution['tcc'] == zero.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert processedDistribution['tca'] == one.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert processedDistribution['tcg'] == zero.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert processedDistribution['agt'] == one.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert processedDistribution['agc'] == zero.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()

        assert processedDistribution['act'] == zero.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert processedDistribution['acc'] == two.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert processedDistribution['aca'] == zero.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert processedDistribution['acg'] == zero.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()

        assert processedDistribution['tgg'] == '0'

        assert processedDistribution['tat'] == one.divide(three, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert processedDistribution['tac'] == two.divide(three, scale, BigDecimal.ROUND_HALF_UP).toString()

        assert processedDistribution['gtt'] == '0'
        assert processedDistribution['gtc'] == '0'
        assert processedDistribution['gta'] == '0'
        assert processedDistribution['gtg'] == '0'

        assert processedDistribution['taa'] == '0'
        assert processedDistribution['tga'] == '0'
        assert processedDistribution['tag'] == '0'
    }
}
