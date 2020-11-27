<%-- 
    Document   : view
    Created on : Sep 23, 2020, 4:49:07 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Page</title>
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

        <h1>View</h1>
        <c:set var="dto" value="${sessionScope.POSTDTO}"/>
        <c:if test="${not empty dto}">
            <form action="DispatcherController" method="POST">
                ${dto.name}
                <c:set var="ownerEmail" value="${dto.articleDto.owner}"/>
                <input type="hidden" name="txtOwner" value="${dto.articleDto.owner}" /><br/>
                ${dto.articleDto.date}
                <input type="hidden" name="txtDate" value="${dto.articleDto.date}" /><br/>
                ${dto.articleDto.title}<br/>
                ${dto.articleDto.description}<br/>          
                <c:forEach var="iDto" items="${dto.imageDto}" varStatus="counter">
                    <img src="data:image/jpg;base64,${iDto}" width="200" height="250"/>
                </c:forEach><br/>
                ${dto.numOfLike}                       
                <c:if test="${sessionScope.ACCOUNT.role == 'member'}">
                    <a href="DispatcherController?btAction=SendEmo&emo=LIKE&txtOwner=${dto.articleDto.owner}">Like</a>
                </c:if>
                ${dto.numOfDislike}
                <c:if test="${sessionScope.ACCOUNT.role == 'member'}">
                    <a href="DispatcherController?btAction=SendEmo&emo=DISLIKE&txtOwner=${dto.articleDto.owner}">Dislike</a><br/>
                    <input type="text" name="txtDetail" value="" />
                    <input type="submit" value="Comment" name="btAction" />
                    <font color="red">${requestScope.ERROR}</font>
                </c:if>
            </form>
            <c:set var="comments" value="${sessionScope.LISTCOMMENT}"/>
            <c:if test="${not empty comments}">
                <c:forEach var="comDto" items="${comments}" varStatus="counter">
                    <form action="DispatcherController">
                        <br/>
                        <input type="hidden" name="txtSender" value="${comDto.sender}" />
                        <input type="hidden" name="txtDate" value="${comDto.date}" />
                        <input type="hidden" name="txtReceiver" value="${ownerEmail}"/>
                        <input type="hidden" name="txtDateOfArticle" value="${comDto.dateOfArticle}" />
                        ${comDto.receiver}<!-- name of whom comment-->                          
                        ${comDto.date}
                        <c:if test="${comDto.sender eq sessionScope.ACCOUNT.email}">
                            <input type="submit" value="Remove" name="btAction" onclick="return confirm('Are you sure')"/>
                        </c:if><br/>
                        ${comDto.detail}<br/>

                    </form>
                </c:forEach>
            </c:if>
        </c:if>
    </body>
</html>
