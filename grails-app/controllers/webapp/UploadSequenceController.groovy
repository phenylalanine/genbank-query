package webapp

import edu.pdx.cs.data.OrganismProcessor
import org.apache.commons.io.IOUtils
import org.biojava.bio.seq.DNATools
import org.biojavax.bio.seq.RichSequence
import org.biojava.bio.seq.Sequence
import org.springframework.web.multipart.MultipartFile

class UploadSequenceController {

    def index() {
        render(view: "UploadForm")
    }

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
        Sequence sequence = DNATools.createDNASequence(sequenceString, organismName)
        RichSequence richSequence = RichSequence.Tools.enrich(sequence)

        Organism organism = new OrganismProcessor(persist: false).process(richSequence)

//        // code to test the mcufFileBuilder
//        def File tempFile = MeanCodonUsageUProcessor.mcufFileBuilder(organism)
//
//        if (tempFile) {
//            response.setContentType("application/octet-stream")
//            response.setHeader("Content-disposition", "attachment;filename=${tempFile.getName()}")
//            tempFile.withInputStream { response.outputStream << it }
//        } else {
//            // do nothing
//        }
//        tempFile.delete()

        // TODO: add data from user selected domain class entry to RSCU results
        // TODO: turn the codon distrobution into MCUF by comparing with stuff in domain class
        // TODO: use results in a view

        response.sendError(200, "Done")     // TODO: Change to response.redirect
    }
}