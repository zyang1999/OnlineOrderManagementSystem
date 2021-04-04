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
                        <label for="productName">Product ID:</label>
                        <input class="form-control" type="text" name="id" value="<%= request.getParameter("id")%>" readonly>
                </div>
                <div class="form-group">
                    <label for="productName">Product Name:</label>
                    <input class="form-control" type="text" name="name" value="<%= request.getParameter("name")%>" readonly>
                </div>
                <div class="form-group category">
                    <label for="category">Category:</label>
                    <input class="form-control" type="text" name="category" value="<%= request.getParameter("category")%>" readonly>
                </div>
                <div class="form-group">
                    <label for="price">Price (RM):</label>
                    <input class="form-control" type="number" name="price" step=".01" value="<%= String.format("%.2f", Double.parseDouble(request.getParameter("price")))%>" min="1" required>
                </div>
                <div class="form-group">
                    <label for="quantity">Quantity:</label>
                    <input class="form-control" type="number" name="stock" value="<%= request.getParameter("stock")%>" min="1" required>
                </div>
                <button type="submit" class="btn btn-primary mb-5 text-center" name="action" value="update">Update</button>
            </form>
        </div>
    </body>
</html>
