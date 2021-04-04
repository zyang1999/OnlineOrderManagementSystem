/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Product;
import model.ProductFacade;
import model.Users;
import model.UsersFacade;

/**
 *
 * @author YANG
 */
@WebServlet(name = "User_Information", urlPatterns = {"/User_Information"})
public class User_Information extends HttpServlet {

    @EJB
    private UsersFacade usersFacade;

    @EJB
    private ProductFacade productFacade;
 
    private List<Product> productList;
    

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
            String type = request.getParameter("type");
            List<Users> userList = usersFacade.findAll();
            List<Users> staffList = new ArrayList();  
            String url = "";
            for(int i=0; i<userList.size(); i++){
                Users user = userList.get(i);
                if(!user.getStatus().equals("DELETED")){
                    switch (type) {
                        case "staff":
                            if(user.getRole().equals("managing")||user.getRole().equals("delivery")){
                                staffList.add(user);
                            }   break;
                        case "delivery":
                            if(user.getStatus().equals("WAITING")){
                                staffList.add(user);
                            }
                            request.setAttribute("orderId", request.getParameter("orderId"));
                            break;
                        default:
                            if(user.getRole().equals("customer")){
                                staffList.add(user);
                            }   break;
                    }   
                }
            }
            
            switch (type) 
            {
                case "staff":
                        url = "ManagingStaff/managing_staff_staff.jsp";
                    break;
                case "delivery":
                        request.setAttribute("orderId", request.getParameter("orderId"));
                        url = "ManagingStaff/managing_staff_delivery.jsp";
                    break;
                default:
                    url ="ManagingStaff/managing_staff_customer.jsp";
                    break;
            }  
            request.setAttribute("userList", staffList);
            out.println(request.getParameter("orderId"));
            request.getRequestDispatcher(url).forward(request, response);        
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
