/**
 * Created with Eclipse!
 * User: Jim Miller (JGM)
 * Date: 7/6/13
 */

package edu.pdx.cs.data

import org.biojavax.bio.seq.RichSequence
import org.junit.Test

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

class RSCUProcessorTests {

    @Test
    void testProcessorCreatesCorrectDomainClass() {
        def mockSequence = mock(RichSequence)

        when(mockSequence.identifier).thenReturn("1234")
        // Same random test string as in MeanCodonUsageProcessorTest()
        when(mockSequence.seqString()).thenReturn("gattacattacccggatttacgataccgaaagtcgattcagatatagaaagccatcat")

        def processor = new RSCUProcessor()
        def processedDistribution = processor.process(mockSequence)

        assert processedDistribution.size() == 59

        // distribution map testing copied roughly from MeanCodonUsageProcessorTest()
        def bigDecimalScale = 10
        def zero = new BigDecimal(0)
        def one = new BigDecimal(1)
        def two = new BigDecimal(2)
        def three = new BigDecimal(3)
        def four = new BigDecimal(4)
        def six = new BigDecimal(6)
        def eight = new BigDecimal(8)
        def twelve = new BigDecimal(12)

        assert processedDistribution["gct"] == "-1"
        assert processedDistribution["gcc"] == "-1"
        assert processedDistribution["gca"] == "-1"
        assert processedDistribution["gcg"] == "-1"

        assert processedDistribution["cgt"] == zero.divide(four, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        assert processedDistribution["cgc"] == zero.divide(four, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        assert processedDistribution["cga"] == six.divide(four, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        assert processedDistribution["cgg"] == six.divide(four, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        assert processedDistribution["aga"] == twelve.divide(four, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        assert processedDistribution["agg"] == zero.divide(four, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()

        assert processedDistribution["aat"] == "-1"
        assert processedDistribution["aac"] == "-1"

        assert processedDistribution["gat"] == four.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        assert processedDistribution["gac"] == zero.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()

        assert processedDistribution["tgt"] == "-1"
        assert processedDistribution["tgc"] == "-1"

        assert processedDistribution["caa"] == "-1"
        assert processedDistribution["cag"] == "-1"

        assert processedDistribution["gaa"] == two.divide(one, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        assert processedDistribution["gag"] == zero.divide(one, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()

        assert processedDistribution["ggt"] == "-1"
        assert processedDistribution["ggc"] == "-1"
        assert processedDistribution["gga"] == "-1"
        assert processedDistribution["ggg"] == "-1"

        assert processedDistribution["cat"] == "-1"
        assert processedDistribution["cac"] == "-1"

        assert processedDistribution["att"] == six.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        assert processedDistribution["atc"] == zero.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        assert processedDistribution["ata"] == zero.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()

        assert processedDistribution["tta"] == "-1"
        assert processedDistribution["ttg"] == "-1"
        assert processedDistribution["ctt"] == "-1"
        assert processedDistribution["ctc"] == "-1"
        assert processedDistribution["cta"] == "-1"
        assert processedDistribution["ctg"] == "-1"

        assert processedDistribution["aaa"] == zero.divide(one, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        assert processedDistribution["aag"] == two.divide(one, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()

        assert processedDistribution["atg"] == null

        assert processedDistribution["ttt"] == zero.divide(one, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        assert processedDistribution["ttc"] == two.divide(one, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()

        assert processedDistribution["cct"] == zero.divide(one, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        assert processedDistribution["ccc"] == zero.divide(one, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        assert processedDistribution["cca"] == four.divide(one, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        assert processedDistribution["ccg"] == zero.divide(one, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()

        assert processedDistribution["tct"] == zero.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        assert processedDistribution["tcc"] == zero.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        assert processedDistribution["tca"] == six.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        assert processedDistribution["tcg"] == zero.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        assert processedDistribution["agt"] == six.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        assert processedDistribution["agc"] == zero.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()

        assert processedDistribution["act"] == zero.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        assert processedDistribution["acc"] == eight.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        assert processedDistribution["aca"] == zero.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        assert processedDistribution["acg"] == zero.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()

        assert processedDistribution["tgg"] == null

        assert processedDistribution["tat"] == two.divide(three, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        assert processedDistribution["tac"] == four.divide(three, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()

        assert processedDistribution["gtt"] == "-1"
        assert processedDistribution["gtc"] == "-1"
        assert processedDistribution["gta"] == "-1"
        assert processedDistribution["gtg"] == "-1"

        assert processedDistribution["taa"] == null
        assert processedDistribution["tga"] == null
        assert processedDistribution["tag"] == null
    }
}
