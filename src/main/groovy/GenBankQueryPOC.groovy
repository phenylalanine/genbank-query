import gov.nih.nlm.ncbi.www.soap.eutils.*

/**
 * Created with IntelliJ IDEA.
 * User: Ryan
 * Date: 5/17/13
 * Time: 5:23 PM
 * To change this template use File | Settings | File Templates.
 */
class GenBankQueryPOC {

    public static void main(String... args) {
        String[] ids = { "" }

        // STEP #1: search in PubMed for "cat"
        def service = new EUtilsServiceStub()
        // call NCBI ESearch utility
        def req = new EUtilsServiceStub.ESearchRequest()
        req.setDb("pubmed")
        req.setTerm("cat+AND+pubmed_nuccore[sb]")
        req.setSort("PublicationDate")
        req.setRetMax("5")
        def res = service.run_eSearch(req)
        // results output
        def numIds = res.getIdList().getId().length
        ids[0] = res.getIdList().getId().join(",")

        println "Search in PubMed for \"cat\" returned " + res.getCount() + " hits"
        println "Search links in nuccore for the first " + numIds + " UIDs: " + ids[0] + "\n"

        // STEP #2: get links in nucleotide database (nuccore)
        // call NCBI ELink utility
        req = new EUtilsServiceStub.ELinkRequest()
        req.setDb("nuccore")
        req.setDbfrom("pubmed")
        req.setId(ids)
        res = service.run_eLink(req)

        def fetchIds = res.getLinkSet()[0].getLinkSetDb()[0].getLink().collect{link -> link.getId().getString()}.join(",")

        println "ELink returned the following UIDs from nuccore: " + fetchIds + "\n"

        // STEP #3: fetch records from nuccore
        service = new EFetchSequenceServiceStub()
        // call NCBI EFetch utility
        req = new EFetchSequenceServiceStub.EFetchRequest()
        req.setDb("nuccore")
        req.setId(fetchIds)
        res = service.run_eFetch(req)
        // results output
        res.getGBSet().getGBSetSequence().each { gb ->
            println "Organism: " + gb.GBSeq.getGBSeq_organism()
            println "Locus: " + gb.GBSeq.getGBSeq_locus()
            println "Definition: " + gb.GBSeq.getGBSeq_definition()
            println "------------------------------------------"
        }
    }
}