package com.example.yeohf.loginsystem.Entity;

import java.util.Date;

public class Chat {

    private String chatId;
    private String message;
    private String userEmail;
    private String rentId;
    private long timeStamp;

    public Chat() {
    }

    public Chat(String chatId, String rentId, String message, String userEmail) {
        this.chatId = chatId;
        this.rentId = rentId;
        this.message = message;
        this.userEmail = userEmail;
        this.timeStamp = new Date().getTime();
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public void setTxtMessage(String message) {
        this.message = message;
    }

    public void setUserId(String email) {
        this.userEmail = email;
    }

    public String getChatId() {
        return chatId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getRentId() {
        return rentId;
    }

    public void setRentId(String rentId) {
        this.rentId = rentId;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getTxtMessage() {
        return this.message;
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public long getTimeStamp() {
        return this.timeStamp;
    }
}

