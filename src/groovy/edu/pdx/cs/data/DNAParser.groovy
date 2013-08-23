package edu.pdx.cs.data

import org.apache.commons.io.IOUtils
import org.biojava.bio.seq.DNATools
import org.biojavax.bio.seq.RichSequence
import org.biojavax.bio.seq.RichSequenceIterator
import org.biojavax.bio.seq.io.EMBLFormat
import org.biojavax.bio.seq.io.EMBLxmlFormat
import org.biojavax.bio.seq.io.FastaFormat
import org.biojavax.bio.seq.io.GenbankFormat
import org.biojavax.bio.seq.io.INSDseqFormat
import org.biojavax.bio.seq.RichSequence.IOTools

/**
 * Created with IntelliJ IDEA.
 * User: Laura
 * Date: 8/2/13
 * Time: 11:49 AM
 * To change this template use File | Settings | File Templates.
 */
class DNAParser {
    /**
     * throws org.biojava.bio.symbol.IllegalSymbolException if given a random text file
     *
     * @param input - input stream to the file to parse
     * @param organismName - only needed for user uploaded text files - put null for non-user files
     * @return a RichSequenceIterator of the parsed dna sequences
     */
    public static RichSequenceIterator parseDNA(InputStream input, String organismName) {
        BufferedInputStream bInput = new BufferedInputStream(input)
        BufferedReader brInput
//        EMBLFormat embl = new EMBLFormat()
//        EMBLxmlFormat emblx = new EMBLxmlFormat()
        FastaFormat fasta = new FastaFormat()
        GenbankFormat genbank = new GenbankFormat()
//        INSDseqFormat insd = new INSDseqFormat()

        if (genbank.canRead(bInput)) {
            brInput = bInput.newReader()
            return IOTools.readGenbankDNA(brInput, null)
        }
        else if (fasta.canRead(bInput)) {
            brInput = bInput.newReader()
            return IOTools.readFastaDNA(brInput, null)
        }
            // something wrong with this one- the returned
            // RichStreamReader throws a null pointer exception on nextRichSequence()
//        else if (embl.canRead(bInput)) {
//            brInput = bInput.newReader()
//            return IOTools.readEMBLDNA(brInput, null)
//        }
            // wasn't able to find samples of these file types to test
//        else if (emblx.canRead(bInput)) {
//            brInput = bInput.newReader()
//            return IOTools.readEMBLxmlDNA(brInput, null)
//        }
//        else if (insd.canRead(bInput)) {
//            brInput = bInput.newReader()
//            return IOTools.readINSDseqDNA(brInput, null)
//        }
        else { // this had better be a text file!
            String seqString
            org.biojava.bio.seq.Sequence sequence
            RichSequence richSequence

            seqString = IOUtils.toString(bInput, "UTF-8").trim()
            sequence = DNATools.createDNASequence(seqString, organismName)
            richSequence = RichSequence.Tools.enrich(sequence)
            return new IOTools.SingleRichSeqIterator(richSequence)
        }
    }
}
