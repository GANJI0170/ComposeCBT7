package com.cookandroid.cbt7.database;

public class addfoundarticle {
    private String found_number;
    private String found_title;
    private String found_keyword;
    private String found_id;
    private String found_date;
    private String found_place;
    private String found_thing;
    private String found_content;
    private String found_data;
    private String found_post_date;
    private String found_hits;
    private String found_image;

    public addfoundarticle(){}

    public String getFound_number() {
        return found_number;
    }
    public void setFound_number() {
        this.found_number = found_number;
    }

    public String getFound_title() {
        return found_title;
    }
    public void setFound_title() {
        this.found_title = found_title;
    }

    public String getFound_keyword() {
        return found_keyword;
    }
    public void setFound_keyword() {
        this.found_keyword = found_keyword;
    }

    public String getFound_id() {
        return found_id;
    }
    public void setFound_id() {
        this.found_id = found_id;
    }

    public String getFound_date() {
        return found_date;
    }
    public void setFound_date() {
        this.found_date = found_date;
    }

    public String getFound_place() {
        return found_place;
    }
    public void setFound_place() {
        this.found_place = found_place;
    }

    public String getFound_thing(){return found_thing;}
    public void setFound_thing(){this.found_thing = found_thing;}

    public String getFound_content(){return found_content;}
    public void setFound_content(){this.found_content = found_content;}

    public String getFound_data(){return found_data;}
    public void setFound_data(){this.found_data = found_data;}

    public String getFound_post_date(){return found_post_date;}
    public void setFound_post_date(){this.found_post_date = found_post_date;}

    public String getFound_hits(){return found_hits;}
    public void setFound_hits(){this.found_hits = found_hits;}

    public String getFound_image(){return found_image;}
    public void setFound_image(){this.found_image = found_image;}

    public addfoundarticle(String found_number, String found_title, String found_keyword, String found_id, String found_date, String found_place, String found_thing, String found_content, String found_data, String found_post_date, String found_hits, String found_image) {
        this.found_number=found_number;
        this.found_title=found_title;
        this.found_keyword=found_keyword;
        this.found_id=found_id;
        this.found_date=found_date;
        this.found_place=found_place;
        this.found_thing=found_thing;
        this.found_content=found_content;
        this.found_data=found_data;

        this.found_post_date=found_post_date;
        this.found_hits=found_hits;
        this.found_image=found_image;
    }
}
