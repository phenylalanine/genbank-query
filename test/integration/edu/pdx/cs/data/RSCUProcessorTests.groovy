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
		when(mockSequence.seqString()).thenReturn("GATTACATTACCCGGATTTACGATACCGAAAGTCGATTCAGATATAGAAAGCCATCAT")

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

		assert createdEntry.distribution["GCT"] == "-1"
		assert createdEntry.distribution["GCC"] == "-1"
		assert createdEntry.distribution["GCA"] == "-1"
		assert createdEntry.distribution["GCG"] == "-1"

		assert createdEntry.distribution["CGT"] == zero.divide(four, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
		assert createdEntry.distribution["CGC"] == zero.divide(four, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
		assert createdEntry.distribution["CGA"] == six.divide(four, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
		assert createdEntry.distribution["CGG"] == six.divide(four, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
		assert createdEntry.distribution["AGA"] == twelve.divide(four, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
		assert createdEntry.distribution["AGG"] == zero.divide(four, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()

		assert createdEntry.distribution["AAT"] == "-1"
		assert createdEntry.distribution["AAC"] == "-1"

		assert createdEntry.distribution["GAT"] == four.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
		assert createdEntry.distribution["GAC"] == zero.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()

		assert createdEntry.distribution["TGT"] == "-1"
		assert createdEntry.distribution["TGC"] == "-1"

		assert createdEntry.distribution["CAA"] == "-1"
		assert createdEntry.distribution["CAG"] == "-1"

		assert createdEntry.distribution["GAA"] == two.divide(one, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
		assert createdEntry.distribution["GAG"] == zero.divide(one, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()

		assert createdEntry.distribution["GGT"] == "-1"
		assert createdEntry.distribution["GGC"] == "-1"
		assert createdEntry.distribution["GGA"] == "-1"
		assert createdEntry.distribution["GGG"] == "-1"

		assert createdEntry.distribution["CAT"] == "-1"
		assert createdEntry.distribution["CAC"] == "-1"

		assert createdEntry.distribution["ATT"] == six.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
		assert createdEntry.distribution["ATC"] == zero.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
		assert createdEntry.distribution["ATA"] == zero.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()

		assert createdEntry.distribution["TTA"] == "-1"
		assert createdEntry.distribution["TTG"] == "-1"
		assert createdEntry.distribution["CTT"] == "-1"
		assert createdEntry.distribution["CTC"] == "-1"
		assert createdEntry.distribution["CTA"] == "-1"
		assert createdEntry.distribution["CTG"] == "-1"

		assert createdEntry.distribution["AAA"] == zero.divide(one, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
		assert createdEntry.distribution["AAG"] == two.divide(one, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()

		assert createdEntry.distribution["ATG"] == null

		assert createdEntry.distribution["TTT"] == zero.divide(one, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
		assert createdEntry.distribution["TTC"] == two.divide(one, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()

		assert createdEntry.distribution["CCT"] == zero.divide(one, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
		assert createdEntry.distribution["CCC"] == zero.divide(one, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
		assert createdEntry.distribution["CCA"] == four.divide(one, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
		assert createdEntry.distribution["CCG"] == zero.divide(one, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()

		assert createdEntry.distribution["TCT"] == zero.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
		assert createdEntry.distribution["TCC"] == zero.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
		assert createdEntry.distribution["TCA"] == six.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
		assert createdEntry.distribution["TCG"] == zero.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
		assert createdEntry.distribution["AGT"] == six.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
		assert createdEntry.distribution["AGC"] == zero.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()

		assert createdEntry.distribution["ACT"] == zero.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
		assert createdEntry.distribution["ACC"] == eight.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
		assert createdEntry.distribution["ACA"] == zero.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
		assert createdEntry.distribution["ACG"] == zero.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()

		assert createdEntry.distribution["TGG"] == null

		assert createdEntry.distribution["TAT"] == two.divide(three, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
		assert createdEntry.distribution["TAC"] == four.divide(three, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()

		assert createdEntry.distribution["GTT"] == "-1"
		assert createdEntry.distribution["GTC"] == "-1"
		assert createdEntry.distribution["GTA"] == "-1"
		assert createdEntry.distribution["GTG"] == "-1"

		assert createdEntry.distribution["TAA"] == null
		assert createdEntry.distribution["TGA"] == null
		assert createdEntry.distribution["TAG"] == null
		
		createdEntry.delete(flush: true)
		// Make sure sucker is really gone from the database.
		assert RSCU.find{organismId == 1234} == null
	}
}
