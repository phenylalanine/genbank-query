package edu.pdx.cs.data

import org.biojavax.bio.seq.RichSequence
import org.biojavax.bio.taxa.NCBITaxon
import org.junit.Test
import webapp.Organism

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: Ryan
 * Date: 6/30/13
 * Time: 5:31 PM
 * To change this template use File | Settings | File Templates.
 */

class OrganismProcessorTest {

    @Test
    void testProcessorCreatesCorrectDomainClass() {
        //create mocks of the objects we need for the test
        //and define their behavior
        def mockSequence = mock(RichSequence)
        def mockTaxon = mock(NCBITaxon)

        when(mockTaxon.NCBITaxID).thenReturn(4321)
        when(mockTaxon.getNames("scientific name")).thenReturn(["homo sapien"] as Set)

        when(mockSequence.taxon) thenReturn(mockTaxon)
        when(mockSequence.identifier).thenReturn("1234")
        when(mockSequence.seqString()).thenReturn("gattacattacccggatttacgataccgaaagtcgattcagatatagaaagccatcat")

        def processor = new OrganismProcessor(
                rscuProcessor: new RSCUProcessor(),
                meanCodonUsageProcessor: new MeanCodonUsageProcessor(),
                gcPercentageProcessor: new GCPercentageProcessor()
        )

        processor.process(mockSequence)

        def newlyCreatedOrganism = Organism.find { organismId == 1234 }

        assert newlyCreatedOrganism.organismId == "1234"
        assert newlyCreatedOrganism.scientificName == "homo sapien"
        assert newlyCreatedOrganism.taxonomyId == "4321"
        assert newlyCreatedOrganism.completeGenome == Boolean.FALSE

        assertCorrectGcPercentage(newlyCreatedOrganism.gcPercentage)
        assertCorrectRscuDistribution(newlyCreatedOrganism.rscuCodonDistribution);
        assertCorrectMcufDistribution(newlyCreatedOrganism.mcufCodonDistribution);

        //clean up the in-memory database in case another test uses it before we tear down
        newlyCreatedOrganism.delete(flush: true)
    }

    def assertCorrectGcPercentage(gc) {
        def num = new BigDecimal("22")
        def denom = new BigDecimal("58")
        def scale = 10
        def temp = num.divide(denom, scale, BigDecimal.ROUND_HALF_UP)
        def str = temp.movePointRight(2).toString()
        assert str == gc
    }

