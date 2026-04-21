/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package CartController;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Accounts;
import models.Cart;
import models.CartItems;
import models.CartItemsJpaController;
import models.CartJpaController;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "UpdateCartController", urlPatterns = {"/updateCart"})
public class UpdateCartController extends HttpServlet {

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
        String pid = request.getParameter("id");
        String action = request.getParameter("action");

        Accounts acc = (Accounts) request.getSession().getAttribute("user");

        if (acc == null) {

            // ===== USER CHƯA LOGIN =====
            Map<String, CartItems> cart
                    = (Map<String, CartItems>) request.getSession().getAttribute("cart");

            CartItems item = cart.get(pid);

            if (item != null) {
                int qty = item.getQuantity();

                if (action.equals("plus")) {
                    item.setQuantity(qty + 1);
                }

                if (action.equals("minus") && qty > 1) {
                    item.setQuantity(qty - 1);
                }
            }

        } else {
            CartJpaController cartDao = new CartJpaController();
            CartItemsJpaController itemDao = new CartItemsJpaController();

            Cart cart = cartDao.findCartByAccount(acc.getAccount());

            CartItems item = itemDao.findByCartAndProduct(cart.getCartId(), pid);

            if (item != null) {

                int qty = item.getQuantity();

                if (action.equals("plus")) {
                    item.setQuantity(qty + 1);
                }

                if (action.equals("minus") && qty > 1) {
                    item.setQuantity(qty - 1);
                }

                try {
                    itemDao.edit(item);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

            response.sendRedirect("Cart");

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
        protected void doPost
        (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            processRequest(request, response);
        }

        /**
         * Returns a short description of the servlet.
         *
         * @return a String containing servlet description
         */
        @Override
        public String getServletInfo
        
            () {
        return "Short description";
        }// </editor-fold>

    }
