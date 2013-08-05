package edu.pdx.cs.data

import org.biojavax.bio.seq.RichSequence
import webapp.Organism

/**
 * Created with IntelliJ IDEA.
 * User: Thang
 * Date: 7/20/13
 * Time: 6:00 PM
 * To change this template use File | Settings | File Templates.
 */
class GCComparativeAnalysis {
    def process(RichSequence richSequence) {
        def OrganismList = Organism.getAll()

        def organismId = Integer.valueOf(richSequence.identifier)
        def String gc
        def sequence = richSequence.seqString()
        def g = sequence.count('g')
        def c = sequence.count('c')
        def num = new BigDecimal(g+c)
        def denom = new BigDecimal(sequence.length())
        def scale = 10
        def isSimilar = false

        def temp = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
        gc = temp.movePointRight(2).toString()

        def newGC = new Organism(organismId: organismId,gcPercentage: gc)
        def clos = { Organism one, Organism two ->
            if (one.organismId == two.organismId && one.gcPercentage == two.gcPercentage)
                return true
            else
                return false
        }

        for (i in OrganismList) {
            if (newGC.isSimilarTo(i,clos))
                isSimilar = true
        }
        if (!isSimilar)
            OrganismList.add(newGC)
        OrganismList.sort{a, b -> a.gcPercentage.toFloat() <=> b.gcPercentage.toFloat()}

        def file = new File("GCCompararison.txt")
        file.withWriter { out ->
            for (i in OrganismList) {
                out.writeLine(i.scientificName + "\t" + i.taxonomyId + "\t" + i.gcPercentage)
            }
        }
        return file
    }
}