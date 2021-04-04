/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Delivery;
import model.DeliveryFacade;
import model.Orders;
import model.OrdersFacade;
import model.Payment;
import model.PaymentFacade;
import model.Product;
import model.ProductFacade;
import model.Review;
import model.ReviewFacade;
import model.Users;

/**
 *
 * @author YANG
 */
@WebServlet(name = "Order", urlPatterns = {"/Order"})
public class Order extends HttpServlet {

    @EJB
    private ReviewFacade reviewFacade;

    @EJB
    private DeliveryFacade deliveryFacade;

    @EJB
    private ProductFacade productFacade;

    @EJB
    private OrdersFacade ordersFacade;

    @EJB
    private PaymentFacade paymentFacade;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession s = request.getSession(false);
            Users currentUser = (Users) s.getAttribute("user");
            String role = request.getParameter("role");
            String action = request.getParameter("action");
            List<Product> productList = productFacade.findAll();
            String url = "";
            if (action.equals("filter")) {
                int id = Integer.parseInt(request.getParameter("productId"));
                for (int i = 0; i < productList.size(); i++) {
                    if (productList.get(i).getId() == id) {
                        request.setAttribute("productList", productList);
                        request.setAttribute("product", productList.get(i));
                        request.setAttribute("selectedProduct", id);
                        request.getRequestDispatcher("Customer/add_order.jsp").forward(request, response);
                        break;
                    }
                }
            } else if (action.equals("list")) {
                for (int i = 0; i < productList.size(); i++) {
                    if (productList.get(i).getId() == Integer.parseInt(request.getParameter("productId"))) {
                        List<Orders> orderList = ordersFacade.findAll();
                        for (int j = 0; j < orderList.size(); j++) {
                            Orders order = orderList.get(j);
                            if (order.getUser().getId().equals(currentUser.getId())) {
                                if (order.getStatus().equals("ON CART")) {
                                    if (order.getProduct().getId() == Integer.parseInt(request.getParameter("productId"))) {
                                        url = "Cart?action=productDetails&orderId=" + order.getId();
                                        break;
                                    }
                                }
                            }
                            url = "Customer/add_order.jsp";
                        }
                        request.setAttribute("product", productList.get(i));
                        request.getRequestDispatcher(url).forward(request, response);
                        break;
                    }
                }
            } else if (action.equals("add")) {
                int id = Integer.parseInt(request.getParameter("productId"));
                int stock = Integer.parseInt(request.getParameter("stock"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                if (quantity > stock) {
                    out.println("<script>"
                            + "alert('Quantity inserted is more than avaliable stock!');"
                            + "window.location.href = 'Order?action=list&productId=" + id + "';"
                            + "</script>");
                } else {
                    for (int i = 0; i < productList.size(); i++) {
                        if (productList.get(i).getId() == id) {
                            Product selectedProduct = productList.get(i);
                            double subtotal = selectedProduct.getPrice() * quantity;
                            ordersFacade.create(new Orders(quantity, subtotal, currentUser.getAddress(), "ON CART", productList.get(i), null, currentUser));
                            out.println("<script>"
                                    + "alert('Added into Cart Successfully!');"
                                    + "window.location.href = 'ManageProduct?role=customer&action=filter&category=ALL';"
                                    + "</script>");
                        }
                    }
                }

            } else if (action.equals("all")) {
                List<Payment> paymentList = paymentFacade.findAll();
                List<Payment> userOrderedList = new ArrayList();
                if (role.equals("customer")) {
                    for (int i = 0; i < paymentList.size(); i++) {
                        if (paymentList.get(i).getCustomer().getId().equals(currentUser.getId())) {
                            userOrderedList.add(paymentList.get(i));
                        }
                    }
                    url = "Customer/order.jsp";

                } else if (role.equals("managing")) {
                    userOrderedList = paymentList;
                    url = "ManagingStaff/order.jsp";

                } else if (role.equals("delivery")) {
                    List<Delivery> deliveryList = deliveryFacade.findAll();
                    for (int i = 0; i < deliveryList.size(); i++) {
                        if (deliveryList.get(i).getUser() != null) {
                            if (deliveryList.get(i).getUser().getId().equals(currentUser.getId())) {
                                Delivery delivery = deliveryList.get(i);
                                userOrderedList.add(delivery.getPayment());
                            }
                        }
                    }
                    url = "Delivery/order.jsp";
                }
                Collections.reverse(userOrderedList);
                request.setAttribute("orderList", userOrderedList);
                request.getRequestDispatcher(url).forward(request, response);

            } else if (action.equals("detail")) {
                int orderId = Integer.parseInt(request.getParameter("orderId"));
                List<Orders> orderList = ordersFacade.findAll();
                List<Orders> selectedOrderList = new ArrayList();
                double totalAmount = 0;
                for (int i = 0; i < orderList.size(); i++) {
                    if (orderList.get(i).getPayment() != null) {
                        if (orderList.get(i).getPayment().getId() == orderId) {
                            selectedOrderList.add(orderList.get(i));
                            totalAmount = totalAmount + orderList.get(i).getSubtotal();
                        }
                    }
                }
                List<Review> reviewList = reviewFacade.findAll();
                for (int i = 0; i < reviewList.size(); i++) {

                    if (reviewList.get(i).getPayment().getId() == orderId) {
                        Review review = reviewList.get(i);
                        request.setAttribute("review", review);
                        break;
                    }
                }
                if (role.equals("customer")) {
                    url = "Customer/order_detail.jsp";
                } else if (role.equals("managing")) {
                    url = "ManagingStaff/order_detail.jsp";
                } else {
                    url = "Delivery/order_detail.jsp";
                }

                request.setAttribute("orderList", selectedOrderList);
                request.setAttribute("totalAmount", totalAmount);
                request.setAttribute("address", selectedOrderList.get(0).getAddress());
                request.setAttribute("customer", selectedOrderList.get(0).getUser());
                request.setAttribute("payment", selectedOrderList.get(0).getPayment());
                request.setAttribute("orderId", orderId);
                request.setAttribute("status", selectedOrderList.get(0).getPayment().getDelivery().getStatus());

                request.getRequestDispatcher(url).forward(request, response);

            } else if (action.equals("filterOrder")) {
                String category = request.getParameter("category");
                String status = "";
                if (category.equals("ALL")) {
                    response.sendRedirect("Order?action=all&role=managing");
                } else {
                    if (category.equals("UNASSIGNED ORDER")) {
                        status = "PENDING";
                    } else if (category.equals("ASSIGNED ORDER")) {
                        status = "PREPARING";
                    } else if (category.equals("DELIVERING")) {
                        status = "DELIVERING";
                    } else if (category.equals("DELIVERY COMPLETED")) {
                        status = "COMPLETED";
                    } else if (category.equals("PENDING PAYMENT")) {
                        status = "PENDING";
                    } else if (category.equals("PAYMENT COMPLETED")) {
                        status = "COMPLETED";
                    }
                    List<Payment> paymentList = paymentFacade.findAll();
                    List<Payment> filterredList = new ArrayList();
                    for (int i = 0; i < paymentList.size(); i++) {
                        if (category.equals("PENDING PAYMENT") || category.equals("PAYMENT COMPLETED")) {
                            if (paymentList.get(i).getStatus().equals(status)) {
                                filterredList.add(paymentList.get(i));
                            }
                        } else {
                            if (paymentList.get(i).getDelivery().getStatus().equals(status)) {
                                filterredList.add(paymentList.get(i));
                            }
                        }
                    }
                    request.setAttribute("orderList", filterredList);
                    request.setAttribute("category", category);
                    request.getRequestDispatcher("ManagingStaff/order.jsp").forward(request, response);
                }

            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
