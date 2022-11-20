import java.sql.*;
import java.sql.Timestamp;
import java.util.logging.*;

public class transfer {

    // Logger
    Logger myLog;
    // DATA FROM THE SERVLET:
    private int valueToDeposit = 0;
    // DATABASE-RELATED INFO:
    // setup the variables for connecting with the database
    private String driver = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://mysql:3306/";
    private String dbName = "BankingSystemDB";
    // root user information
    private String user = "root"; 
    private String password = "wolfe2022";
    // set up the sql query

    // For the Withdrawing Portion:
    private withdrawAction withdraw;
    // For the Deposit Portion, we are NOT verifing, so:

    // set up the sql query
    // need multiple queries to enter information in to transaction table
    private String checkOwnershipSQL = "";
    private String insertTransactionSQL = "";

    // variables needed for connection establishment and query
    private Connection connection = null;
    private PreparedStatement statement1 = null;
    private PreparedStatement statement2 = null;
    private ResultSet resultSet1 = null;
    private ResultSet resultSet2 = null;

    private int account_id = 0;
    private int type_id = 0;
    
    
    public transfer(String usernm, String accountNumToWithdraw, int withdrawAmt, String accountNumToDeposit, String routingNum) {
        // Initialize Logger
        if (myLog == null) {
            myLog  = Logger.getLogger (this.getClass().getName());
        }
        myLog.warning ("Initializing Variables");
        withdraw = new withdrawAction(usernm, accountNumToWithdraw, withdrawAmt);
        valueToDeposit = withdrawAmt;

        // initialize the queries
        checkOwnershipSQL = "SELECT ACC.account_number, ACC.account_id, ACC.type_id FROM accounts AS ACC WHERE ACC.account_number = '"+accountNumToDeposit+"' AND ACC.routing_number = '"+routingNum+"';";
    }

    public boolean sendInfo() {
        if (withdraw.sendInfo()) {
            if (deposit()) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    public boolean deposit() {
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
                // check verification
                if(resultSet1.next()) {
                    myLog.info("attempt to find ownership was successful ! ");
                    type_id = resultSet1.getInt("ACC.type_id");
                    account_id = resultSet1.getInt("ACC.account_id");
                    myLog.info("loaded account information into variables ! ");

                    myLog.info("check if valueToDeposit > 0 ! ");
                    if(valueToDeposit > 0){
                        myLog.info("Deposit Value not 0 ! ");

                        Timestamp ts = new Timestamp(System.currentTimeMillis());
                        // prepare the sql statement
                        if(type_id == 1){
                            myLog.info("Loading insert for Debit ! ");
                            insertTransactionSQL = "INSERT INTO transaction (account_id, transaction_timestamp, transaction_description, transaction_category, monetary_value, payment_due_date) VALUES ('"+account_id+"', '"+ts+"', 'E-Transfer', 'Debit', '"+valueToDeposit+"', '"+ts+"');";                            
                        } else if(type_id == 3) {
                            myLog.info("Loading insert for Checking ! ");
                            insertTransactionSQL = "INSERT INTO transaction (account_id, transaction_timestamp, transaction_description, transaction_category, monetary_value, payment_due_date) VALUES ('"+account_id+"', '"+ts+"', 'E-Transfer', 'Checking', '"+valueToDeposit+"', '"+ts+"');";
                        } else {
                            myLog.info("Loading insert for Credit ! ");
                            insertTransactionSQL = "INSERT INTO transaction (account_id, transaction_timestamp, transaction_description, transaction_category, monetary_value, payment_due_date) VALUES ('"+account_id+"', '"+ts+"', 'E-Transfer', 'Credit', '"+valueToDeposit+"', '"+ts+"');";
                        }
                        
                        myLog.info("Deposit prep complete ! ");
                        statement2 = connection.prepareStatement(insertTransactionSQL);
                        statement2.executeUpdate();
                        myLog.info("Deposit complete ! ");

                        // the only successful condition is right here
                        System.out.println("Queries completed a deposit successfully");
                        return true;
                    } else {
                        System.out.println("Negative dollar deposits are invalid !");                        
                    }
                } else {
                    System.out.println("User does not own this account"); 
                    myLog.info("User depositing into a different user's account ! ");
                    return true;
                }
            } catch (Exception e){
                myLog.info("Account does not exist with our bank !");
                return true;
            } finally {
                if (resultSet1 != null) try { resultSet1.close(); } catch (SQLException ignore) {}
                if (resultSet2 != null) try { resultSet2.close(); } catch (SQLException ignore) {}
                if (statement1 != null) try { statement1.close(); } catch (SQLException ignore) {}
                if (statement2 != null) try { statement2.close(); } catch (SQLException ignore) {}
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
