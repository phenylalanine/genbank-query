package edu.pdx.cs.data

import org.apache.commons.logging.LogFactory
import org.biojavax.bio.seq.RichSequence

/**
 * Created with IntelliJ IDEA.
 * User: Laura
 * Date: 7/6/13
 * Time: 10:04 AM
 * To change this template use File | Settings | File Templates.
 */
class GCPercentageProcessor implements Processor<String> {

    private static final log = LogFactory.getLog(this)

    @Override
    String process(RichSequence richSequence) {
        def sequence = richSequence.seqString()
        def g = sequence.count('g')
        def c = sequence.count('c')
        def num = new BigDecimal(g + c)
        def denom = new BigDecimal(sequence.length())
        def scale = 10

        def temp = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
        def gc = temp.movePointRight(2).toString() // multiplying by 100 added 2 0s at the end, implying more
        // exactness than was there

        return gc
    }
}
