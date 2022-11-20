import java.sql.Timestamp;
import java.util.Date;

public class transaction extends accounts{

    private int accountId = 0;
    private Timestamp transactionTimeStamp;
    private int monetaryValue;
    private Timestamp paymentDueDate;
    private int accountDeposit;
    private int accountWithdrawal;
    private int transferMoney;
    private int updateBalance;
    
    // CREATE 'DEPOSIT', 'WITHDRAWAL', AND 'TRANSFER MONEY' METHODS WITHIN THIS CLASS
    // CREATE A METHOD THAT DISPLAYS TRANSACTION HISTORY

    public transaction(int accountId, String username, String password) {
        super(accountId, username, password);
    }

    public void setTransactionTimeStamp(Timestamp timeStamp){
        this.transactionTimeStamp = timeStamp;
    }
    
    public void setMonetaryValue(int value){
        this.monetaryValue = value;
    }

    public void setPaymentDueDate(Timestamp dueDate){
        this.paymentDueDate = dueDate;
    }

    public void setaccountDeposit(int deposit){
        this.accountDeposit = deposit;
    }

    public void setaccountWithdrawal(int withdrawal){
        this.accountWithdrawal = withdrawal;
    }

    public void settransferMoney(int transferMoney){
        this.transferMoney = transferMoney;
    }

    public void setupdateBalance(int update){
        this.updateBalance = update;
    }

    // getters

    public Timestamp getTransactionTimeStamp(){
        return this.transactionTimeStamp;
    }
    
    public int getMonetaryValue(){
        return this.monetaryValue;
    }

    public Timestamp getPaymentDueDate(){
        return this.paymentDueDate;
    }

    public int getAccountId(){
        return this.accountId;
    }

    public int getaccountDeposit(){
        return this.accountDeposit;
    }

    public int getaccountWithdrawal(){
        return this.accountWithdrawal;
    }

    public int gettransferMoney(){
        return this.transferMoney;
    }

    public int getupdateBalance(){
        return this.updateBalance;
    }
}
