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
import model.Product;
import model.ProductFacade;
import model.Users;

/**
 *
 * @author YANG
 */
@WebServlet(name = "ManageProduct", urlPatterns = {"/ManageProduct"})
public class ManageProduct extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
            String action = request.getParameter("action");
            productList = productFacade.findAll();
            HttpSession s = request.getSession(false);
            Users currentUser = (Users) s.getAttribute("user");
            String role = request.getParameter("role");
            if (action.equals("delete")) {
                int id = Integer.parseInt(request.getParameter("productId"));
                for (int i = 0; i < productList.size(); i++) {
                    if (productList.get(i).getId() == id) {
                        productList.get(i).setStatus("DELETED");
                        productFacade.edit(productList.get(i));
                        break;
                    }
                }
            } else if (action.equals("add")) {
                String name = request.getParameter("productName");
                double price = Double.parseDouble(request.getParameter("price"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                String category = request.getParameter("category");
                productFacade.create(new Product(category, name, quantity, price, "ACTIVE", currentUser));
                out.println("<script>"
                        + "alert('Added Successfully!');"
                        + "window.location.href = 'ManageProduct?role=managing&action=filter&category=ALL';"
                        + "</script>");
            } else if (action.equals("update")) {
                int id = Integer.parseInt(request.getParameter("id"));
                String name = request.getParameter("name");
                String category = request.getParameter("category");
                double price = Double.parseDouble(request.getParameter("price"));
                int quantity = Integer.parseInt(request.getParameter("stock"));

                for (int i = 0; i < productList.size(); i++) {
                    if (productList.get(i).getId() == id) {
                        productList.get(i).setName(name);
                        productList.get(i).setPrice(price);
                        productList.get(i).setStock(quantity);
                        productList.get(i).setCategory(category);
                        productList.get(i).setModifiedBy(currentUser);
                        productFacade.edit(productList.get(i));
                        out.println("<script>"
                                + "alert('Updated Successfully!');"
                                + "window.location.href = 'ManageProduct?role=managing&action=filter&category=ALL';"
                                + "</script>");
                        break;
                    }
                }
            } else if (action.equals("search")) {
                String search = request.getParameter("search");
                List<Product> searchingList = new ArrayList();
                for (int i = 0; i < productList.size(); i++) {
                    if (productList.get(i).getStatus().equals("ACTIVE")) {
                        if (productList.get(i).getName().toLowerCase().contains(search.toLowerCase())) {
                            searchingList.add(productList.get(i));
                        }
                    }
                }
                request.setAttribute("productList", searchingList);
                if (role.equals("customer")) {
                    request.getRequestDispatcher("Customer/product.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("ManagingStaff/managing_staff_product.jsp").forward(request, response);
                }

            } else if (action.equals("filter")) {
                String category = request.getParameter("category");
                List<Product> filterredList = new ArrayList();

                for (int i = 0; i < productList.size(); i++) {
                    if (productList.get(i).getStatus().equals("ACTIVE")) {
                        if (category.equals("ALL")) {
                            filterredList.add(productList.get(i));
                        } else {
                            if (productList.get(i).getCategory().equals(category)) {
                                filterredList.add(productList.get(i));
                            }
                        }
                    }
                }
                Collections.reverse(filterredList);
                request.setAttribute("productList", filterredList);
                request.setAttribute("category", category);
                if (role.equals("customer")) {
                    request.getRequestDispatcher("Customer/product.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("ManagingStaff/managing_staff_product.jsp").forward(request, response);
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
