package edu.pdx.cs.data

import org.apache.commons.logging.LogFactory
import org.biojavax.bio.seq.RichSequence
import webapp.Organism

/**
 * Created with IntelliJ IDEA.
 * User: Laura
 * Date: 7/3/13
 * Time: 12:56 PM
 * To change this template use File | Settings | File Templates.
 */
class MeanCodonUsageProcessor implements Processor<Map<String, String>> {

    private static final log = LogFactory.getLog(this)

    @Override
    Map<String, String> process(RichSequence richSequence) {
        def Map<String, String> distribution = [:]

        def String tempStr
        def String sequence = richSequence.seqString()

        // take out possible extra letters
        sequence = BioConstants.removeExtra(sequence)

        // map to hold counts
        def counts = [
                ttt: 0, ttc: 0, tta: 0, ttg: 0,
                tct: 0, tcc: 0, tca: 0, tcg: 0,
                tat: 0, tac: 0, taa: 0, tag: 0,
                tgt: 0, tgc: 0, tga: 0, tgg: 0,

                ctt: 0, ctc: 0, cta: 0, ctg: 0,
                cct: 0, ccc: 0, cca: 0, ccg: 0,
                cat: 0, cac: 0, caa: 0, cag: 0,
                cgt: 0, cgc: 0, cga: 0, cgg: 0,

                att: 0, atc: 0, ata: 0, atg: 0,
                act: 0, acc: 0, aca: 0, acg: 0,
                aat: 0, aac: 0, aaa: 0, aag: 0,
                agt: 0, agc: 0, aga: 0, agg: 0,

                gtt: 0, gtc: 0, gta: 0, gtg: 0,
                gct: 0, gcc: 0, gca: 0, gcg: 0,
                gat: 0, gac: 0, gaa: 0, gag: 0,
                ggt: 0, ggc: 0, gga: 0, ggg: 0
        ]

        def i = 0
        def len = sequence.length()

        // count codons
        // this will have problems if fed something with letters other than A,C,G,T
        while (i + 3 < len) {
            tempStr = sequence.substring(i, i + 3)
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
                } else {
                    distribution[value[0]] = '1'
                }
            } else {
                sum = 0

                for (str in value) {
                    sum += counts[str]
                }

                if (sum == 0) {
                    for (str in value) {
                        distribution[str] = '0'
                    }
                } else {
                    denom = new BigDecimal(sum)

                    for (str in value) {
                        num = new BigDecimal(counts[str])
                        result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
                        distribution[str] = result.toString()
                    }
                }
            }
        }

        return distribution
    }
}