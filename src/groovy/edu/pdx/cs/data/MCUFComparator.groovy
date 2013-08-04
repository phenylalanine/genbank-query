/**
 * Created with Eclipse!
 * User: Jim Miller (JGM)
 * Date: 8/3/13
 * 
 */

package edu.pdx.cs.data

import org.apache.commons.logging.LogFactory
import webapp.Organism

class MCUFComparator {
	private static final log = LogFactory.getLog(this)
	private int bigDecimalScale
	private Organism orgMaster, orgCompared
	MCUFComparison mcufComp
	BigDecimal mcuf
	
	public MCUFComparator(org1, org2) {
		bigDecimalScale = 10
		orgMaster = org1
		orgCompared = org2
		compare()
	}
	
	// (JGM) In the returned List, the Master organism's MCUF distribution Map is first.
	public List<Map<String, String>> getMCUFCodonDistributions() {
		return [orgMaster.mcufCodonDistribution, orgCompared.mcufCodonDistribution]
	}

	public BigDecimal getMCUF() {
		return mcuf
	}
	
	public getMCUFComparison() {
		return mcufComp
	}
	
	// Calculate MCUF.
	private void compare() {
		def BigDecimal sumOfDiffs
		def BigDecimal temp

		sumOfDiffs = new BigDecimal('0')
		for (entry in orgMaster.mcufCodonDistribution.entrySet()) {
			temp = (new BigDecimal(entry.value)).subtract(new BigDecimal(orgCompared.mcufCodonDistribution[entry.key]))
			sumOfDiffs = sumOfDiffs.add(temp.abs())
		}
		mcuf = sumOfDiffs.divide(new BigDecimal("64"), bigDecimalScale, BigDecimal.ROUND_HALF_UP)
		mcufComp = new MCUFComparison(orgMaster.organismId, orgMaster.scientificName, orgMaster.taxonomyId,
			orgMaster.mcufCodonDistribution, orgCompared.organismId, orgCompared.scientificName, orgCompared.taxonomyId,
			orgCompared.mcufCodonDistribution, mcuf)
	}
}
