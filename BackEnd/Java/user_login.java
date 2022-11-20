
import java.sql.*;

public class user_login {
    // Member Variables:

    // DATABASE-RELATED INFO:
    // setup the variables for connecting with the database
    private String driver = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://mysql:3306/";
    private String dbName = "BankingSystemDB";
    // root user information
    private String user = "root"; 
    private String password = "wolfe2022";
    // variables needed for connection establishment and query
    private Connection connection = null;
    private PreparedStatement statement = null;
    private ResultSet resultSet = null;
    private boolean userExists = false;
    // DATA FROM THE SERVLET:
    private String userName;
    private String passWord;
    // set up the sql query
    private String sqlCheck;

    public user_login(String username, String pass) {
        userName = username;
        passWord = pass;

        sqlCheck  = "SELECT US.username FROM users AS US WHERE US.username = '"+userName+"' AND US.account_password = '"+passWord+"';";
    }

    public boolean sendInfo() {
        try {
            // First step: Connection...
            // Set up the driver
            Class.forName(driver);
            // establish connection to database
            connection = DriverManager.getConnection(url + dbName, user, password);
            // Prepare the sql statement to check if username and password match up
            statement = connection.prepareStatement(sqlCheck);
            resultSet = statement.executeQuery();
            userExists = resultSet.next();

            return userExists;

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Cannot Connect to the database");
            return false;
        } finally {
            if (resultSet != null) try { resultSet.close(); } catch (SQLException ignore) {}
            if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
            if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
        }
    }
    
}