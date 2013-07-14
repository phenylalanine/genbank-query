package edu.pdx.cs.data

import org.biojavax.bio.seq.RichSequence

/**
 * Created with IntelliJ IDEA.
 * User: Laura
 * Date: 7/11/13
 * Time: 5:17 PM
 * To change this template use File | Settings | File Templates.
 */
class GCPercentageUProcessor implements UProcessor {
    @Override
    ProcessedUploadedSequence process(RichSequence richSequence) {
        def BigDecimal gc
        def sequence = richSequence.seqString()
        def g = sequence.count('g')
        def c = sequence.count('c')
        def num = new BigDecimal(g+c)
        def denom = new BigDecimal(sequence.length())
        def scale = 10

        def temp = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
        gc = temp.movePointRight(2)

        return new ProcessedUploadedSequence(
                analysis: "GC Percentage",
                columns: [['BigDecimal', 'GC Percentage']],
                analysisData: [[gc]]
        )
    }
}
