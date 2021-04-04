/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Delivery;
import model.DeliveryFacade;
import model.Payment;
import model.PaymentFacade;
import model.UsersFacade;

/**
 *
 * @author YANG
 */
@WebServlet(name = "Deliver", urlPatterns = {"/Deliver"})
public class Deliver extends HttpServlet {

    @EJB
    private UsersFacade usersFacade;

    @EJB
    private PaymentFacade paymentFacade;

    @EJB
    private DeliveryFacade deliveryFacade;

    private String action, status, message;
    private int paymentId;
    private List<Payment> paymentList;
    private Payment payment;
    private Delivery delivery;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            action = request.getParameter("action");
            paymentList = paymentFacade.findAll();
            paymentId = Integer.parseInt(request.getParameter("paymentId"));

            if (action.equals("deliver")) {
                status = "DELIVERING";
                message = "Start Delivering!";

            } else if (action.equals("complete")) {
                status = "COMPLETED";
                message = "Delivered Successfully!";
            }

            for (int i = 0; i < paymentList.size(); i++) {
                payment = paymentList.get(i);
                if (payment.getId() == paymentId) {
                    delivery = payment.getDelivery();
                    delivery.setStatus(status);
                    delivery.getUser().setStatus("WAITING");
                    deliveryFacade.edit(delivery);
                    usersFacade.edit(delivery.getUser());
                    out.println("<script>"
                            + "alert('" + message + "');"
                            + "window.location.href = 'Order?action=detail&role=delivery&orderId=" + paymentId + "';"
                            + "</script>");
                    break;
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
