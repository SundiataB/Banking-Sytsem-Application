public class account_types{
    
    private int typeId;
    private String accountName;
    private int balanceUpperLimit;
    private int balanceLowerLimit;
    private int minimumSpend;
    private int annualFee;

    // CHECK WHETHER ACCOUNTID EXIST WITHIN THE DATABASE
    // IF TRUE, SET ALL OF 'SET METHODS' TO MATCH ACCOUNTID DATA
    // IF FALSE, ERROR CODE


    //public account_types(int accountId) {
    //    this.accountId = accountId;
    //}

    public void setAccountName(String accName){
        this.accountName = accName;
    }
    
    public void setBalanceUpperLimit(int balUpLimit){
        this.balanceUpperLimit = balUpLimit;
    }

    public void setBalanceLowerLimit(int balLowLimit){
        this.balanceLowerLimit = balLowLimit;
    }

    public void setMinimumSpend(int minSpend){
        this.minimumSpend = minSpend;
    }

    public void setAnnualFee(int annualFee){
        this.annualFee = annualFee;
    }

    // getters

    public int getTypeId(){
        return this.typeId;
    }
    
    public String getAccountName(){
        return this.accountName;
    }
    
    public int getBalanceUpperLimit(){
        return this.balanceUpperLimit;
    }

    public int getBalanceLowerLimit(){
        return this.balanceLowerLimit;
    }

    public int getMinimumSpend(){
        return this.minimumSpend;
    }

    public int getAnnualFee(){
        return this.annualFee;
    }

}
