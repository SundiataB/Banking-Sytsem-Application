import java.sql.*;
import java.util.logging.*;

public class newBankAccount {
    // Member Variables:
    // Logger
    Logger classLog;
    // DATABASE-RELATED INFO:
    // setup the variables for connecting with the database
    private String driver = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://mysql:3306/";
    private String dbName = "BankingSystemDB";
    // root user information
    private String user = "root"; 
    private String password = "wolfe2022";

    // set up the sql query to check for unique account_number
    private String accountNumcheck;
    // set up the sql query for accounts table
    private String accountInsert;
    // set up the sql query for ownership table
    private String ownershipInsert;
    private String ownershipValues;
    private String ownershipsql;
    // variables needed for connection establishment
    private Connection connection = null;
    private PreparedStatement statement1 = null;
    private PreparedStatement statement2 = null;
    private PreparedStatement statement3 = null;
    private ResultSet resultSet1 = null;
    private boolean alreadyExists = false;

    // DATA FROM THE SERVLET:
    private String userName;
    private int typeID;
    // the remaining variables we need
    private long accountNumber;
    private String routingNumber = "999999999";

    // Methods:
    public newBankAccount(String user, int type) {
        // Initialize Logger
        if (classLog == null) {
            classLog  = Logger.getLogger (this.getClass().getName());
        }
        classLog.warning ("Initializing Variables");
        
        // Initialize all of the requisite account information for the query to use.
        userName = user;
        typeID = type;
        
        classLog.info("Here is the account type: " + typeID);
    }

    public String sendInfo() {
        try {
            // First step: Connection...
            // Set up the driver
            Class.forName(driver);
            // establish connection to database
            classLog.info("attempt to use DriverManager.getConnection()");
            connection = DriverManager.getConnection(url + dbName, user, password);
            classLog.info("attempt to connect to database was successful ! ");

            try {

                do { //Generate a unique account number for the new account
                    classLog.info("Generating an account number ");
                    accountNumber =  (long)(Math.random() * (9999999999L - 1111111111L)) + 1111111111L;
                    accountNumcheck = "SELECT ACC.account_number FROM accounts as ACC WHERE ACC.account_number ='"+String.valueOf(accountNumber)+"'";
                    statement1 = connection.prepareStatement(accountNumcheck);
                    resultSet1 = statement1.executeQuery();
                    alreadyExists = resultSet1.next();
                } while (alreadyExists);
                classLog.info("Unique account number generated ! The number is "+String.valueOf(accountNumber));

                // Initialize the query with the account info
                accountInsert = "INSERT INTO accounts (account_number, type_id, routing_number) VALUES ('"+String.valueOf(accountNumber)+"', '"+typeID+"', '"+routingNumber+"');";
                // Prepare the sql statement to add the new account info to the database
                statement2 = connection.prepareStatement(accountInsert);
                classLog.info("attempt to prepare the statement was successful ! ");
                // Send the information to the database and store in BankingSystemDB
                statement2.executeUpdate();
                classLog.info("account table update successful ! ");

                // Initialize the query with the account ownership info
                ownershipInsert = "INSERT INTO account_ownership (account_id, user_id)";
                ownershipValues = "VALUES ((SELECT ACC.account_id FROM accounts AS ACC WHERE ACC.account_number = '"+String.valueOf(accountNumber)+"'), (SELECT US.user_id FROM users AS US WHERE US.username = '"+userName+"'));";
                ownershipsql = ownershipInsert+" "+ownershipValues;
                // Prepare the sql statement to add the new account info to the database
                statement3 = connection.prepareStatement(ownershipsql);
                classLog.info("attempt to prepare the statement was successful ! ");
                // Send the information to the database and store in BankingSystemDB
                statement3.executeUpdate();
                classLog.info("account_ownership table update successful ! ");

                // If it returns true, then the query was successfully executed
                System.out.println("Queries executed successfully");
                classLog.info("Queries executed successfully !!! ");
                return String.valueOf(accountNumber);

            } catch (Exception e) {
                classLog.info("A Query for the database failed !!! ");
                System.err.println("A Query for the database failed");
            } finally {
                if (resultSet1 != null) try { resultSet1.close(); } catch (SQLException ignore) {}
                if (statement1 != null) try { statement1.close(); } catch (SQLException ignore) {}
                if (statement2 != null) try { statement2.close(); } catch (SQLException ignore) {}
                if (statement3 != null) try { statement3.close(); } catch (SQLException ignore) {}
            }

            System.out.println("Database connection established");
            classLog.info("Database Connection was successfully established !!! ");    
        } catch (Exception e) {
            e.printStackTrace();
            classLog.info("Database Connection was unsuccessful !!! ");
            System.err.println("Cannot Connect to the database");
            return null;
        } finally {
            if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
        }

        return null;
    }

}
