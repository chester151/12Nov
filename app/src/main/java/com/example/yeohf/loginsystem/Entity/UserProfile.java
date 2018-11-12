package com.example.yeohf.loginsystem.Entity;

public class UserProfile {
    public String userContact;
    public String userEmail;
    public String userName;
    public String userID;

    public UserProfile(){

    }

    public UserProfile(String username, String usercontact){
        this.userName=username;
        this.userContact= usercontact;
    }

    public UserProfile(String userID, String username,String userContact,String userEmail) {
        this.userID= userID;
        this.userContact = userContact;
        this.userName = username;
        this.userEmail= userEmail;

       // this.userPassword=userPassword;
    }

    public String getUserContact() {
        return userContact;
    }

    public void setUserContact(String userContact) {
        this.userContact = userContact;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
