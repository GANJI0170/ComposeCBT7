package com.cookandroid.cbt7.database;

public class articlelostList {
    private String lost_image;
    private String lost_title;
    private String lost_keyword;
    private String lost_id;
    private String lost_post_date;
    private String lost_hits;
    private String lost_number;

    public articlelostList(){}

    public String getLost_image() {
        return lost_image;
    }
    public void setLost_image(String articleImage) {
        this.lost_image = articleImage;
    }

    public String getLost_title() {
        return lost_title;
    }
    public void setLost_title() {
        this.lost_title = lost_title;
    }

    public String getLost_keyword() {
        return lost_keyword;
    }
    public void setLost_keyword() {
        this.lost_keyword = lost_keyword;
    }

    public String getLost_id() {
        return lost_id;
    }
    public void setLost_id() {
        this.lost_id = lost_id;
    }

    public String getLost_post_date() {
        return lost_post_date;
    }
    public void setLost_post_date() {
        this.lost_post_date = lost_post_date;
    }

    public String getLost_hits() {
        return lost_hits;
    }
    public void setLost_hits() {
        this.lost_hits = lost_hits;
    }
    public String getLost_number(){return lost_number;}
    public void setLost_number(){this.lost_number = lost_number;}
}
