package com.tr.ereceipt.ui.ereceipt;

import java.sql.*;

/*
* Connect Database using Project Structure
* https://www.oracle.com/database/technologies/appdev/jdbc-downloads.html
* https://www.codersarts.com/post/how-to-connect-a-javafx-program-to-oracle-database
* https://www.swtestacademy.com/database-operations-javafx/
*/

/*
 * Manually Adding the JAR (If not using a build tool):
 * Download the ojdbc8.jar from the Oracle website.
 * Place the JAR file in a suitable location within your project, such as a libs directory.
 *
 * Add the JAR to your project classpath:
 *      Eclipse: Right-click your project > Properties > Java Build Path > Libraries > Add JARs...
 *      and select the ojdbc8.jar.
 *      IntelliJ IDEA: File > Project Structure > Modules > Dependencies > + > JARs or directories and select the ojdbc8.jar.
 */

// Remember to commit your database when creating and inserting values in it

public class DBUtil {
    // JDBC Driver
    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    // Connection
    private static Connection con;
    private static ResultSet rs;
    private static PreparedStatement ps;
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
            *  Initialize of the driver connection is saved using getConnection,
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
        if (rs != null) {
            try {
                rs.close();
                System.out.println("ResultSet closed");
            } catch (SQLException e) {
                System.out.println("ResultSet close failed");
            }
        }
        if (ps != null) {
            try {
                ps.close();
                System.out.println("PreparedStatement closed");
            } catch (SQLException e) {
                System.out.println("PreparedStatement close failed");
            }
        }
    }

    public static void setRs(ResultSet rs) {
        DBUtil.rs = rs;
    }

    public static void setPs(PreparedStatement ps) {
        DBUtil.ps = ps;
    }
}
