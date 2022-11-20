import java.sql.*;
import java.util.logging.*;

public class createAccount {
    // Member Variables:
    // Logger
    Logger myLog;
    // DATABASE-RELATED INFO:
    // setup the variables for connecting with the database
    private String driver = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://mysql:3306/";
    private String dbName = "BankingSystemDB";
    // root user information
    private String user = "root"; 
    private String password = "wolfe2022";
    // set up the sql query
    private String sqlinsert;
    private String sqlvalues;
    private String sql;
    private String sqlcheck;
    // variables needed for connection establishment
    private Connection connection = null;
    private PreparedStatement statement = null;
    private ResultSet resultSet = null;
    private boolean alreadyExists = false;

    // DATA FROM THE SERVLET:
    private String userName;
    private String passWord;
    // the remaining variables we need
    private String firstname;
    private String lastname;
    private String email;
    private String contactnumber;
    private String streetaddress;
    private String postcode;
    // Date-time is stored as follows: "YYYY-MM-DD". BE SURE THAT THE DATA IS STORED LIKE THAT, OR ELSE IT WILL FAIL!!!!
    private String dateofbirth;
    private String ssn;

    // Methods:
    public createAccount(String user, String pass, String first, String last, String eml, String contact, String street, String post, String dob, String social) {
        // Initialize Logger
        if (myLog == null) {
            myLog  = Logger.getLogger (this.getClass().getName());
        }
        myLog.warning ("Initializing Variables");
        
        // Initialize all of the requisite account information for the query to use.
        userName = user;
        passWord = pass;
        firstname = first;
        lastname = last;
        email = eml;
        contactnumber = contact;
        streetaddress = street;
        postcode = post;
        dateofbirth = dob;
        ssn = social;

        myLog.info("Here is the date of birth: " + dateofbirth);

        // Initialize the query with the aforementioned requisite info
        sqlinsert = "INSERT INTO users (username, account_password, first_name, last_name, email, contact_number, street_address, postcode, date_of_birth, social_security_num, activity_status)";
        sqlvalues = "VALUES ('"+userName+"', '"+passWord+"', '"+firstname+"', '"+lastname+"', '"+email+"', '"+contactnumber+"', '"+streetaddress+"', '"+postcode+"', '"+dateofbirth+"', "+ssn+", TRUE);";
        sql = sqlinsert+" "+sqlvalues;
        sqlcheck = "SELECT US.username FROM users as US WHERE username ='"+userName+"'";
    }

    public boolean sendInfo() {
        try {
            // First step: Connection...
            // Set up the driver
            Class.forName(driver);
            // establish connection to database
            myLog.info("attempt to use DriverManager.getConnection()");
            connection = DriverManager.getConnection(url + dbName, user, password);
            myLog.info("attempt to connect to database was successful ! ");
            // Prepare the sql statement to first check and see if there is already that information in the database
            statement = connection.prepareStatement(sqlcheck);
            resultSet = statement.executeQuery();
            alreadyExists = resultSet.next();
            myLog.info("attempt to initialize the values was successful ! ");

            if(!alreadyExists){
                // Prepare the sql statement to add their info to the database
                statement = connection.prepareStatement(sql);
                myLog.info("attempt to prepare the statement was successful ! ");
                // Send the information to the database and store in BankingSystemDB
                statement.executeUpdate();
                // If it returns true, then the query was successfully executed
                myLog.info("Attempt to execute query successful ! ");
                myLog.info("Database Connection was successfully established and query executed - new user created ! ");
                return true;
            } else {
                // If it returns false, then the information already exists, and therefore there is no purpose
                // in going further.
                myLog.info("Database Connection was successfully established and but username already exists ! ");
                return false;
            }
            
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
