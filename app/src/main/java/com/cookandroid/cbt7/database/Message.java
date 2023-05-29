package com.cookandroid.cbt7.database;

public class Message {
    private String content;
    private long timestamp;

    public Message() {
        // Default constructor required for Firebase
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}