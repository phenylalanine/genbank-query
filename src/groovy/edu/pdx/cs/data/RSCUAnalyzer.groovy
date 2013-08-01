/**
 * Created with Eclipse!
 * User: Jim Miller (JGM)
 * Date: 7/21/13
 * 
 * 
 */

package edu.pdx.cs.data

import java.math.MathContext
import org.apache.commons.logging.LogFactory
import org.biojavax.bio.seq.RichSequence
import webapp.Organism


// (JGM)  All broken, don't use yet.
class RSCUAnalyzer {
	private static final log = LogFactory.getLog(this)
	Organism orgUpload
	List<Organism> orgsGenBank
	RSCUComparator RSCUComp
	Closure RSCUComparatorClosure
	def closureState = [:]
	
	public RSCUAnalyzer(orgUp, orgsGB, RSCUComparatorClos) {
		orgUpload = orgUp
		orgsGenBank = orgsGB
		RSCUComparatorClosure = RSCUComparatorClos
	}
	
	public File analyze() {
		List results = []
		String fileName = orgUpload.scientificName + "_RSCU.txt"
		for (org in orgsGenBank) {
			if (orgUpload.isSimilarTo(org, RSCUComparatorClosure)) {
				RSCUComparatorClosure.delegate = closureState
				results += [[org.scientificName, org.taxonomyId, closureState.RSCUComp.getSlope()]]
			}
		}
		// (JGM) Sort result list by RSCU trendline slope
		results = results.sort{ a, b -> a[2] <=> b[2] }
		// (JGM) Print to a text file and return the file
		def textFile = new File(fileName)
		textFile.withWriter { out ->
			out.writeLine("Scientific Name, Taxonomy ID, RSCU Trendline Slope")
			for (item in results) {
				out.writeLine(item[0] + ", " + item[1] + ", " + item[2].toString())
			}
		}
		return textFile
	}
}
