package webapp

import edu.pdx.cs.data.DNAParser
import edu.pdx.cs.data.MaybeCompressedInputStream
import edu.pdx.cs.data.OrganismProcessor
import org.apache.shiro.SecurityUtils
import org.biojavax.bio.seq.RichSequence
import org.biojavax.bio.seq.RichSequenceIterator
import org.springframework.web.multipart.MultipartFile

class AdminController {
    def genBankSyncService
    static layout = "main"

    def index() {

        def genbankVersion = genBankSyncService.currentGenBankReleaseVersion
        def syncedVersion = grailsApplication.config.genbank.version

        if (SecurityUtils.subject.isPermitted("admin")) {
            render(view: "adminPage", model:[genbankVersion:genbankVersion, syncedVersion:syncedVersion])
        }
        else {
            redirect(controller: "auth", action: "index", params: [targetUri: "/admin"])
        }
    }

    /* Copied from MainController.upload
        Ugly hack to be able to quick-search from admin page
        redirect was not working due to request.getFile error
        TODO: make this less hack-y
     */
    def upload() {
        def organismName0 = params.get("userOrganism0")
        def MultipartFile userSeqFile0 = request.getFile("userSequenceFile0")
        def organismName1 = params.get("userOrganism1")
        def MultipartFile userSeqFile1 = request.getFile("userSequenceFile1")
        def opt = params.get("opt")

        RichSequenceIterator rsIterator
        RichSequence richSequence
        def genbankOrganism

        //flash.organisms = []
        flash.put("organisms", [])
        flash.put("opt", opt)

        def genbankOrganismName0 = params.get("genbankOrganism0")
        def genbankOrganismName1 = params.get("genbankOrganism1")

        try {

            if (organismName0 && userSeqFile0) {
                rsIterator = DNAParser.parseDNA(new MaybeCompressedInputStream(userSeqFile0.inputStream), organismName0)
                richSequence = rsIterator.nextRichSequence() // will only get the first one if there are more than one
                flash.get("organisms").push(new OrganismProcessor(persist: false).process(richSequence))
            }
            if (organismName1 && userSeqFile1) {
                rsIterator = DNAParser.parseDNA(new MaybeCompressedInputStream(userSeqFile0.inputStream), organismName0)
                richSequence = rsIterator.nextRichSequence() // will only get the first one if there are more than one
                flash.get("organisms").push(new OrganismProcessor(persist: false).process(richSequence))
            }
            if (genbankOrganismName0) {
                genbankOrganism = Organism.find { scientificName == genbankOrganismName0 }
                if (genbankOrganism) {
                    flash.get("organisms").push(genbankOrganism)
                }
            }
            if (genbankOrganismName1) {
                genbankOrganism = Organism.find { scientificName == genbankOrganismName1 }
                if (genbankOrganism) {
                    flash.get("organisms").push(genbankOrganism)
                }
            }

            if (flash.get("organisms").size() > 2 || flash.get("organisms").size() < 1){
                //response.sendError(400, "Please upload organisms according to the instructions")
                throw new Exception("400: Please upload organisms according to the instructions")
            }

        }
        catch (e) {
            response.sendError(400, e.getMessage())
        }
        if (!response.committed) {
            redirect(controller: "main", action: "index")
        }
    }
}
