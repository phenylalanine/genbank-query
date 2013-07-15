/**
 * Created with Eclipse!
 * User: Jim Miller (JGM)
 * Date: 6/30/13
 * 
 * Largely copied from MeanCodonUsage.groovy
 */

package webapp

class RSCU {
    Integer organismId
    // Use codon as key, e.g. "ACG".
    // There are 64 codons total.
    Map<String, String> distribution

    static constraints = {
        organismId unique: true, blank: false
    }
	
	boolean isSimilarTo(RSCU otherRSCU, Closure comparatorClos) {
		return comparatorClos(this, otherRSCU)
    }
}