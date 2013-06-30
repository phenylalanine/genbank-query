package webapp



import org.junit.*
import grails.test.mixin.*

@TestFor(MeanCodonUsageController)
@Mock(MeanCodonUsage)
class MeanCodonUsageControllerTests {

    // auto-generated tests
//    def populateValidParams(params) {
//        assert params != null
//        // TODO: Populate valid properties like...
//        //params["name"] = 'someValidName'
//    }
//
    void testIndex() {
//        controller.index()
//        assert "/meanCodonUsage/list" == response.redirectedUrl
    }
//
//    void testList() {
//
//        def model = controller.list()
//
//        assert model.meanCodonUsageInstanceList.size() == 0
//        assert model.meanCodonUsageInstanceTotal == 0
//    }
//
//    void testCreate() {
//        def model = controller.create()
//
//        assert model.meanCodonUsageInstance != null
//    }
//
//    void testSave() {
//        controller.save()
//
//        assert model.meanCodonUsageInstance != null
//        assert view == '/meanCodonUsage/create'
//
//        response.reset()
//
//        populateValidParams(params)
//        controller.save()
//
//        assert response.redirectedUrl == '/meanCodonUsage/show/1'
//        assert controller.flash.message != null
//        assert MeanCodonUsage.count() == 1
//    }
//
//    void testShow() {
//        controller.show()
//
//        assert flash.message != null
//        assert response.redirectedUrl == '/meanCodonUsage/list'
//
//        populateValidParams(params)
//        def meanCodonUsage = new MeanCodonUsage(params)
//
//        assert meanCodonUsage.save() != null
//
//        params.id = meanCodonUsage.id
//
//        def model = controller.show()
//
//        assert model.meanCodonUsageInstance == meanCodonUsage
//    }
//
//    void testEdit() {
//        controller.edit()
//
//        assert flash.message != null
//        assert response.redirectedUrl == '/meanCodonUsage/list'
//
//        populateValidParams(params)
//        def meanCodonUsage = new MeanCodonUsage(params)
//
//        assert meanCodonUsage.save() != null
//
//        params.id = meanCodonUsage.id
//
//        def model = controller.edit()
//
//        assert model.meanCodonUsageInstance == meanCodonUsage
//    }
//
//    void testUpdate() {
//        controller.update()
//
//        assert flash.message != null
//        assert response.redirectedUrl == '/meanCodonUsage/list'
//
//        response.reset()
//
//        populateValidParams(params)
//        def meanCodonUsage = new MeanCodonUsage(params)
//
//        assert meanCodonUsage.save() != null
//
//        // test invalid parameters in update
//        params.id = meanCodonUsage.id
//        //TODO: add invalid values to params object
//
//        controller.update()
//
//        assert view == "/meanCodonUsage/edit"
//        assert model.meanCodonUsageInstance != null
//
//        meanCodonUsage.clearErrors()
//
//        populateValidParams(params)
//        controller.update()
//
//        assert response.redirectedUrl == "/meanCodonUsage/show/$meanCodonUsage.id"
//        assert flash.message != null
//
//        //test outdated version number
//        response.reset()
//        meanCodonUsage.clearErrors()
//
//        populateValidParams(params)
//        params.id = meanCodonUsage.id
//        params.version = -1
//        controller.update()
//
//        assert view == "/meanCodonUsage/edit"
//        assert model.meanCodonUsageInstance != null
//        assert model.meanCodonUsageInstance.errors.getFieldError('version')
//        assert flash.message != null
//    }
//
//    void testDelete() {
//        controller.delete()
//        assert flash.message != null
//        assert response.redirectedUrl == '/meanCodonUsage/list'
//
//        response.reset()
//
//        populateValidParams(params)
//        def meanCodonUsage = new MeanCodonUsage(params)
//
//        assert meanCodonUsage.save() != null
//        assert MeanCodonUsage.count() == 1
//
//        params.id = meanCodonUsage.id
//
//        controller.delete()
//
//        assert MeanCodonUsage.count() == 0
//        assert MeanCodonUsage.get(meanCodonUsage.id) == null
//        assert response.redirectedUrl == '/meanCodonUsage/list'
//    }

}
