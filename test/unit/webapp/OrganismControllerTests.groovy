package webapp

import grails.test.mixin.*

@TestFor(OrganismController)
@Mock(Organism)
class OrganismControllerTests {

//    def populateValidParams(params) {
//        assert params != null
//        // TODO: Populate valid properties like...
//        //params["name"] = 'someValidName'
//    }
//
    void testIndex() {
//        controller.index()
//        assert "/organism/list" == response.redirectedUrl
    }
//
//    void testList() {
//
//        def model = controller.list()
//
//        assert model.organismInstanceList.size() == 0
//        assert model.organismInstanceTotal == 0
//    }
//
//    void testCreate() {
//        def model = controller.create()
//
//        assert model.organismInstance != null
//    }
//
//    void testSave() {
//        controller.save()
//
//        assert model.organismInstance != null
//        assert view == '/organism/create'
//
//        response.reset()
//
//        populateValidParams(params)
//        controller.save()
//
//        assert response.redirectedUrl == '/organism/show/1'
//        assert controller.flash.message != null
//        assert Organism.count() == 1
//    }
//
//    void testShow() {
//        controller.show()
//
//        assert flash.message != null
//        assert response.redirectedUrl == '/organism/list'
//
//        populateValidParams(params)
//        def organism = new Organism(params)
//
//        assert organism.save() != null
//
//        params.id = organism.id
//
//        def model = controller.show()
//
//        assert model.organismInstance == organism
//    }
//
//    void testEdit() {
//        controller.edit()
//
//        assert flash.message != null
//        assert response.redirectedUrl == '/organism/list'
//
//        populateValidParams(params)
//        def organism = new Organism(params)
//
//        assert organism.save() != null
//
//        params.id = organism.id
//
//        def model = controller.edit()
//
//        assert model.organismInstance == organism
//    }
//
//    void testUpdate() {
//        controller.update()
//
//        assert flash.message != null
//        assert response.redirectedUrl == '/organism/list'
//
//        response.reset()
//
//        populateValidParams(params)
//        def organism = new Organism(params)
//
//        assert organism.save() != null
//
//        // test invalid parameters in update
//        params.id = organism.id
//        //TODO: add invalid values to params object
//
//        controller.update()
//
//        assert view == "/organism/edit"
//        assert model.organismInstance != null
//
//        organism.clearErrors()
//
//        populateValidParams(params)
//        controller.update()
//
//        assert response.redirectedUrl == "/organism/show/$organism.id"
//        assert flash.message != null
//
//        //test outdated version number
//        response.reset()
//        organism.clearErrors()
//
//        populateValidParams(params)
//        params.id = organism.id
//        params.version = -1
//        controller.update()
//
//        assert view == "/organism/edit"
//        assert model.organismInstance != null
//        assert model.organismInstance.errors.getFieldError('version')
//        assert flash.message != null
//    }
//
//    void testDelete() {
//        controller.delete()
//        assert flash.message != null
//        assert response.redirectedUrl == '/organism/list'
//
//        response.reset()
//
//        populateValidParams(params)
//        def organism = new Organism(params)
//
//        assert organism.save() != null
//        assert Organism.count() == 1
//
//        params.id = organism.id
//
//        controller.delete()
//
//        assert Organism.count() == 0
//        assert Organism.get(organism.id) == null
//        assert response.redirectedUrl == '/organism/list'
//    }
}
