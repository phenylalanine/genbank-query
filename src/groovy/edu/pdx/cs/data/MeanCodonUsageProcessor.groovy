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
        // now the math... done in groups by amino acid

        // Ala/A - GCT, GCC, GCA, GCG

        sum = counts['GCT'] + counts['GCC'] + counts['GCA'] + counts['GCG']

        if (sum == 0) {
            distribution['GCT'] = '0'
            distribution['GCC'] = '0'
            distribution['GCA'] = '0'
            distribution['GCG'] = '0'
        }
        else {
            num = new BigDecimal(counts['GCT'])
            denom = new BigDecimal(sum)
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['GCT'] = result.toString()

            num = new BigDecimal(counts['GCC'])
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['GCC'] = result.toString()

            num = new BigDecimal(counts['GCA'])
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['GCA'] = result.toString()

            num = new BigDecimal(counts['GCG'])
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['GCG'] = result.toString()
        }

        // Arg/R - CGT, CGC, CGA, CGG, AGA, AGG

        sum = counts['CGT'] + counts['CGC'] + counts['CGA'] + counts['CGG'] + counts['AGA'] + counts['AGG']

        if (sum == 0) {
            distribution['CGT'] = '0'
            distribution['CGC'] = '0'
            distribution['CGA'] = '0'
            distribution['CGG'] = '0'
            distribution['AGA'] = '0'
            distribution['AGG'] = '0'
        }
        else {
            num = new BigDecimal(counts['CGT'])
            denom = new BigDecimal(sum)
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['CGT'] = result.toString()

            num = new BigDecimal(counts['CGC'])
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['CGC'] = result.toString()

            num = new BigDecimal(counts['CGA'])
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['CGA'] = result.toString()

            num = new BigDecimal(counts['CGG'])
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['CGG'] = result.toString()

            num = new BigDecimal(counts['AGA'])
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['AGA'] = result.toString()

            num = new BigDecimal(counts['AGG'])
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['AGG'] = result.toString()
        }

        // Asn/N - AAT, AAC

        sum = counts['AAT'] + counts['AAC']

        if (sum == 0) {
            distribution['AAT'] = '0'
            distribution['AAC'] = '0'
        }
        else {
            num = new BigDecimal(counts['AAT'])
            denom = new BigDecimal(sum)
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['AAT'] = result.toString()

            num = new BigDecimal(counts['AAC'])
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['AAC'] = result.toString()
        }

        // Asp/D - GAT, GAC

        sum = counts['GAT'] + counts['GAC']

        if (sum == 0) {
            distribution['GAT'] = '0'
            distribution['GAC'] = '0'
        }
        else {
            num = new BigDecimal(counts['GAT'])
            denom = new BigDecimal(sum)
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['GAT'] = result.toString()

            num = new BigDecimal(counts['GAC'])
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['GAC'] = result.toString()
        }

        // Cys/C - TGT, TGC

        sum = counts['TGT'] + counts['TGC']

        if (sum == 0) {
            distribution['TGT'] = '0'
            distribution['TGC'] = '0'
        }
        else {
            num = new BigDecimal(counts['TGT'])
            denom = new BigDecimal(sum)
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['TGT'] = result.toString()

            num = new BigDecimal(counts['TGC'])
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['TGC'] = result.toString()
        }

        // Gln/Q - CAA, CAG

        sum = counts['CAA'] + counts['CAG']

        if (sum == 0) {
            distribution['CAA'] = '0'
            distribution['CAG'] = '0'
        }
        else {
            num = new BigDecimal(counts['CAA'])
            denom = new BigDecimal(sum)
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['CAA'] = result.toString()

            num = new BigDecimal(counts['CAG'])
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['CAG'] = result.toString()
        }

        // Glu/E - GAA, GAG

        sum = counts['GAA'] + counts['GAG']

        if (sum == 0) {
            distribution['GAA'] = '0'
            distribution['GAG'] = '0'
        }
        else {
            num = new BigDecimal(counts['GAA'])
            denom = new BigDecimal(sum)
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['GAA'] = result.toString()

            num = new BigDecimal(counts['GAG'])
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['GAG'] = result.toString()
        }

        // Gly/G - GGT, GGC, GGA, GGG

        sum = counts['GGT'] + counts['GGC'] + counts['GGA'] + counts['GGG']

        if (sum == 0) {
            distribution['GGT'] = '0'
            distribution['GGC'] = '0'
            distribution['GGA'] = '0'
            distribution['GGG'] = '0'
        }
        else {
            num = new BigDecimal(counts['GGT'])
            denom = new BigDecimal(sum)
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['GGT'] = result.toString()

            num = new BigDecimal(counts['GGC'])
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['GGC'] = result.toString()

            num = new BigDecimal(counts['GGA'])
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['GGA'] = result.toString()

            num = new BigDecimal(counts['GGG'])
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['GGG'] = result.toString()
        }

        // His/H - CAT, CAC

        sum = counts['CAT'] + counts['CAC']

        if (sum == 0) {
            distribution['CAT'] = '0'
            distribution['CAC'] = '0'
        }
        else {
            num = new BigDecimal(counts['CAT'])
            denom = new BigDecimal(sum)
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['CAT'] = result.toString()

            num = new BigDecimal(counts['CAC'])
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['CAC'] = result.toString()
        }

        // Ile/I - ATT, ATC, ATA

        sum = counts['ATT'] + counts['ATC'] + counts['ATA']

        if (sum == 0) {
            distribution['ATT'] = '0'
            distribution['ATC'] = '0'
            distribution['ATA'] = '0'
        }
        else {
            num = new BigDecimal(counts['ATT'])
            denom = new BigDecimal(sum)
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['ATT'] = result.toString()

            num = new BigDecimal(counts['ATC'])
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['ATC'] = result.toString()

            num = new BigDecimal(counts['ATA'])
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['ATA'] = result.toString()
        }

        // Leu/L - TTA, TTG, CTT, CTC, CTA, CTG

        sum = counts['TTA'] + counts['TTG'] + counts['CTT'] + counts['CTC'] + counts['CTA'] + counts['CTG']

        if (sum == 0) {
            distribution['TTA'] = '0'
            distribution['TTG'] = '0'
            distribution['CTT'] = '0'
            distribution['CTC'] = '0'
            distribution['CTA'] = '0'
            distribution['CTG'] = '0'
        }
        else {
            num = new BigDecimal(counts['TTA'])
            denom = new BigDecimal(sum)
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['TTA'] = result.toString()

            num = new BigDecimal(counts['TTG'])
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['TTG'] = result.toString()

            num = new BigDecimal(counts['CTT'])
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['CTT'] = result.toString()

            num = new BigDecimal(counts['CTC'])
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['CTC'] = result.toString()

            num = new BigDecimal(counts['CTA'])
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['CTA'] = result.toString()

            num = new BigDecimal(counts['CTG'])
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['CTG'] = result.toString()
        }

        // Lys/K - AAA, AAG

        sum = counts['AAA'] + counts['AAG']

        if (sum == 0) {
            distribution['AAA'] = '0'
            distribution['AAG'] = '0'
        }
        else {
            num = new BigDecimal(counts['AAA'])
            denom = new BigDecimal(sum)
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['AAA'] = result.toString()

            num = new BigDecimal(counts['AAG'])
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['AAG'] = result.toString()
        }

        // Met/M - ATG

        if (counts['ATG'] == 0) {
            distribution['ATG'] = '0'
        }
        else {
            distribution['ATG'] = '1'
        }

        // Phe/F - TTT, TTC

        sum = counts['TTT'] + counts['TTC']

        if (sum == 0) {
            distribution['TTT'] = '0'
            distribution['TTC'] = '0'
        }
        else {
            num = new BigDecimal(counts['TTT'])
            denom = new BigDecimal(sum)
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['TTT'] = result.toString()

            num = new BigDecimal(counts['TTC'])
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['TTC'] = result.toString()
        }

        // Pro/P - CCT, CCC, CCA, CCG

        sum = counts['CCT'] + counts['CCC'] + counts['CCA'] + counts['CCG']

        if (sum == 0) {
            distribution['CCT'] = '0'
            distribution['CCC'] = '0'
            distribution['CCA'] = '0'
            distribution['CCG'] = '0'
        }
        else {
            num = new BigDecimal(counts['CCT'])
            denom = new BigDecimal(sum)
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['CCT'] = result.toString()

            num = new BigDecimal(counts['CCC'])
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['CCC'] = result.toString()

            num = new BigDecimal(counts['CCA'])
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['CCA'] = result.toString()

            num = new BigDecimal(counts['CCG'])
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['CCG'] = result.toString()
        }

        // Ser/S - TCT, TCC, TCA, TCG, AGT, AGC

        sum = counts['TCT'] + counts['TCC'] + counts['TCA'] + counts['TCG'] + counts['AGT'] + counts['AGC']

        if (sum == 0) {
            distribution['TCT'] = '0'
            distribution['TCC'] = '0'
            distribution['TCA'] = '0'
            distribution['TCG'] = '0'
            distribution['AGT'] = '0'
            distribution['AGC'] = '0'
        }
        else {
            num = new BigDecimal(counts['TCT'])
            denom = new BigDecimal(sum)
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['TCT'] = result.toString()

            num = new BigDecimal(counts['TCC'])
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['TCC'] = result.toString()

            num = new BigDecimal(counts['TCA'])
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['TCA'] = result.toString()

            num = new BigDecimal(counts['TCG'])
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['TCG'] = result.toString()

            num = new BigDecimal(counts['AGT'])
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['AGT'] = result.toString()

            num = new BigDecimal(counts['AGC'])
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['AGC'] = result.toString()
        }

        // Thr/T - ACT, ACC, ACA, ACG

        sum = counts['ACT'] + counts['ACC'] + counts['ACA'] + counts['ACG']

        if (sum == 0) {
            distribution['ACT'] = '0'
            distribution['ACC'] = '0'
            distribution['ACA'] = '0'
            distribution['ACG'] = '0'
        }
        else {
            num = new BigDecimal(counts['ACT'])
            denom = new BigDecimal(sum)
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['ACT'] = result.toString()

            num = new BigDecimal(counts['ACC'])
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['ACC'] = result.toString()

            num = new BigDecimal(counts['ACA'])
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['ACA'] = result.toString()

            num = new BigDecimal(counts['ACG'])
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['ACG'] = result.toString()
        }

        // Trp/W - TGG

        if (counts['TGG'] == 0) {
            distribution['TGG'] = '0'
        }
        else {
            distribution['TGG'] = '1'
        }

        // Tyr/Y - TAT, TAC

        sum = counts['TAT'] + counts['TAC']

        if (sum == 0) {
            distribution['TAT'] = '0'
            distribution['TAC'] = '0'
        }
        else {
            num = new BigDecimal(counts['TAT'])
            denom = new BigDecimal(sum)
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['TAT'] = result.toString()

            num = new BigDecimal(counts['TAC'])
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['TAC'] = result.toString()
        }

        // Val/V - GTT, GTC, GTA, GTG

        sum = counts['GTT'] + counts['GTC'] + counts['GTA'] + counts['GTG']

        if (sum == 0) {
            distribution['GTT'] = '0'
            distribution['GTC'] = '0'
            distribution['GTA'] = '0'
            distribution['GTG'] = '0'
        }
        else {
            num = new BigDecimal(counts['GTT'])
            denom = new BigDecimal(sum)
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['GTT'] = result.toString()

            num = new BigDecimal(counts['GTC'])
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['GTC'] = result.toString()

            num = new BigDecimal(counts['GTA'])
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['GTA'] = result.toString()

            num = new BigDecimal(counts['GTG'])
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['GTG'] = result.toString()
        }

        // STOP - TAA, TGA, TAG

        sum = counts['TAA'] + counts['TGA'] + counts['TAG']

        if (sum == 0) {
            distribution['TAA'] = '0'
            distribution['TGA'] = '0'
            distribution['TAG'] = '0'
        }
        else {
            num = new BigDecimal(counts['TAA'])
            denom = new BigDecimal(sum)
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['TAA'] = result.toString()

            num = new BigDecimal(counts['TGA'])
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['TGA'] = result.toString()

            num = new BigDecimal(counts['TAG'])
            result = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
            distribution['TAG'] = result.toString()
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
