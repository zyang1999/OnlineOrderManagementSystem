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
        <title>Cart</title>
    </head>
    <body>
        <jsp:include page="navigator.jsp"></jsp:include>
            <div class="container">
                <h1 class="text-center pt-3 pb-3">On Cart</h1>
                <table class="table">
                    <thead>
                        <tr>
                            <th>Product ID</th>
                            <th>Product Name</th>
                            <th>Price per Item (RM)</th>
                            <th>Quantity</th>
                            <th>Subtotal (RM)</th>
                            <th class="text-center">Action</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="order" items="${cartList}">
                        <tr>
                            <td style="display: none" class="orderId">${order.getId()}</td>
                            <td>${order.getProduct().getId()}</td>
                            <td>${order.getProduct().getName()}</td>
                            <td><fmt:formatNumber type = "number" pattern="0.00" value = "${order.getProduct().getPrice()}" /></td>
                            <td>${order.getQuantity()}</td>
                            <td><fmt:formatNumber type = "number" pattern="0.00" value = "${order.getSubtotal()}" /></td>
                            <td class="text-center">
                                <a class="btn btn-primary" href="Cart?action=productDetails&orderId=${order.getId()}">Update</a>
                                <button class="btn btn-danger delete">Delete</button>
                            </td>   
                        </tr>
                    </c:forEach>                     
                </tbody>
            </table>
            <div>
                <h5 class="float-right">Total Amount: RM <fmt:formatNumber type = "number" pattern="0.00" value = "${totalAmount}" /></h5>
            </div>
            <c:if test="${cartList != '[]'}">
                <div class="text-center">           
                    <a class="btn btn-secondary" href="Cart?action=order&totalAmount=${totalAmount}">Place Order</a>
                </div>
            </c:if>
        </div>
    </body>
    <script>
        $(document).ready(function () {
            $('.delete').click(function () {

                var orderId = $(this).closest("tr").find(".orderId").text();
                $.ajax({
                    type: 'POST',
                    data: {
                        orderId: orderId,
                        action: "delete"
                    },
                    url: 'Cart',
                    success: function () {
                        window.location.href = "Cart?action=list&role=customer";
                        alert("Deleted successfully!")
                    }
                });
            });
        });
    </script>
</html>
