package webapp

import groovy.io.GroovyPrintStream

import java.net.URLEncoder

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

	List<Integer> getIdsForScientificName(String scientificName) {
		try {
			def url = "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?db=taxonomy&term=*" +
				URLEncoder.encode(scientificName, "UTF-8") + "*&field=scientific+name"
			def xml = new XmlSlurper().parse(url)
			
//			def xmlPrintStream = new GroovyPrintStream("test3.xml")
//			xmlPrintStream.println(xml)
//			xmlPrintStream.close()
//			return [1]

			def ids = []
			xml.IdList.Id.each {id ->
				ids += id.text().toInteger()
			}
			
			return ids
		} catch (Exception e) {
			log.warn("Error retrieving ID information for scientific name: " + scientificName, e)
			return null
		}
	}
	
	Organism getOrganismForId(int id) {
//		try {
//			def xml = new XmlSlurper()
//					.parse(
//					"http://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=taxonomy&id=${id}&retmode=xml"
//			)
//
//			NCBITaxon ncbiTaxon = new SimpleNCBITaxon(id)
//
//			ncbiTaxon.addName("scientific name", xml.Taxon.ScientificName.text())
//
//			xml.Taxon.LineageEx.Taxon.each { taxon ->
//				ncbiTaxon.addName(taxon.Rank.text(), taxon.ScientificName.text())
//			}
//
//			ncbiTaxon.geneticCode = xml.Taxon.GeneticCode.GCId.text() as Integer
//			ncbiTaxon.mitoGeneticCode = xml.Taxon.MitoGeneticCode.MGCId.text() as Integer
//			ncbiTaxon.parentNCBITaxID = xml.Taxon.ParentTaxId.text() as Integer
//
//			return ncbiTaxon
//		} catch (Exception e) {
//			log.warn("Error retrieving organism information for id: " + id, e)
//			return null
//		}
	}
}
