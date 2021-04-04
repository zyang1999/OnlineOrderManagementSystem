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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Payment;
import model.PaymentFacade;
import model.Review;
import model.ReviewFacade;
import model.Users;

/**
 *
 * @author YANG
 */
@WebServlet(name = "ManageReview", urlPatterns = {"/ManageReview"})
public class ManageReview extends HttpServlet {

    @EJB
    private PaymentFacade paymentFacade;

    @EJB
    private ReviewFacade reviewFacade;

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
            String action = request.getParameter("action");
            if(action.equals("list")){
                List<Review> reviewList = reviewFacade.findAll();
                Collections.reverse(reviewList);
                request.setAttribute("reviewList", reviewList);
                request.getRequestDispatcher("ManagingStaff/managing_staff_review.jsp").forward(request, response);
            }else{
                int rating = Integer.parseInt(request.getParameter("rating"));
                String feedback = request.getParameter("feedback");
                int paymentId = Integer.parseInt(request.getParameter("paymentId"));
                List<Payment> paymentList = paymentFacade.findAll();
                for(int i = 0;i<paymentList.size();i++){
                    if(paymentList.get(i).getId() == paymentId){
                        Payment payment = paymentList.get(i);
                        HttpSession s = request.getSession(false);
                        Users currentUser = (Users)s.getAttribute("user");
                        DateFormat df = new SimpleDateFormat("dd/MM/yy");
                        Date now = new Date();
                        reviewFacade.create(new Review(rating, feedback, df.format(now), payment, currentUser));
                        out.println("<script>"
                                + "alert('Feedback is submitted!');"
                                + "window.location.href = 'Order?role=customer&action=detail&orderId=" + paymentId +"';"
                                + "</script>");
                    }
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
