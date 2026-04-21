/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package accountController;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Accounts;
import models.AccountsJpaController;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "UpdateAccountController", urlPatterns = {"/updateAccount"})
public class UpdateAccountController extends HttpServlet {

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
        
        String acc = request.getParameter("acc");
            AccountsJpaController dao = new AccountsJpaController(); 
        Accounts x = dao.findAccounts(acc);
        Accounts accLogin = (Accounts)request.getSession().getAttribute("user");
        System.out.println(accLogin.getAccount());
        if(accLogin.getRoleInSystem() != 1&&!acc.equals(accLogin.getAccount())){
           response.sendRedirect("error.jsp"); 
           return ; 
        } 
    
        request.setAttribute("ds", x);
        request.getRequestDispatcher("privates/sualoai_acc.jsp").forward(request, response);
        
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
            int currentRole = (int) request.getSession().getAttribute("role"); 
        try {
            String acc = request.getParameter("acc");
            String pass = request.getParameter("pass");
            String lname = request.getParameter("lname");
            String fname = request.getParameter("fname");
            String phone = request.getParameter("phone");
            String bdaystr = request.getParameter("bday");
            Date bday = Date.valueOf(bdaystr);
            boolean gender = Boolean.parseBoolean(request.getParameter("gender"));
            
            String role = request.getParameter("role");
            boolean isUse = request.getParameter("isUse") != null;
            AccountsJpaController dao = new AccountsJpaController();
            
            Accounts x = dao.findAccounts(acc);  // Lấy entity cũ từ DB

            x.setPass(pass);
            x.setLastName(lname);
            x.setFirstName(fname);
            x.setPhone(phone);
            x.setBirthday(bday);
            x.setGender(gender);
            x.setIsUse(isUse);
            if(currentRole== 1){
            x.setRoleInSystem(Integer.parseInt(request.getParameter("role")));
            }
            dao.edit(x);
            
        } catch (Exception ex) {
            Logger.getLogger(NewAccountControllelr.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (currentRole == 1 || currentRole==2) {
        response.sendRedirect("listAccount");
        }else{
            response.sendRedirect("index.jsp");
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
