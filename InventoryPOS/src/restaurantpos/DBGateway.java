package restaurantpos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBGateway {

    static Connection connection = null;
    ResultSet RS;
    static Statement stmt = null;
    static Statement stmtDrop = null;

    public DBGateway() {
    }

    public Connection connectionTest() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Missing Oracle JDBC Driver?");
            e.printStackTrace();
        }

        System.out.println("Successfully Connected with Oracle");

        try {
            connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost", "system",
                    "123456");
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            System.out.println(e.toString());
            e.printStackTrace();
        }


        if (connection != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }
        
//            SelectUsers();
        
        return connection;
    }
    
    public Connection Connect_To_MySQL() {
        String url = "jdbc:mysql://142.4.49.157/newshadpos";
        String username = "newshadpos";
        String password = "asdfghjkl0";
        System.out.println("-------- Mysql JDBC Connection Testing ------");
        Connection connection = null;
        try {
            Class.forName("com.mysql.fabric.jdbc.FabricMySQLDriver");

        } catch (ClassNotFoundException e) {
            System.out.println("Where is your Mysql JDBC Driver?");
            e.printStackTrace();
        }

        System.out.println("Mysql JDBC Driver Registered!");

        try {
            connection = DriverManager.getConnection(url,username,password);
        } catch (SQLException e) {

            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        } 

        if (connection != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }
        return connection;
    }

    // Select all users
    public static void SelectUsers() throws SQLException {
        String createPackage = null;
        String createFunction = null;
        String createItemPackage = null;
        String createGetItem = null;

        String queryDrop =
                "DROP FUNCTION IF EXISTS getUser ";

        createPackage = "CREATE OR REPLACE PACKAGE types "
                + "AS "
                + "    TYPE ref_cursor IS REF CURSOR; "
                + "END;";

        createItemPackage = "CREATE OR REPLACE PACKAGE types "
                + "AS "
                + "    TYPE ref_cate IS REF CURSOR; "
                + "END; ";

        createFunction = "CREATE OR REPLACE  "
                + "FUNCTION getUser(username IN VARCHAR2, userpass IN VARCHAR2) "
                + "    RETURN types.ref_cursor "
                + "AS "
                + "    stock_cursor types.ref_cursor; "
                + "BEGIN "
                + "    OPEN stock_cursor FOR "
                + "        SELECT user_id, user_name, user_pass, user_role, created_on  "
                + "	FROM users  "
                + "	where user_name = username and "
                + "	user_pass = userpass; "
                + "	 "
                + "    RETURN stock_cursor; "
                + "END; ";

        createGetItem = "CREATE OR REPLACE  "
                + "FUNCTION getItem(catname IN VARCHAR2) "
                + "    RETURN types.ref_cursor "
                + "AS "
                + "    stock_cursor types.ref_cursor; "
                + "BEGIN "
                + "    OPEN stock_cursor FOR "
                + "        SELECT cat_id, cat_name	FROM inv_category  "
                + "	where cat_name = catname; "
                + "	 "
                + "    RETURN stock_cursor; "
                + "END;";

        try {
            System.out.println("Calling DROP SelectUsers Function");
            stmtDrop = connection.createStatement();
            stmtDrop.execute(queryDrop);
        } catch (SQLException e) {
        } finally {
            if (stmtDrop != null) {
                stmtDrop.close();
            }
        }

        try {
            stmt = connection.createStatement();
            stmt.executeUpdate(createPackage);
            stmt.executeUpdate(createFunction);
            stmt.executeUpdate(createItemPackage);
            stmt.executeUpdate(createGetItem);

            System.out.println("Function for SelectUsers created Successfully");
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public void addInventory(String[] data) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO products (product_id,product_name, details)VALUES (?, ?, ?)");
            statement.setString(1, data[1]);
            statement.setString(2, data[2]);
            statement.setString(3, data[3]);

            statement.execute();
            System.out.println("Added Successfully");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            System.out.println("Error while inserting data!");
        }
    }

    public ResultSet retrieveInventory() throws IOException {
        try {
            java.sql.Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM products");

            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void reduceInventory(int a) {
        try {
            PreparedStatement statement = connection
                    .prepareStatement("DELETE FROM products WHERE product_id = ?");

            statement.setInt(1, a);
            statement.execute();
            System.out.println("Deleted Successfully");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            System.out.println("Error while deleting data!");
        }
    }

    public int numberOfInventory() {
        int a = 0;
        try {
            java.sql.Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM products");
            while (rs.next()) {
                a++;
            }
            return a;
        } catch (SQLException e) {
            e.printStackTrace();
            return (Integer) null;
        }
    }
}
