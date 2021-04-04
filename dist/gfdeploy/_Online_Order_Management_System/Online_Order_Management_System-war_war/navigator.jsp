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
        <title>JSP Page</title>
    </head>
    <body>
        <nav class="navbar navbar-expand-sm bg-light">
            <ul class="nav">
                <li class="nav-item">
                    <a class="nav-link" href="/Online_Order_Management_System-war/customer_login.jsp">Customer</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" href="/Online_Order_Management_System-war/login.jsp">Staff</a>
                </li>
            </ul>
        </nav>
    </body>
</html>
