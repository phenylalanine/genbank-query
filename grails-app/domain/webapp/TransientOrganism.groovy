package webapp

class TransientOrganism extends Organism {
    //set mapWith to "none" so we dont get a table created for this
    static mapWith = "none"

    //This never gets saved so its pointless to declare the id unique
    static constraints = {
        organismId nullable: true, unique: false
        scientificName nullable: false
        taxonomyId nullable: true
        taxonomy nullable: true
        completeGenome blank: false

        gcPercentage nullable: false
        rscuCodonDistribution nullable: false
        mcufCodonDistribution nullable: false
    }
    /**
     * Overriding these here lets us
     * take advantage of the same
     * validation as we have in the
     * normal Organism class while
     * guaranteeing we cant be saved
     */
    @Override
    public Organism save(){
        return save([:])
    }

    @Override
    public Organism save(Map params){
        validate()
        return this
    }
}