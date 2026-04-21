/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package accountController;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Accounts;
import models.AccountsJpaController;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "NewAccountControllelr", urlPatterns = {"/newAccount"})
public class NewAccountControllelr extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
        request.getRequestDispatcher("privates/loaimoi_acc.jsp").forward(request, response);

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
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(false);
        try {

            String acc = request.getParameter("acc");
            String pass = request.getParameter("pass");
            String lname = request.getParameter("lname");
            String fname = request.getParameter("fname");
            String phone = request.getParameter("phone");
            String bdaystr = request.getParameter("bday");
            Date bday = Date.valueOf(bdaystr);
            boolean gender = Boolean.parseBoolean(request.getParameter("gender"));
            AccountsJpaController dao = new AccountsJpaController();
            Accounts check = dao.findAccounts(acc);   // tìm theo primary key
            Integer role;
            boolean isUse;
            if (check == null) {
                role = 3;
                isUse = true;
            } else {

                role = Integer.parseInt(request.getParameter("role"));
                isUse = request.getParameter("isUse") != null;
            }
            if (check != null) {
                // Account đã tồn tại
                request.setAttribute("error", "Account đã tồn tại!");
                request.getRequestDispatcher("privates/loaimoi_acc.jsp").forward(request, response);
                return;   // 🔥 BẮT BUỘC
            }

            Accounts x = new Accounts(acc, pass, lname, fname, bday, gender, phone, isUse, role);
            dao.create(x);
        } catch (Exception ex) {
            Logger.getLogger(NewAccountControllelr.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (session.getAttribute("user") != null) {
            response.sendRedirect("listAccount");
        } else {
            response.sendRedirect("listProduct");

        }
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
