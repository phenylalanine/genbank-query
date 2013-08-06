/**
 * Created with IntelliJ IDEA.
 * User: Thang
 * Date: 7/14/13
 * Time: 12:57 PM
 * To change this template use File | Settings | File Templates.
 */
var syncedVersion;
var imgVersion;
var genbankVersion;

window.onload = Init;

function Init() {
    imgVersion = document.getElementById("imgVersion");
    syncedVersion = document.getElementById("syncedVersionValue").value;
    genbankVersion = document.getElementById("genbankVersionValue").value;

    if (syncedVersion === genbankVersion) {
        imgVersion.src = "/webapp/static/images/lPg.gif";
        imgVersion.title = "Updated";
    }
    else {
        imgVersion.src = "/webapp/static/images/lPr.gif";
        imgVersion.title = "Outdated";
    }
    document.getElementById("syncedVersion").innerHTML = "<li>Synced version: " + syncedVersion + "</li>";
    document.getElementById("genbankVersion").innerHTML = "<li>Genbank version: " + genbankVersion + "</li>";

    document.getElementById("syncGenbank").onclick =
        function() {
            if (confirm("Are you sure you want to re-sync the genbank? The re-sync may take a long time to complete.")) {
                if (genbankVersion === syncedVersion)
                    alert("The genbank is already updated");
                else {
                    // processGenBank()
                    alert("The genbank is updating");
                    alert("Complete!");
                    syncedVersion = genbankVersion;
                    document.getElementById("syncedVersion").innerHTML = "<li>Synced version: " + syncedVersion + "</li>" ;
                    imgVersion.src = "/webapp/static/images/lPg.gif";
                    imgVersion.title = "Updated";
                }
            } else {
                // Do nothing!
            }
            return false;
        }
}

