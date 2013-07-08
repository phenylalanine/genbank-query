package webapp

import edu.pdx.cs.data.ProcessingPipeline
import edu.pdx.cs.data.Processor
import org.biojava.bio.BioException
import org.biojavax.SimpleNamespace
import org.biojavax.bio.BioEntry
import org.biojavax.bio.seq.RichSequenceIterator
import org.biojavax.bio.seq.RichSequence
import org.biojavax.bio.seq.io.SimpleRichSequenceBuilder
import org.junit.Test

/**
 * Created with IntelliJ IDEA.
 * User: katya
 * Date: 7/7/13
 * Time: 3:39 PM
 * To change this template use File | Settings | File Templates.
 */

class ProcessingPipelineTests {


    class DummyProcessor implements Processor {

            String name

            DummyProcessor (String name){
                this.name = name
            }

            void process(RichSequence richSequence) {
                println name + " executes " + richSequence.name
            }
    }

    class DummyRichSequenceIterator implements RichSequenceIterator {

        Iterator<RichSequence> richSequences

        DummyRichSequenceIterator(Iterator<RichSequence> seqlist) {
            this.richSequences = seqlist
        }

        RichSequence nextRichSequence() throws NoSuchElementException, BioException {
            return richSequences.next()
        }

        boolean hasNext() {
            return richSequences.hasNext()
        }

        BioEntry nextBioEntry() throws NoSuchElementException, BioException {
            return null  //To change body of implemented methods use File | Settings | File Templates.
        }

        org.biojava.bio.seq.Sequence nextSequence() throws NoSuchElementException, BioException {
            return null  //To change body of implemented methods use File | Settings | File Templates.
        }
    }

    @Test
    void testPipelineProcessor(){

        // list of dummy processors
        def processor1 = new DummyProcessor("processor1")
        def processor2 = new DummyProcessor("processor2")
        def processor3 = new DummyProcessor("processor3")

        def processors = [processor1, processor2, processor3]


        // create pipeline
        def pipeline = new ProcessingPipeline(processors)

        // create sequence iterator
        def richSequence1 = makeRichSequence("sequence1")
        def richSequence2 = makeRichSequence("sequence2")
        def richSequence3 = makeRichSequence("sequence3")

        def seqlist = [richSequence1, richSequence2, richSequence3]
        def sequenceIterator = new DummyRichSequenceIterator(seqlist.iterator())


        // push data through pipeline and record the output
        def buf = new ByteArrayOutputStream()
        def newOut = new PrintStream(buf)

        System.out = newOut
        pipeline.process(sequenceIterator)
        def output = buf.toString()

        // make sure all sequences were processed
        assert !sequenceIterator.hasNext()

        // check the number of process() calls
        assert output.split("\n").size() == processors.size() * seqlist.size()

        // check for execution of each processor on each sequence
        assert output.contains('processor1 executes sequence1')
        assert output.contains('processor1 executes sequence2')
        assert output.contains('processor1 executes sequence3')
        assert output.contains('processor2 executes sequence1')
        assert output.contains('processor2 executes sequence2')
        assert output.contains('processor2 executes sequence3')
        assert output.contains('processor3 executes sequence1')
        assert output.contains('processor3 executes sequence2')
        assert output.contains('processor3 executes sequence3')
    }

    static RichSequence makeRichSequence(String name) {
        def builder = new SimpleRichSequenceBuilder()
        builder.name = name
        builder.namespace = new SimpleNamespace("dummy namespace")
        builder.accession = "123"
        return builder.makeRichSequence()
    }
}