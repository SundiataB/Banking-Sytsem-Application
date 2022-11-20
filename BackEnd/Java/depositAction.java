import java.sql.*;
import java.sql.Timestamp;
import java.util.logging.*;

public class depositAction {
    // Member Variables:
    // Logger
    Logger myLog;
    // DATA FROM THE SERVLET:
    private String accountNumToWithdraw = "";
    private int valueToDeposit = 0;
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
    private String accIDTypeIDSQL = "";
    private String insertTransactionSQL = "";

    // variables needed for connection establishment and query
    private Connection connection = null;
    private PreparedStatement statement1 = null;
    private PreparedStatement statement2 = null;
    private PreparedStatement statement3 = null;
    private ResultSet resultSet1 = null;
    private ResultSet resultSet2 = null;
    private ResultSet resultSet3 = null;

    private int account_id = 0;
    private int type_id = 0;

    public depositAction(String usernm, String accountNum, int depositAmt) {
        // Initialize Logger
        if (myLog == null) {
            myLog  = Logger.getLogger (this.getClass().getName());
        }
        myLog.warning ("Initializing Variables");
        
        // Initialize all of the requisite account information for the query to use.
        username = usernm;
        accountNumToWithdraw = accountNum;
        valueToDeposit = depositAmt;

        // initialize the queries
        checkOwnershipSQL = "SELECT US.username FROM users AS US WHERE US.user_id = (SELECT AO.user_id FROM account_ownership AS AO WHERE AO.account_id = (SELECT ACC.account_id FROM accounts AS ACC WHERE ACC.account_number = '"+accountNumToWithdraw+"'));";
        accIDTypeIDSQL = "SELECT  ACC.account_id, ACC.type_id FROM accounts AS ACC WHERE ACC.account_number = '"+accountNumToWithdraw+"';";
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
                    myLog.info("preparing to find type_id ! ");
                    // prepare the sql statement to get type_id for that account
                    statement2 = connection.prepareStatement(accIDTypeIDSQL);
                    resultSet2 = statement2.executeQuery();
                    myLog.info("type_id query was successful ! ");

                    if(resultSet2.next())
                    {
                        myLog.info("Results for both account info queries were successful ! ");
                        type_id = resultSet2.getInt("ACC.type_id");
                        account_id = resultSet2.getInt("ACC.account_id");
                        myLog.info("loaded account information into variables ! ");

                        myLog.info("check if valueToDeposit > 0! ");
                        if(valueToDeposit >= 0){
                            myLog.info("Deposit Value not 0 ! ");

                            Timestamp ts = new Timestamp(System.currentTimeMillis());
                            // prepare the sql statement
                            if(type_id == 1){
                                myLog.info("Loading insert for Debit ! ");
                                insertTransactionSQL = "INSERT INTO transaction (account_id, transaction_timestamp, transaction_description, transaction_category, monetary_value, payment_due_date) VALUES ('"+account_id+"', '"+ts+"', 'Digital', 'Debit', '"+valueToDeposit+"', '"+ts+"');";                            
                            } else if(type_id == 3) {
                                myLog.info("Loading insert for Checking ! ");
                                insertTransactionSQL = "INSERT INTO transaction (account_id, transaction_timestamp, transaction_description, transaction_category, monetary_value, payment_due_date) VALUES ('"+account_id+"', '"+ts+"', 'Digital', 'Checking', '"+valueToDeposit+"', '"+ts+"');";
                            } else {
                                myLog.info("Loading insert for Credit ! ");
                                insertTransactionSQL = "INSERT INTO transaction (account_id, transaction_timestamp, transaction_description, transaction_category, monetary_value, payment_due_date) VALUES ('"+account_id+"', '"+ts+"', 'Digital', 'Credit', '"+valueToDeposit+"', '"+ts+"');";
                            }
                            
                            myLog.info("Deposit prep complete ! ");
                            statement3 = connection.prepareStatement(insertTransactionSQL);
                            statement3.executeUpdate();
                            myLog.info("Deposit complete ! ");

                            // the only successful condition is right here
                            System.out.println("Queries completed a deposit successfully");
                            return true;
                        } else {
                            System.out.println("Insufficient parameters in Bank account to make deposit");
                        }
                    } else {
                        System.out.println("Negative dollar deposits are invalid !");                        
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
                if (statement1 != null) try { statement1.close(); } catch (SQLException ignore) {}
                if (statement2 != null) try { statement2.close(); } catch (SQLException ignore) {}
                if (statement3 != null) try { statement3.close(); } catch (SQLException ignore) {}
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
