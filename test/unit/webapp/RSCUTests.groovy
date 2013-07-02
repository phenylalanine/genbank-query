package webapp

import grails.test.mixin.*
import org.junit.*

/**
* See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
*/
@TestFor(RSCU)
class RSCUTests {

    void testConstraints() {
        def thingOne = new RSCU()
        thingOne.organismId = 123
        thingOne.distribution = [ACT:0.56]

        mockForConstraintsTests(RSCU, [thingOne])

        // test unique

        def thingTwo = new RSCU()
        thingTwo.organismId = 123
        thingTwo.distribution = [ACT:0.12]

        assert !thingTwo.validate()
        assert "unique" == thingTwo.errors["organismId"]

        // this one should work

        def thingThree = new RSCU()
        thingThree.organismId = 124
        thingThree.distribution = [ACT:0.12]

        assert thingThree.validate()

        // test nullable
        def thingFour = new RSCU()

        assert !thingFour.validate()
        assert "nullable" == thingFour.errors["organismId"]
    }
}