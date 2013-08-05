/**
 * Created with Eclipse!
 * User: Jim Miller (JGM)
 * Date: 8/4/13
 *
 * Integration tests for comparators, analyzers, using persistent, randomly-generated data.
 * All tests meant to be run in persistentTest environment.
 */

package edu.pdx.cs.data

import org.junit.Ignore
import org.junit.Test
import webapp.Organism

import java.math.MathContext

// @Ignore whole class if necessary to not break testing runs, such as on Jenkins.
@Ignore
class ProcessorComparatorPersistentTests {


	@Test
	void TestBasicRSCUComparator() {
		System.out.println("Organism.count():  " + Organism.count())
		def organismOne = Organism.get(78)
		def organismTwo = Organism.get(78)
		RSCUComparator rscuCompor = new RSCUComparator(organismOne, organismTwo)
		System.out.println("rscuCompor - trendlineSlope:  " + rscuCompor.rscuComp.trendlineSlope)
	}

    @Ignore
	@Test
	// (JGM) Results depend on randomly-generated data from BootStrap.groovy, and so will not be consistent
	// across installations of genbank-query.  Test meant to be run in persistentTest environment.
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

    @Ignore
	@Test
	// (JGM) Results depend on randomly-generated data from BootStrap.groovy, and so will not be consistent
	// across installations of genbank-query.  Test meant to be run in persistentTest environment.
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

    @Ignore
	@Test
	// (JGM) Results depend on randomly-generated data from BootStrap.groovy, and so will not be consistent
	// across installations of genbank-query.  Test meant to be run in persistentTest environment.
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

    @Ignore
	@Test
	// (JGM) Results depend on randomly-generated data from BootStrap.groovy, and so will not be consistent
	// across installations of genbank-query.  Test meant to be run in persistentTest environment.
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

    @Ignore
	@Test
	// (JGM) Results depend on randomly-generated data from BootStrap.groovy, and so will not be consistent
	// across installations of genbank-query.  Test meant to be run in persistentTest environment.
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

    @Ignore
	@Test
	// (JGM) Results depend on randomly-generated data from BootStrap.groovy, and so will not be consistent
	// across installations of genbank-query.  Test meant to be run in persistentTest environment.
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

    @Ignore
	@Test
	// (JGM) Results depend on randomly-generated data from BootStrap.groovy, and so will not be consistent
	// across installations of genbank-query.  Test meant to be run in persistentTest environment.
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

    @Ignore
	@Test
	// (JGM) Results depend on randomly-generated data from BootStrap.groovy, and so will not be consistent
	// across installations of genbank-query.  Test meant to be run in persistentTest environment.
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
	// (JGM) Results depend on randomly-generated data from BootStrap.groovy, and so will not be consistent
	// across installations of genbank-query.  Test meant to be run in persistentTest environment.
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
