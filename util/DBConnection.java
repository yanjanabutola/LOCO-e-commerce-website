package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/your_database_name";
    private static final String USER = "your_username";
    private static final String PASSWORD = "your_password";

    // Private constructor to prevent instantiation
    private DBConnection() {}

    public static Connection getConnection() throws SQLException {
        try {
            // Newer JDBC versions don't require Class.forName()
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            // Wrap and rethrow with more context
            throw new SQLException("Failed to connect to the database", e);
        }
    }

    // Helper method to close connection quietly
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                // Log this error if needed
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}