package edu.pdx.cs.data

import edu.pdx.cs.data.BioConstants
import org.biojavax.bio.seq.RichSequence

/**
 * Created with IntelliJ IDEA.
 * User: Laura
 * Date: 7/14/13
 * Time: 10:49 AM
 * To change this template use File | Settings | File Templates.
 */
class RSCUUProcessor implements UProcessor {
    @Override
    public ProcessedUploadedSequence process(RichSequence richSequence) {
        def codonsListSize = BioConstants.codons.size()
        def bigDecimalScale = 10
        def name = richSequence.getName()
        def sequence = richSequence.seqString()
        def len = sequence.length()
        def counts = [:]
        def RSCUDistribution = [:]
        def i, j, currSynonymsSize, currSynonymsSum
        def currCodon
        def currSynonymsList

        // Initialize the counts Map to all 0 values.
        for (i = 0; i < codonsListSize; i++) {
            counts[BioConstants.codons[i]] = 0
        }
        // Parse the input sequence string for codon triples and count them, ignoring any
        // 1 or 2 leftover bases, and ignoring any codons in the ignoredCodons List.
        i = 0
        while (i + 3 <= len) {
            currCodon = sequence.substring(i, i + 3)
            if (!BioConstants.ignoredCodons.contains(currCodon))
                counts[currCodon] += 1
            i += 3
        }
        // Calculate RSCU for each of 59 codons (all except STOPs, methionine, and tryptophan).
        // Therefore, currently the RSCU processor assumes all input codons are from equally-expressed genes.
        for (i = 0; i < codonsListSize; i++) {
            currCodon = BioConstants.codons[i]
            currSynonymsList = BioConstants.synonyms[currCodon]
            currSynonymsSize = currSynonymsList.size()
            currSynonymsSum = 0
            for (j = 0; j < currSynonymsSize; j++) {
                currSynonymsSum += counts[currSynonymsList[j]]
            }
            // When the denominator is 0 (0 count for all synonymous codons), arbitrarily represent RSCU as -1.
            if (currSynonymsSum == 0)
                RSCUDistribution[currCodon] = new BigDecimal(-1).toString()
            else
                RSCUDistribution[currCodon] = (new BigDecimal(counts[currCodon] * currSynonymsSize)
                        .divide(new BigDecimal(currSynonymsSum), bigDecimalScale, BigDecimal.ROUND_HALF_UP)).toString()
        }

        // format data
        def List rowList = []

        RSCUDistribution.each {key, value ->
            rowList += [[key, new BigDecimal(value)]]
        }

        return new ProcessedUploadedSequence(
                analysis:"RSCU",
                columns:[["String", "Codon"], ["BigDecimal", name]],
                analysisData:rowList
        )
    }
}
