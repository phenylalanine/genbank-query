import gov.nih.nlm.ncbi.www.soap.eutils.*

/**
 * Created with IntelliJ IDEA.
 * User: Ryan
 * Date: 5/17/13
 * Time: 5:23 PM
 * To change this template use File | Settings | File Templates.
 */
class GenBankQueryPOC {

    public static void main(String... args){
        String[] ids = { "" };
        String fetchIds = "";
        // STEP #1: search in PubMed for "cat"
        //
        try
        {
            EUtilsServiceStub service = new EUtilsServiceStub();
            // call NCBI ESearch utility
            EUtilsServiceStub.ESearchRequest req = new EUtilsServiceStub.ESearchRequest();
            req.setDb("pubmed");
            req.setTerm("cat+AND+pubmed_nuccore[sb]");
            req.setSort("PublicationDate");
            req.setRetMax("5");
            EUtilsServiceStub.ESearchResult res = service.run_eSearch(req);
            // results output
            int N = res.getIdList().getId().length;
            ids[0] = "";
            for (int i = 0; i < N; i++)
            {
                if (i > 0) ids[0] += ",";
                ids[0] += res.getIdList().getId()[i];
            }
            System.out.println("Search in PubMed for \"cat\" returned " + res.getCount() + " hits");
            System.out.println("Search links in nuccore for the first "+N+" UIDs: "+ids[0]);
            System.out.println();
        }
        catch (Exception e) { System.out.println(e.toString()); }
        // STEP #2: get links in nucleotide database (nuccore)
        //
        try
        {
            EUtilsServiceStub service = new EUtilsServiceStub();
            // call NCBI ELink utility
            EUtilsServiceStub.ELinkRequest req = new EUtilsServiceStub.ELinkRequest();
            req.setDb("nuccore");
            req.setDbfrom("pubmed");
            req.setId(ids);
            EUtilsServiceStub.ELinkResult res = service.run_eLink(req);
            for (int i = 0; i < res.getLinkSet()[0].getLinkSetDb()[0].getLink().length; i++)
            {
                if (i > 0) fetchIds += ",";
                fetchIds += res.getLinkSet()[0].getLinkSetDb()[0].getLink()[i].getId().getString();
            }
            System.out.println("ELink returned the following UIDs from nuccore: " + fetchIds);
            System.out.println();

        }
        catch (Exception e) { System.out.println(e.toString()); }
        // STEP #3: fetch records from nuccore
        //
        try
        {
            EFetchSequenceServiceStub service = new EFetchSequenceServiceStub();
            // call NCBI EFetch utility
            EFetchSequenceServiceStub.EFetchRequest req = new EFetchSequenceServiceStub.EFetchRequest();
            req.setDb("nuccore");
            req.setId(fetchIds);
            EFetchSequenceServiceStub.EFetchResult res = service.run_eFetch(req);
            // results output
            for (int i = 0; i < res.getGBSet().getGBSetSequence().length; i++)
            {
                EFetchSequenceServiceStub.GBSeq_type0 obj = res.getGBSet().getGBSetSequence()[i].getGBSeq();
                System.out.println("Organism: " + obj.getGBSeq_organism());
                System.out.println("Locus: " + obj.getGBSeq_locus());
                System.out.println("Definition: " + obj.getGBSeq_definition());
                System.out.println("------------------------------------------");
            }
        }
        catch (Exception e) { System.out.println(e.toString()); }
    }
}
