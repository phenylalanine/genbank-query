package webapp

class UploadSequenceController {

    def index() {
        render(view: "UploadForm")
    }

    def upload() {
        def organismName = params.get("userOrganism")
        def userSeqFile = request.getFile('userSequenceFile')

        if (userSeqFile == null) {
            response.sendError(400, "Bad Request: userSequenceFile not found")
        }
        if (organismName == null) {
            response.sendError(400, "Bad Request: organismName param not found")
        }

        // TODO: Perform analysis on userSeqFile.inputStream

        response.sendError(200, "Done")     // TODO: Change to response.redirect
    }
}
