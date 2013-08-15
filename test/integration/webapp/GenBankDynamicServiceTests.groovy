package webapp

import static org.junit.Assert.*
import org.junit.*

class GenBankDynamicServiceTests {

	@Test
	void testGetIdsForScientificName() {
		// (JGM) Searching for "rhesus macaque norovirus" (truncated).
		// (This is a common name, so I don't know why it works in the scientific name field, but it does.)
		// The asserts test the output from the query:
		// http://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?db=taxonomy&term=*rhesus+macaque+norov*&field=scientific+name
		def ids = new GenBankDynamicService().getIdsForScientificName("rhesus macaque norov")
		assert ids.size() == 1
		assert ids[0] == 872275
	}
}
