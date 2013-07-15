/**
 * Created with Eclipse!
 * User: Jim Miller (JGM)
 * Date: 7/13/13
 * 
 * Integration tests for domain classes' similarTo() comparators using closures.
 */

package edu.pdx.cs.data

import org.junit.Test
import static org.mockito.Mockito.*
import edu.pdx.cs.data.BioConstants
import webapp.GCPercentage
import webapp.MeanCodonUsage
import webapp.Organism
import webapp.RSCU

class Processor_ComparatorTests {
	def bigDecimalScale = 10
	def zero = new BigDecimal(0)
	def one = new BigDecimal(1)
	def two = new BigDecimal(2)
	def three = new BigDecimal(3)
	def four = new BigDecimal(4)
	def six = new BigDecimal(6)
	def eight = new BigDecimal(8)
	def twelve = new BigDecimal(12)

	@Test
	void testSimilarTo() {
		def RSCUOne = new RSCU()
		RSCUOne.organismId = 123
		RSCUOne.distribution = [
			(BioConstants.ACT):six.divide(four, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString(),
			(BioConstants.GGA):three.divide(four, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString(),
		]
		
		def RSCUTwo = new RSCU()
		RSCUTwo.organismId = 456
		RSCUTwo.distribution = [
			(BioConstants.ACT):six.divide(eight, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString(),
			(BioConstants.GGA):three.toString()
		]
		
		// This test closure calls two RSCU objects "similar" if their RSCU values for codon ACT have < 1 difference.
		def clos = {RSCU currentRSCU, RSCU otherRSCU ->
					(Math.abs(currentRSCU.distribution.get(BioConstants.ACT).toBigDecimal() - otherRSCU.distribution.get(BioConstants.ACT).toBigDecimal()) < 1) ? true : false
				   }
		
		assert RSCUOne.isSimilarTo(RSCUTwo, clos)
		
		// Similar closures can easily be defined for testing similarity of the other domain classes.
	}
}
