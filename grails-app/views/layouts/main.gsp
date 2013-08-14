<%@ page import="org.apache.shiro.SecurityUtils" %>
<%--
  Created by IntelliJ IDEA.
  User: Joseph Lee <josel@pdx.edu>
  Date: 7/17/13
  Time: 1:00 AM
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
    <%--
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css">
    --%>

    <r:layoutResources />
    <g:layoutHead/>

</head>

<body style="background-color: #ffffff">

<%--
    Upload Modal
--%>
<div id="upload" class="modal fade">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h3>Add Sequences to Analyze</h3>
    </div>
    <div class="modal-body">
        <g:uploadForm action="upload" class="form-horizontal" name="upload-form">
            <div id="upload-form-list"></div>

            <div class="add-upload" style="text-align: center">
                <input type="button" id="upload" class="btn add-seq" value="Add Custom Sequence"/>
                <input type="button" id="genbank" class="btn add-seq" value="Add GenBank Sequence"/>
                <input type="submit" class="btn btn-submit" disabled/>
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
            <g:if test="${SecurityUtils.subject.isPermitted("admin")}">
                <li class="dropdown">
                    <a class="dropdown-toggle" id="dLabel" role="button" data-toggle="dropdown" href="#">
                        admin
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
                        <li>
                            <g:link controller="admin" action="index">Admin Page</g:link>
                        </li>
                        <li>
                            <g:link controller="auth" action="signOut">Log Out</g:link>
                        </li>
                    </ul>
                </li>
            </g:if>
            <g:else>
                <li>
                    <g:link controller="auth" action="login">Log in</g:link>
                </li>
            </g:else>

            <li><a href="#" id="upload-nav">Analyze Sequences</a></li>

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
