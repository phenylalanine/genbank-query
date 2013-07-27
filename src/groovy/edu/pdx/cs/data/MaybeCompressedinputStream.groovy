package edu.pdx.cs.data
/**
 * Created with IntelliJ IDEA.
 * User: Ryan
 * Date: 7/26/13
 * Time: 8:12 AM
 * To change this template use File | Settings | File Templates.
 */
class MaybeCompressedinputStream extends InputStream {

    InputStream maybeCompressed

    MaybeCompressedinputStream(InputStream maybeCompressed) {

    }

    @Override
    int read(byte[] b) throws IOException {
        return maybeCompressed.read()
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
}
