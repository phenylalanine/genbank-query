package edu.pdx.cs.data

import org.biojavax.bio.seq.RichSequence
import org.biojavax.bio.seq.RichSequenceIterator
import org.biojavax.bio.seq.SimpleRichSequence
import org.junit.Ignore
import org.junit.Test

import static org.junit.Assert.assertEquals

/**
 * Created with IntelliJ IDEA.
 * User: Ryan
 * Date: 7/27/13
 * Time: 10:23 AM
 * To change this template use File | Settings | File Templates.
 */
class BioSQLGORMTests {

    @Ignore //this is a long running test, we dont need to run it with every build
    @Test
    void testBioEntryCRUD() {
        //Read in the test sequence file
        InputStream is = new MaybeCompressedInputStream(new FileInputStream("sequences/gbbct103.seq.gz"))
        BufferedReader br = new BufferedReader(new InputStreamReader(is))
        RichSequenceIterator sequences = RichSequence.IOTools.readGenbankDNA(br, null)

        //persist every RichSequence
        while (sequences.hasNext()) {
            sequences.nextRichSequence().save(flush: true)
        }

        //make sure we end up with the # of RichSequences that we think we should have
        assertEquals(SimpleRichSequence.findAll().size(), 373)
    }
}
