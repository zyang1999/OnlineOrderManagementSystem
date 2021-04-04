<%-- 
    Document   : managing_staff_nav
    Created on : Oct 13, 2020, 10:43:04 AM
    Author     : YANG
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <title>JSP Page</title>
    </head>
    <body>
        <nav class="navbar navbar-expand-sm bg-light">
            <ul class="nav">
                <li class="nav-item">
                    <a class="nav-link" href="/Online_Order_Management_System-war/User_Information?type=staff">Staff</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" href="/Online_Order_Management_System-war/User_Information?type=customer">Customer</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" href="/Online_Order_Management_System-war/Order?action=all&role=managing">Order</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" href="/Online_Order_Management_System-war/ManageProduct?action=filter&category=ALL&role=managing">Product</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" href="/Online_Order_Management_System-war/ManageReview?action=list">Review</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" href="/Online_Order_Management_System-war/ManagingStaff/password.jsp">Password</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" href="/Online_Order_Management_System-war/Logout">Log Out</a>
                </li>
            </ul>
        </nav>
    </body>
</html>
