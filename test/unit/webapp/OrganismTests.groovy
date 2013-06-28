package webapp



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Organism)
class OrganismTests {

    void testConstraints() {
        def validOrganism = new Organism(organismId: 1, taxonomyId: 1)
        mockForConstraintsTests(Organism, [validOrganism])

        //make an organism with no organismId, this should fail
        def organism = new Organism()
        assert !organism.validate()

        //check to make sure the lack of an id threw a nullable error code
        assert "nullable" == organism.errors["organismId"]

        //test the unique constraint on organismId
        organism = new Organism(organismId: 1)
        assert !organism.validate()
        assert "unique" == organism.errors["organismId"]

        //Make sure we can create a valid organism with all properties set
        organism = new Organism(organismId: 2, taxonomyId: 2, scientificName: "homo sapien")
        assert organism.validate()

        //Make sure the default value for completeGenome and the nullable
        //properties for taxonomyId, scientificName, and taxonomy are enforced.
        //We should be able to create a valid organism with just a unique id
        organism = new Organism(organismId: 3)
        assert organism.validate()

        //test that we can create an organism with taxonomy data
        organism = new Organism(organismId: 4)
        organism.taxonomy = [order: "Chroococcales", class: "Synechococcus"]
        assert organism.validate()
    }
}
