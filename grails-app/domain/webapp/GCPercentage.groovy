package webapp

import groovy.lang.Closure;

class GCPercentage {
    Integer organismId
    String gcPercentage

    static constraints = {
        organismId unique: true, blank: false
        gcPercentage blank: false
    }
	
	boolean isSimilarTo(GCPercentage otherGCPercentage, Closure comparatorClos) {
		return comparatorClos(this, otherGCPercentage)
	}
}
