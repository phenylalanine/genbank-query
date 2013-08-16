package webapp

import edu.pdx.cs.data.OrganismProcessor
import edu.pdx.cs.data.RSCUComparator
import org.apache.commons.io.IOUtils
import org.biojava.bio.seq.DNATools
import org.biojavax.bio.seq.RichSequence
import org.springframework.web.multipart.MultipartFile
import org.apache.shiro.SecurityUtils

class MainController {
    static layout = "main"

    def index() {
        def dataMap
        def organisms = []
        def opt = []

        // Check number of params to decide to show modal on load
        if (flash.containsKey("organisms")) {
            organisms = flash.get("organisms")
            opt = flash.get("opt")
        }
        dataMap = [
                organisms: organisms,
                opt: opt,
                codonDistributions: aminoDist(organisms)
        ]
        if (organisms.size() == 2) {
            dataMap.put("rscuComp", new RSCUComparator(organisms[0], organisms[1]))
        }

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
        def opt = params.get("opt")

        String sequenceString
        org.biojava.bio.seq.Sequence sequence
        RichSequence richSequence
        def genbankOrganism

        //flash.organisms = []
        flash.put("organisms", [])
        flash.put("opt", opt)

        def genbankOrganismName0 = params.get("genbankOrganism0")
        def genbankOrganismName1 = params.get("genbankOrganism1")

        if (organismName0 && userSeqFile0) {
            sequenceString = IOUtils.toString(userSeqFile0.inputStream, "UTF-8").trim()
            sequence = DNATools.createDNASequence(sequenceString, organismName0)
            richSequence = RichSequence.Tools.enrich(sequence)
            flash.get("organisms").push(new OrganismProcessor(persist: false).process(richSequence))
        }
        if (organismName1 && userSeqFile1) {
            sequenceString = IOUtils.toString(userSeqFile1.inputStream, "UTF-8").trim()
            sequence = DNATools.createDNASequence(sequenceString, organismName1)
            richSequence = RichSequence.Tools.enrich(sequence)
            flash.get("organisms").push(new OrganismProcessor(persist: false).process(richSequence))
        }
        if (genbankOrganismName0) {
            genbankOrganism = Organism.find { scientificName == genbankOrganismName0 }
            if (genbankOrganism) {
                flash.get("organisms").push(genbankOrganism)
            }
        }
        if (genbankOrganismName1) {
            genbankOrganism = Organism.find { scientificName == genbankOrganismName1 }
            if (genbankOrganism) {
                flash.get("organisms").push(genbankOrganism)
            }
        }

        if (flash.get("organisms").size() > 2 || flash.get("organisms").size() < 1){
            response.sendError(400, "Please upload organisms according to the instructions")
        }

        // TODO: add data from user selected domain class entry to RSCU results
        // TODO: turn the codon distrobution into MCUF by comparing with stuff in domain class
        // TODO: use results in a view

        if (!response.committed) {
            redirect(action: "index")
        }
    }

    private def aminoDist(organisms) {
        def seqDist = organisms.collect{ it.mcufCodonDistribution }
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
        def rowVals, row
        for (amino in aminoTable) {
            row = [name: amino.key]
            rowVals = []
            dataTables.push([
                    name: amino.key,
                    values: amino.value.collectNested { aminoVal ->
                        [aminoVal] + seqDist.collect{ it[aminoVal.toLowerCase()] }
                    },
            ])
            row.put("values", rowVals)
        }
        return dataTables
    }

    /*
    Perform least-squares estimation of paramters for the RSCU Distribution between two organisms
    Returns an ArrayList containing [Y-intercept, Slope]
     */
    def bestFitParams(organisms) {
        def keys = organisms[0].rscuCodonDistribution.keySet()
        def org1 = [], org2 = []
        BigDecimal sum1 = 0, sum2 = 0, mean1, mean2, b0, b1
        for (codon in keys) {
            def xval = new BigDecimal(organisms[0].rscuCodonDistribution[codon])
            def yval = new BigDecimal(organisms[1].rscuCodonDistribution[codon])
            org1.push(xval)
            org2.push(yval)
            sum1 += xval
            sum2 += yval
        }
        mean1 = sum1 / keys.size()
        mean2 = sum2 / keys.size()

        BigDecimal sxx = 0, sxy = 0
        for (x in org1) {
            sxx += (x - mean1) * (x - mean1)
        }
        for (int i = 0; i < org1.size(); i++) {
            sxy += org2[i] * (org1[i] - mean1)
        }

        b1 = sxy / sxx
        b0 = mean2 - b1 * mean1

        return [b0, b1]
    }
}
