/**
 * Created with Eclipse!
 * User: Jim Miller (JGM)
 * Date: 6/30/13
 */

package webapp

import org.junit.*
import grails.test.mixin.*

@TestFor(RSCUController)
@Mock(RSCU)
class RSCUControllerTests {

//    def populateValidParams(params) {
//        assert params != null
//        // TODO: Populate valid properties like...
//        //params["name"] = 'someValidName'
//    }
//
    void testIndex() {
//        controller.index()
//        assert "/RSCU/list" == response.redirectedUrl
    }
//
//    void testList() {
//
//        def model = controller.list()
//
//        assert model.RSCUInstanceList.size() == 0
//        assert model.RSCUInstanceTotal == 0
//    }
//
//    void testCreate() {
//        def model = controller.create()
//
//        assert model.RSCUInstance != null
//    }
//
//    void testSave() {
//        controller.save()
//
//        assert model.RSCUInstance != null
//        assert view == '/RSCU/create'
//
//        response.reset()
//
//        populateValidParams(params)
//        controller.save()
//
//        assert response.redirectedUrl == '/RSCU/show/1'
//        assert controller.flash.message != null
//        assert RSCU.count() == 1
//    }
//
//    void testShow() {
//        controller.show()
//
//        assert flash.message != null
//        assert response.redirectedUrl == '/RSCU/list'
//
//        populateValidParams(params)
//        def RSCU = new RSCU(params)
//
//        assert RSCU.save() != null
//
//        params.id = RSCU.id
//
//        def model = controller.show()
//
//        assert model.RSCUInstance == RSCU
//    }
//
//    void testEdit() {
//        controller.edit()
//
//        assert flash.message != null
//        assert response.redirectedUrl == '/RSCU/list'
//
//        populateValidParams(params)
//        def RSCU = new RSCU(params)
//
//        assert RSCU.save() != null
//
//        params.id = RSCU.id
//
//        def model = controller.edit()
//
//        assert model.RSCUInstance == RSCU
//    }
//
//    void testUpdate() {
//        controller.update()
//
//        assert flash.message != null
//        assert response.redirectedUrl == '/RSCU/list'
//
//        response.reset()
//
//        populateValidParams(params)
//        def RSCU = new RSCU(params)
//
//        assert RSCU.save() != null
//
//        // test invalid parameters in update
//        params.id = RSCU.id
//        //TODO: add invalid values to params object
//
//        controller.update()
//
//        assert view == "/RSCU/edit"
//        assert model.RSCUInstance != null
//
//        RSCU.clearErrors()
//
//        populateValidParams(params)
//        controller.update()
//
//        assert response.redirectedUrl == "/RSCU/show/$RSCU.id"
//        assert flash.message != null
//
//        //test outdated version number
//        response.reset()
//        RSCU.clearErrors()
//
//        populateValidParams(params)
//        params.id = RSCU.id
//        params.version = -1
//        controller.update()
//
//        assert view == "/RSCU/edit"
//        assert model.RSCUInstance != null
//        assert model.RSCUInstance.errors.getFieldError('version')
//        assert flash.message != null
//    }
//
//    void testDelete() {
//        controller.delete()
//        assert flash.message != null
//        assert response.redirectedUrl == '/RSCU/list'
//
//        response.reset()
//
//        populateValidParams(params)
//        def RSCU = new RSCU(params)
//
//        assert RSCU.save() != null
//        assert RSCU.count() == 1
//
//        params.id = RSCU.id
//
//        controller.delete()
//
//        assert RSCU.count() == 0
//        assert RSCU.get(RSCU.id) == null
//        assert response.redirectedUrl == '/RSCU/list'
//    }
}
