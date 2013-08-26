/**
 * Created with Eclipse!
 * User: Jim Miller (JGM)
 * Date: 8/25/13
 */

package edu.pdx.cs.data

import org.junit.Test;
import webapp.Organism

class UploadAnalyzerTests {

	@Test
	// (JGM) Verify by examining any sent and received email in appropriate accounts.
	void testUploadAnalysis() {
		UploadAnalyzer.processSeqGzFile()
		// (JGM) RSCU analysis fails because some of the sequences in our test seq.gz file are too short
		// to produce valid RSCU best fit lines (the null codon synonym group problem).
		//UploadAnalyzer.analyzeAgainstGenbank(Organism.get(59), AnalysisConstants.Type.RSCU, "jgm2@pdx.edu")
		UploadAnalyzer.analyzeAgainstGenbank(Organism.get(38), AnalysisConstants.Type.MCUF, "jgm2@pdx.edu")
		UploadAnalyzer.analyzeAgainstGenbank(Organism.get(45), AnalysisConstants.Type.GCPERCENT, "jgm2@pdx.edu")
	}
}
