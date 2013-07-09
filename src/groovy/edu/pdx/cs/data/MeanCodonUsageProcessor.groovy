package edu.pdx.cs.data

import org.biojavax.bio.seq.RichSequence
import webapp.MeanCodonUsage

import java.math.RoundingMode

/**
 * Created with IntelliJ IDEA.
 * User: Laura
 * Date: 7/3/13
 * Time: 12:56 PM
 * To change this template use File | Settings | File Templates.
 */
class MeanCodonUsageProcessor implements Processor {
    @Override
    void process(RichSequence richSequence) {
        def organismId = Integer.valueOf(richSequence.identifier)
        def Map<String, String> distribution = [:]

        def String tempStr
        def String sequence = richSequence.seqString()

        // map to hold counts
        def counts = [
                TTT:0, TTC:0, TTA:0, TTG:0,
                TCT:0, TCC:0, TCA:0, TCG:0,
                TAT:0, TAC:0, TAA:0, TAG:0,
                TGT:0, TGC:0, TGA:0, TGG:0,

                CTT:0, CTC:0, CTA:0, CTG:0,
                CCT:0, CCC:0, CCA:0, CCG:0,
                CAT:0, CAC:0, CAA:0, CAG:0,
                CGT:0, CGC:0, CGA:0, CGG:0,

                ATT:0, ATC:0, ATA:0, ATG:0,
                ACT:0, ACC:0, ACA:0, ACG:0,
                AAT:0, AAC:0, AAA:0, AAG:0,
                AGT:0, AGC:0, AGA:0, AGG:0,

                GTT:0, GTC:0, GTA:0, GTG:0,
                GCT:0, GCC:0, GCA:0, GCG:0,
                GAT:0, GAC:0, GAA:0, GAG:0,
                GGT:0, GGC:0, GGA:0, GGG:0
        ]

        def i = 0
        def len = sequence.length()

        // count codons
        // this will have problems if fed something with letters other than A,C,G,T
        while (i+3 < len) {
            tempStr = sequence.substring(i, i+3)
            counts[tempStr] += 1
            i += 3
        }

        def sum
        def BigDecimal num
        def BigDecimal denom
        def BigDecimal result
        def scale = 10
        // now the math...

        for (value in BioConstants.aminoAcids.values()) {
            if (value.size() == 1) {
                if (counts[value[0]] == 0) {
                    distribution[value[0]] = '0'
                }
                else{
                    distribution[value[0]] = '1'
                }
            }
            else {
                sum = 0

                for (str in value) {
                    sum += counts[str]
                }

                if (sum == 0)
                {
                    for (str in value) {
                        distribution[str] = '0'
                    }
                }
                else {
                    denom = new BigDecimal(sum)

                    for (str in value) {
                        num = new BigDecimal(counts[str])
                        result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
                        distribution[str] = result.toString()
                    }
                }
            }
        }

        // save to domain class

        try {
            new MeanCodonUsage(
                organismId: organismId,
                distribution: distribution
            ).save(flush:true)
        } catch (Exception e) {
            // TODO: log this
        }
    }
}
