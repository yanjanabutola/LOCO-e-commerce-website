package dao;

import java.sql.*;
import java.util.*;
import model.Product;

public class ProductDAO {
    private Connection conn;

    public ProductDAO(Connection conn) {
        this.conn = conn;
    }

    // Get all products
    public List<Product> getAllProducts() throws SQLException {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM cart";

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            Product p = new Product(
                rs.getInt("Product ID"),
                rs.getString("Name"),
                rs.getString("Description"),
                rs.getDouble("Price"),
                rs.getString("Category"),
                rs.getString("Image")
            );
            list.add(p);
        }
        return list;
    }

    // Main method for testing
    public static void main(String args[]) {
        String jdbcURL = "jdbc:mysql://localhost:3306/loco";
        String dbUser = "root";
        String dbPassword = "";

        try {
            Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
            ProductDAO dao = new ProductDAO(conn);
            List<Product> products = dao.getAllProducts();

            for (Product p : products) {
                System.out.println(p.getId() + " | " + p.getName() + " | " + p.getDescription() +
                        " | $" + p.getPrice() + " | " + p.getCategory() + " | " + p.getImageUrl());
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

