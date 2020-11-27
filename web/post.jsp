<%-- 
    Document   : share
    Created on : Sep 18, 2020, 9:02:38 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Post Page</title>
        <link rel="stylesheet" href="style.css" />
    </head>
    <body>
        <nav>
            <div class="logo">
                Welcome ${sessionScope.ACCOUNT.name}
            </div>
            <input type="checkbox" id="click">
            <label for="click" class="menu-btn">
                <i class="fas fa-bars"></i>
            </label>
            <ul>
                <li><c:if test="${sessionScope.NUMNOTI > 0}">
                        <p>${sessionScope.NUMNOTI}</p>
                    </c:if> </li>
                <li><a href="DispatcherController?btAction=Notification">Notification</a></li>
                <li><a href="DispatcherController?btAction=Search">Search</a></li>
                <li><a href="DispatcherController?btAction=Manage">Manage</a></li>
                <li><a href="DispatcherController?btAction=Logout">Logout</a></li>
            </ul>
        </nav>
            <br/>
    <center>
        <div class="container">
            <header>Hey, tell us what you think!</header>
                <c:set var="artDto" value="${sessionScope.ARTICLE}"/>
            <form action="DispatcherController" method="POST" enctype="multipart/form-data">
                <c:set var="dto" value="${requestScope.ERROR}"/>
                <div class="input-field">
                    <input type="text" name="txtTitle" value="${param.txtTitle}" placeholder="200 characters"/><br/>
                    <label>Title</label>
                </div>
                <font color="red">${requestScope.ERRORACCOUNT.errEmail}</font><br/>
                <div class="input-field">
                    <input type="text" placeholder="2000 characters" name="txtDes"></textarea><br/>
                    <label>Description</label>
                </div>
                <c:if test="${not empty dto.title}">
                    <font color="red">${dto.title}</font><br/>
                </c:if>
                <c:if test="${not empty dto.description}">
                    <font color="red">${dto.description}</font>
                </c:if><br/>

                <input type="file" name="file" multiple="multiple" accept="image/*"/><br/>


                <div class="button">
                    <div class="inner"></div>
                    <button type="submit" value="Post" name="btAction" /> Post
                </div>
            </form>
        </div>
    </center>
</body>
</html>
