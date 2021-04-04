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
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Orders;
import model.Payment;
import model.PaymentFacade;
import model.Users;

/**
 *
 * @author YANG
 */
@WebServlet(name = "ManagePayment", urlPatterns = {"/ManagePayment"})
public class ManagePayment extends HttpServlet {

    @EJB
    private PaymentFacade paymentFacade;

    private int amount, paymentId;
    private List<Payment> paymentList;
    private Payment payment;
    private String action;
    private double change;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            action = request.getParameter("action");
            paymentId = Integer.parseInt(request.getParameter("paymentId"));
            paymentList = paymentFacade.findAll();
            HttpSession s = request.getSession(false);
            Users currentUser = (Users) s.getAttribute("user");
            boolean flag = true;

            for (int i = 0; i < paymentList.size(); i++) {
                if (paymentList.get(i).getId() == paymentId) {
                    payment = paymentList.get(i);
                    break;
                }
            }
            if (action.equals("collect")) {
                amount = Integer.parseInt(request.getParameter("amount"));
                if (amount < payment.getDueAmount()) {
                    out.println("<script>"
                            + "alert('The amount received is lesser than total amount due. Please correct it again.');"
                            + "window.location.href='Order?role=managing&action=detail&orderId=" + paymentId + "';"
                            + "</script>");
                    flag = false;
                } else {
                    payment.setAmountReceived(amount);
                    payment.setStatus("COMPLETED");
                    DateFormat df = new SimpleDateFormat("dd/MM/yy");
                    Date now = new Date();
                    payment.setPayOn(df.format(now));
                    payment.setCollectedBy(currentUser);
                    paymentFacade.edit(payment);
                    change = amount - payment.getDueAmount();

                }
            } else {
                change = payment.getAmountReceived() - payment.getDueAmount();
            }

            if (flag) {
                request.setAttribute("change", String.format("%.2f", change));
                List<Orders> orderList = payment.getOrderss();
                request.setAttribute("orderList", orderList);
                request.setAttribute("payment", payment);
                request.setAttribute("customer", orderList.get(0).getUser());
                request.getRequestDispatcher("ManagingStaff/receipt.jsp").forward(request, response);
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
