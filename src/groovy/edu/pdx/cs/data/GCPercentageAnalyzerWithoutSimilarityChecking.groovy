/**
 * Created with Eclipse!
 * User: Jim Miller (JGM)
 * Date: 8/3/13
 *
 */

package edu.pdx.cs.data

import java.io.File;

import org.apache.commons.logging.LogFactory

import webapp.Organism

class GCPercentageAnalyzerWithoutSimilarityChecking {
	private static final log = LogFactory.getLog(this)
	Organism orgMaster
	List<Organism> orgsCompared
	List<GCPercentageComparison> comparisonResults
	
	public GCPercentageAnalyzerWithoutSimilarityChecking(org1, moreOrgs) {
		orgMaster = org1
		orgsCompared = moreOrgs
		comparisonResults = []
		analyze()
	}
	
	public analyze() {
		GCPercentageComparator gcPercentageCompor
		
		for (org in orgsCompared) {
			gcPercentageCompor = new GCPercentageComparator(orgMaster, org)
			comparisonResults += gcPercentageCompor.getGCPercentageComparison()
		}
		// sort result list by GC percentage difference (lowest to highest).
		comparisonResults = comparisonResults.sort{a,b -> a.gcDifference <=> b.gcDifference}

	}
	
	// (JGM) Return a list of comparison results, one for each Organism in the "compared" set.
	public List<GCPercentageComparison> getComparisonResults() {
		return comparisonResults
	}

	// (JGM) Print to a text file and return the file
	public File gcPercentageFileBuilder() {
		String fileName = orgMaster.scientificName + "_GCPercentageAnalysis.txt"
		def textFile = new File(fileName)

		textFile.withWriter { out ->
			out.writeLine("Organism ID, Scientific Name, Taxonomy ID, GC Percentage Difference")
			for (item in comparisonResults) {
				out.writeLine(item.org2OrganismId + ", " + item.org2ScientificName + ", " + item.org2TaxonomyId
					+ ", " + item.gcDifference)
			}
		}
		return textFile
	}
}
