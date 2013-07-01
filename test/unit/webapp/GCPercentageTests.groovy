package webapp



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(GCPercentage)
class GCPercentageTests {

    void testConstraints() {
        def existGC = new GCPercentage(organismId: 123, gcPercentage: 12.34)
        mockForConstraintsTests(GCPercentage, [existGC])

        // fail if id is null
        def testgc = new GCPercentage()
        assert !testgc.validate()
        assert "nullable" == testgc.errors["organismId"]

        // test the unique constraint
        testgc = new GCPercentage(organismId: 123)
        assert !testgc.validate()
        assert "unique" == testgc.errors["organismId"]

        // test the range constraint
        testgc = new GCPercentage(organismId: 234, gcPercentage: -10.0)
        assert !testgc.validate()
        assert "range" == testgc.errors["gcPercentage"]

        testgc = new GCPercentage(organismId: 234, gcPercentage: 111.11)
        assert !testgc.validate()
        assert "range" == testgc.errors["gcPercentage"]

        // normal gc
        testgc = new GCPercentage(organismId: 234, gcPercentage: 78.9)
        assert testgc.validate()
    }
}
