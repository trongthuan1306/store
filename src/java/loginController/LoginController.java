/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package loginController;

import CartController.CartService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
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
@WebServlet(name = "LoginController", urlPatterns = {"/dangnhap"})
public class LoginController extends HttpServlet {

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
        request.getRequestDispatcher("login.jsp").forward(request, response);

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
        String acc = request.getParameter("acc").trim();
        String pass = request.getParameter("psw");
        Accounts x = new AccountsJpaController().getLogin(acc, pass);

        if (x == null) {
            HttpSession session = request.getSession();

            // --- cập nhật số lần sai trong session object -- 
            int nSai = (int) session.getAttribute("sls");
            nSai++;
            session.setAttribute("sls", nSai);
            request.setAttribute("msg", "Sai tài khoản");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else if (x.getIsUse() == false) {
            request.setAttribute("msg", "Tài khoản đã bị khóa");
            request.getRequestDispatcher("login.jsp").forward(request, response);

        } else {
            //-- Cap nhat danh sach online khi dangw nhaajp 
            Map<String, HttpSession> danhsach = (Map<String, HttpSession>) getServletContext().getAttribute("online");
            HttpSession oldSession = danhsach.get(acc);
            System.out.println(oldSession);
            if (oldSession != null) {
                oldSession.invalidate();
                danhsach.remove(oldSession);
            }
            HttpSession session = request.getSession();
            danhsach.put(acc, session);
            getServletContext().setAttribute("online", danhsach);
            session.setAttribute("user", x);
            session.setAttribute("role", x.getRoleInSystem());
            CartService service = new CartService();

            service.mergeCart(session, x);
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
