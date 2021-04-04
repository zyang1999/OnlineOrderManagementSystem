<%-- 
    Document   : managing_staff_home
    Created on : Oct 13, 2020, 10:06:50 AM
    Author     : YANG
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <title>Managing Staff</title>
        
    </head>
    <body>
        <jsp:include page="managing_staff_nav.jsp"></jsp:include>
        <div class="container">
            <h1 class="text-center pt-3 pb-3">Customer Information</h1>
            <form action="ManageUser" class="mx-auto" style="width:40%">
                <div class="form-group row">
                    <input type="hidden" name="type" value="customer">
                    <input type="text" class="form-control col-sm-8" placeholder="Search for a staff" name="name"> 
                    <button tupe="submit" class="btn btn-secondary ml-1 col-sm-2" name="action" value="search">Search</button>
                </div>
            </form>
            <a href="User_Information?type=customer" class="btn btn-secondary float-left" role="button">Show All Customer</a>
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>Username</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Email</th>
                        <th>Phone Number</th>
                        <th>Role</th>
                        <th class="text-center">Action</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="user" items="${userList}">
                    <tr>
                        <td style="display:none;" class="userId">${user.getId()}</td>
                        <td>${user.getUsername()}</td>
                        <td>${user.getFirstName()}</td>
                        <td>${user.getLastName()}</td>
                        <td>${user.getEmail()}</td>
                        <td>${user.getPhoneNumber()}</td>
                        <td>${user.getRole()}</td>
                        <td class="text-center">
                            <a class="btn btn-primary" href="Profile?role=managing&userId=${user.getId()}">Update</a>
                            <button class="btn btn-danger delete">Delete</button>
                        </td>   
                    </tr>
                </c:forEach>                  
                </tbody>
            </table>            
        </div>
    </body>
    <script>
        $(document).ready(function () {
            $('.delete').click(function () {             
                var $userId = $(this).closest("tr").find(".userId").text();
                
                $.ajax({
                    type:'POST',
                    data:{
                        staffId: $userId,
                        action: "delete"
                    },
                    url:'ManageUser',
                    success:function(){
                        window.location.href = "User_Information?type=customer";
                        alert("Deleted successfully!")
                    }
                });
            });
        });
      </script>
</html>
