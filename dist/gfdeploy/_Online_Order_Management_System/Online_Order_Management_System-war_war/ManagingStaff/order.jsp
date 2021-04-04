<%-- 
    Document   : managing_staff_product
    Created on : Oct 13, 2020, 10:58:22 AM
    Author     : YANG
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="managing_staff_nav.jsp"></jsp:include>
        <div class="container">
            <h1 class="text-center pt-3 pb-3">Order Information</h1>
            <select name="category" class="form-control float-left ml-2 category" style="width:30%"id="category">
                <option value="ALL">Show All</option>
                <option value="UNASSIGNED ORDER">Unassigned Order</option>
                <option value="ASSIGNED ORDER">Assigned Order</option>
                <option value="DELIVERING">On Delivering</option>
                <option value="PENDING PAYMENT">Pending Payment</option>
                <option value="DELIVERY COMPLETED">Delivery Completed</option>
                <option value="PAYMENT COMPLETED">Payment Completed</option>
            </select><br><br> 
            <table class="table">
                <thead>
                    <tr>
                        <th>Order ID</th>
                        <th>Total Amount (RM)</th>
                        <th>Status</th>
                        <th>Date</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="order" items="${orderList}">
                        <tr>
                            <td>${order.getId()}</td>
                            <td><fmt:formatNumber type = "number" pattern="0.00" value = "${order.getDueAmount()}" /></td>
                            <td>${order.getDelivery().getStatus()}</td>
                            <td>${order.getCreatedAt()}</td>
                            <td class="text-center">
                                <a href="Order?role=managing&action=detail&orderId=${order.getId()}" class="btn btn-primary">View Details</a>
                            </td>
                        </tr>
                    </c:forEach>                     
                </tbody>
            </table>      
        </div>
    </body>
    <script>
        $(document).ready(function () {
            if(${category != null}){
                $(".category").val("${category}");
            }else{
                $(".category").val("ALL");
            }
            $("select.category").change(function(){
                var category = $(this).children("option:selected").val();
                window.location.href = "Order?action=filterOrder&category="+category;
            });         
        });
    </script>
    
</html>
