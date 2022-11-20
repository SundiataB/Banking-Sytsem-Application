import java.sql.*;
import java.util.*;
import java.util.logging.*;
import java.util.ArrayList;



public class accountSummary {
    private Logger classLog;
    
    // setup the variables for connecting with the database
    private String driver = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://mysql:3306/";
    private String dbName = "BankingSystemDB";

    // root user information
    private String user = "root";
    private String password = "wolfe2022";

    

    // for account numbers associated with a user
    private ArrayList<String> accountNumbers = new ArrayList<String>(0);
    private ArrayList<Integer> account_types = new ArrayList<Integer>(0);
    private ArrayList<String> routing_numbers = new ArrayList<String>(0);
    private ArrayList<Integer> current_balances = new ArrayList<Integer>(0);

    private int numberofAccounts = 0;

    // variables needed for connection establishment
    private Connection connection = null;
    private PreparedStatement statement1 = null;
    private PreparedStatement statement2 = null;
    private ResultSet resultSet1 = null;
    private ResultSet resultSet2 = null;
    // Data from servlet.
    private String username;

    // set up the sql query
    //approved SQL
    private String getAccountNumsSQL = "";
    //approved SQL
    private String accountbalanceSQL = ""; 

    public String check = "Initialization value.";
    
    
    // Constructor
    public accountSummary (String servletUsername) {
        if (classLog == null) {
            classLog  = Logger.getLogger(this.getClass().getName() + "JDBC");
        }
        username = servletUsername;
        getAccountNumsSQL = "SELECT ACC.account_number, ACC.type_id, ACC.routing_number FROM accounts AS ACC WHERE ACC.account_id IN (SELECT AO.account_id FROM account_ownership AS AO WHERE AO.user_id = (SELECT US.user_id FROM users AS US WHERE US.username = '"+username+"')) ORDER BY ACC.account_id;";
        accountbalanceSQL = "SELECT ACC.account_number, sum(TSN.monetary_value) FROM transaction AS TSN INNER JOIN accounts AS ACC ON ACC.account_id = TSN.account_id WHERE ACC.account_id IN (SELECT AO.account_id FROM account_ownership as AO WHERE AO.user_id = (SELECT US.user_id FROM users as US WHERE US.username = '"+username+"')) GROUP BY ACC.account_number ORDER BY ACC.account_id;"; 
    }
    
    public void sendInfo() {
        classLog.warning("variables for account summary page servlet setup. attempting try catch block");
        try {
            // setup the driver
            Class.forName(driver);
            classLog.info("attempt to use DriverManager.getConnection()");
            // establish connection to database
            connection = DriverManager.getConnection(url + dbName, user, password);
            classLog.info("attempt to connect to database was successful !!! ");

            classLog.info("Attempting Statement1");
            // prepare the transactions sql statement
            statement1 = connection.prepareStatement(getAccountNumsSQL);
            classLog.info("attempt to prepare the statement1 was successful!!! ");
            // return the results of the query
            resultSet1 = statement1.executeQuery();
            classLog.info("Attempt to execute query successful!!! ");
            
            classLog.info("Inserting resultset1 into data");
            
            // get the account numbers of the user
            while(resultSet1.next()) {
                accountNumbers.add(resultSet1.getString("ACC.account_number"));
                classLog.info("account number is "+resultSet1.getString("ACC.account_number"));
                account_types.add(resultSet1.getInt("ACC.type_id"));
                classLog.info("account types is "+resultSet1.getInt("ACC.type_id"));
                routing_numbers.add(resultSet1.getString("ACC.routing_number"));
                classLog.info("routing number is "+resultSet1.getString("ACC.routing_number"));
            }
            // get the number of accounts that the user has
            numberofAccounts = accountNumbers.size();
            classLog.info("number of accounts is "+numberofAccounts);
            
            
            classLog.info("Attempting Statement2");
            // prepare the transactions sql statement
            statement2 = connection.prepareStatement(accountbalanceSQL);
            classLog.info("attempt to prepare the statement1 was successful!!! ");
            // return the results of the query
            resultSet2 = statement2.executeQuery();
            classLog.info("Attempt to execute query successful!!! ");
            
            classLog.info("Inserting resultSet2 into data");
            // get the account numbers of the user
            while(resultSet2.next()) {
                current_balances.add(resultSet2.getInt("sum(TSN.monetary_value)"));
                classLog.info("Account number is:"+resultSet2.getString("ACC.account_number")+" balance of accounts is "+resultSet2.getInt("sum(TSN.monetary_value)"));
            }
            
            System.out.println("Database connection established");
            classLog.info("Database Connection was successfully established !!! ");
            check = "Try block successful";
        } catch (Exception e) {
            check = "Exception caught.";
            classLog.info("Database Connection was unsuccessful !!! ");
            System.err.println("Cannot Connect to the database");
            //throw new ServletException("Login failed", e);
        } finally {
            if (resultSet1 != null) try { resultSet1.close(); } catch (SQLException ignore) {}
            if (statement1 != null) try { statement1.close(); } catch (SQLException ignore) {}
            if (resultSet2 != null) try { resultSet2.close(); } catch (SQLException ignore) {}
            if (statement2 != null) try { statement2.close(); } catch (SQLException ignore) {}
            if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
        }
    }

    public ArrayList<String> getAccountNumbers () {
        return accountNumbers;
    }

    public ArrayList<Integer> getAccountTypes () {
        return account_types;
    }

    public ArrayList<String> getRoutingNumbers () {
        return routing_numbers;
    }

    public ArrayList<Integer> getCurrentBalances () {
        return current_balances;
    }

    public int getNumberofAccounts () {
        return numberofAccounts;
    }

    // JUnit testing purposes. 
    public String sendInfoTest() {
        this.sendInfo();
        return check;
    }
}
