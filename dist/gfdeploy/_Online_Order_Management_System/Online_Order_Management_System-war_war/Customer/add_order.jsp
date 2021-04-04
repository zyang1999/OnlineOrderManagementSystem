<%-- 
    Document   : Managing_staff_add
    Created on : Oct 13, 2020, 7:19:48 PM
    Author     : YANG
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <title>Add Order</title>
    </head>
    <body>
        <jsp:include page="navigator.jsp"></jsp:include>
        <h1 class="text-center pt-5">Add new Order</h1>
        <div class="container pt-5 details" style="width: 40%;">          
            <form action="Order?action=add" method="POST">
                <div class="form-group ">
                    <label for="price">Product ID:</label>
                    <input class="form-control" type="text" name="productId" value="${product.getId()}" readonly>
                </div>
                <div class="form-group ">
                    <label for="price">Product Name:</label>
                    <input class="form-control" type="text" name="name" value="${product.getName()}" readonly>
                </div>
                <div class="form-group ">
                    <label for="price">Price:</label>
                    <input class="form-control" type="text" name="price" value="<fmt:formatNumber type = "number" pattern="0.00" value = "${product.getPrice()}" />" readonly>
                </div>
                <div class="form-group">
                    <label for="quantity">In Stock:</label>
                    <input class="form-control" type="text" name="stock" value="${product.getStock()}" readonly>
                </div>
                <div class="form-group">
                    <label for="quantity">Quantity:</label>
                    <input class="form-control" type="number" name="quantity" min="1" required>
                </div>
                <div class="text-center">
                     <button type="submit" class="btn btn-primary mb-5" name="action" value="add">Add to Cart</button>
                </div>               
        </form>
        </div>
    </body>
</html>
