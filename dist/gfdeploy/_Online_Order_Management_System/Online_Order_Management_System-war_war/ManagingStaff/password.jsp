<%-- 
    Document   : login
    Created on : Oct 11, 2020, 9:56:13 PM
    Author     : YANG
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Online Order System</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body>
        <jsp:include page="managing_staff_nav.jsp"></jsp:include>
        <h1 class="mx-auto text-center pt-5">Edit Password</h1>
        <div class="container pt-5" style="width: 20%;">          
            <form action="../Password?role=managing" method="POST">
                <div class="form-group">
                    <label for="username">Old Password:</label>
                    <input class="form-control" type="text" name="oldPassword" required>
                </div>
                <div class="form-group">
                    <label for="password">New Password:</label>
                    <input class="form-control" type="password" name="newPassword" required>
                </div>   
                <div class="text-center">
                    <input type="submit" class="btn btn-primary" value="Update"><br><br>
                </div>
            </form>
        </div>
    </body>
</html>
