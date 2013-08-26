<%--
  Created by IntelliJ IDEA.
  User: Thang
  Date: 7/7/13
  Time: 3:28 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>


<html>
    <head>
      <title> Admin Page</title>
        <meta name="layout" content="main"/>
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css">
    </head>

    <body>
        <h2>Application Status</h2>
            <ul>
                <li>App version: <g:meta name="app.version"/></li>
                <li>Grails version: <g:meta name="app.grails.version"/></li>
                <li>Groovy version: ${GroovySystem.getVersion()}</li>
                <li>JVM version: ${System.getProperty('java.version')}</li>
                <li>Reloading active: ${grails.util.Environment.reloadingAgentEnabled}</li>
                <li>Controllers: ${grailsApplication.controllerClasses.size()}</li>
                <li>Domains: ${grailsApplication.domainClasses.size()}</li>
                <li>Services: ${grailsApplication.serviceClasses.size()}</li>
                <li>Tag Libraries: ${grailsApplication.tagLibClasses.size()}</li>
            </ul>
        <div id="divDatabase">
            <h2>Database <img id="imgVersion" /></h2>

            <ul>
               <input type="hidden" name="genbankVersionValue" id="genbankVersionValue" value="${genbankVersion}">
               <input type="hidden" name="syncedVersionValue" id="syncedVersionValue" value="${syncedVersion}">
               <div id="syncedVersion"></div>
               <div id="genbankVersion"></div>
            </ul>
            <input type="submit" name="syncGenbank" id="syncGenbank" value="Re-Sync" >
        </div>


    <g:javascript src='admin.js'>
        genbankVersion = ${version};
    </g:javascript>
    </body>

</html>

