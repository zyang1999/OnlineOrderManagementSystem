<%-- 
    Document   : managing_staff_product
    Created on : Oct 13, 2020, 10:58:22 AM
    Author     : YANG
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="managing_staff_nav.jsp"></jsp:include>
        <div class="container">
            <h1 class="text-center pt-3 pb-3">Review</h1>
            <table class="table">
                <thead>
                    <tr>
                        <th>Feedback ID</th>
                        <th>Order ID</th>
                        <th>Rating</th>
                        <th>Feedback</th>
                        <th>Created By</th>
                        <th>Date</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="review" items="${reviewList}">
                        <tr>
                            <td>${review.getId()}</td>
                            <td>${review.getPayment().getId()}</td>
                            <td>${review.getRating()} out of 5</td>
                            <td>${review.getFeedback()}</td>
                            <td>${review.getUser().getUsername()}</td>
                            <td>${review.getCreatedAt()}</td>
                        </tr>
                    </c:forEach>                     
                </tbody>
            </table>      
        </div>
    </body>
</html>
