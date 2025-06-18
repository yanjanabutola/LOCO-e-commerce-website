package servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import util.DBConnection;

public class AddProductServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String productName = req.getParameter("productName");
        String urlSlug = req.getParameter("urlSlug");
        String description = req.getParameter("description");
        int catId = Integer.parseInt(req.getParameter("catId"));
        double price = Double.parseDouble(req.getParameter("price"));
        int stockQuantity = Integer.parseInt(req.getParameter("stockQuantity"));
        String status = req.getParameter("status");

        String sql = "INSERT INTO products (product_name, url_slug, cat_id, description, price, stock_quantity, status) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, productName);
            stmt.setString(2, urlSlug);
            stmt.setInt(3, catId);
            stmt.setString(4, description);
            stmt.setDouble(5, price);
            stmt.setInt(6, stockQuantity);
            stmt.setString(7, status);

            stmt.executeUpdate();

            // ✅ Redirect to product display after adding
            res.sendRedirect("products");

        } catch (SQLException e) {
            e.printStackTrace();
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
        }
    }
}
