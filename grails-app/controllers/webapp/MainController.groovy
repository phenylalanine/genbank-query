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
        def organisms = []

        // Check number of params to decide to show modal on load
        if (flash.keySet().size() > 0) {
            organisms = flash.get("organisms")
        }

        dataMap = [
                organisms: organisms,
                codonDistributions: organisms.collect { aminoDist(it) },
                //gcPercentages: organisms.collect { it. }
        ]

        render(view: "index", model: dataMap)
    }

    /*
    Expected parameters
    userOrganism: string
    userSequenceFile: File
     */

    def upload() {
        def organismName0 = params.get("userOrganism0")
        def MultipartFile userSeqFile0 = request.getFile("userSequenceFile0")
        def organismName1 = params.get("userOrganism1")
        def MultipartFile userSeqFile1 = request.getFile("userSequenceFile1")

        String sequenceString
        org.biojava.bio.seq.Sequence sequence
        RichSequence richSequence
        def genbankOrganism

        //flash.organisms = []
        flash.put("organisms", [])

        def genbankOrganismName0 = params.get("genbankOrganism0")
        def genbankOrganismName1 = params.get("genbankOrganism1")

        if (organismName0 && userSeqFile0) {
            sequenceString = IOUtils.toString(userSeqFile0.inputStream, "UTF-8").trim()
            sequence = DNATools.createDNASequence(sequenceString, organismName0)
            richSequence = RichSequence.Tools.enrich(sequence)
            flash.get("organisms").push(new OrganismProcessor(persist: false).process(richSequence))
        } else if (organismName1 && userSeqFile1) {
            sequenceString = IOUtils.toString(userSeqFile1.inputStream, "UTF-8").trim()
            sequence = DNATools.createDNASequence(sequenceString, organismName1)
            richSequence = RichSequence.Tools.enrich(sequence)
            flash.get("organisms").push(new OrganismProcessor(persist: false).process(richSequence))
        } else if (genbankOrganismName0) {
            genbankOrganism = Organism.find { scientificName == genbankOrganismName0 }
            if (genbankOrganism) {
                flash.get("organisms").push(genbankOrganism)
            }
        } else if (genbankOrganismName1) {
            genbankOrganism = Organism.find { scientificName == genbankOrganismName1 }
            if (genbankOrganism) {
                flash.get("organisms").push(genbankOrganism)
            }
        } else {
            response.sendError(400, "Please upload organisms according to the instructions")
        }

        // TODO: add data from user selected domain class entry to RSCU results
        // TODO: turn the codon distrobution into MCUF by comparing with stuff in domain class
        // TODO: use results in a view

        if (!response.committed) {
            redirect(action: "index")
        }
    }

    private def aminoDist(organism) {
        def seqDist = organism.mcufCodonDistribution
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
