modules = {
    application {
        resource url:'js/application.js'
    }

    mainpage {
        dependsOn 'jquery, bootstrap'
        resource url:'js/main.js'
    }

    admin {
        resource url:'js/admin.js'
        resource url:'images/lPg.gif'
        resource url:'images/lPr.gif'
    }
}