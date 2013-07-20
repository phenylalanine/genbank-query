<%--
  Created by IntelliJ IDEA.
  User: monleezy
  Date: 7/17/13
  Time: 1:00 AM
  To change this template use File | Settings | File Templates.
--%>

<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title><g:layoutTitle default="GenBank-Query"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <r:require module="bootstrap"/>

    <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
    <link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
    <link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}">

    <link rel="stylesheet" href="${resource(dir: 'css', file: 'mobile.css')}" type="text/css">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css">

    <r:layoutResources />
    <g:layoutHead/>

</head>

<body style="background-color: #ffffff">

<%--
    Upload Modal
--%>
<div id="upload" class="modal fade hide">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h3>Upload Sequence</h3>
    </div>
    <div class="modal-body">
        <g:uploadForm action="upload" class="form-horizontal">
            <div class="control-group">
                <label class="control-label" for="userSequenceFile">
                    Sequence File
                </label>
                <div class="controls">
                    <input type="File" id="userSequenceFile" name="userSequenceFile" required
                           style="background-color: rgba(0, 0, 0, 0); border: 0;"/>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="userOrganism">
                    Organism Name
                </label>
                <div class="controls">
                    <input type="text" name="userOrganism" id="userOrganism"
                           placeholder="Enter scientific name" required/>
                </div>
            </div>
            <div class="control-group">
                <div class="controls">
                    <input type="submit" />
                </div>
            </div>
        </g:uploadForm>
    </div>
</div>

<%-- Top-level div --%>
<div class="container-narrow" id="main-body">
    <%--
        Navigation Bar
    --%>
    <div class="masthead">
        <ul class="nav nav-pills pull-right">
            <li><a href="#" id="upload-nav">Upload Sequence</a></li>
            <li>
                <form class="navbar-search">
                    <input type="text" class="search-query" placeholder="Search Organisms" autocomplete="off">
                </form>
            </li>
        </ul>
        <h2 class="muted">GenBank-Query
            <a href="http://github.com/phenylalanine/genbank-query">
            <r:img uri="/images/GitHub-Mark-32px.png" height="24px" width="24px" /></a>
        </h2>
    </div>

    <hr>

    <g:layoutBody/>

</div>



<r:require module="mainpage" />
<r:layoutResources />



</body>
</html>
