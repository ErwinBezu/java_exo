package Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseManager {

    private static final String URI = "jdbc:mysql://localhost:3306/banque_app";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection () throws SQLException {
        return DriverManager.getConnection(URI,USERNAME,PASSWORD);
    }
}
