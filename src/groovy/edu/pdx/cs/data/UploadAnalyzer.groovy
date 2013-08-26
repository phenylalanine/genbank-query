/**
 * Created with Eclipse!
 * User: Jim Miller (JGM)
 * Date: 8/25/13
 */

package edu.pdx.cs.data

import org.biojavax.bio.seq.RichSequence
import org.biojavax.bio.seq.RichSequenceIterator
import org.biojavax.bio.seq.SimpleRichSequence
import webapp.Organism

// (JGM) For now, the class uses one seq.gz file to fill in for "Genbank".  This should be changed to
// analyze against our store of persisted Organisms.
class UploadAnalyzer {
	private static orgList = []
		
	// (JGM) Create a list of Organisms to use as our "Genbank"
	static void processSeqGzFile() {
		OrganismProcessor processor = new OrganismProcessor(persist: false)
		InputStream is = new MaybeCompressedInputStream(new FileInputStream("sequences/gbbct103.seq.gz"))
		BufferedReader br = new BufferedReader(new InputStreamReader(is))
		RichSequenceIterator sequences = RichSequence.IOTools.readGenbankDNA(br, null)

		while (sequences.hasNext())
			orgList += processor.process(sequences.nextRichSequence())
	}
	
	// TODO:  Spawn a thread for this method.
	// (JGM) Calls to this method will eventually occur in MainController.groovy
	static void analyzeAgainstGenbank(Organism masterOrg, AnalysisConstants.Type analysisType, String destAddress) {
		def analysisFile
		
//		Thread.start {
			if (analysisType == AnalysisConstants.Type.GCPERCENT) {
				def analyzer = new GCPercentageAnalyzerWithoutSimilarityChecking(masterOrg, orgList)
				analysisFile = analyzer.gcPercentageFileBuilder()
			}
			else if (analysisType == AnalysisConstants.Type.MCUF) {
				def analyzer = new MCUFAnalyzerWithoutSimilarityChecking(masterOrg, orgList)
				analysisFile = analyzer.mcufFileBuilder()
			}
			else {
				def analyzer = new RSCUAnalyzerWithoutSimilarityChecking(masterOrg, orgList)
				analysisFile = analyzer.rscuFileBuilder()
			}
			AnalysisFileMailer.sendEmail(destAddress, analysisFile.name)
//		}
	}
}
