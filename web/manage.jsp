<%-- 
    Document   : managePost
    Created on : Sep 24, 2020, 9:10:27 AM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Post</title>
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
                <li><a href="DispatcherController?btAction=Manage" class="active">Manage</a></li>
                <li><a href="DispatcherController?btAction=Logout">Logout</a></li>
            </ul>
        </nav>
    <center>
        <h1>Manage Post</h1>
        <c:set var="result" value="${requestScope.AUTHPOST}"/>
            <c:if test="${not empty result}">
                <c:forEach var="dto" items="${result}" varStatus="counter">
                    <form action="DispatcherController" method="POST">
                        ${dto.name}<br/>
                        <input type="hidden" name="txtOwner" value="${dto.articleDto.owner}" />
                        ${dto.articleDto.date}<br/>
                        ${dto.articleDto.title}<br/>
                        ${dto.articleDto.description}<br/>
                        <input type="hidden" name="txtDate" value="${dto.articleDto.date}" />
                        ${dto.numOfLike}
                        ${dto.numOfDislike}<br/>
                        <c:forEach var="imgDto" items="${dto.imageDto}" varStatus="counter">            
                            <img src="data:image/jpg;base64,${imgDto}" width="200" height="250"/>
                        </c:forEach><br/>
                        <input type="submit" value="Delete" name="btAction" onclick="return confirm('Are you sure')"/>
                        <input type="submit" value="Read More" name="btAction" />
                    </form>
                </c:forEach>
            </c:if>
    </center>
    </body>
</html>
