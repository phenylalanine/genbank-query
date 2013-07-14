/**
 * Created with Eclipse!
 * User: Jim Miller (JGM)
 * Date: 7/6/13
 */

package edu.pdx.cs.data

import org.biojavax.bio.seq.RichSequence
import org.junit.Test
import static org.mockito.Mockito.*
import webapp.RSCU

class RSCUProcessorTests {

	@Test
	void testProcessorCreatesCorrectDomainClass(){
		def mockSequence = mock(RichSequence)

		when(mockSequence.identifier).thenReturn("1234")
		// Same random test string as in MeanCodonUsageProcessorTest()
		when(mockSequence.seqString()).thenReturn("gattacattacccggatttacgataccgaaagtcgattcagatatagaaagccatcat")

		def processor = new RSCUProcessor()
		processor.process(mockSequence)

		def createdEntry = RSCU.find{organismId == 1234}
		assert createdEntry.organismId == 1234
		assert createdEntry.distribution.size() == 59
		
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

		assert createdEntry.distribution["gct"] == "-1"
		assert createdEntry.distribution["gcc"] == "-1"
		assert createdEntry.distribution["gca"] == "-1"
		assert createdEntry.distribution["gcg"] == "-1"

		assert createdEntry.distribution["cgt"] == zero.divide(four, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
		assert createdEntry.distribution["cgc"] == zero.divide(four, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
		assert createdEntry.distribution["cga"] == six.divide(four, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
		assert createdEntry.distribution["cgg"] == six.divide(four, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
		assert createdEntry.distribution["aga"] == twelve.divide(four, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
		assert createdEntry.distribution["agg"] == zero.divide(four, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()

		assert createdEntry.distribution["aat"] == "-1"
		assert createdEntry.distribution["aac"] == "-1"

		assert createdEntry.distribution["gat"] == four.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
		assert createdEntry.distribution["gac"] == zero.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()

		assert createdEntry.distribution["tgt"] == "-1"
		assert createdEntry.distribution["tgc"] == "-1"

		assert createdEntry.distribution["caa"] == "-1"
		assert createdEntry.distribution["cag"] == "-1"

		assert createdEntry.distribution["gaa"] == two.divide(one, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
		assert createdEntry.distribution["gag"] == zero.divide(one, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()

		assert createdEntry.distribution["ggt"] == "-1"
		assert createdEntry.distribution["ggc"] == "-1"
		assert createdEntry.distribution["gga"] == "-1"
		assert createdEntry.distribution["ggg"] == "-1"

		assert createdEntry.distribution["cat"] == "-1"
		assert createdEntry.distribution["cac"] == "-1"

		assert createdEntry.distribution["att"] == six.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
		assert createdEntry.distribution["atc"] == zero.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
		assert createdEntry.distribution["ata"] == zero.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()

		assert createdEntry.distribution["tta"] == "-1"
		assert createdEntry.distribution["ttg"] == "-1"
		assert createdEntry.distribution["ctt"] == "-1"
		assert createdEntry.distribution["ctc"] == "-1"
		assert createdEntry.distribution["cta"] == "-1"
		assert createdEntry.distribution["ctg"] == "-1"

		assert createdEntry.distribution["aaa"] == zero.divide(one, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
		assert createdEntry.distribution["aag"] == two.divide(one, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()

		assert createdEntry.distribution["atg"] == null

		assert createdEntry.distribution["ttt"] == zero.divide(one, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
		assert createdEntry.distribution["ttc"] == two.divide(one, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()

		assert createdEntry.distribution["cct"] == zero.divide(one, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
		assert createdEntry.distribution["ccc"] == zero.divide(one, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
		assert createdEntry.distribution["cca"] == four.divide(one, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
		assert createdEntry.distribution["ccg"] == zero.divide(one, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()

		assert createdEntry.distribution["tct"] == zero.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
		assert createdEntry.distribution["tcc"] == zero.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
		assert createdEntry.distribution["tca"] == six.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
		assert createdEntry.distribution["tcg"] == zero.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
		assert createdEntry.distribution["agt"] == six.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
		assert createdEntry.distribution["agc"] == zero.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()

		assert createdEntry.distribution["act"] == zero.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
		assert createdEntry.distribution["acc"] == eight.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
		assert createdEntry.distribution["aca"] == zero.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
		assert createdEntry.distribution["acg"] == zero.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()

		assert createdEntry.distribution["tgg"] == null

		assert createdEntry.distribution["tat"] == two.divide(three, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
		assert createdEntry.distribution["tac"] == four.divide(three, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()

		assert createdEntry.distribution["gtt"] == "-1"
		assert createdEntry.distribution["gtc"] == "-1"
		assert createdEntry.distribution["gta"] == "-1"
		assert createdEntry.distribution["gtg"] == "-1"

		assert createdEntry.distribution["taa"] == null
		assert createdEntry.distribution["tga"] == null
		assert createdEntry.distribution["tag"] == null
		
		createdEntry.delete(flush: true)
		// Make sure sucker is really gone from the database.
		assert RSCU.find{organismId == 1234} == null
	}
}
