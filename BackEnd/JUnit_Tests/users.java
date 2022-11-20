
import java.security.Timestamp;


import java.util.Date;

public class users{

    private int userId = 2818;
    private String username;
    private String accountPassword;
    private String firstName;
    private String lastName;
    private String email;
    private String contactNum;
    private String address;
    private String postCode;
    private Date dateOfBirth;
    private int socialSecurityNumber;
    private boolean activityStatus;
    
    // CHECK WHETHER USERNAME AND PASSWORD EXIST WITHIN THE DATABASE
    // IF TRUE, SET ALL OF 'SET METHODS' TO MATCH USERNAME AND PASSWORD DATA
    // IF FALSE, ERROR CODE
    
    public users(String username, String password) {
        this.username = username;
        this.accountPassword = password;
    }

    public void setUserId(int userId){
        this.userId = userId;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String accountpassword){
        this.accountPassword = accountpassword;
    }
    
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setContact(String contactNum){
        this.contactNum = contactNum;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public void setPostCode(String postCode){
        this.postCode = postCode;
    }

    public void setDateOfBirth(Date dateOfBirth){
        this.dateOfBirth = dateOfBirth;
    }

    public void setSocialSecurityNumber(int socialSecurityNumber){
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public void setActivityStatus(Boolean activityStatus){
        this.activityStatus = activityStatus;
    }

    // Getters 

    public int getUserId(){
        return this.userId;
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.accountPassword;
    }
    
    public String getFirstName(){
        return this.firstName;
    }

    public String getLastName(){
        return this.lastName;
    }

    public String getEmail(){
        return this.email;
    }

    public String getContact(){
        return this.contactNum;
    }

    public String getAddress(){
        return this.address;
    }

    public String getPostCode(){
        return this.postCode;
    }

    public int getSocialSecurityNumber(){
        return this.socialSecurityNumber;
    }

    public Date getDateOfBirth(){
        return this.dateOfBirth;
    }

    public boolean getActivityStatus(){
        return this.activityStatus;
    }
}
