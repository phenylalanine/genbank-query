package webapp

import groovy.lang.Closure;

class Organism {

    Integer organismId

    Boolean completeGenome = false
    String scientificName
    String taxonomyId
    //A map from taxonomy ranks to values
    Map<String, String> taxonomy

    static constraints = {
        organismId unique: true, blank: false
        completeGenome blank: false
        scientificName nullable: true
        taxonomyId nullable: true
        //for now, we dont require taxonomy, since it is a pain to get at the data
        taxonomy nullable: true
    }
	
	boolean isSimilarTo(Organism otherOrganism, Closure comparatorClos) {
		return comparatorClos(this, otherOrganism)
	}
}
