package webapp

import org.apache.commons.logging.LogFactory

class Organism {
    static searchable = [only: ['scientificName']]
    private static final log = LogFactory.getLog(this)
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

	@Override
	public String toString() {
		String s = ""

		// (JGM) Entire maps separated by semicolons rather than commas.
		// This is lazy string build coding.
		s += (organismId.toString() + "," + scientificName + "," + taxonomyId + ",")
        s += (completeGenome.toString() + "," + gcPercentage + ",")
        for (e in taxonomy)
            s += (e.getKey() + "," + e.getValue() + ",")
        s -= ","
        s += ";"
        for (e in rscuCodonDistribution)
            s += (e.getKey() + "," + e.getValue() + ",")
        s -= ","
        s += ";"
        for (e in mcufCodonDistribution)
            s += (e.getKey() + "," + e.getValue() + ",")
        s -= ","
		return s
	}
}