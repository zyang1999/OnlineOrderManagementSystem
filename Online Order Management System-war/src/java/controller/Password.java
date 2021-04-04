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
import javax.servlet.http.HttpSession;
import model.Users;
import model.UsersFacade;

/**
 *
 * @author YANG
 */
@WebServlet(name = "Password", urlPatterns = {"/Password"})
public class Password extends HttpServlet {

    @EJB
    private UsersFacade usersFacade;

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
            /* TODO output your page here. You may use following sample code. */
            String oldPassword = request.getParameter("oldPassword");
            String newPassword = request.getParameter("newPassword");
            String role = request.getParameter("role");
            String url = "";
            Users user = null;
            HttpSession s = request.getSession(false);
            Users currentUser = (Users)s.getAttribute("user");
           
           if(role.equals("customer")){
               url = "Customer";
           }else if(role.equals("managing")){
               url = "ManagingStaff";
           }else{
               url = "Delivery";
           }
           
           List<Users> userList = usersFacade.findAll();
           for(int i =0;i<userList.size();i++){
               if(userList.get(i).getId().equals(currentUser.getId())){
                    if(userList.get(i).getPassword().equals(oldPassword)){
                        user = userList.get(i);
                        break;
                    }
               }
           }
           
           if(user != null){
                user.setPassword(newPassword);
                usersFacade.edit(user);
                out.println("<script>"
                        + "alert('Update Password successfully!');"
                        + "window.location.href = '"+ url +"/password.jsp';"
                        + "</script>");
           }else{
               out.println("<script>"
                           + "alert('Old password is incorrect!');"
                           + "window.location.href = '"+ url +"/password.jsp';"
                           + "</script>");
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
