package webapp

import edu.pdx.cs.data.GenBankClient

class AdminController {

    def index() {

        def client = new GenBankClient(GenBankClient.GENBANK_FTP_URL)
        def genbankVersion = client.getCurrentGenBankReleaseVersion()
        def syncedVersion = grailsApplication.config.genbank.version

        render(view: "adminPage", model:[genbankVersion:genbankVersion, syncedVersion:syncedVersion])

    }
}
