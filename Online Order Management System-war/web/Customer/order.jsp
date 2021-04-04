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
        <jsp:include page="navigator.jsp"></jsp:include>
        <div class="container">
            <h1 class="text-center pt-3 pb-3">Order Information</h1>
            <a href="ManageProduct?role=customer&action=filter&category=ALL" class="btn btn-info float-right" role="button">Add New Order</a><br><br> 
            <table class="table">
                <thead>
                    <tr>
                        <th>Order ID</th>
                        <th>Total Amount</th>
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
                                <a href="Order?action=detail&role=customer&orderId=${order.getId()}" class="btn btn-primary">View Details</a>
                            </td>   
                        </tr>
                    </c:forEach>                     
                </tbody>
            </table>      
        </div>
    </body>
</html>
