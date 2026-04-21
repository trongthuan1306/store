/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package CartController;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Accounts;
import models.Cart;
import models.CartItems;
import models.CartItemsJpaController;
import models.CartJpaController;
import models.OrderDetails;
import models.OrderDetailsJpaController;
import models.Orders;
import models.OrdersJpaController;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "CreateOrdersController", urlPatterns = {"/createOrder"})
public class CreateOrdersController extends HttpServlet {

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
        try {
            HttpSession session = request.getSession();
            Accounts acc = (Accounts) session.getAttribute("user");
            
            String name = request.getParameter("name");
            String addr = request.getParameter("address");
            String phone = request.getParameter("phone");
            
            CartItemsJpaController itemDao = new CartItemsJpaController();
            CartJpaController cartDao = new CartJpaController();
            OrdersJpaController orderDao = new OrdersJpaController();
            OrderDetailsJpaController detailDao = new OrderDetailsJpaController();
            
            Cart cart = cartDao.findCartByAccount(acc.getAccount());
            
            List<CartItems> list = itemDao.findByAccount(acc.getAccount());
            
            Orders order = new Orders();
            
            order.setCustName(name);
            order.setCustAddr(addr);
            order.setCustPhone(phone);
            order.setCreatedDate(new Date());
            order.setOrdState(0);
            order.setAccount(acc);
            
            orderDao.create(order);
            
            double total = 0;
            
            for (CartItems i : list) {
                
                try {
                    OrderDetails d = new OrderDetails();
                    
                    d.setOrders(order);
                    d.setProducts(i.getProductId());
                    d.setQuantity(i.getQuantity());
                    d.setPrice((double)i.getProductId().getPrice());
                    
                    detailDao.create(d);
                    
                    total += i.getQuantity() * i.getProductId().getPrice();
                } catch (Exception ex) {
                    Logger.getLogger(CreateOrdersController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }

            order.setTotalValue(total);
            orderDao.edit(order);
            
            response.sendRedirect("showOrder");
        } catch (Exception ex) {
            Logger.getLogger(CreateOrdersController.class.getName()).log(Level.SEVERE, null, ex);
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
