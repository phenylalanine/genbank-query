/**
 * Created with Eclipse!
 * User: Jim Miller (JGM)
 * Date: 7/21/13
 * 
 * Compare the RSCU analyses of the Master (usually uploaded) organism against a Compared (often GenBank) organism,
 * producing a best fit trendline's slope and y-intercept.
 */

package edu.pdx.cs.data

import java.math.MathContext
import org.apache.commons.logging.LogFactory
import webapp.Organism

class RSCUComparator {
	private static final log = LogFactory.getLog(this)
	private int bigDecimalScale
	private Organism orgMaster, orgCompared
	RSCUComparison rscuComp
	BigDecimal trendlineSlope, trendlineYIntercept
	
	public RSCUComparator(org1, org2) {
		bigDecimalScale = 10
		orgMaster = org1
		orgCompared = org2
		compare()
	}
	
	// (JGM) In the returned List, the Master organism's RSCU distribution Map is first.
	public List<Map<String, String>> getRSCUCodonDistributions() {
		return [orgMaster.rscuCodonDistribution, orgCompared.rscuCodonDistribution]
	}

	public BigDecimal getSlope() {
		return trendlineSlope
	}
	
	public BigDecimal getYIntercept() {
		return trendlineYIntercept
	}
	
	public getRSCUComparison() {
		return rscuComp
	}
	
	// (JGM) x is the Master organism, y is the Compared organism.
	// Find the best fit line (linear).
	// http://people.hofstra.edu/stefan_waner/realworld/calctopic1/regression.html
	private void compare() {
		BigDecimal sumProducts = new BigDecimal(0), sumXValues = new BigDecimal(0),
			sumYValues = new BigDecimal(0), sumXSquares = new BigDecimal(0)
		BigDecimal slopeNumerator, slopeDenominator, slope, yIntercept
		BigDecimal distSize = orgMaster.rscuCodonDistribution.size()

		// First, if any codon's RSCU value is -1, in either organism, discard the comparison
		// by returning null.  *** Is this a proper way to handle -1 values?? ***
		List<Map> rscuCodonDistributions = [orgMaster.rscuCodonDistribution, orgCompared.rscuCodonDistribution]
		for (dist in rscuCodonDistributions)
			for (e in dist)
				if (e.getValue() == "-1") {
					trendlineSlope = null
					trendlineYIntercept = null
					return
				}

		for (e in orgMaster.rscuCodonDistribution.keySet()) {
			sumProducts = sumProducts.add(orgMaster.rscuCodonDistribution.get(e).toBigDecimal()
				.multiply(orgCompared.rscuCodonDistribution.get(e).toBigDecimal(), MathContext.UNLIMITED),
				MathContext.UNLIMITED)
			sumXValues = sumXValues.add(orgMaster.rscuCodonDistribution.get(e).toBigDecimal(), MathContext.UNLIMITED)
			sumYValues = sumYValues.add(orgCompared.rscuCodonDistribution.get(e).toBigDecimal(), MathContext.UNLIMITED)
			sumXSquares = sumXSquares.add(orgMaster.rscuCodonDistribution.get(e).toBigDecimal().pow(2,
				MathContext.UNLIMITED), MathContext.UNLIMITED)
		}
		slopeNumerator = distSize.multiply(sumProducts, MathContext.UNLIMITED)
			.subtract(sumXValues.multiply(sumYValues, MathContext.UNLIMITED), MathContext.UNLIMITED)
		slopeDenominator = distSize.multiply(sumXSquares, MathContext.UNLIMITED)
			.subtract(sumXValues.pow(2, MathContext.UNLIMITED), MathContext.UNLIMITED)
		slope = slopeNumerator.divide(slopeDenominator, bigDecimalScale, BigDecimal.ROUND_HALF_UP)
		yIntercept = (sumYValues.subtract(slope.multiply(sumXValues, MathContext.UNLIMITED), MathContext.UNLIMITED))
			.divide(distSize, bigDecimalScale, BigDecimal.ROUND_HALF_UP)
		
		trendlineSlope = slope
		trendlineYIntercept = yIntercept
		rscuComp = new RSCUComparison(orgMaster.organismId, orgMaster.scientificName, orgMaster.taxonomyId,
			orgMaster.rscuCodonDistribution, orgCompared.organismId, orgCompared.scientificName, orgCompared.taxonomyId,
			orgCompared.rscuCodonDistribution, trendlineSlope, trendlineYIntercept)
	}
}
