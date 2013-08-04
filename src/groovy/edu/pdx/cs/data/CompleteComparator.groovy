/**
 * Created with Eclipse!
 * User: Jim Miller (JGM)
 * Date: 8/3/13
 *
 */

package edu.pdx.cs.data

import org.apache.commons.logging.LogFactory

class CompleteComparator {
	private static final log = LogFactory.getLog(this)
	
	public static RSCUComparison doRSCUComparison(org1, org2) {
		RSCUComparator rscuCompor = new RSCUComparator(org1, org2)
		return rscuCompor.rscuComp
	}

	public static MCUFComparison doMCUFComparison(org1, org2) {
		MCUFComparator mcufCompor = new MCUFComparator(org1, org2)
		return mcufCompor.mcufComp
	}
	
	public static GCPercentageComparison doGCPercentageComparison(org1, org2) {
		GCPercentageComparator gcPercentageCompor = new GCPercentageComparator(org1, org2)
		return gcPercentageCompor.gcPercentageComp
	}
}
