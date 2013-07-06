package webapp

class UserSequenceInputController {

    def index() {
        render(view: "UserSequence")
    }

    def upload() {
        InputStream userSeqStream = request.getFile('userSequenceFile').inputStream
        def organismName = params.get("userOrganism")

        // TODO: Perform analysis on userSeqInStream
        // TODO: Return proper response code

        response.sendError(200, "Done")     // TODO: Change to response.redirect
    }
}
