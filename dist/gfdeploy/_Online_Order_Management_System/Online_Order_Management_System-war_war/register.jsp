<%-- 
    Document   : Managing_staff_add
    Created on : Oct 13, 2020, 7:19:48 PM
    Author     : YANG
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="navigator.jsp"></jsp:include>
        <h1 class="text-center pt-5">Register</h1>
        <div class="container pt-5" style="width: 40%;">          
            <form id="form" action="ManageUser" method="POST">
                <div class="form-group">
                    <label for="username">Username:</label>
                    <input class="form-control" type="text" id="username" name="username" required>
                </div>
                <div class="row">
                    <div class="form-group col">
                        <label for="firstname">First Name:</label>
                        <input class="form-control" type="text" id="firstName" name="firstName" required>
                    </div>
                    <div class="form-group col">
                        <label for="lastName">Last Name:</label>
                        <input class="form-control" type="text" id="lastName" name="lastName" required>
                    </div>
                </div>
                <div class="form-group">
                    <label for="username">Email Address:</label>
                    <input class="form-control" type="email" id="email" name="email" required>
                </div>
                <div class="form-group">
                    <label for="username">Contact Number:</label>
                    <input class="form-control" type="number" id="phoneNumber" name="phoneNumber" required>
                </div>
                <div class="form-group">
                    <label for="username">Home Address:</label>
                    <textarea class="form-control mt-3" rows="5" name="address" required></textarea>
                </div>
                <div class="form-group">
                    <label for="password">Password:</label>
                    <input class="form-control" type="password" id="password" name="password" required>
                </div>    
                <div class="text-center">
                    <button type="submit" class="btn btn-primary mb-5" name="action" value="register">Register</button>
                </div>
            </form>
        </div>
    </body>
</html>
