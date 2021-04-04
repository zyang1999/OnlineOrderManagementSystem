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
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="js/addons/rating.js"></script>
        <title>Cart</title>
    </head>
    <body>
        <jsp:include page="managing_staff_nav.jsp"></jsp:include>
        <div class="container">
            <h1 class="text-center pt-3 pb-3">Order Details</h1>
            <h6>Order ID: ${orderId}</h6>
            <h6>Recipient: ${customer.getFirstName()} ${customer.getLastName()}</h6>
            <h6>Deliver To:</h6>
            <h6>${address}</h6>
            <h6>Ordered Date: ${payment.getCreatedAt()}</h6>
            <table class="table">
                <thead>
                    <tr>
                        <th>Product ID</th>
                        <th>Product Name</th>
                        <th>Price per Item （RM）</th>
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
            <div>
                <h5 class="float-right">Total Amount: RM <fmt:formatNumber type = "number" pattern="0.00" value = "${totalAmount}" /></h5>
            </div>
            <div class="float-left mt-5">
                <c:if test="${status == 'PENDING'}">
                    <a href="User_Information?type=delivery&orderId=${orderId}" class="btn btn-primary">Assign Delivery</a>
                </c:if>
                <c:if test="${orderList.get(0).getPayment().getStatus()== 'PENDING'}"> 
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#payment">Collect Payment</button>
                    <div class="modal" id="payment">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                  <h4 class="modal-title">Payment Received</h4>
                                  <button type="button" class="close" data-dismiss="modal">&times;</button>
                                </div>
                                <div class="modal-body">
                                    <form action="ManagePayment?action=collect" method="POST">
                                        <div class="form-group">
                                            <input type="hidden" name="paymentId" value="${orderList.get(0).getPayment().getId()}">
                                            <label for="amount">Total Amount Received:</label>
                                            <input class="form-control" type="number" name="amount" required>
                                        </div>     
                                        <div class="text-center">
                                            <input type="submit" class="btn btn-primary" value="Collect">
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:if>
                <c:if test="${orderList.get(0).getPayment().getStatus()== 'COMPLETED'}">
                    <a href="ManagePayment?action=view&paymentId=${orderList.get(0).getPayment().getId()}" class="btn btn-primary">View Receipt</a>
                </c:if>
            </div>
        </div>
    </body>
</html>
