package code.vibes.onlinebankingsystem.databaseconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static Connection provideConnection() throws SQLException {

        String url = getEnvironmentVariable(
                "DB_URL",
                "jdbc:mysql://localhost:3306/bankingsystem?useSSL=false&serverTimezone=UTC");
        String username = getEnvironmentVariable("DB_USERNAME", null);
        String password = getEnvironmentVariable("DB_PASSWORD", null);

        return DriverManager.getConnection(url, username, password);
    }

    private static String getEnvironmentVariable(String name, String defaultValue) throws SQLException {
        String value = System.getenv(name);
        if (value == null || value.trim().isEmpty()) {
            if (defaultValue != null) {
                return defaultValue;
            }
            throw new SQLException("Required environment variable is missing: " + name);
        }
        return value;
    }
}
