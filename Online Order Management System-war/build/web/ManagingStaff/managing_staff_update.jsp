<%-- 
    Document   : managing_staff_update
    Created on : Oct 13, 2020, 10:54:29 PM
    Author     : YANG
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        ${staff}
        <jsp:include page="managing_staff_nav.jsp"></jsp:include>
        <h1 class="text-center pt-5">Update Staff</h1>
        <div class="container pt-5" style="width: 40%;">          
            <form action="ManageUser" method="POST">
                <div class="form-group">
                    <label for="username">User ID:</label>
                    <input class="form-control" type="text" id="username" name="userId" value="${profile.getId()}"readonly >
                </div>
                <div class="form-group">
                    <label for="username">Username:</label>
                    <input class="form-control" type="text" id="username" name="username" value="${profile.getUsername()}"readonly >
                </div>
                <div class="row">
                    <div class="form-group col">
                        <label for="firstname">First Name:</label>
                        <input class="form-control" type="text" id="firstName" name="firstName" value="${profile.getFirstName()}" required>
                    </div>
                    <div class="form-group col">
                        <label for="lastName">Last Name:</label>
                        <input class="form-control" type="text" id="lastName" name="lastName" value="${profile.getLastName()}" required>
                    </div>
                </div>
                <div class="form-group">
                    <label for="username">Email Address:</label>
                    <input class="form-control" type="email" id="email" name="email" value="${profile.getEmail()}" readonly>
                </div>
                <div class="form-group">
                    <label for="username">Contact Number:</label>
                    <input class="form-control" type="number" id="phoneNumber" name="phoneNumber" value="${profile.getPhoneNumber()}" required>
                </div>
                <div class="form-group role">
                    <label for="role" >Role:</label><br>
                    <select name="role" class="form-control"id="roles">
                        <option value="managing">Managing Staff</option>
                        <option value="delivery">Delivery Staff</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="username">Home Address:</label>
                    <textarea class="form-control mt-3" rows="5" name="address"required>${profile.getAddress()}</textarea>
                </div>
                <button type="submit" class="btn btn-primary mb-5" name="action" value="update">Update</button>
        </form>
        </div>
    </body>
    <script>
        $(document).ready(function(){
            $("div.role select").val('${profile.getRole()}');
        });
    </script>
</html>
