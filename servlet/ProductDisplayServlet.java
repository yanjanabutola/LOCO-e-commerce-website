package servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.ProductDAO;
import model.Product;
import util.DBConnection;

public class ProductDisplayServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res) 
            throws ServletException, IOException {
        
        Connection conn = null;
        try {
            // Get database connection
            conn = DBConnection.getConnection();
            ProductDAO dao = new ProductDAO(conn);
            List<Product> productList = dao.getAllProducts();

            // Set attributes and forward to JSP
            req.setAttribute("productList", productList);
            req.getRequestDispatcher("productDisplay.jsp").forward(req, res);
            
        } catch (SQLException e) {
            e.printStackTrace();
            // Send proper error response
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                         "Database error: " + e.getMessage());
        } finally {
            // Ensure connection is always closed
            DBConnection.closeConnection(conn);
        }
    }
}