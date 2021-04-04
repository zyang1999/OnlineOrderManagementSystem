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
            <h1 class="text-center pt-3 pb-3">Product Information</h1>
            <form action="ManageProduct" class="mx-auto" style="width:40%">
                <div class="form-group row">
                    <input type="hidden" name="role" value="customer">
                    <input type="text" class="form-control col-sm-8" placeholder="Search for a product" name="search"> 
                    <button tupe="submit" class="btn btn-secondary ml-1 col-sm-2" name="action" value="search">Search</button>
                </div>
            </form>
            <h6 class="float-left">Category: </h6>
            <select name="category" class="form-control float-left ml-2 category" style="width:30%"id="category">
                <option value="ALL">Show All</option>
                <option value="FASHION">Fashion</option>
                <option value="ELECTRONICS, MEDIA">Electronics & Media</option>
                <option value="TOYS, HOBBY, DIY">Toys, Hobby & DIY</option>
                <option value="FOOD, PERSONAL, CARE">Food & Personal Care</option>
                <option value="FURNITURE, APPLIANCES">Furniture & Appliances</option>
            </select>
            <table class="table">
                <thead>
                    <tr>
                        <th>Product ID</th>
                        <th>Product Name</th>
                        <th>Category</th>
                        <th>Price (RM)</th>
                        <th>Stock Available</th>
                        <th class="text-center">Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="product" items="${productList}">
                        <tr>
                            <td class="productId">${product.getId()}</td>
                            <td>${product.getName()}</td>
                            <td>${product.getCategory()}</td>
                            <td><fmt:formatNumber type = "number" pattern="0.00" value = "${product.getPrice()}" /></td>
                            <td>${product.getStock()}</td>
                            <td class="text-center">
                                <a href="Order?action=list&productId=${product.getId()}" class="btn btn-primary">Add Order</a>
                            </td>   
                        </tr>
                    </c:forEach>                     
                </tbody>
            </table>      
        </div>
    </body>
    <script>
        $(document).ready(function () {
            $(".category").val("${category}");
            $("select.category").change(function(){
                var category = $(this).children("option:selected").val();
                window.location.href = "ManageProduct?role=customer&action=filter&category="+category;
            });         
        });
    </script>
</html>
