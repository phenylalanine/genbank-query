/**
 * Created with Eclipse!
 * User: Jim Miller (JGM)
 * Date: 7/21/13
 * 
 * Compare the RSCU analyses of the uploaded organism against a GenBank organism,
 * producing a best fit trendline's slope and y-intercept.
 */

package edu.pdx.cs.data

import java.math.MathContext
import org.apache.commons.logging.LogFactory
import org.biojavax.bio.seq.RichSequence
import webapp.Organism

class RSCUComparison {
	private static final log = LogFactory.getLog(this)
	private int bigDecimalScale
	private Organism orgUpload, orgGenBank
	String trendlineSlope, trendlineYIntercept
	
	public RSCUComparison(orgUp, orgGB) {
		bigDecimalScale = 10
		orgUpload = orgUp
		orgGenBank = orgGB
		compare()
	}
	
	// (JGM) x is the uploaded organism, y is the GenBank organism.
	// Find the best fit line (linear).
	// http://people.hofstra.edu/stefan_waner/realworld/calctopic1/regression.html
	private void compare() {
		BigDecimal sumProducts = new BigDecimal(0), sumXValues = new BigDecimal(0),
			sumYValues = new BigDecimal(0), sumXSquares = new BigDecimal(0)
		BigDecimal slopeNumerator, slopeDenominator, slope, yIntercept
		BigDecimal distSize = orgUpload.rscuCodonDistribution.size()

		// First, if any codon's RSCU value is -1, in either organism, discard the comparison
		// by returning null.
		List<Map> rscuCodonDistributions = [orgUpload.rscuCodonDistribution, orgGenBank.rscuCodonDistribution]
		for (dist in rscuCodonDistributions)
			for (e in dist)
				if (e.getValue() == "-1") {
					trendlineSlope = null
					trendlineYIntercept = null
					return
				}

		for (e in orgUpload.rscuCodonDistribution.keySet()) {
			sumProducts = sumProducts.add(orgUpload.rscuCodonDistribution.get(e).toBigDecimal()
				.multiply(orgGenBank.rscuCodonDistribution.get(e).toBigDecimal(), MathContext.UNLIMITED),
				MathContext.UNLIMITED)
			sumXValues = sumXValues.add(orgUpload.rscuCodonDistribution.get(e).toBigDecimal(), MathContext.UNLIMITED)
			sumYValues = sumYValues.add(orgGenBank.rscuCodonDistribution.get(e).toBigDecimal(), MathContext.UNLIMITED)
			sumXSquares = sumXSquares.add(orgUpload.rscuCodonDistribution.get(e).toBigDecimal().pow(2,
				MathContext.UNLIMITED), MathContext.UNLIMITED)
		}
		slopeNumerator = distSize.multiply(sumProducts, MathContext.UNLIMITED)
			.subtract(sumXValues.multiply(sumYValues, MathContext.UNLIMITED), MathContext.UNLIMITED)
		slopeDenominator = distSize.multiply(sumXSquares, MathContext.UNLIMITED)
			.subtract(sumXValues.pow(2, MathContext.UNLIMITED), MathContext.UNLIMITED)
		slope = slopeNumerator.divide(slopeDenominator, bigDecimalScale, BigDecimal.ROUND_HALF_UP)
		yIntercept = (sumYValues.subtract(slope.multiply(sumYValues, MathContext.UNLIMITED), MathContext.UNLIMITED))
			.divide(distSize, bigDecimalScale, BigDecimal.ROUND_HALF_UP)
		
		trendlineSlope = slope.toString()
		trendlineYIntercept = yIntercept.toString()
	}
}
