package webapp

import groovy.lang.Closure;

class MeanCodonUsage {

    Integer organismId

    // use codon as key, e.g. "ACG"
    // there are 64 codons total
    Map<String, String> distribution

    static constraints = {
        organismId unique: true, blank: false
    }
	
	boolean similarTo(MeanCodonUsage otherMeanCodonUsage, Closure comparatorClos) {
		return comparatorClos(this, otherMeanCodonUsage)
	}
}
