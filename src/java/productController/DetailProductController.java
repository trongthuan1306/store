/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package productController;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Accounts;
import models.Products;
import models.ProductsJpaController;
import models.UserViews;
import models.UserViewsJpaController;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "DetailProductController", urlPatterns = {"/detailProduct"})
public class DetailProductController extends HttpServlet {

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
        String id = request.getParameter("id");
        ProductsJpaController dao = new ProductsJpaController();
        Products p = dao.findProducts(id);
        // san pham da xem
        Cookie[] nck = request.getCookies();
        String gt = createNewValue(nck, id);
        Cookie ck = new Cookie("spdx", gt);
        ck.setMaxAge(15 * 24 * 60 * 60);
        ck.setPath(request.getContextPath());
        response.addCookie(ck);
        List<Products> ldx = convertTextToList(gt);
        Accounts acc =  (Accounts) request.getSession().getAttribute("user");
        System.out.println(acc);
        if (acc != null) {

            UserViews uv = new UserViews();
            uv.setAccount(acc);
            uv.setProductId(p);
            uv.setViewTime(new Date());

            new UserViewsJpaController().create(uv);
        }
        request.setAttribute("dx", ldx);

        request.setAttribute("product", p);
        request.getRequestDispatcher("detail_pro.jsp").forward(request, response);
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

    private String createNewValue(Cookie[] nck, String id) {
        String kq = "";
        for (Cookie i : nck) {
            if (i.getName().equalsIgnoreCase("spdx")) {
                kq += i.getValue() + "_";
            }
        }
        kq += id;
        return kq;
    }

    private List<Products> convertTextToList(String gt) {

        List<Products> kq = new ArrayList<>();
        List<String> check = new ArrayList<>(); // dùng để kiểm tra trùng

        if (gt != null && !gt.isEmpty()) {
            String[] items = gt.split("_");
            for (int i = items.length - 1; i >= 0; i--) {
                String id = items[i];
                if (!check.contains(id)) { // nếu chưa có thì mới thêm
                    Products x = new ProductsJpaController().findProducts(id);

                    if (x != null) {
                        kq.add(x);
                        check.add(id);
                    }
                }
            }
        }

        return kq;
    }

}
