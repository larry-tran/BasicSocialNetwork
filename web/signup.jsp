<%-- 
    Document   : signup
    Created on : Sep 16, 2020, 10:25:15 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sign Up Page</title>
        <link rel="stylesheet" href="style1.css" />
    </head>
    <body>
        <div class="container">
            <header>Sign Up</header>
            <form action="DispatcherController" method="POST">
                <c:set var="err" value="${requestScope.ERRDTO}"/>
                <div class="input-field">
                    <input type="text" name="txtName" value="${param.txtName}"/>
                    <label>Name</label>
                </div>
                <c:if test="${not empty err.errName}">
                    <font color="red">${err.errName}</font>
                </c:if>
                <div class="input-field">
                    <input type="text" name="txtEmail" value="${param.txtEmail}" />
                    <label>Email</label>
                </div>
                <c:if test="${not empty err.errEmail}">
                    <font color="red">${err.errEmail}</font>
                </c:if>
                <c:if test="${not empty requestScope.ERREMAIL}">
                    <font color="red">${requestScope.ERREMAIL}</font>
                </c:if>
                <div class="input-field">
                    <input type="password" name="txtPassword" value="${param.txtPassword}" />
                    <label>Password</label>
                </div>
                <c:if test="${not empty err.errPassword}">
                    <font color="red">${err.errPassword}</font>
                </c:if>
                <div class="input-field">
                    <input type="password" name="txtConfirm" value="" />
                    <label>Confirm</label>
                </div>
                <c:if test="${not empty err.errConfirm}">
                    <font color="red">${err.errConfirm}</font>
                </c:if>
                <div class="input-field">
                    <input type="text" name="txtVerifyCode" value="" />
                    <label>Verify Code</label>
                </div>

                <input type="submit" value="SendCode" name="btAction" />

                <c:if test="${not empty err.errCode}">
                    <font color="red">${err.errCode}</font>
                </c:if>
                <div class="button">
                    <div class="inner"></div>
                    <button type="submit" value="Signup" name="btAction" /> Sign up
                </div>
                <div class="signup">
                    <a href="login.jsp">Back to login</a>
                </div>
        </div>
    </form>


</body>
</html>
