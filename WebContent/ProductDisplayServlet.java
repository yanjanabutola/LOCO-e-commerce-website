package servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.ProductDAO;
import model.ProductVariant;
import util.DBConnection;

public class ProductDisplayServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try (Connection conn = DBConnection.getConnection()) {
            ProductDAO dao = new ProductDAO(conn);
            List<ProductVariant> productVariants = dao.getAllProductVariants();

            req.setAttribute("products", productVariants);
            req.getRequestDispatcher("productDisplay.jsp").forward(req, res);

        } catch (SQLException e) {
            e.printStackTrace();
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
        }
    }
}
