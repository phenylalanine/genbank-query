/**
 * Created with Eclipse!
 * User: Jim Miller (JGM)
 * Date: 7/21/13
 * 
 * 
 */

package edu.pdx.cs.data

import java.io.File;

import org.apache.commons.logging.LogFactory

import webapp.Organism

class RSCUAnalyzerWithoutSimilarityChecking {
	private static final log = LogFactory.getLog(this)
	Organism orgMaster
	List<Organism> orgsCompared
	List<RSCUComparison> comparisonResults
	
	public RSCUAnalyzerWithoutSimilarityChecking(org1, moreOrgs) {
		orgMaster = org1
		orgsCompared = moreOrgs
		comparisonResults = []
		analyze()
	}
	
	public analyze() {
		RSCUComparator rscuCompor
		
		for (org in orgsCompared) {
			rscuCompor = new RSCUComparator(orgMaster, org)
			comparisonResults += rscuCompor.getRSCUComparison()
		}
		// (JGM) Sort result list by RSCU trendline slope (closest to 1 being the best)
		comparisonResults = comparisonResults.sort{ a, b -> Math.abs(1 - a.trendlineSlope) <=> Math.abs(1 - b.trendlineSlope) }

	}
	
	// (JGM) Return a list of comparison results, one for each Organism in the "compared" set.
	public List<RSCUComparison> getComparisonResults() {
		return comparisonResults
	}

	// (JGM) Print to a text file and return the file
	public File rscuFileBuilder() {
		String fileName = orgMaster.scientificName + "_RSCUAnalysis.txt"
		def textFile = new File(fileName)

		textFile.withWriter { out ->
			out.writeLine("Organism ID, Scientific Name, Taxonomy ID, RSCU Trendline Slope, RSCU Trendline Y-Intercept")
			for (item in comparisonResults) {
				out.writeLine(item.org2OrganismId + ", " + item.org2ScientificName + ", " + item.org2TaxonomyId
					+ ", " + item.trendlineSlope + ", " + item.trendlineYIntercept)
			}
		}
		return textFile
	}
}
