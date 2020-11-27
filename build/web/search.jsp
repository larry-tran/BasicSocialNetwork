<%-- 
    Document   : search
    Created on : Sep 15, 2020, 11:00:20 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Page</title>
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
                <li><a class="active" href="DispatcherController?btAction=Search">Search Page</a></li>
                <li><a href="DispatcherController?btAction=Manage">Manage</a></li>
                <li><a href="DispatcherController?btAction=Logout">Logout</a></li>
            </ul>
            <div class="box">
                <form action="DispatcherController">
                    <input type="text" class="input" placeholder="search..." name="txtSearch" value="${param.txtSearch}">
                    <input type="submit" value="Search" name="btAction" />
                </form>

            </div>
        </nav>

    <center>
        <c:if test="${sessionScope.ACCOUNT.role == 'member'}">
            <a href="post.jsp">What are you thinking</a><br/>
        </c:if>  
        <c:set var="searchValue" value="${param.txtSearch}"/>
        <c:if test="${not empty searchValue}">
            <c:set var="result" value="${requestScope.LIST}"/>
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
                        <input type="submit" value="Read More" name="btAction" />
                    </form>
                </c:forEach>
            </c:if>
            <c:if test="${empty result}">
                <h2>No record</h2>
            </c:if>
        </c:if>

        Page<c:forEach var = "i" begin = "1" end = "${requestScope.PAGECOUNT}">
            <a href="DispatcherController?txtSearch=${searchValue}&btAction=Search&numPage=${i}">${i}</a>
        </c:forEach>
    </center>       
</body>
</html>
