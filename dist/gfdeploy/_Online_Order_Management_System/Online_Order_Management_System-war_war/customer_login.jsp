<%-- 
    Document   : login
    Created on : Oct 11, 2020, 9:56:13 PM
    Author     : YANG
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Online Order System</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body>
        <jsp:include page="navigator.jsp"></jsp:include>
        <h1 class="mx-auto text-center pt-5">Online Order Management System</h1>
        <h6 class="mx-auto text-center pt-3">for Customer</h6>
        <div class="container pt-5" style="width: 20%;">          
            <form action="Login?type=customer&role=customer" method="POST">
                <div class="form-group">
                    <label for="username">Username:</label>
                    <input class="form-control" type="text" id="username" name="username" required>
                </div>
                <div class="form-group">
                    <label for="password">Password:</label>
                    <input class="form-control" type="password" id="password" name="password" required>
                </div>    
                <div class="text-center">
                    <input type="submit" class="btn btn-primary" value="Log In"><br><br>
                    <a href="register.jsp" class="mt-3">Create new Account</a>
                </div>
                
            </form>
            
        </div>
    </body>
</html>
