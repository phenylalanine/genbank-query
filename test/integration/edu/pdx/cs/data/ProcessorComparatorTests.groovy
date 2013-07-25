/**
 * Created with Eclipse!
 * User: Jim Miller (JGM)
 * Date: 7/13/13
 *
 * Integration tests for domain classes' similarTo() comparators using closures.
 */

package edu.pdx.cs.data

import org.junit.Test
import webapp.Organism

import java.math.MathContext

class ProcessorComparatorTests {
    def bigDecimalScale = 10
    def one = new BigDecimal(1)
    def two = new BigDecimal(2)
    def three = new BigDecimal(3)
    def four = new BigDecimal(4)
    def six = new BigDecimal(6)
    def eight = new BigDecimal(8)

    @Test
    void testSimilarTo() {
        def organismOne = new Organism()
        organismOne.organismId = 1234
        organismOne.rscuCodonDistribution = [
                (BioConstants.ACT): six.divide(four, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString(),
                (BioConstants.GGA): three.divide(four, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        ]
        organismOne.mcufCodonDistribution = [
                (BioConstants.ACA): six.divide(four, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString(),
                (BioConstants.CCA): three.divide(four, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        ]
        organismOne.gcPercentage = "42"

        def organismTwo = new Organism()
        organismTwo.organismId = 5678
        organismTwo.rscuCodonDistribution = [
                (BioConstants.ACT): six.divide(eight, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString(),
                (BioConstants.GGA): three.toString()
        ]
        organismTwo.mcufCodonDistribution = [
                (BioConstants.ACA): six.divide(four, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString(),
                (BioConstants.CCA): three.divide(four, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        ]

        // This test closure calls two RSCU objects "similar" if their RSCU values for codon ACT have < 1 difference.
        def clos = { Organism currentRSCU, Organism otherRSCU ->
            (Math.abs(currentRSCU.rscuCodonDistribution.get(BioConstants.ACT)
                    .toBigDecimal()
                    .subtract(otherRSCU.rscuCodonDistribution.get(BioConstants.ACT)
                    .toBigDecimal(), MathContext.UNLIMITED)) < 1)
        }

        assert organismOne.isSimilarTo(organismTwo, clos)
        // Similar closures can easily be defined for testing similarity of the other domain classes.
    }
}
