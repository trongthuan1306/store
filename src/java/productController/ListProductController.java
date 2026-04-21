/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package productController;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Categories;
import models.CategoriesJpaController;
import models.Products;
import models.ProductsJpaController;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "ListProductController", urlPatterns = {"/listProduct"})
public class ListProductController extends HttpServlet {

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
        ProductsJpaController dao = new ProductsJpaController();
        List<Products> lst = dao.findNewestProducts();
        Cookie[] ck = request.getCookies();
        String value = "";
        if (ck != null) {
            for (Cookie c : ck) {
                if (c.getName().equals("spdx")) {
                    value = c.getValue();
                }
            }
        }
        String min = request.getParameter("minPrice");
        String max = request.getParameter("maxPrice");
        String discount = request.getParameter("discount");
        String sort = request.getParameter("sort");
        List<Products> list;
        boolean showAll = Boolean.parseBoolean(request.getParameter("showAll"));

        if ((min != null && !min.isEmpty()) || (max != null && !max.isEmpty())) {

            double minPrice = 0;
            double maxPrice = Double.MAX_VALUE;

            if (min != null && !min.isEmpty()) {
                minPrice = Double.parseDouble(min);
            }

            if (max != null && !max.isEmpty()) {
                maxPrice = Double.parseDouble(max);
            }

            list = dao.filterByPrice(minPrice, maxPrice);
            if (list.isEmpty()) {
                request.setAttribute("error", "Don't have Product!!!");
            } else {

                request.setAttribute("ds", list);
            }

        } else if (discount != null && !discount.isEmpty()) {

            if (discount.equals("1")) {
                list = dao.getDiscountProducts();
            } else if (discount.endsWith("0")) {
                list = dao.getNoDiscountProducts();
            } else {
                list = dao.findProductsEntities();
            }

            request.setAttribute("ds", list);
        } else if (sort != null && !sort.isEmpty()) {
            list = dao.sortByPrice(sort);
            request.setAttribute("ds", list);
        } else {
            if (showAll == true ) {
            request.setAttribute("showAll", showAll);
            }
            request.setAttribute("ds", lst);
        }
        List<Products> ldx = convertTextToList(value);
        List<Categories> lstCate = new CategoriesJpaController().findCategoriesEntities();
        request.setAttribute("dscate", lstCate);
        request.setAttribute("dx", ldx);

        request.getRequestDispatcher("cacloai_pro.jsp").forward(request, response);
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
