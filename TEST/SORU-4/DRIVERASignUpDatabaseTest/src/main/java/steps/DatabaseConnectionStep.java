package steps;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionStep {
    private Connection connection;

    public DatabaseConnectionStep() {
        try {
            // PostgreSQL JDBC URL
            String url = "jdbc:postgresql://localhost:5432/DriveraDB";
            String user = "username";
            String password = "password";

            // PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");

            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // Error if driver not found
        } catch (SQLException e) {
            e.printStackTrace(); // Error if connection cannot be established
        }
    }

    public Connection getConnection() {
        return connection;
    }

}
