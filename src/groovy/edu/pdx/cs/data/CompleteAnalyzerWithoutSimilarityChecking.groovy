/**
 * Created with Eclipse!
 * User: Jim Miller (JGM)
 * Date: 8/3/13
 *
 */

package edu.pdx.cs.data

import java.util.List;

import org.apache.commons.logging.LogFactory

class CompleteAnalyzerWithoutSimilarityChecking {
	private static final log = LogFactory.getLog(this)
	
	public static List<RSCUComparison> doRSCUAnalysis(org1, moreOrgs) {
		RSCUAnalyzerWithoutSimilarityChecking rscuAnal = new RSCUAnalyzerWithoutSimilarityChecking(org1, moreOrgs)
		rscuAnal.rscuFileBuilder()
		return rscuAnal.comparisonResults
	}

	public static List<MCUFComparison> doMCUFAnalysis(org1, moreOrgs) {
		MCUFAnalyzerWithoutSimilarityChecking mcufAnal = new MCUFAnalyzerWithoutSimilarityChecking(org1, moreOrgs)
		mcufAnal.mcufFileBuilder()
		return mcufAnal.comparisonResults
	}
	
	public static List<GCPercentageComparison> doGCPercentageAnalysis(org1, moreOrgs) {
		GCPercentageAnalyzerWithoutSimilarityChecking gcPercentageAnal = new GCPercentageAnalyzerWithoutSimilarityChecking(org1, moreOrgs)
		gcPercentageAnal.gcPercentageFileBuilder()
		return gcPercentageAnal.comparisonResults
	}
}
