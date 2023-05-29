package com.cookandroid.cbt7.database;

public class ChatRoom {
    private String name;
    private String member;
    private String message;
    private String date;
    public ChatRoom() {
        // 기본 생성자가 필요합니다.
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}