public class accounts {

    //private users userTest;
    //private account_types userTypeTest;
    private int accountId;
    //private int userId;
    private int typeId;
    private String accountNumber;
    private String routingNumber;
    private int currentBalance;

    // CHECK WHETHER ACCOUNTID AND USERID EXIST WITHIN THE DATABASE
    // IF TRUE, SET ALL OF 'SET METHODS' TO MATCH ACCOUNTID AND USERID DATA
    // IF FALSE, ERROR CODE
    // CREATE FUNCTION TO STORE CURRENT DATA INTO A TABLE

    public accounts(int accountId, String username, String password) {
        // this.userId = userId;
        // this.typeId = typeId;
        //userTest = new users(username, password);
        //userTypeTest = new account_types(accountId);
    }
    
    public void setAccountNumber(String accNum){
        this.accountNumber = accNum;
    }

    public void setRoutingNumber(String routingNum){
        this.routingNumber = routingNum;
    }

    public void setCurrentBalance(int currBalance){
        this.currentBalance = currBalance;
    }

    // getters

    public int getAccountId(){
        return this.accountId;
    }
    
    //public int getUserId(){
    //    return this.userId;
    //}

    //public String getUsername(){
    //    return userTest.getUsername();
    //}

    public int getTypeId(){
        return this.typeId;
    }

    public String getAccountNumber(){
        return this.accountNumber;
    }

    public String getRoutingNumber(){
        return this.routingNumber;
    }

    public int getCurrentBalance(){
        return this.currentBalance;
    }
    
}
