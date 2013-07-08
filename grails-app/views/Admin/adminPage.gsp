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
    </head>

    <body>
        <div id="status" role="complementary">
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

            <h2>Database</h2>
            <ul>
                <li>Database version: </li>
                <input type="submit" name="updateDatabase" id="updateDatabase" value="Update" >
                <li>Genbank version:</li>
                <input type="submit" name="syncGenbank" id="syncGenbank" value="Re-Sync" >
            </ul>
        </div>
    </body>
</html>