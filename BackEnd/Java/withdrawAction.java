import java.sql.*;
import java.sql.Timestamp;
import java.util.logging.*;

public class withdrawAction {
    // Member Variables:
    // Logger
    Logger myLog;
    // DATA FROM THE SERVLET:
    private String accountNumToWithdraw = "";
    private int valueToWithdraw = 0;
    private String username = "";

    // DATABASE-RELATED INFO:
    // setup the variables for connecting with the database
    private String driver = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://mysql:3306/";
    private String dbName = "BankingSystemDB";
    // root user information
    private String user = "root"; 
    private String password = "wolfe2022";
    // set up the sql query
    // need multiple queries to enter information in to transaction table
    private String checkOwnershipSQL = "";
    private String accountBalanceSQL = "";
    private String typeIdSQL = "";
    private String insertTransactionSQL = "";

    // variables needed for connection establishment and query
    private Connection connection = null;
    private PreparedStatement statement1 = null;
    private PreparedStatement statement2 = null;
    private ResultSet resultSet1 = null;
    private ResultSet resultSet2 = null;
    private PreparedStatement statement3 = null;
    private PreparedStatement statement4 = null;
    private ResultSet resultSet3 = null;
    private ResultSet resultSet4 = null;

    private int accountBal = 0;
    private String account_id = "";
    private int type_id = 0;

    public withdrawAction(String usernm, String accountNum, int withdrawAmt) {
        // Initialize Logger
        if (myLog == null) {
            myLog  = Logger.getLogger (this.getClass().getName());
        }
        myLog.warning ("Initializing Variables");
        
        // Initialize all of the requisite account information for the query to use.
        username = usernm;
        accountNumToWithdraw = accountNum;
        valueToWithdraw = -Math.abs(withdrawAmt);

        // initialize the queries
        checkOwnershipSQL = "SELECT US.username FROM users AS US WHERE US.user_id = (SELECT AO.user_id FROM account_ownership AS AO WHERE AO.account_id = (SELECT ACC.account_id FROM accounts AS ACC WHERE ACC.account_number = '"+accountNumToWithdraw+"'));";
        accountBalanceSQL = "SELECT TSN.account_id, sum(TSN.monetary_value) FROM transaction AS TSN WHERE TSN.account_id = (SELECT ACC.account_id FROM accounts AS ACC WHERE ACC.account_number = '"+accountNumToWithdraw+"') GROUP BY TSN.account_id ORDER BY TSN.account_id DESC;";
        typeIdSQL = "SELECT  ACC.type_id FROM accounts AS ACC WHERE ACC.account_number = '"+accountNumToWithdraw+"';";
    }

    public boolean sendInfo() {
        try {
            // setup the driver
            Class.forName(driver);
            // establish connection to database
            myLog.info("attempt to use DriverManager.getConnection()");
            connection = DriverManager.getConnection(url + dbName, user, password);
            myLog.info("attempt to connect to database was successful ! ");
            try {
                myLog.info("preparing check ownership query ! ");
                // prepare the sql statement to verify if the user owns the account they are withdrawing from
                statement1 = connection.prepareStatement(checkOwnershipSQL);
                resultSet1 = statement1.executeQuery();
                resultSet1.next();
                myLog.info("attempt to find ownership was successful ! ");

                String checkedUsername = resultSet1.getString("US.username");

                myLog.info(username + ".");
                myLog.info(checkedUsername + ".");

                // check verification
                if(username.equals(checkedUsername)){
                    myLog.info("User requesting and owner of account are the same user ! ");
                    // prepare the sql statement to get the balance and id num of the transactions for that account
                    myLog.info("attempt to find ownership was successful ! ");
                    myLog.info("preparing to find account balance ! ");
                    statement2 = connection.prepareStatement(accountBalanceSQL);
                    resultSet2 = statement2.executeQuery();
                    myLog.info("account balance query was successful ! ");
                    myLog.info("preparing to find type_id ! ");
                    // prepare the sql statement to get type_id for that account
                    statement3 = connection.prepareStatement(typeIdSQL);
                    resultSet3 = statement3.executeQuery();
                    myLog.info("type_id query was successful ! ");

                    if(resultSet2.next() && resultSet3.next())
                    {
                        myLog.info("Results for both account info queries were successful ! ");

                        accountBal = resultSet2.getInt("sum(TSN.monetary_value)");
                        account_id = resultSet2.getString("TSN.account_id");
                        type_id = resultSet3.getInt("ACC.type_id");
                        myLog.info("loaded account information into variables ! ");

                        myLog.info("check if accountbal is grater than value to wwithdraw ! ");
                        if(accountBal >= Math.abs(valueToWithdraw)){
                            myLog.info("Enough funds to make withdrawal ! ");

                            Timestamp ts = new Timestamp(System.currentTimeMillis());
                            // prepare the sql statement
                            if(type_id == 1){
                                myLog.info("Loading insert for Debit ! ");
                                insertTransactionSQL = "INSERT INTO transaction (account_id, transaction_timestamp, transaction_description, transaction_category, monetary_value, payment_due_date) VALUES ('"+account_id+"', '"+ts+"', 'Digital', 'Debit', '"+valueToWithdraw+"', '"+ts+"');";                            
                            } else if(type_id == 3) {
                                myLog.info("Loading insert for Checking ! ");
                                insertTransactionSQL = "INSERT INTO transaction (account_id, transaction_timestamp, transaction_description, transaction_category, monetary_value, payment_due_date) VALUES ('"+account_id+"', '"+ts+"', 'Digital', 'Checking', '"+valueToWithdraw+"', '"+ts+"');";
                            } else {
                                myLog.info("Account type was not one of the types that can have a withdrawal ! ");
                                return false;
                            }
                            
                            myLog.info("Withdrawal prep complete ! ");
                            statement4 = connection.prepareStatement(insertTransactionSQL);
                            statement4.executeUpdate();
                            myLog.info("Withdrawal complete ! ");

                            // the only successful condition is right here
                            System.out.println("Queries completed a withdraw successfully");
                            return true;
                        } else {
                            System.out.println("Insufficient Funds in Bank account to make withdrawal");
                        }
                    } else {
                        System.out.println("Zero Funds in account to make withdrawal");                        
                    }
                } else {
                    System.out.println("User does not own this account"); 
                    myLog.info("User requesting and owner of account are NOT the same user ! ");
                }
            } catch (Exception e){
                e.printStackTrace();
                System.out.println("Query was unsuccessful !");
            } finally {
                if (resultSet1 != null) try { resultSet1.close(); } catch (SQLException ignore) {}
                if (resultSet2 != null) try { resultSet2.close(); } catch (SQLException ignore) {}
                if (resultSet3 != null) try { resultSet3.close(); } catch (SQLException ignore) {}
                if (resultSet4 != null) try { resultSet4.close(); } catch (SQLException ignore) {}
                if (statement1 != null) try { statement1.close(); } catch (SQLException ignore) {}
                if (statement2 != null) try { statement2.close(); } catch (SQLException ignore) {}
                if (statement3 != null) try { statement3.close(); } catch (SQLException ignore) {}
                if (statement4 != null) try { statement4.close(); } catch (SQLException ignore) {}
            }
            System.out.println("Database connection established");
        } catch (Exception e) {
            System.err.println("Cannot Connect to the database");
        } finally {
            if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
        }
        return false;
    }
}
