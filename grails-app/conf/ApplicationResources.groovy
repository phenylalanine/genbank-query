modules = {
    application {
        resource url:'js/application.js'
    }

    mainpage {
        dependsOn 'jquery, bootstrap'
        resource url:'js/main.js'
    }
}