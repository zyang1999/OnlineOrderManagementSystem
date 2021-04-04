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
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="js/addons/rating.js"></script>
        <title>Receipt</title>
    </head>
    <body>
        <jsp:include page="managing_staff_nav.jsp"></jsp:include>
            <div class="container">
                <h1 class="text-center pt-3 pb-3">Payment Receipt</h1>
                <h6>Order ID: ${payment.getId()}</h6>
            <h6>Recipient: ${customer.getFirstName()} ${customer.getLastName()}</h6>
            <h6>Deliver To:</h6>
            <h6>${orderList.get(0).getAddress()}</h6>
            <h6>Ordered Date: ${payment.getCreatedAt()}</h6>
            <h6>Payment On: ${payment.getPayOn()}</h6>
            <h6>Collected By: ${payment.getCollectedBy().getUsername()}</h6>
            <table class="table">
                <thead>
                    <tr>
                        <th>Product ID</th>
                        <th>Product Name</th>
                        <th>Price per Item</th>
                        <th>Quantity</th>
                        <th>Subtotal (RM)</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="order" items="${orderList}">
                        <tr>
                            <td style="display: none" class="orderId">${order.getId()}</td>
                            <td>${order.getProduct().getId()}</td>
                            <td>${order.getProduct().getName()}</td>
                            <td><fmt:formatNumber type = "number" pattern="0.00" value = "${order.getProduct().getPrice()}" /></td>
                            <td>${order.getQuantity()}</td>
                            <td><fmt:formatNumber type = "number" pattern="0.00" value = "${order.getSubtotal()}" /></td> 
                        </tr>
                    </c:forEach>                     
                </tbody>
            </table>
            <div class="float-right" >
                <h5>Total Amount: RM <fmt:formatNumber type = "number" pattern="0.00" value = "${payment.getDueAmount()}" /></h5>
                <h5>Total Amount Paid: RM <fmt:formatNumber type = "number" pattern="0.00" value = "${payment.getAmountReceived()}" /></h5>
                <h5>Change: RM ${change}</h5>
            </div>
        </div>
    </body>
</html>
