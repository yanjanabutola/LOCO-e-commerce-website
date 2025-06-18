package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.ProductVariant;

public class ProductDAO {
    private Connection conn;

    public ProductDAO(Connection conn) {
        this.conn = conn;
    }

    // ✅ Fetch all product variants with product name and image URL (from url_slug)
    public List<ProductVariant> getAllProductVariants() throws SQLException {
        List<ProductVariant> variants = new ArrayList<>();

        String sql = "SELECT p.id AS product_id, p.product_name, p.url_slug, pv.id AS variant_id, pv.colour, pv.size, pv.price, pv.stock_quantity " +
                     "FROM products p " +
                     "JOIN product_variants pv ON p.id = pv.product_id " +
                     "WHERE p.status = 'active'";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ProductVariant pv = new ProductVariant();
                pv.setId(rs.getInt("variant_id"));
                pv.setProductId(rs.getInt("product_id"));
                pv.setProductName(rs.getString("product_name"));
                pv.setColour(rs.getString("colour"));
                pv.setSize(rs.getString("size"));
                pv.setPrice(rs.getDouble("price"));
                pv.setStockQuantity(rs.getInt("stock_quantity"));

                // ✅ Generate image URL from url_slug (assuming .jpg in /images/)
                String urlSlug = rs.getString("url_slug");
                pv.setImageUrl("images/" + urlSlug + ".jpg");

                variants.add(pv);
            }
        }

        return variants;
    }
}
