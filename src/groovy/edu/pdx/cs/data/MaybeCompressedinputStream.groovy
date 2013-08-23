package edu.pdx.cs.data

import org.bouncycastle.apache.bzip2.CBZip2InputStream
import java.util.zip.GZIPInputStream
import java.util.zip.ZipException

/**
 * This is a convenience class for working with different
 * types of {@link InputStream}s. This will automatically
 * handle GZIP or BZIP compressed files.
 */
class MaybeCompressedInputStream extends InputStream {

    private InputStream maybeCompressed

    MaybeCompressedInputStream(InputStream maybeCompressed) {
        this.maybeCompressed = determineCompression(maybeCompressed)
    }

    private InputStream determineCompression(InputStream maybeCompressed) {
        maybeCompressed = new BufferedInputStream(maybeCompressed)
        maybeCompressed.mark(2048)

        try { //maybe its gzipped!
            return new GZIPInputStream(maybeCompressed)
        } catch (ZipException ze) {
            maybeCompressed.reset() //nope!
        }

        try { //could it be bzipped?
            return new CBZip2InputStream(maybeCompressed)
        } catch (IOException ioe){
            maybeCompressed.reset() //nope!
        }

        //damn just a plain old boring file, your tax dollars at work
        return maybeCompressed
    }

    @Override
    int read(byte[] b) throws IOException {
        return maybeCompressed.read(b)
    }

    @Override
    int read(byte[] b, int off, int len) throws IOException {
        return maybeCompressed.read(b, off, len)
    }

    @Override
    long skip(long n) throws IOException {
        return maybeCompressed.skip(n)
    }

    @Override
    int available() throws IOException {
        return maybeCompressed.available()
    }

    @Override
    void close() throws IOException {
        maybeCompressed.close()
    }

    @Override
    synchronized void mark(int readlimit) {
        maybeCompressed.mark(readlimit)
    }

    @Override
    synchronized void reset() throws IOException {
        maybeCompressed.reset()
    }

    @Override
    boolean markSupported() {
        return maybeCompressed.markSupported()
    }

    @Override
    int read() throws IOException {
        return maybeCompressed.read();
    }

    @Override
    BufferedReader newReader() {
        return maybeCompressed.newReader()
    }
}
