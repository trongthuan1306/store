/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package productController;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import models.Accounts;
import models.AccountsJpaController;
import models.Categories;
import models.CategoriesJpaController;
import models.Products;
import models.ProductsJpaController;

/**
 *
 * @author ASUS
 */
@MultipartConfig

@WebServlet(name = "NewProductController", urlPatterns = {"/newProduct"})
public class NewProductController extends HttpServlet {

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

        CategoriesJpaController cateDAO = new CategoriesJpaController();
        AccountsJpaController accDAO = new AccountsJpaController();

        List<Categories> categoryList = cateDAO.findCategoriesEntities();
        List<Accounts> accountList = accDAO.findAccountsEntities();

        request.setAttribute("catelist", categoryList);
        request.setAttribute("acclist", accountList);
        request.getRequestDispatcher("privates/loaimoi_pro.jsp").forward(request, response);
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
            request.setCharacterEncoding("UTF-8");
            String id = request.getParameter("productId");

            String name = request.getParameter("name");
            String brief = request.getParameter("brief");
            Integer typeId = Integer.parseInt(request.getParameter("typeId"));
            CategoriesJpaController daoo = new CategoriesJpaController();
            Categories y = daoo.findCategories(typeId);
            String account = request.getParameter("account");
            AccountsJpaController dao = new AccountsJpaController();
            Accounts x = dao.findAccounts(account);
            String unit = request.getParameter("unit");
            
            String priceStr = request.getParameter("price");
            Integer price = 0;
            if (priceStr != null && !priceStr.trim().isEmpty()) {
                try {
                    price = Integer.parseInt(priceStr.trim());
                } catch (NumberFormatException e) {
                    throw new Exception("Invalid format for price: '" + priceStr + "'");
                }
            }

            String discountStr = request.getParameter("discount");
            Integer discount = 0;
            if (discountStr != null && !discountStr.trim().isEmpty()) {
                try {
                    discount = (int) Double.parseDouble(discountStr.trim());
                } catch (NumberFormatException e) {
                    throw new Exception("Invalid format for discount: '" + discountStr + "'");
                }
            }

            Date postedDate = Date.valueOf(LocalDate.now());

           
            Part filePart = request.getPart("productImage");
            String originalFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String newFileName = UUID.randomUUID().toString() + "_" + originalFileName;
            // Logic to save image to both build and source folders
            String uploadPath = getServletContext().getRealPath("/images/sanPham");
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String finalPath = uploadPath + File.separator + newFileName;
            filePart.write(finalPath);
            System.out.println("Saved to build: " + finalPath);

            // Copy to source 'web' folder to persist across Clean & Build
            String sourcePath = null;
            if (uploadPath.contains("build" + File.separator + "web")) {
                sourcePath = uploadPath.replace("build" + File.separator + "web", "web");
            } else if (uploadPath.contains("build/web")) {
                sourcePath = uploadPath.replace("build/web", "web");
            }

            if (sourcePath != null) {
                File sourceDir = new File(sourcePath);
                if (!sourceDir.exists()) {
                    sourceDir.mkdirs();
                }
                String finalSourcePath = sourcePath + File.separator + newFileName;
                java.nio.file.Files.copy(Paths.get(finalPath), Paths.get(finalSourcePath), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Successfully copied to source: " + finalSourcePath);
            } else {
                System.out.println("Warning: Could not determine source path. Image may be lost after Clean and Build.");
            }
            String imagePath = "/images/sanPham/" + newFileName;
            Products pro = new Products(id, name, imagePath, brief, postedDate, unit, price, discount, x, y);
            new ProductsJpaController().create(pro);
        response.sendRedirect("listProduct");
        } catch (models.exceptions.PreexistingEntityException ex) {
            Logger.getLogger(NewProductController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("error", "Product ID already exists!");
            doGet(request, response);
        } catch (Exception ex) {
            Logger.getLogger(NewProductController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("error", "An error occurred while adding the product: " + ex.getMessage());
            doGet(request, response);
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
