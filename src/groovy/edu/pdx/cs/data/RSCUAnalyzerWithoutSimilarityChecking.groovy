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

class RSCUAnalyzerWithoutSimilarityChecking {
	private static final log = LogFactory.getLog(this)
	Organism orgUpload
	List<Organism> orgsGenBank
	RSCUComparator RSCUComp
	
	public RSCUAnalyzerWithoutSimilarityChecking(orgUp, orgsGB) {
		orgUpload = orgUp
		orgsGenBank = orgsGB
	}
	
	public File analyze() {
		List results = []
		String fileName = orgUpload.scientificName + "_RSCU.txt"
		for (org in orgsGenBank) {
			RSCUComp = new RSCUComparator(orgUpload, org)
			results += [[org.scientificName, org.taxonomyId, RSCUComp.getSlope(), RSCUComp.getYIntercept()]]
		}
		// (JGM) Sort result list by RSCU trendline slope (closest to 1 being the best)
		results = results.sort{ a, b -> Math.abs(1 - a[2]) <=> Math.abs(1 - b[2]) }
		// (JGM) Print to a text file and return the file
		def textFile = new File(fileName)
		textFile.withWriter { out ->
			out.writeLine("Scientific Name, Taxonomy ID, RSCU Trendline Slope, RSCU Trendline Y-Intercept")
			for (item in results) {
				out.writeLine(item[0] + ", " + item[1] + ", " + item[2].toString() + ", " + item[3].toString())
			}
		}
		return textFile
	}
}
