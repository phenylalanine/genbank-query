package webapp

class Organism {
    //genbankId if the sequence has one
    String organismId
    String taxonomyId
    String scientificName

    Boolean completeGenome = false

    //A map from taxonomy ranks to values
    Map<String, String> taxonomy

    String gcPercentage
    Map<String, String> rscuCodonDistribution
    Map<String, String> mcufCodonDistribution

    static constraints = {
        organismId nullable: true, unique: true
        scientificName nullable: false
        taxonomyId nullable: true
        taxonomy nullable: true
        completeGenome blank: false

        gcPercentage nullable: false
        rscuCodonDistribution nullable: false
        mcufCodonDistribution nullable: false
    }

    boolean isSimilarTo(Organism otherOrganism, Closure comparatorClos) {
        return comparatorClos(this, otherOrganism)
    }
}