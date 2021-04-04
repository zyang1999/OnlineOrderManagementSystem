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
@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {

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
            
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String role = request.getParameter("role");

            Users user = null;

            List<Users> userList = usersFacade.findAll();
            for(int i=0; i<userList.size(); i++){
                if(!userList.get(i).getStatus().equals("DELETED")){
                        if(userList.get(i).getUsername().equals(username)&&userList.get(i).getRole().equals(role)){
                        user = userList.get(i);
                        break;          
                    }
                }
            }
      
            if (user != null){
                if (user.getPassword().equals(password)){
                    HttpSession currentUser = request.getSession();
                    currentUser.setAttribute("user", user);
                    if(user.getRole().equals("managing")){
                        response.sendRedirect("User_Information?type=staff");
                    }else if (user.getRole().equals("customer")){
                        response.sendRedirect("Order?action=all&role=customer");
                    }else{
                        response.sendRedirect("Order?action=all&role=delivery");
                    }
                    
                }else{
                    if(request.getParameter("type").equals("customer")){
                        request.getRequestDispatcher("customer_login.jsp").include(request, response);
                    }else{
                        request.getRequestDispatcher("login.jsp").include(request, response);
                    }
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css\">");          
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<p class='text-center pt-5'>Invalid Username or Password</p>");
                    out.println("</body>");
                    out.println("</html>");
                }
            }else{
                if(request.getParameter("type").equals("customer")){
                    request.getRequestDispatcher("customer_login.jsp").include(request, response);
                }else{
                    request.getRequestDispatcher("login.jsp").include(request, response);
                }
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css\">");          
                out.println("</head>");
                out.println("<body>");
                out.println("<p class='text-center pt-5'>Invalid Username or Password</p>");
                out.println("</body>");
                out.println("</html>");
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
