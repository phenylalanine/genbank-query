package webapp

import edu.pdx.cs.data.GenBankFTPClient

class AdminController {

    def index() {

        def client = new GenBankFTPClient(GenBankFTPClient.GENBANK_FTP_URL)
        def genbankVersion = client.getCurrentGenBankReleaseVersion()
        def syncedVersion = grailsApplication.config.genbank.version

        render(view: "adminPage", model:[genbankVersion:genbankVersion, syncedVersion:syncedVersion])

    }
}
