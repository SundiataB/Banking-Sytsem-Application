// This should open a connection to the database 
// Note: I placed the mySQL jar file in a folder called "lib" in the Java folder
// class path used: ./lib/mysql-connector-java-8.0.28.jar:.

import java.sql.*;
//import java.sql.DriverManager;
//import java.sql.SQLException;
public class DBConnection {
    // Main class to run test, can be removed
    public static void main(String[] args) {
        connect();
    }

    // This Class opens the connection
    public static void connect() 
    {
        //String url = "jdbc:mysql://mysql:3306/Bank-Server";
        //String dbUsername = "root";
        //String dbPassword = "wolfe2022";
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://mysql:3306/";
        String dbName = "BankingSystemDB";
        // root user information
        String user = "root"; 
        String password = "wolfe2022";

        // variables needed for connection establishment
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        boolean login = false;

        try{
           
           // establish connection to database
           connection = DriverManager.getConnection(url + dbName, user, password);
           // prepare the sql statement

           //statement.setString(1, userName);
           //statement.setString(2, password);
           // return the results of the query
           resultSet = statement.executeQuery();

           // if there is a row of data it will confirm that there is a user with that username and password
           login = resultSet.next();

           System.out.println("Database connection established");

            // Connect
            System.out.println("Connected");
        }
        catch(SQLException e) {
            // Exception if not able to connect
            e.printStackTrace();
            System.out.println("Connection to database failed\n" + e);
        }
    }    
}
