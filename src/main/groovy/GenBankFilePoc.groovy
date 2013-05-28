import groovy.time.TimeCategory
import groovy.time.TimeDuration
import org.apache.commons.io.IOUtils
import org.apache.commons.net.ftp.FTP
import org.apache.commons.net.ftp.FTPClient

import java.util.zip.GZIPInputStream

/**
 * Created with IntelliJ IDEA.
 * User: Ryan
 * Date: 5/25/13
 * Time: 7:23 PM
 * To change this template use File | Settings | File Templates.
 */
class GenBankFilePoc {

    public static void main(String... args) {
        def timeStart = new Date()

        FTPClient ftp = new FTPClient();
        ftp.connect("ftp.ncbi.nih.gov");
        ftp.login("anonymous", "");
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        def copyTo = new File("copyTo.seq")

        def is = new BufferedInputStream(new GZIPInputStream(ftp.retrieveFileStream("/genbank/gbbct1.seq.gz")));
        try {
            IOUtils.copy(is, new FileOutputStream(copyTo))
        } finally {
            is.close();
            ftp.disconnect();

            def timeStop = new Date()
            TimeDuration duration = TimeCategory.minus(timeStop, timeStart)
            println "Downloading the file and copying to \"copyTo.seq\" took: ${duration}"
        }
    }
}
