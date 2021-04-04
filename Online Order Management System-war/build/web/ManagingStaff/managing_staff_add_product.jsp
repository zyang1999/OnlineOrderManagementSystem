<%-- 
    Document   : Managing_staff_add
    Created on : Oct 13, 2020, 7:19:48 PM
    Author     : YANG
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="managing_staff_nav.jsp"></jsp:include>
        <h1 class="text-center pt-5">Add New Product</h1>
        <div class="container pt-5" style="width: 40%;">          
            <form action="../ManageProduct" method="POST">
                <div class="form-group">
                    <label for="productName">Product Name:</label>
                    <input class="form-control" type="text" name="productName" required>
                </div>
                <div class="form-group">
                    <label for="category">Category:</label>
                    <select name="category" class="form-control"id="category">
                        <option value="FASHION">Fashion</option>
                        <option value="ELECTRONICS, MEDIA">Electronics & Media</option>
                        <option value="TOYS, HOBBY, DIY">Toys, Hobby & DIY</option>
                        <option value="FOOD, PERSONAL, CARE">Food & Personal Care</option>
                        <option value="FURNITURE, APPLIANCES">Furniture & Appliances</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="price">Price:</label>
                    <input class="form-control" type="number" name="price" step=".01"  min="1" required>
                </div>
                <div class="form-group">
                    <label for="quantity">Quantity:</label>
                    <input class="form-control" type="number" name="quantity" min="1" required>
                </div>
                <button type="submit" class="btn btn-primary mb-5 mx-auto" name="action" value="add">Add</button>
        </form>
        </div>
    </body>
</html>
