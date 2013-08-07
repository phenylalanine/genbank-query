package webapp

class AdminController {
    def genBankSyncService

    def index() {

        def genbankVersion = genBankSyncService.currentGenBankReleaseVersion
        def syncedVersion = grailsApplication.config.genbank.version

        render(view: "adminPage", model:[genbankVersion:genbankVersion, syncedVersion:syncedVersion])

    }
}
