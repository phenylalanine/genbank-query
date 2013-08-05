package webapp



import grails.test.mixin.*
import org.codehaus.groovy.grails.plugins.testing.GrailsMockMultipartFile
import org.junit.*
import org.springframework.mock.web.MockMultipartHttpServletRequest

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(MainController)
class MainControllerTests {

    /**
     * Test valid request params for upload
     */
    void testValidUpload() {
        // setup file
        //controller.metaClass.request = new MockMultipartHttpServletRequest()
        def uploadFile0 = new GrailsMockMultipartFile("userSequenceFile0", "GTCA" as byte[])
        def uploadFile1 = new GrailsMockMultipartFile("userSequenceFile1", "GTCA" as byte[])
        request.setParameter("userOrganism0", "Drosophila melanogaster")
        request.setParameter("userOrganism1", "Drosophila meganocator")
        request.addFile(uploadFile0)
        request.addFile(uploadFile1)
        controller.upload()
        assert response.status == 302
    }

    /**
     * Test missing request params for required params:
     * organismName, userSeqFile
     */
    void testMissingParams() {
        controller.upload()
        assert response.status == 400
    }

    /**
     * Test missing request params for required params:
     * userSeqFile
     */
    void testMissingFile() {
        request.setParameter("userOrganism0", "Drosophila melanogaster")
        controller.upload()
        assert response.status == 400
    }

    /**
     * Test missing request params for required params:
     * organismName
     */
    void testMissingName() {
        controller.metaClass.request = new MockMultipartHttpServletRequest()
        def uploadFile = new GrailsMockMultipartFile("userSequenceFile0", "GTCA" as byte[])
        controller.request.addFile(uploadFile)
        controller.upload()
        assert response.status == 400
    }


    /**
     * Test view for index of controller
     */
    void testIndex() {
        controller.index()
        assert view == "/main/index"
    }
}
