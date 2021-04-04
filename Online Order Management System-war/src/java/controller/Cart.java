/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import model.ProductFacade;
import model.Users;

/**
 *
 * @author YANG
 */
@WebServlet(name = "Cart", urlPatterns = {"/Cart"})
public class Cart extends HttpServlet {

    @EJB
    private ProductFacade productFacade;

    @EJB
    private DeliveryFacade deliveryFacade;

    @EJB
    private PaymentFacade paymentFacade;

    @EJB
    private OrdersFacade ordersFacade;
    

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
            String action = request.getParameter("action");
            List<Orders> orderList = ordersFacade.findAll();
            double totalAmount = 0;
            if (action.equals("list")) {
                List<Orders> cartList = new ArrayList();
                for (int i = 0; i < orderList.size(); i++) {
                    if (orderList.get(i).getUser().getId().equals(currentUser.getId())) {
                        if (orderList.get(i).getStatus().equals("ON CART")) {
                            totalAmount = totalAmount + orderList.get(i).getSubtotal();
                            cartList.add(orderList.get(i));
                        }
                    }
                }
                request.setAttribute("cartList", cartList);
                request.setAttribute("totalAmount", String.format("%.2f", totalAmount));
                request.getRequestDispatcher("Customer/cart.jsp").forward(request, response);
            } else if (action.equals("delete")) {
                for (int i = 0; i < orderList.size(); i++) {
                    Orders order = orderList.get(i);
                    if (order.getId() == Integer.parseInt(request.getParameter("orderId"))) {
                        ordersFacade.remove(order);
                        break;
                    }
                }
            } else if (action.equals("productDetails")) {
                for (int i = 0; i < orderList.size(); i++) {
                    if (orderList.get(i).getId() == Integer.parseInt(request.getParameter("orderId"))) {
                        request.setAttribute("order", orderList.get(i));
                        request.getRequestDispatcher("Customer/update_order.jsp").forward(request, response);
                        break;
                    }
                }
            } else if (action.equals("update")) {
                int stock = Integer.parseInt(request.getParameter("stock"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                int orderId = Integer.parseInt(request.getParameter("orderId"));
                double price = Double.parseDouble(request.getParameter("price"));
                if (quantity > stock) {
                    out.println("<script>"
                            + "alert('The quantity is more than avaliable stock!');"
                            + "window.location.href = 'Cart?action=productDetails&orderId=" + orderId + "';"
                            + "</script>");
                } else {
                    for (int i = 0; i < orderList.size(); i++) {
                        Orders order = orderList.get(i);
                        if (orderList.get(i).getId() == orderId) {
                            order.setQuantity(quantity);
                            double subtotal = quantity * price;
                            order.setSubtotal(subtotal);
                            ordersFacade.edit(order);
                            out.println("<script>"
                                    + "alert('Update Successfully!');"
                                    + "window.location.href = 'Cart?action=list';"
                                    + "</script>");
                            break;
                        }
                    }
                }
            } else if (action.equals("order")) {

                boolean flag = true;
                for (int i = 0; i < orderList.size(); i++) {
                    if (orderList.get(i).getUser().getId().equals(currentUser.getId())) {
                        if (orderList.get(i).getStatus().equals("ON CART")) {
                            if (orderList.get(i).getQuantity() > orderList.get(i).getProduct().getStock()) {
                                out.println("<script>"
                                        + "alert('The Quantity of the product order is more than the current stock!');"
                                        + "window.location.href = 'Cart?action=productDetails&orderId=" + orderList.get(i).getId() +"';"
                                        + "</script>");
                                flag = false;
                                break;
                            }
                        }
                    }
                }
                if (flag) {
                    DateFormat df = new SimpleDateFormat("dd/MM/yy");
                    Date now = new Date();
                    Delivery delivery = new Delivery("PENDING");
                    Payment payment = new Payment(Double.parseDouble(request.getParameter("totalAmount")), "PENDING", df.format(now), currentUser, delivery);
                    deliveryFacade.create(delivery);
                    paymentFacade.create(payment);
                    for (int i = 0; i < orderList.size(); i++) {
                        if (orderList.get(i).getUser().getId().equals(currentUser.getId())) {
                            if (orderList.get(i).getStatus().equals("ON CART")) {
                                Orders order = orderList.get(i);
                                order.setStatus("ORDERED");
                                order.setPayment(payment);
                                ordersFacade.edit(order);
                                int stock = order.getProduct().getStock() - order.getQuantity();
                                order.getProduct().setStock(stock);
                                productFacade.edit(order.getProduct());
                            }
                        }
                    }
                    out.println("<script>"
                            + "alert('Order has been placed!');"
                            + "window.location.href = \"Order?action=all&role=customer\""
                            + "</script>");
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
