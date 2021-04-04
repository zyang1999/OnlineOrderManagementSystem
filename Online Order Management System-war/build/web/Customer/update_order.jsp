<%-- 
    Document   : Managing_staff_add
    Created on : Oct 13, 2020, 7:19:48 PM
    Author     : YANG
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="navigator.jsp"></jsp:include>
        <h1 class="text-center pt-5">Update Order</h1>
        <div class="container pt-5" style="width: 40%;">          
            <form action="Cart?action=update" method="POST">
                <div class="form-group">
                    <label for="productName">Order ID:</label>
                    <input class="form-control" type="text" name="orderId" value="${order.getId()}" readonly>
                </div>
                <div class="form-group">
                    <label for="productName">Product ID:</label>
                    <input class="form-control" type="text" name="productId" value="${order.getProduct().getId()}" readonly>
                </div>
                <div class="form-group">
                    <label for="productName">Product Name:</label>
                    <input class="form-control" type="text" name="name" value="${order.getProduct().getName()}" readonly>
                </div>
                <div class="form-group">
                    <label for="price">Price:</label>
                    <input class="form-control" type="number" name="price" value="<fmt:formatNumber type = "number" pattern="0.00" value = "${order.getProduct().getPrice()}" />" readonly>
                </div>
                <div class="form-group">
                    <label for="price">Stock:</label>
                    <input class="form-control" type="number" name="stock" value="${order.getProduct().getStock()}" readonly>
                </div>
                <div class="form-group">
                    <label for="quantity">Quantity:</label>
                    <input class="form-control" type="number" name="quantity" value="${order.getQuantity()}"  min="1" required>
                </div>
                <button type="submit" class="btn btn-primary mb-5 text-center">Update</button>
        </form>
        </div>
    </body>
</html>
