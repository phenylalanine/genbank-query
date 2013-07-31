/**
 * Created with Eclipse!
 * User: Jim Miller (JGM)
 * Date: 7/13/13
 *
 * Integration test for domain classes' similarTo() comparators using closures.
 * Integration tests for RSCUComparison, RSCUAnalyzer.
 */

package edu.pdx.cs.data

import org.junit.Ignore
import org.junit.Test
import webapp.Organism

import java.math.MathContext

class ProcessorComparatorTests {
    def bigDecimalScale = 10
    def one = new BigDecimal(1)
    def two = new BigDecimal(2)
    def three = new BigDecimal(3)
    def four = new BigDecimal(4)
    def six = new BigDecimal(6)
    def eight = new BigDecimal(8)

    @Test
    void testSimilarTo() {
        def organismOne = new Organism()
        organismOne.organismId = 1234
        organismOne.rscuCodonDistribution = [
                (BioConstants.ACT): six.divide(four, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString(),
                (BioConstants.GGA): three.divide(four, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        ]
        organismOne.mcufCodonDistribution = [
                (BioConstants.ACA): six.divide(four, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString(),
                (BioConstants.CCA): three.divide(four, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        ]
        organismOne.gcPercentage = "42"

        def organismTwo = new Organism()
        organismTwo.organismId = 5678
        organismTwo.rscuCodonDistribution = [
                (BioConstants.ACT): six.divide(eight, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString(),
                (BioConstants.GGA): three.toString()
        ]
        organismTwo.mcufCodonDistribution = [
                (BioConstants.ACA): six.divide(four, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString(),
                (BioConstants.CCA): three.divide(four, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        ]

        // This test closure calls two RSCU objects "similar" if their RSCU values for codon ACT have < 1 difference.
        def clos = { Organism currentRSCU, Organism otherRSCU ->
            (Math.abs(currentRSCU.rscuCodonDistribution.get(BioConstants.ACT)
                    .toBigDecimal()
                    .subtract(otherRSCU.rscuCodonDistribution.get(BioConstants.ACT)
                    .toBigDecimal(), MathContext.UNLIMITED)) < 1)
        }

        assert organismOne.isSimilarTo(organismTwo, clos)
        // Similar closures can easily be defined for testing similarity of the other domain classes.
    }

	@Test
	void TestBasicRSCUComparison() {
		System.out.println(Organism.count())
		def organismOne = Organism.get(78)
		def organismTwo = Organism.get(78)
		RSCUComparison RSCUComp = new RSCUComparison(organismOne, organismTwo)
		System.out.println("RSCUComp.getSlope():  " + RSCUComp.getSlope())
	}

	@Test
	void TestRSCUComparison() {
		def organismOne = Organism.get(71)
		def organismTwo = Organism.get(78)
		def RSCUComparatorClosure = { Organism currentRSCU, Organism otherRSCU ->
			RSCUComparison RSCUComp = new RSCUComparison(currentRSCU, otherRSCU)
			System.out.println("RSCUComp.getSlope():  " + RSCUComp.getSlope())
			(Math.abs(RSCUComp.getSlope()
				.subtract(new BigDecimal(1), MathContext.UNLIMITED)) <= new BigDecimal(".1"))
		}

		assert !organismOne.isSimilarTo(organismTwo, RSCUComparatorClosure)
	}

    @Ignore
	@Test
	void TestRSCUAnalyzer() {
		def organismOne = Organism.get(71)
		def organismTwo = Organism.get(78)
		def orgsList = [organismTwo]
		def RSCUComparatorClosure = { Organism currentRSCU, Organism otherRSCU ->
			delegate.RSCUComp = new RSCUComparison(organismOne, organismTwo)
			(Math.abs(RSCUComp.getSlope()
					.subtract(new BigDecimal(1), MathContext.UNLIMITED)) > new BigDecimal(".1"))
		}

		RSCUAnalyzer analyzer = new RSCUAnalyzer(organismOne, orgsList, RSCUComparatorClosure)
		def returnFile = analyzer.analyze()

//		 BufferedReader br = new BufferedReader(new FileReader("Organismus Numberus 11_RSCU.txt"));
//		 String line = null;
//		 while ((line = br.readLine()) != null) {
//			 System.out.println(line);
//		 }
		assert returnFile.length() == 52  // Header line only, no data
	}
}