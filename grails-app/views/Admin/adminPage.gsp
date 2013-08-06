<%--
  Created by IntelliJ IDEA.
  User: Thang
  Date: 7/7/13
  Time: 3:28 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<style type="text/css">
* {
    margin: 0;
    padding: 0;
    font-family: "HelveticaNeue-Light", "Helvetica Neue Light", "Helvetica Neue", Helvetica, Arial, "Lucida Grande", sans-serif;
}

body {
    background-color: #ffffff;
}

#divWrapper {
    background-color: #efe;
    width: 20em;
    border: solid black;
    border-radius: 0 0 20px 20px;
    border-width: 0 1px 1px 1px;
    margin: 0 auto;
    padding: 2em 2em;
}

h2 {
    font-style: italic;
    font-size: 1.3em;
    color: #666;
    margin-top: 0px;
}

#divDatabase {
    margin: 10px 0 20px 0;
    padding-bottom: 10px;
    padding-top: 15px;
    border: solid black;
    border-width: 1px 0;
}

</style>

<html>
    <head>
      <title> Admin Page</title>
    </head>

    <body>
    <div id="divWrapper">
        <form name="frmMain" action="">
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
                <h2>Database</h2>
                <img id="imgVersion" />
                <ul>
                   <input type="hidden" name="genbankVersionValue" id="genbankVersionValue" value="${genbankVersion}">
                   <input type="hidden" name="syncedVersionValue" id="syncedVersionValue" value="${syncedVersion}">
                   <div id="syncedVersion"></div>
                   <div id="genbankVersion"></div>
                </ul>
                <input type="submit" name="syncGenbank" id="syncGenbank" value="Re-Sync" >
            </div>
        </form>
        </div>

    </body>
</html>

<g:javascript src='admin.js'>
    genbankVersion = ${version};
</g:javascript>