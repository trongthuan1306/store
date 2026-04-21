/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Accounts;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {

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
        String url = "login.jsp";
        String action = request.getParameter("act");
        HttpSession session = request.getSession();
        Accounts x = (Accounts) session.getAttribute("user");
        switch (action.toLowerCase()) {
            case "dx":
                url = "dangxuat";
                break;
            case "dn":
                url = "dangnhap";
                break;
            case "acc_list":
                url = "listAccount";
                break;
            case "acc_upd":
                if (x != null) {
                    url = "updateAccount";
                }
                break;
            case "acc_del":
                if (x != null) {
                    url = "deleteAccount";
                }
                break;
            case "acc_new":
                if (x != null) {
                    url = "newAccount";
                }
                break;
            case "acc_lock":
                if (x != null) {
                    url = "lockLogin";
                }
                break;
            case "acc_unlock":
                if (x != null) {
                    url = "unlockLogin";
                }
                break;
            case "cate_new":
                if (x != null) {
                    url = "newCategories";
                }
                break;
            case "cate_list":
                url = "listCategories";
                break;
            case "cate_del":
                if (x != null) {
                    url = "deleteCategories";
                }
                break;
            case "cate_upd":
                if (x != null) {
                    url = "updateCategories";
                }
                break;
            case "pro_del":
                if (x != null) {
                    url = "deleteProduct";
                }
                break;
            case "pro_upd":
                if (x != null) {
                    url = "updateProduct";
                }
                break;
            case "pro_new":
                if (x != null) {
                    url = "newProduct";
                }
                break;
            case "pro_list":
                
                    url = "listProduct";
                
                break;
            case "pro_detail":
               
                    url = "detailProduct";
                
                break;
       
            default:   
                url = "login.jsp";

        }
        RequestDispatcher rd = request.getRequestDispatcher(url);
        rd.forward(request, response);

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
