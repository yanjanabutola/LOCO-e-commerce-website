package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/loco?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";          // ✅ Your MySQL username
    private static final String PASSWORD = "";          // ✅ Your MySQL password (update if required)

    // Private constructor to prevent instantiation
    private DBConnection() {}

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new SQLException("❗ Failed to connect to database at " + URL + " → " + e.getMessage(), e);
        }
    }

    // Method to close DB connection safely
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("❗ Error closing database connection: " + e.getMessage());
            }
        }
    }
}
