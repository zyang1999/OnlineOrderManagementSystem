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
import model.Delivery;
import model.DeliveryFacade;
import model.Payment;
import model.PaymentFacade;
import model.Users;
import model.UsersFacade;

/**
 *
 * @author YANG
 */
@WebServlet(name = "ManageUser", urlPatterns = {"/ManageUser"})
public class ManageUser extends HttpServlet {

    @EJB
    private DeliveryFacade deliveryFacade;

    @EJB
    private UsersFacade usersFacade;



    @EJB
    private PaymentFacade paymentFacade;

 
    
    public List<Users> userList;
    private String url;
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
            userList = usersFacade.findAll();
            HttpSession s = request.getSession(false);
            Users currentUser = (Users)s.getAttribute("user");
            switch (action) {
                case "search":
                    String name = request.getParameter("name");
                    String type = request.getParameter("type");
                    List<Users> searchingList = new ArrayList();     
                    for(int i=0;i<userList.size();i++){
                        String fullName= userList.get(i).getFirstName() + " " + userList.get(i).getLastName();
                        if(!userList.get(i).getStatus().equals("DELETED")){
                            if(fullName.toLowerCase().contains(name.toLowerCase())){
                                if(type.equals("customer")){
                                    if(userList.get(i).getRole().equals("customer")){
                                        searchingList.add(userList.get(i));
                                    }                                                                   
                                }else{
                                    if(userList.get(i).getRole().equals("managing")||userList.get(i).getRole().equals("delivery")){
                                        searchingList.add(userList.get(i));
                                    }
                                }
                            }
                        }
                    }   
                    out.println(searchingList);
                    request.setAttribute("userList", searchingList);
                    if(type.equals("staff")){
                        request.getRequestDispatcher("ManagingStaff/managing_staff_staff.jsp").forward(request, response);
                    }else{
                        request.getRequestDispatcher("ManagingStaff/managing_staff_customer.jsp").forward(request, response);
                    }
                    
                    break;
                case "delete":
                    {
                        int id = Integer.parseInt(request.getParameter("staffId"));
                        for(int i =0;i<userList.size(); i++){
                            if (userList.get(i).getId() == id){
                                userList.get(i).setStatus("DELETED");
                                usersFacade.edit(userList.get(i));
                                break;
                            }
                        }       break;                       
                    }
                case "assign":
                    {
                        int staffId = Integer.parseInt(request.getParameter("staffId"));
                        int paymentId = Integer.parseInt(request.getParameter("orderId"));
                        List<Payment> paymentList = paymentFacade.findAll();
                        for(int i =0;i<userList.size(); i++){
                            if (userList.get(i).getId()== staffId){
                                Users deliveryMan = userList.get(i);
                                deliveryMan.setStatus("ASSIGNED");
                                usersFacade.edit(deliveryMan);
                                for(int j=0;j<paymentList.size();j++){
                                    if(paymentList.get(j).getId() == paymentId){
                                        Payment payment = paymentList.get(j);
                                        Delivery delivery = payment.getDelivery();
                                        delivery.setUser(deliveryMan);
                                        delivery.setStatus("PREPARING");
                                        deliveryFacade.edit(delivery);
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        break;
                    }
                default:      
                    String username = request.getParameter("username");
                    String password = request.getParameter("password");
                    String firstName = request.getParameter("firstName");
                    String lastName = request.getParameter("lastName");
                    String email = request.getParameter("email");
                    String phoneNumber = request.getParameter("phoneNumber");
                    String role = request.getParameter("role");
                    String address = request.getParameter("address");
                    String message = "";
                    if(action.equals("update")){
                        int userId = Integer.parseInt(request.getParameter("userId"));
                        for(int i=0;i<userList.size();i++){
                            if(userList.get(i).getId() == userId){
                                Users user = userList.get(i);
                                user.setFirstName(firstName);
                                user.setLastName(lastName);
                                user.setEmail(email);
                                user.setPhoneNumber(phoneNumber);
                                user.setAddress(address);
                                user.setModifiedBy(currentUser);
                                if(currentUser.getRole().equals("managing")){
                                    if (role != null){
                                        user.setRole(role);
                                        url = "User_Information?type=staff";
                                    }else{
                                        url = "User_Information?type=customer";
                                    }
                                }else if(currentUser.getRole().equals("customer")){
                                    url = "Profile?role=customer";
                                }else{
                                    url = "Profile?role=delivery";
                                }
                                usersFacade.edit(user);
                                out.println("<script>"
                                        + "alert('Update Successfully!');"
                                        + "window.location.href = '" + url +"';"
                                        + "</script>");                                
                                break;
                            }
                        }
                    }else{
                        if(duplicateUsername(username)){
                            message = "Username has been used!";
                            
                        }
                        if(duplicateEmail(email)){
                            message = message + "Email has been used!";
                        }
                        if(action.equals("register")){
                            
                            if(!message.isEmpty()){
                                out.println("<script>"
                                + "alert('" + message + "');"
                                + "window.location.href = 'register.jsp';"
                                + "</script>");
                            }
                            else{
                                Users user = new Users(username, password, firstName, lastName, phoneNumber, email, "customer", "null", address, null);
                                usersFacade.create(user);
                                s.setAttribute("user", user);
                                response.sendRedirect("Order?action=all&role=customer");
                            }
                        }else if(action.equals("add")){
                            if(!message.isEmpty()){
                                out.println("<script>"
                                + "alert('" + message + "');"
                                + "window.location.href = 'ManagingStaff/managing_staff_add.jsp';"
                                + "</script>");
                            }else{
                                if (role.equals("managing")){
                                    usersFacade.create(new Users(username, password, firstName, lastName, phoneNumber, email, role, "NULL", address, currentUser));
                                }else{
                                    Users user = new Users(username, password, firstName, lastName, phoneNumber, email, role, "WAITING", address, currentUser);
                                    usersFacade.create(user);
                                }
                                out.println("<script>"
                                        + "alert('Added Successfully!');"
                                        + "window.location.href = 'User_Information?type=staff';"
                                        + "</script>");
                            }
                        }   break;
                    }
                    
            }
        }
    }
    
    public boolean duplicateUsername(String username){           
        for(int i = 0;i<userList.size();i++){
            if(userList.get(i).getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }
    
    public boolean duplicateEmail(String email){           
        for(int i = 0;i<userList.size();i++){
            if(userList.get(i).getEmail().equals(email)){
                return true;
            }
        }
        return false;
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
