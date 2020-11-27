<%-- 
    Document   : notification
    Created on : Sep 24, 2020, 5:21:17 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Notification Page</title>
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
                <li><a class="active" href="#">Notification</a></li>               
                <li><a href="DispatcherController?btAction=Search">Search</a></li>
                <li><a href="DispatcherController?btAction=Manage">Manage</a></li>
                <li><a href="DispatcherController?btAction=Logout">Logout</a></li>
            </ul>
        </nav>

        <h1>Notification table</h1>
        <c:set var="acount" value="${sessionScope.ACCOUNT}"/>
        <c:set var="result" value="${sessionScope.NOTIFILIST}"/>
        <c:if test="${not empty result}">
            <c:forEach var="dto" items="${result}" varStatus="counter">
                <form action="DispatcherController" method="POST">
                    <input type="hidden" name="txtOwner" value="${dto.receiver}" />
                    <input type="hidden" name="txtDate" value="${dto.dateOfArticle}" />
                    ${dto.showNotification()}
                    <input type="submit" value="Read More" name="btAction" />                   
                </form><br/>
            </c:forEach>
        </c:if>
    </body>
</html>
