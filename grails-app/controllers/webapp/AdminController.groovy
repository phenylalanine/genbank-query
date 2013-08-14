package webapp

import org.apache.shiro.SecurityUtils

class AdminController {
    def genBankSyncService

    def index() {

        def genbankVersion = genBankSyncService.currentGenBankReleaseVersion
        def syncedVersion = grailsApplication.config.genbank.version

        if (SecurityUtils.subject.isPermitted("admin")) {
            render(view: "adminPage", model:[genbankVersion:genbankVersion, syncedVersion:syncedVersion])
        }
        else {
            response.sendError(403, "Forbidden")
        }
    }
}
