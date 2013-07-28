package edu.pdx.cs.data

import org.apache.commons.logging.LogFactory
import org.biojavax.bio.seq.RichSequence
import webapp.Organism
import webapp.TransientOrganism

/**
 * Created with IntelliJ IDEA.
 * User: Ryan
 * Date: 6/30/13
 * Time: 3:18 PM
 * To change this template use File | Settings | File Templates.
 */
class OrganismProcessor implements Processor<Organism> {

    private static final log = LogFactory.getLog(this)

    private RSCUProcessor rscuProcessor
    private MeanCodonUsageProcessor meanCodonUsageProcessor
    private GCPercentageProcessor gcPercentageProcessor
    private persist = true

    @Override
    Organism process(RichSequence richSequence) {
        def organismId = richSequence.identifier

        def taxon = richSequence.taxon

        def scientificName = taxon ? richSequence.taxon.getNames("scientific name").first() : null
        def taxonomyId = taxon ? richSequence.taxon.NCBITaxID : null

        def rscuCodonDistribution = new RSCUProcessor().process(richSequence)
        def mcufCodonDistribution = new MeanCodonUsageProcessor().process(richSequence)
        def gcPercentage = new GCPercentageProcessor().process(richSequence)

        try {
            if (persist) {
                new Organism(
                        organismId: organismId,
                        scientificName: scientificName,
                        taxonomyId: taxonomyId,
                        rscuCodonDistribution: rscuCodonDistribution,
                        mcufCodonDistribution: mcufCodonDistribution,
                        gcPercentage: gcPercentage
                ).save(flush: true)
            } else {
                new TransientOrganism(
                        organismId: organismId,
                        scientificName: richSequence.name, // gets the name entered by user
                        taxonomyId: taxonomyId,
                        rscuCodonDistribution: rscuCodonDistribution,
                        mcufCodonDistribution: mcufCodonDistribution,
                        gcPercentage: gcPercentage
                ).save()
            }

        } catch (Exception e) {
            log.warn("Error persisting Organism object: ", e)
            return null
        }
    }
}
