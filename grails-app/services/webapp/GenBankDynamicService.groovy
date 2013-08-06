package webapp

import org.biojavax.bio.taxa.NCBITaxon
import org.biojavax.bio.taxa.SimpleNCBITaxon

class GenBankDynamicService {

    NCBITaxon getTaxonomyForId(int id) {
        try {
            def xml = new XmlSlurper()
                    .parse(
                    "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=taxonomy&id=${id}&retmode=xml"
            )

            NCBITaxon ncbiTaxon = new SimpleNCBITaxon(id)

            ncbiTaxon.addName("scientific name", xml.Taxon.ScientificName.text())

            xml.Taxon.LineageEx.Taxon.each { taxon ->
                ncbiTaxon.addName(taxon.Rank.text(), taxon.ScientificName.text())
            }

            ncbiTaxon.geneticCode = xml.Taxon.GeneticCode.GCId.text() as Integer
            ncbiTaxon.mitoGeneticCode = xml.Taxon.MitoGeneticCode.MGCId.text() as Integer
            ncbiTaxon.parentNCBITaxID = xml.Taxon.ParentTaxId.text() as Integer

            return ncbiTaxon
        } catch (Exception e) {
            log.warn("Error retrieving taxonomy information for id: " + id, e)
            return null
        }
    }

}
