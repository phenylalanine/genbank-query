package webapp

import org.apache.commons.logging.LogFactory

class Organism {
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
	
	// (JGM) This is unfinished.
	@Override
	public String toString() {
		String s = ""

		s += (organismId + "," + taxonomyId + "," + scientificName)
		s += ("," + gcPercentage)
		for (e in rscuCodonDistribution)
			 s += ("," + e.getKey() + "," + e.getValue())
		for (e in mcufCodonDistribution)
			s += ("," + e.getKey() + "," + e.getValue())
		//		} catch (IOException ex){
		//			log.warn("Error writing to file: Organism.temp", e)
		//		} finally {
		//			try {writer.close();} catch (Exception ex) {
		//				log.warn("Error closing file: Organism.temp", e)
		//			}
		//		}
		return s
	}
}
