/**
 * Created with Eclipse!
 * User: Jim Miller (JGM)
 * Date: 7/6/13
 */

package edu.pdx.cs.data

import edu.pdx.cs.data.BioConstants
import org.biojavax.bio.seq.RichSequence
import webapp.RSCU

class RSCUProcessor implements Processor {
	
	@Override
	void process(RichSequence richSequence) {
		def organismId = Integer.valueOf(richSequence.identifier)
		def sequence = richSequence.seqString()
		def counts = [:]
		def RSCUDistribution = [:]
		def len = sequence.length()
		def i, j, currSynonymsSize, currSynonymsSum
		def currCodon
		def currSynonymsList
		def bigDecimalScale = 10
		
		// Initialize the counts Map to all 0 values.
		for (i = 0; i < BioConstants.codons.size(); i++) {
			counts[BioConstants.codons[i]] = 0
		}
		// Parse the input sequence string for codon triples, ignoring any 1 or 2 leftover bases,
		// and ignoring any codons in the ignoredCodons List.
		i = 0
		while (i + 3 <= len) {
			currCodon = sequence.substring(i, i + 3)
			if (!BioConstants.ignoredCodons.contains(currCodon))
				counts[currCodon] += 1
			i += 3
		}
		// Calculate RSCU for each of 59 codons (all except STOPs, methionine, and tryptophan).
		// Therefore, currently the RSCU processor assumes all input codons are equally-expressed.
		for (i = 0; i < BioConstants.codons.size(); i++) {
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
		// Save processed data as an RSCU object in the database.
		try {
			new RSCU(
				organismId: organismId,
				distribution: RSCUDistribution
			).save(flush:true)
		} catch (Exception e) {
			// TODO: log this
		}
	}
}
