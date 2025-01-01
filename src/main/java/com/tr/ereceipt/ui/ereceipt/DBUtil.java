package com.tr.ereceipt.ui.ereceipt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
* Connect Database using Project Structure
* https://www.oracle.com/database/technologies/appdev/jdbc-downloads.html
* https://www.codersarts.com/post/how-to-connect-a-javafx-program-to-oracle-database
* https://www.swtestacademy.com/database-operations-javafx/
*/

public class DBUtil {
    // JDBC Driver
    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    // Connection
    private static Connection con;
    // Connection String
    // String connStr = "jdbc:oracle:thin:@IP:Port/SID";
    private static final String conStr = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String user = "travisrego";
    private static final String password = "123";
    private static boolean isDriverLoaded = false;

    // Loading the Oracle JDBC Driver
    static {
        try {
            // Get class using Class Name
            Class.forName(JDBC_DRIVER);
            System.out.println("Driver loaded");
            isDriverLoaded = true;
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found");
        }
    }

    // Connecting to Oracle Database
    public static Connection getConnection() throws SQLException {
        if (isDriverLoaded) {
            /* java.sql library has a class called DriverManager
            *  Initialize of the driver connection is saved using getConnection
            *  which is a method in DriverManager class
            */
            con = DriverManager.getConnection(conStr, user, password);
            System.out.println("Connection established");
        }
        return con;
    }

    // Disconnecting the Oracle Database
    public static void closeConnection() {
        if (con != null) {
            try {
                con.close();
                System.out.println("Connection closed");
            } catch (SQLException e) {
                System.out.println("Connection close failed");
            }
        }
    }

}
