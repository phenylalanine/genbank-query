/**
 * Created with Eclipse!
 * User: Jim Miller (JGM)
 * Date: 7/13/13
 *
 * Integration test for domain classes' similarTo() comparators using closures.
 * Integration tests for RSCUComparator, RSCUAnalyzer.
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
	void TestBasicRSCUComparator() {
		System.out.println("Organism.count():  " + Organism.count())
		def organismOne = Organism.get(78)
		def organismTwo = Organism.get(78)
		RSCUComparator rscuCompor = new RSCUComparator(organismOne, organismTwo)
		System.out.println("rscuCompor - trendlineSlope:  " + rscuCompor.rscuComp.trendlineSlope)
	}

	@Test
	void TestRSCUComparator() {
		def organismOne = Organism.get(71)
		def organismTwo = Organism.get(78)
		def rscuComparatorClosure = { Organism currentRSCUOrg, Organism otherRSCUOrg ->
			RSCUComparator rscuCompor = new RSCUComparator(currentRSCUOrg, otherRSCUOrg)
			System.out.println("rscuCompor - trendlineSlope:  " + rscuCompor.rscuComp.trendlineSlope)
			(Math.abs(rscuCompor.rscuComp.trendlineSlope
				.subtract(new BigDecimal(1), MathContext.UNLIMITED)) <= new BigDecimal(".1"))
		}

		assert !organismOne.isSimilarTo(organismTwo, rscuComparatorClosure)
	}
	
	@Test
	void TestMCUFComparator() {
		def organismOne = Organism.get(71)
		def organismTwo = Organism.get(78)
		def mcufComparatorClosure = { Organism currentMCUFOrg, Organism otherMCUFOrg ->
			MCUFComparator mcufCompor = new MCUFComparator(currentMCUFOrg, otherMCUFOrg)
			System.out.println("mcufCompor - mcuf:  " + mcufCompor.mcufComp.mcuf)
			(Math.abs(mcufCompor.mcufComp.mcuf
				.subtract(new BigDecimal(1), MathContext.UNLIMITED)) <= new BigDecimal(".1"))
		}

		assert !organismOne.isSimilarTo(organismTwo, mcufComparatorClosure)
	}
	
	@Test
	void TestGCPercentageComparator() {
		def organismOne = Organism.get(71)
		def organismTwo = Organism.get(78)
		def gcPercentageComparatorClosure = { Organism currentGCPercentageOrg, Organism otherGCPercentageOrg ->
			GCPercentageComparator gcPercentageCompor = new GCPercentageComparator(currentGCPercentageOrg, otherGCPercentageOrg)
			System.out.println("gcPercentageCompor - gcDifference:  " + gcPercentageCompor.gcPercentageComp.gcDifference)
			(Math.abs(gcPercentageCompor.gcPercentageComp.gcDifference
				.subtract(new BigDecimal(1), MathContext.UNLIMITED)) <= new BigDecimal(".1"))
		}

		assert !organismOne.isSimilarTo(organismTwo, gcPercentageComparatorClosure)
	}
	
	@Test
	void TestCompleteComparator() {
		def organismOne = Organism.get(71)
		def organismTwo = Organism.get(78)
		def rscuComparatorClosure = { Organism currentRSCUOrg, Organism otherRSCUOrg ->
			RSCUComparison rscuComp = CompleteComparator.doRSCUComparison(currentRSCUOrg, otherRSCUOrg)
			System.out.println("rscuCompor - trendlineSlope:  " + rscuComp.trendlineSlope)
			(Math.abs(rscuComp.trendlineSlope
				.subtract(new BigDecimal(1), MathContext.UNLIMITED)) <= new BigDecimal(".1"))
		}
		def mcufComparatorClosure = { Organism currentMCUFOrg, Organism otherMCUFOrg ->
			MCUFComparison mcufComp = CompleteComparator.doMCUFComparison(currentMCUFOrg, otherMCUFOrg)
			System.out.println("mcufCompor - mcuf:  " + mcufComp.mcuf)
			(Math.abs(mcufComp.mcuf
				.subtract(new BigDecimal(1), MathContext.UNLIMITED)) <= new BigDecimal(".1"))
		}
		def gcPercentageComparatorClosure = { Organism currentGCPercentageOrg, Organism otherGCPercentageOrg ->
			GCPercentageComparison gcPercentageComp = CompleteComparator.doGCPercentageComparison(currentGCPercentageOrg, otherGCPercentageOrg)
			System.out.println("gcPercentageCompor - gcDifference:  " + gcPercentageComp.gcDifference)
			(Math.abs(gcPercentageComp.gcDifference
				.subtract(new BigDecimal(1), MathContext.UNLIMITED)) <= new BigDecimal(".1"))
		}
		
		assert !organismOne.isSimilarTo(organismTwo, rscuComparatorClosure)
		assert !organismOne.isSimilarTo(organismTwo, mcufComparatorClosure)
		assert !organismOne.isSimilarTo(organismTwo, gcPercentageComparatorClosure)
	}

	@Test
	void TestRSCUAnalyzerWithoutSimilarityChecking() {
		def orgUpload = Organism.get(71)
		def orgsGenBank = [Organism.get(81), Organism.get(82), Organism.get(83), Organism.get(84), Organism.get(85)]
		def analyzer = new RSCUAnalyzerWithoutSimilarityChecking(orgUpload, orgsGenBank)
		analyzer.rscuFileBuilder()
		def results = analyzer.getComparisonResults()

		// (JGM) Also, manually check the contents of this file!
		assert (new File("Organismus Numberus 71_RSCUAnalysis.txt")).exists()
		// (JGM) The first organismID in the first line should be "81", in this example RSCU analysis.
		assert results.get(0).org2OrganismId == "81"
	}
	
	@Test
	void TestMCUFAnalyzerWithoutSimilarityChecking() {
		def orgUpload = Organism.get(71)
		def orgsGenBank = [Organism.get(81), Organism.get(82), Organism.get(83), Organism.get(84), Organism.get(85)]
		def analyzer = new MCUFAnalyzerWithoutSimilarityChecking(orgUpload, orgsGenBank)
		analyzer.mcufFileBuilder()
		def results = analyzer.getComparisonResults()

		// (JGM) Also, manually check the contents of this file!
		assert (new File("Organismus Numberus 71_MCUFAnalysis.txt")).exists()
		// (JGM) The first organismID in the first line should be "81", in this example MCUF analysis.
		assert results.get(0).org2OrganismId == "81"
	}
	
	@Test
	void TestGCPercentageAnalyzerWithoutSimilarityChecking() {
		def orgUpload = Organism.get(71)
		def orgsGenBank = [Organism.get(81), Organism.get(82), Organism.get(83), Organism.get(84), Organism.get(85)]
		def analyzer = new GCPercentageAnalyzerWithoutSimilarityChecking(orgUpload, orgsGenBank)
		analyzer.gcPercentageFileBuilder()
		def results = analyzer.getComparisonResults()

		// (JGM) Also, manually check the contents of this file!
		assert (new File("Organismus Numberus 71_GCPercentageAnalysis.txt")).exists()
		// (JGM) The first organismID in the first line should be "82", in this example GC Percentage analysis.
		assert results.get(0).org2OrganismId == "82"
	}
	
	@Test
	void TestCompleteAnalyzerWithoutSimilarityChecking() {
		def orgUpload = Organism.get(91)
		def orgsGenBank = [Organism.get(101), Organism.get(102), Organism.get(103), Organism.get(104), Organism.get(105)]
		def resultsRSCU = CompleteAnalyzerWithoutSimilarityChecking.doRSCUAnalysis(orgUpload, orgsGenBank)
		def resultsMCUF = CompleteAnalyzerWithoutSimilarityChecking.doMCUFAnalysis(orgUpload, orgsGenBank)
		def resultsGCPercentage = CompleteAnalyzerWithoutSimilarityChecking.doGCPercentageAnalysis(orgUpload, orgsGenBank)

		// (JGM) Also, manually check the contents of this file!
		assert (new File("Organismus Numberus 91_RSCUAnalysis.txt")).exists()
		// (JGM) The first organismID in the first line should be "105", in this example RSCU analysis.
		assert resultsRSCU.get(0).org2OrganismId == "105"
		// (JGM) Also, manually check the contents of this file!
		assert (new File("Organismus Numberus 91_MCUFAnalysis.txt")).exists()
		// (JGM) The first organismID in the first line should be "101", in this example MCUF analysis.
		assert resultsMCUF.get(0).org2OrganismId == "101"
		// (JGM) Also, manually check the contents of this file!
		assert (new File("Organismus Numberus 91_GCPercentageAnalysis.txt")).exists()
		// (JGM) The first organismID in the first line should be "101", in this example GC Percentage analysis.
		assert resultsGCPercentage.get(0).org2OrganismId == "101"
	}
	
    @Ignore
	@Test
	void TestRSCUAnalyzer() {
		def organismOne = Organism.get(71)
		def organismTwo = Organism.get(78)
		def orgsList = [organismTwo]
		def RSCUComparatorClosure = { Organism currentRSCU, Organism otherRSCU ->
			delegate.RSCUComp = new RSCUComparator(organismOne, organismTwo)
			(Math.abs(RSCUComp.getSlope()
					.subtract(new BigDecimal(1), MathContext.UNLIMITED)) > new BigDecimal(".1"))
		}

		RSCUAnalyzer analyzer = new RSCUAnalyzer(organismOne, orgsList, RSCUComparatorClosure)
		def returnFile = analyzer.rscuFileBuilder()

//		 BufferedReader br = new BufferedReader(new FileReader("Organismus Numberus 11_RSCU.txt"));
//		 String line = null;
//		 while ((line = br.readLine()) != null) {
//			 System.out.println(line);
//		 }
		assert returnFile.length() == 52  // Header line only, no data
	}
}