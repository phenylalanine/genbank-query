package webapp



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(TransientOrganism)
class TransientOrganismTests {

    /**
     * This is basically the same exact test as in {@link OrganismTests}
     *
     * We are just checking to make sure the validation still works even
     * though we are not going to persist the Organism
     */
    void testConstraints() {
        def validOrganism = new TransientOrganism(
                organismId: 1,
                scientificName: "Paineus in the asseus",
                gcPercentage: "42",
                rscuCodonDistribution: [ACT: "20"],
                mcufCodonDistribution: [ACT: "20"]
        )
        mockForConstraintsTests(TransientOrganism, [validOrganism])

        //make an empty transient organism, this should fail
        def organism = new TransientOrganism()
        assert !organism.validate()

        assert "nullable" == organism.errors["scientificName"]
        assert "nullable" == organism.errors["rscuCodonDistribution"]
        assert "nullable" == organism.errors["mcufCodonDistribution"]
        assert "nullable" == organism.errors["gcPercentage"]

        //test that this doesnt care if its ids are unique
        organism = new TransientOrganism(
                organismId: 1,
                scientificName: "homo sapien",
                gcPercentage: "42",
                rscuCodonDistribution: [ACT: "20"],
                mcufCodonDistribution: [ACT: "20"]
        )
        assert organism.validate()

        //Make sure we can create a valid transient organism with all properties set
        organism = new TransientOrganism(
                organismId: 2,
                taxonomyId: 2,
                scientificName: "homo sapien",
                gcPercentage: "42",
                rscuCodonDistribution: [ACT: "20"],
                mcufCodonDistribution: [ACT: "20"]
        )
        assert organism.validate()
    }
}
