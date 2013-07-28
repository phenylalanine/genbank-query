package webapp

import edu.pdx.cs.data.OrganismProcessor
import org.apache.commons.io.IOUtils
import org.biojava.bio.seq.DNATools
import org.biojavax.bio.seq.RichSequence
import org.springframework.web.multipart.MultipartFile

class MainController {
    static layout = "main"

    def index() {
        def dataMap
        def showUpload
        def organismId1 = 1

        // Check number of params
        if (params.size() > 1) {
            showUpload = "hide"
        }
        else {
            showUpload = "show"
        }

        def codonDist = aminoDist(MeanCodonUsage.list()[organismId1].distribution)

        dataMap = [
                upload: showUpload,
                codonDist: codonDist
        ]
        render(view: "index", model: dataMap)
        //render aminoDist(MeanCodonUsage.list()[1].distribution)
    }

    /*
    Expected parameters
    userOrganism: string
    userSequenceFile: File
     */
    def upload() {
        def organismName = params.get("userOrganism")
        def MultipartFile userSeqFile = request.getFile("userSequenceFile")

        if (userSeqFile == null) {
            response.sendError(400, "Bad Request: userSequenceFile not found")
            return
        }
        if (organismName == null) {
            response.sendError(400, "Bad Request: organismName param not found")
            return
        }

        String sequenceString = IOUtils.toString(userSeqFile.inputStream, "UTF-8")
        org.biojava.bio.seq.Sequence sequence = DNATools.createDNASequence(sequenceString, organismName)
        RichSequence richSequence = RichSequence.Tools.enrich(sequence)

        Organism organism = new OrganismProcessor(persist: false).process(richSequence)

        // TODO: add data from user selected domain class entry to RSCU results
        // TODO: turn the codon distrobution into MCUF by comparing with stuff in domain class
        // TODO: use results in a view

        response.sendError(200, "Done")     // TODO: Change to response.redirect
    }

    private def aminoDist(seqDist) {
        def aminoTable = [
                "Phenylalanine": ["TTT", "TTC"],
                "Leucine": ["TTA", "TTG", "CTT", "CTC", "CTA", "CTG"],
                "Isoleucine": ["ATT", "ATC", "ATA"],
                "Methionine": ["ATG"],
                "Valine": ["GTT", "GTC", "GTA", "GTG"],
                "Serine": ["TCT", "TCC", "TCA", "TCG", "AGT", "AGC"],
                "Proline": ["CCT", "CCC", "CCA", "CCG"],
                "Threonine": ["ACT", "ACC", "ACA", "ACG"],
                "Alanine": ["GCT", "GCC", "GCA", "GCG"],
                "Tyrosine": ["TAT", "TAC"],
                "Stop (Ochre)": ["TAA"],
                "Stop (Amber)": ["TAG"],
                "Histidine": ["CAT", "CAC"],
                "Glutamine": ["CAA", "CAG"],
                "Asparagine": ["AAT", "AAC"],
                "Lysine": ["AAA", "AAG"],
                "Aspartic Acid": ["GAT", "GAC"],
                "Glutamic Acid": ["GAA", "GAG"],
                "Cysteine": ["TGT", "TGC"],
                "Stop (Opal)": ["TGA"],
                "Tryptophan": ["TGG"],
                "Arginine": ["CGT", "CGC", "CGA", "CGG", "AGA", "AGG"],
                "Glycine": ["GGT", "GGC", "GGA", "GGG"]
        ]
        def dataTables = []
        for (amino in aminoTable) {
            dataTables.push([
                    name: amino.key,
                    values: amino.value.collectNested { [it, seqDist[it.toLowerCase()]] },
            ])
        }
        return dataTables
    }
}
