/**
 * Created with Eclipse!
 * User: Jim Miller (JGM)
 * Date: 8/3/13
 *
 */

package edu.pdx.cs.data

import org.apache.commons.logging.LogFactory
import webapp.Organism

class GCPercentageComparator {
	private static final log = LogFactory.getLog(this)
	private Organism orgMaster, orgCompared
	GCPercentageComparison gcPercentageComp
	BigDecimal gcDifference
	
	public GCPercentageComparator(org1, org2) {
		orgMaster = org1
		orgCompared = org2
		compare()
	}
	
	public BigDecimal getGCDifference() {
		return gcDifference
	}
	
	public getGCPercentageComparison() {
		return gcPercentageComp
	}
	
	// Calculate MCUF.
	private void compare() {
		gcDifference = Math.abs(orgMaster.gcPercentage.toBigDecimal() - orgCompared.gcPercentage.toBigDecimal())
		gcPercentageComp = new GCPercentageComparison(orgMaster.organismId, orgMaster.scientificName, orgMaster.taxonomyId,
			orgMaster.gcPercentage, orgCompared.organismId, orgCompared.scientificName, orgCompared.taxonomyId,
			orgCompared.gcPercentage, gcDifference)
	}
}
