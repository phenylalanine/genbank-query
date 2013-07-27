package edu.pdx.cs.data


import org.junit.Test

import java.util.zip.GZIPOutputStream
import org.bouncycastle.apache.bzip2.CBZip2OutputStream

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue

/**
 * Created with IntelliJ IDEA.
 * User: Ryan
 * Date: 7/26/13
 * Time: 9:27 PM
 * To change this template use File | Settings | File Templates.
 */
class MaybeCompressedInputStreamTest {

    @Test
    void testCanDealWithNormalInputStream() {
        def readIn = "Test String"
        def is = new ByteArrayInputStream(readIn.bytes)
        is = new MaybeCompressedInputStream(is)

        def readBack = new byte[11]
        is.read(readBack)
        readBack = new String(readBack)

        assertEquals("Test String", readBack)
    }

    @Test
    void testCanDealWithGZipInputStream() {
        //create a gzip file to test with
        def tempGZipFilePath = "${System.getProperty('java.io.tmpdir')}/test.gz"
        def tempGZipFile = new File(tempGZipFilePath)
        def readIn = "Test String"

        def gzos = new GZIPOutputStream(new FileOutputStream(tempGZipFile))

        gzos.withWriter {
            it.write(readIn)
        }

        def readBack = new byte[11]
        InputStream gzis = new MaybeCompressedInputStream(new FileInputStream(tempGZipFilePath))

        gzis.read(readBack)
        gzis.close()

        assertEquals(readIn, new String(readBack))

        //make sure we delete the temp file
        assertTrue(tempGZipFile.delete())
    }

    @Test
    void testCanDealWithBZipInputStream(){
        //create a bzip file to test with
        def tempBZipFilePath = "${System.getProperty('java.io.tmpdir')}/test.bz2"
        def tempBZipFile = new File(tempBZipFilePath)
        def readIn = "Test String"

        def bzos = new CBZip2OutputStream(new FileOutputStream(tempBZipFile))

        bzos.withWriter {
            it.write(readIn)
        }

        def readBack = new byte[11]
        InputStream bzis = new MaybeCompressedInputStream(new FileInputStream(tempBZipFilePath))

        bzis.read(readBack)
        bzis.close()

        assertEquals(readIn, new String(readBack))

        //make sure we delete the temp file
        assertTrue(tempBZipFile.delete())
    }
}