package com.cookandroid.cbt7.database;

public class addlostarticle {
    private String lost_number;
    private String lost_title;
    private String lost_keyword;
    private String lost_id;
    private String lost_date;
    private String lost_place;
    private String lost_thing;
    private String lost_content;
    private String lost_data;
    private String lost_post_date;
    private String lost_hits;
    private String lost_image;

    public addlostarticle(){}

    public String getLost_number() {
        return lost_number;
    }
    public void setLost_number() {
        this.lost_number = lost_number;
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

    public String getLost_date() {
        return lost_date;
    }
    public void setLost_date() {
        this.lost_date = lost_date;
    }

    public String getLost_place() {
        return lost_place;
    }
    public void setLost_place() {
        this.lost_place = lost_place;
    }

    public String getLost_thing(){return lost_thing;}
    public void setLost_thing(){this.lost_thing = lost_thing;}

    public String getLost_content(){return lost_content;}
    public void setLost_content(){this.lost_content = lost_content;}

    public String getLost_data(){return lost_data;}
    public void setLost_data(){this.lost_data = lost_data;}

    public String getLost_post_date(){return lost_post_date;}
    public void setLost_post_date(){this.lost_post_date = lost_post_date;}

    public String getLost_hits(){return lost_hits;}
    public void setLost_hits(){this.lost_hits = lost_hits;}

    public String getLost_image(){return lost_image;}
    public void setLost_image(){this.lost_image = lost_image;}

    public addlostarticle(String lost_number, String lost_title, String lost_keyword, String lost_id, String lost_date, String lost_place, String lost_thing, String lost_content, String lost_data, String lost_post_date, String lost_hits, String lost_image) {
        this.lost_number=lost_number;
        this.lost_title=lost_title;
        this.lost_keyword=lost_keyword;
        this.lost_id=lost_id;
        this.lost_date=lost_date;
        this.lost_place=lost_place;
        this.lost_thing=lost_thing;
        this.lost_content=lost_content;
        this.lost_data=lost_data;

        this.lost_post_date=lost_post_date;
        this.lost_hits=lost_hits;
        this.lost_image=lost_image;
    }
}
