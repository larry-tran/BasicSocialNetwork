<%-- 
    Document   : login
    Created on : Sep 28, 2020, 8:09:33 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <link rel="stylesheet" href="style1.css" />
    </head>
    <body>
        <div class="container">
            <header>Login</header>
            <form action="DispatcherController" method="POST">
                <div class="input-field">
                    <input type="text" name="txtEmail" value="${param.txtEmail}"/><br/>
                    <label>Email</label>
                </div>
                <font color="red">${requestScope.ERRORACCOUNT.errEmail}</font><br/>
                <div class="input-field">
                    <input class="pswrd" type="password" name="txtPassword">
                    <label>Password</label>
                </div>
                <font color="red">${requestScope.ERRORACCOUNT.errPassword}</font><br/>
                <div class="button">
                    <div class="inner"></div>
                    <button type="submit" value="Login" name="btAction" /> Login
                </div>
            </form>
            <div class="signup">
                Not a member? <a href="signup.jsp">Signup now</a>
            </div>
        </div>
    </body>
</html>
