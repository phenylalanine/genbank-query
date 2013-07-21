package webapp

import edu.pdx.cs.data.OrganismProcessor
import org.apache.commons.io.IOUtils
import org.biojava.bio.seq.DNATools
import org.biojavax.bio.seq.RichSequence
import org.springframework.web.multipart.MultipartFile

class MainController {
    static layout = "main"

    def index() {
        def dataMap
        def showUpload

        // Check number of params
        if (params.size() > 1) {
            showUpload = "hide"
        }
        else {
            showUpload = "show"
        }

        dataMap = [upload: showUpload]

        render(view: "index", model: dataMap)
    }

    /*
    Expected parameters
    userOrganism: string
    userSequenceFile: File
     */
    def upload() {
        def organismName = params.get("userOrganism")
        def MultipartFile userSeqFile = request.getFile("userSequenceFile")

        if (userSeqFile == null) {
            response.sendError(400, "Bad Request: userSequenceFile not found")
            return
        }
        if (organismName == null) {
            response.sendError(400, "Bad Request: organismName param not found")
            return
        }

        String sequenceString = IOUtils.toString(userSeqFile.inputStream, "UTF-8")
        org.biojava.bio.seq.Sequence sequence = DNATools.createDNASequence(sequenceString, organismName)
        RichSequence richSequence = RichSequence.Tools.enrich(sequence)

        Organism organism = new OrganismProcessor(persist: false).process(richSequence)

        // TODO: add data from user selected domain class entry to RSCU results
        // TODO: turn the codon distrobution into MCUF by comparing with stuff in domain class
        // TODO: use results in a view

        response.sendError(200, "Done")     // TODO: Change to response.redirect
    }
}