    def assertCorrectRscuDistribution(rscuDistribution){
        def bigDecimalScale = 10
        def zero = new BigDecimal(0)
        def one = new BigDecimal(1)
        def two = new BigDecimal(2)
        def three = new BigDecimal(3)
        def four = new BigDecimal(4)
        def six = new BigDecimal(6)
        def eight = new BigDecimal(8)
        def twelve = new BigDecimal(12)

        assert rscuDistribution["gct"] == "-1"
        assert rscuDistribution["gcc"] == "-1"
        assert rscuDistribution["gca"] == "-1"
        assert rscuDistribution["gcg"] == "-1"

        assert rscuDistribution["cgt"] == zero.divide(four, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        assert rscuDistribution["cgc"] == zero.divide(four, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        assert rscuDistribution["cga"] == six.divide(four, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        assert rscuDistribution["cgg"] == six.divide(four, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        assert rscuDistribution["aga"] == twelve.divide(four, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        assert rscuDistribution["agg"] == zero.divide(four, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()

        assert rscuDistribution["aat"] == "-1"
        assert rscuDistribution["aac"] == "-1"

        assert rscuDistribution["gat"] == four.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        assert rscuDistribution["gac"] == zero.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()

        assert rscuDistribution["tgt"] == "-1"
        assert rscuDistribution["tgc"] == "-1"

        assert rscuDistribution["caa"] == "-1"
        assert rscuDistribution["cag"] == "-1"

        assert rscuDistribution["gaa"] == two.divide(one, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        assert rscuDistribution["gag"] == zero.divide(one, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()

        assert rscuDistribution["ggt"] == "-1"
        assert rscuDistribution["ggc"] == "-1"
        assert rscuDistribution["gga"] == "-1"
        assert rscuDistribution["ggg"] == "-1"

        assert rscuDistribution["cat"] == "-1"
        assert rscuDistribution["cac"] == "-1"

        assert rscuDistribution["att"] == six.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        assert rscuDistribution["atc"] == zero.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        assert rscuDistribution["ata"] == zero.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()

        assert rscuDistribution["tta"] == "-1"
        assert rscuDistribution["ttg"] == "-1"
        assert rscuDistribution["ctt"] == "-1"
        assert rscuDistribution["ctc"] == "-1"
        assert rscuDistribution["cta"] == "-1"
        assert rscuDistribution["ctg"] == "-1"

        assert rscuDistribution["aaa"] == zero.divide(one, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        assert rscuDistribution["aag"] == two.divide(one, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()

        assert rscuDistribution["atg"] == null

        assert rscuDistribution["ttt"] == zero.divide(one, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        assert rscuDistribution["ttc"] == two.divide(one, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()

        assert rscuDistribution["cct"] == zero.divide(one, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        assert rscuDistribution["ccc"] == zero.divide(one, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        assert rscuDistribution["cca"] == four.divide(one, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        assert rscuDistribution["ccg"] == zero.divide(one, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()

        assert rscuDistribution["tct"] == zero.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        assert rscuDistribution["tcc"] == zero.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        assert rscuDistribution["tca"] == six.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        assert rscuDistribution["tcg"] == zero.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        assert rscuDistribution["agt"] == six.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        assert rscuDistribution["agc"] == zero.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()

        assert rscuDistribution["act"] == zero.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        assert rscuDistribution["acc"] == eight.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        assert rscuDistribution["aca"] == zero.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        assert rscuDistribution["acg"] == zero.divide(two, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()

        assert rscuDistribution["tgg"] == null

        assert rscuDistribution["tat"] == two.divide(three, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()
        assert rscuDistribution["tac"] == four.divide(three, bigDecimalScale, BigDecimal.ROUND_HALF_UP).toString()

        assert rscuDistribution["gtt"] == "-1"
        assert rscuDistribution["gtc"] == "-1"
        assert rscuDistribution["gta"] == "-1"
        assert rscuDistribution["gtg"] == "-1"

        assert rscuDistribution["taa"] == null
        assert rscuDistribution["tga"] == null
        assert rscuDistribution["tag"] == null
    }
    
    def assertCorrectMcufDistribution(mcufDistribution) {
        // and now the details...
        // using BigDecimal division to make sure things come out the same
        // counts were done manually
        def zero = new BigDecimal('0')
        def one = new BigDecimal('1')
        def two = new BigDecimal('2')
        def three = new BigDecimal('3')
        def four = new BigDecimal('4')
        def scale = 10

        assert mcufDistribution['gct'] == '0'
        assert mcufDistribution['gcc'] == '0'
        assert mcufDistribution['gca'] == '0'
        assert mcufDistribution['gcg'] == '0'

        assert mcufDistribution['cgt'] == zero.divide(four, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert mcufDistribution['cgc'] == zero.divide(four, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert mcufDistribution['cga'] == one.divide(four, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert mcufDistribution['cgg'] == one.divide(four, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert mcufDistribution['aga'] == two.divide(four, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert mcufDistribution['agg'] == zero.divide(four, scale, BigDecimal.ROUND_HALF_UP).toString()

        assert mcufDistribution['aat'] == '0'
        assert mcufDistribution['aac'] == '0'

        assert mcufDistribution['gat'] == two.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert mcufDistribution['gac'] == zero.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()

        assert mcufDistribution['tgt'] == '0'
        assert mcufDistribution['tgc'] == '0'

        assert mcufDistribution['caa'] == '0'
        assert mcufDistribution['cag'] == '0'

        assert mcufDistribution['gaa'] == one.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert mcufDistribution['gag'] == zero.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString()

        assert mcufDistribution['ggt'] == '0'
        assert mcufDistribution['ggc'] == '0'
        assert mcufDistribution['gga'] == '0'
        assert mcufDistribution['ggg'] == '0'

        assert mcufDistribution['cat'] == '0'
        assert mcufDistribution['cac'] == '0'

        assert mcufDistribution['att'] == two.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert mcufDistribution['atc'] == zero.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert mcufDistribution['ata'] == zero.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()

        assert mcufDistribution['tta'] == '0'
        assert mcufDistribution['ttg'] == '0'
        assert mcufDistribution['ctt'] == '0'
        assert mcufDistribution['ctc'] == '0'
        assert mcufDistribution['cta'] == '0'
        assert mcufDistribution['ctg'] == '0'

        assert mcufDistribution['aaa'] == zero.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert mcufDistribution['aag'] == one.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString()

        assert mcufDistribution['atg'] == '0'

        assert mcufDistribution['ttt'] == zero.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert mcufDistribution['ttc'] == one.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString()

        assert mcufDistribution['cct'] == zero.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert mcufDistribution['ccc'] == zero.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert mcufDistribution['cca'] == one.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert mcufDistribution['ccg'] == zero.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString()

        assert mcufDistribution['tct'] == zero.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert mcufDistribution['tcc'] == zero.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert mcufDistribution['tca'] == one.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert mcufDistribution['tcg'] == zero.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert mcufDistribution['agt'] == one.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert mcufDistribution['agc'] == zero.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()

        assert mcufDistribution['act'] == zero.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert mcufDistribution['acc'] == two.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert mcufDistribution['aca'] == zero.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert mcufDistribution['acg'] == zero.divide(two, scale, BigDecimal.ROUND_HALF_UP).toString()

        assert mcufDistribution['tgg'] == '0'

        assert mcufDistribution['tat'] == one.divide(three, scale, BigDecimal.ROUND_HALF_UP).toString()
        assert mcufDistribution['tac'] == two.divide(three, scale, BigDecimal.ROUND_HALF_UP).toString()

        assert mcufDistribution['gtt'] == '0'
        assert mcufDistribution['gtc'] == '0'
        assert mcufDistribution['gta'] == '0'
        assert mcufDistribution['gtg'] == '0'

        assert mcufDistribution['taa'] == '0'
        assert mcufDistribution['tga'] == '0'
        assert mcufDistribution['tag'] == '0'
    }
}
