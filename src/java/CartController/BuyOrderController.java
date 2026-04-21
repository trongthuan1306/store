/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package CartController;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Accounts;
import models.CartItems;
import models.CartItemsJpaController;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "BuyOrderController", urlPatterns = {"/buyOrder"})
public class BuyOrderController extends HttpServlet {

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
        Accounts acc = (Accounts) request.getSession().getAttribute("user");
        if (acc == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String productId = request.getParameter("productId");
        double totalOriginal = 0;
        double totalAfterDiscount = 0;
        double totalDiscount = 0;
        java.util.List<models.CartItems> list = new java.util.ArrayList<>();

        if (productId != null && !productId.isEmpty()) {
            // "Buy Now" mode - single product
            models.ProductsJpaController productDao = new models.ProductsJpaController();
            models.Products product = productDao.findProducts(productId);
            
            if (product != null) {
                models.CartItems temporaryItem = new models.CartItems();
                temporaryItem.setProductId(product);
                temporaryItem.setQuantity(1);
                list.add(temporaryItem);
                
                double price = product.getPrice();
                double discount = product.getDiscount();
                totalOriginal = price;
                double priceAfter = price - (price * (discount / 100));
                totalAfterDiscount = priceAfter;
                totalDiscount = totalOriginal - totalAfterDiscount;
            }
        } else {
            // Standard "Cart Checkout" mode
            CartItemsJpaController itemDao = new CartItemsJpaController();
            list = itemDao.findByAccount(acc.getAccount());

            for (models.CartItems item : list) {
                double price = item.getProductId().getPrice();
                double discount = item.getProductId().getDiscount();
                int qty = item.getQuantity();
                totalOriginal += price * qty;
                double priceAfter = price - (price * (discount / 100));
                totalAfterDiscount += priceAfter * qty;
            }
            totalDiscount = totalOriginal - totalAfterDiscount;
        }

        request.setAttribute("totalD", totalDiscount);
        request.setAttribute("totalO", totalOriginal);
        request.setAttribute("totalAfter", totalAfterDiscount);
        request.setAttribute("ds", list);

        request.getRequestDispatcher("privates/buy_pro.jsp").forward(request, response);
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
