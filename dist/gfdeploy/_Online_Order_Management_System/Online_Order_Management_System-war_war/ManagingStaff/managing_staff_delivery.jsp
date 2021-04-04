<%-- 
    Document   : manaing_staff_delivery
    Created on : Oct 13, 2020, 10:39:31 AM
    Author     : YANG
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="managing_staff_nav.jsp"></jsp:include>
        <div class="container">
            <h1 class="text-center pt-3 pb-3">Assign Delivery</h1>
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>Username</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Phone Number</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="staff" items="${userList}">
                        <tr>
                            <td style="display:none;" class="staffId">${staff.getId()}</td>
                            <td>${staff.getUsername()}</td>
                            <td>${staff.getFirstName()}</td>
                            <td>${staff.getLastName()}</td>
                            <td>${staff.getPhoneNumber()}</td>
                            <td class="text-center">
                                <button class="btn btn-primary assign" >Assign</button>
                            </td>   
                        </tr>
                </c:forEach>   
                </tbody>
            </table>             
        </div>
    </body>
    <script>
        $(document).ready(function () {
            $('.assign').click(function () {             
                var $staffId = $(this).closest("tr").find(".staffId").text();
                $.ajax({
                    type:'POST',
                    data:{
                        staffId: $staffId,
                        orderId: ${orderId},
                        action: "assign"
                    },
                    url:'ManageUser',
                    success:function(data){
                        window.location.href = "Order?action=all&role=managing";
                        alert("Assigned Successfully!");
                    },
                    failure: function(){
                        alert("failed");
                    }
                });
            });
        });
    </script>
</html>
