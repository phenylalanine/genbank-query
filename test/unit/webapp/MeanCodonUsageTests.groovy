package webapp



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(MeanCodonUsage)
class MeanCodonUsageTests {

    void testConstraints() {
        def thingOne = new MeanCodonUsage()
        thingOne.organismId = 123
        thingOne.distribution = [ACT:0.56]

        mockForConstraintsTests(MeanCodonUsage, [thingOne])

        // test unique

        def thingTwo = new MeanCodonUsage()
        thingTwo.organismId = 123
        thingTwo.distribution = [ACT:0.12]

        assert !thingTwo.validate()
        assert "unique" == thingTwo.errors["organismId"]

        // this one should work

        def thingThree = new MeanCodonUsage()
        thingThree.organismId = 124
        thingThree.distribution = [ACT:0.12]

        assert thingThree.validate()
    }
}
