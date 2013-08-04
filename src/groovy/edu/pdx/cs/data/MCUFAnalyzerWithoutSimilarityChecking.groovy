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

class MCUFAnalyzerWithoutSimilarityChecking {
	private static final log = LogFactory.getLog(this)
	Organism orgMaster
	List<Organism> orgsCompared
	List<MCUFComparison> comparisonResults
	
	public MCUFAnalyzerWithoutSimilarityChecking(org1, moreOrgs) {
		orgMaster = org1
		orgsCompared = moreOrgs
		comparisonResults = []
		analyze()
	}
	
	public analyze() {
		MCUFComparator MCUFCompor
		
		for (org in orgsCompared) {
			MCUFCompor = new MCUFComparator(orgMaster, org)
			comparisonResults += MCUFCompor.getMCUFComparison()
		}
        // sort result list by mcuf (lowest to highest).
		comparisonResults = comparisonResults.sort{a,b -> a.mcuf <=> b.mcuf}

	}
	
	// (JGM) Return a list of comparison results, one for each Organism in the "compared" set.
	public List<MCUFComparison> getComparisonResults() {
		return comparisonResults
	}

	// (JGM) Print to a text file and return the file
	public File mcufFileBuilder() {
		String fileName = orgMaster.scientificName + "_MCUFAnalysis.txt"
		def textFile = new File(fileName)

		textFile.withWriter { out ->
			out.writeLine("Organism ID, Scientific Name, Taxonomy ID, MCUF")
			for (item in comparisonResults) {
				out.writeLine(item.org2OrganismId + ", " + item.org2ScientificName + ", " + item.org2TaxonomyId
					+ ", " + item.mcuf)
			}
		}
		return textFile
	}
}
