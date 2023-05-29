package com.cookandroid.cbt7.database;

public class chatList {
    private String name;
    private String member;
    private String message;
    private String date;

    public chatList(){}

    public String getName() {
        return member;
    }

    public void setName(String  member) {
        this.member = member;
    }

    public String getChatMessage() {
        return message;
    }

    public void setChatMessage(String message) {
        this.message = message;
    }

    public String getChatDate() {
        return date;
    }

    public void setChatDate(String date) {
        this.date = date;
    }

}