package edu.pdx.cs.data

import org.biojavax.bio.seq.RichSequence
import org.junit.Test
import webapp.Organism

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when
import static org.mockito.Mockito.when

/**
 * Created with IntelliJ IDEA.
 * User: Thang
 * Date: 7/27/13
 * Time: 5:04 PM
 * To change this template use File | Settings | File Templates.
 */
class GCComparativeAnalysisTest {
    @Test
    void testTextFile() {
        def seq = mock(RichSequence)

        when(seq.identifier).thenReturn("1234")
        when(seq.seqString()).thenReturn("gattacattacccggatttacgataccgaaagtcgattcagatatagaaagccatcat")
        def test = new GCComparativeAnalysis()
        test.process(seq)

    }
}
