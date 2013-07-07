package edu.pdx.cs.data
import org.biojavax.bio.seq.RichSequenceIterator
import org.biojavax.bio.seq.RichSequence

/**
 * Created with IntelliJ IDEA.
 * User: katya
 * Date: 7/6/13
 * Time: 7:15 PM
 * To change this template use File | Settings | File Templates.
 */
class ProcessingPipeline {

    List<Processor> processors

    ProcessingPipeline(List<Processor> processors)
    {
        this.processors = processors
    }


    void process(RichSequenceIterator richSequences){

        RichSequence sequence
        while (richSequences.hasNext()) {
            sequence = richSequences.nextRichSequence()
            this.processors.each { processor->
                processor.process(sequence)
            }
        }
    }
}
