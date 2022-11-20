import java.util.logging.*;
import java.sql.*;
import java.util.ArrayList;

public class recentTransaction {
    // Member Variables:
    //logger
    public Logger classLog; 
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
    // store the account numbers
    private PreparedStatement statement1 = null;
    // get transaction info from account numbers
    private PreparedStatement statement2 = null;
    // get account balance of account numbers
    private PreparedStatement statement3 = null;
    private ResultSet resultSet1 = null;
    private ResultSet resultSet2 = null;
    private ResultSet resultSet3 = null;
    // DATA FROM THE SERVLET:
    private String userName;
    // set up the sql query
    private String getAccountNumsSQL = "";
    private String transactionsSQL = ""; // adjusted using a loop later on
    private String accountbalanceSQL = "";

    // for account numbers associated with a user
    private ArrayList<String> accountNumbers = new ArrayList<String>(0);
    private int numberofAccounts;

    // for transaction information based on account numbers
    private ArrayList<ArrayList<Timestamp>> transactionDates = new ArrayList< ArrayList<Timestamp> >(0);
    private ArrayList<ArrayList<String>> transactionDescriptions = new ArrayList< ArrayList<String> >(0);
    private ArrayList<ArrayList<String>> transactionCategories = new ArrayList< ArrayList<String> >(0);
    private ArrayList<ArrayList<Integer>> transactionAmounts = new ArrayList< ArrayList<Integer> >(0);
    private ArrayList<Integer> accountBalance = new ArrayList<Integer>(0);
    
    //Methods//

    //CONSTRUCTOR
    public recentTransaction(String usernm) {
        if (classLog == null) {
            classLog  = Logger.getLogger(this.getClass().getName());
        }
        userName = usernm;
        getAccountNumsSQL = "SELECT ACC.account_number FROM accounts AS ACC WHERE ACC.account_id IN (SELECT AO.account_id FROM account_ownership AS AO WHERE AO.user_id = (SELECT US.user_id FROM users AS US WHERE US.username = '"+userName+"')) ORDER BY ACC.account_id;";
        accountbalanceSQL = "SELECT ACC.account_number, sum(TSN.monetary_value) FROM transaction AS TSN INNER JOIN accounts AS ACC ON ACC.account_id = TSN.account_id WHERE ACC.account_id IN (SELECT AO.account_id FROM account_ownership as AO WHERE AO.user_id = (SELECT US.user_id FROM users as US WHERE US.username = '"+userName+"')) GROUP BY ACC.account_number ORDER BY ACC.account_id;";

    }

    //GET functions for returning variables
    public int getNumberofAccounts() {
        return numberofAccounts;
    }
    public ArrayList<String> getAccountNumbers() {
        return accountNumbers;
    }
    public ArrayList<Integer> getAccountBalance() {
        return accountBalance;
    }
    public ArrayList<ArrayList<Timestamp>> getTransactionDates() {
        return transactionDates;
    }
    public ArrayList<ArrayList<String>> getTransactionDescriptions() {
        return transactionDescriptions;
    }
    public ArrayList<ArrayList<String>> getTransactionCategories() {
        return transactionCategories;
    }
    public ArrayList<ArrayList<Integer>> getTransactionAmounts() {
        return transactionAmounts;
    }

    //Database connection function
    public boolean sendInfo() {
        try {
            // setup the driver
            Class.forName(driver);
            classLog.info("attempt to use DriverManager.getConnection()");
            // establish connection to database
            connection = DriverManager.getConnection(url + dbName, user, password);
            classLog.info("attempt to connect to database was successful !!! ");
            
            try{
                classLog.info("Attempting Statement1");
                // prepare the transactions sql statement
                statement1 = connection.prepareStatement(getAccountNumsSQL);
                classLog.info("attempt to prepare the statement1 was successful!!! ");
                // return the results of the query
                resultSet1 = statement1.executeQuery();
                classLog.info("Attempt to execute query successful!!! ");
                
                classLog.info("Inserting resultset1 into data");
                while(resultSet1.next()) {
                    //resultSet1.next();
                    accountNumbers.add(resultSet1.getString("ACC.account_number"));
                } 
                
                numberofAccounts = accountNumbers.size();

                classLog.info("Attempting Statement2");
                // select transaction information for a users bank account
                for(int i=0; i<numberofAccounts; i++){
                    classLog.info("Statement2 loop number"+i);
                    
                    // smaller arraylists for this particular account number
                    ArrayList<Timestamp> dates = new ArrayList<Timestamp>(0);
                    ArrayList<String> descriptions = new ArrayList<String>(0);
                    ArrayList<String> category = new ArrayList<String>(0);
                    ArrayList<Integer> moneyValues = new ArrayList<Integer>(0);
                    
                    // update the sql query to work for this new account number
                    transactionsSQL = "SELECT ACC.account_number, TSN.transaction_timestamp, TSN.transaction_description, TSN.transaction_category, TSN.monetary_value FROM transaction AS TSN INNER JOIN accounts AS ACC ON ACC.account_id = TSN.account_id WHERE ACC.account_number = '"+accountNumbers.get(i)+"';";
                    // prepare the transactions sql statement
                    statement2 = connection.prepareStatement(transactionsSQL);
                    classLog.info("attempt to prepare the statement2 was successful!!! ");
                    // return the results of the query
                    resultSet2 = statement2.executeQuery();
                    classLog.info("Attempt to execute query successful!!! ");

                    classLog.info("Inserting resultset2 into data");
                    // check if next row exists for this account number
                    while(resultSet2.next()) {
                        //potential - either the issue is that the while is doing it each loop or the first next() is failing
                        // go to next row
                        classLog.info("moving to next row");
                        //resultSet2.next();
                        classLog.info("moved successfully");
                        // add information to arraylists
                        classLog.info("Adding dates");
                        dates.add(resultSet2.getTimestamp("TSN.transaction_timestamp"));
                        classLog.info("Adding descriptions");
                        descriptions.add(resultSet2.getString("TSN.transaction_description"));
                        classLog.info("Adding category");
                        category.add(resultSet2.getString("TSN.transaction_category"));
                        classLog.info("Adding money value");
                        moneyValues.add(resultSet2.getInt("TSN.monetary_value"));
                        classLog.info("Success for this row");
                    } 

                    classLog.info("Inserting data into top level of arraylist");
                    // add to final arraylist
                    transactionDates.add(dates);
                    transactionDescriptions.add(descriptions);
                    transactionCategories.add(category);
                    transactionAmounts.add(moneyValues);
                }
                
                classLog.info("Attempting Statement3");
                // prepare the transactions sql statement
                statement3 = connection.prepareStatement(accountbalanceSQL);
                classLog.info("attempt to prepare the statement3 was successful!!! ");
                // return the results of the query
                resultSet3 = statement3.executeQuery();
                classLog.info("Attempt to execute query successful!!! ");
                
                classLog.info("Adding data into variables");
                while (resultSet3.next()) {
                    classLog.info("moving to next row");
                    //resultSet3.next();
                    classLog.info("moved successfully");
                    // get data from transactions sql query
                    classLog.info("attempt to put account balance in now");
                    accountBalance.add(resultSet3.getInt("sum(TSN.monetary_value)"));
                    classLog.info("added successfully");
                } 

                System.out.println("Queries executed successfully");
                classLog.info("Queries executed successfully !!! ");
            } catch (Exception e) {
                classLog.info("A Query for the database failed !!! ");
                System.err.println("A Query for the database failed");
            } finally {
                if (resultSet1 != null) try { resultSet1.close(); } catch (SQLException ignore) {}
                if (statement1 != null) try { statement1.close(); } catch (SQLException ignore) {}
                if (resultSet2 != null) try { resultSet2.close(); } catch (SQLException ignore) {}
                if (statement2 != null) try { statement2.close(); } catch (SQLException ignore) {}
                if (resultSet3 != null) try { resultSet3.close(); } catch (SQLException ignore) {}
                if (statement3 != null) try { statement3.close(); } catch (SQLException ignore) {}
            }
            
            System.out.println("Database connection established");
            classLog.info("Database Connection was successfully established !!! ");
        } catch (Exception e) {
            classLog.info("Database Connection was unsuccessful !!! ");
            System.err.println("Cannot Connect to the database");
        } finally {
            if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
        }

        return true;
    }
}