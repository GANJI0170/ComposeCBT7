package com.cookandroid.cbt7;

public class found_list {
    private  String Found_number;
    private  String Found_content;
    private  double Found_similarity;
    private  String Found_date;
    private  String Found_hits;
    private  String Found_id;
    private  String Found_keyword;
    private  String Found_post_date;
    //    private  String Found_place;
//    private  String Found_thing;
    private  String Found_title;
    private  String Found_image;

    public  found_list(){};

    public String getFound_number(){
        return Found_number;
    }

    public void setFound_number(String Found_number){
        this.Found_number = Found_number;
    }

    public String getFound_content(){
        return Found_content;
    }

    public void setFound_content(String Found_content){
        this.Found_content = Found_content;
    }

    public double getFound_similarity() { return Found_similarity; }

    public void setFound_similarity(double Found_similarity) { this.Found_similarity = Found_similarity; }

    public String getFound_date(){
        return Found_date;
    }

    public void setFound_date(String Found_date){
        this.Found_date = Found_date;
    }


    public String getFound_hits(){
        return Found_hits;
    }

    public void setFound_hits(String Found_hits){
        this.Found_hits = Found_hits;
    }

    public String getFound_id(){
        return Found_id;
    }

    public void setFound_id(String Found_id){
        this.Found_id = Found_id;
    }

    public String getFound_keyword(){
        return Found_keyword;
    }

    public void setFound_keyword(String Found_keyword){
        this.Found_keyword = Found_keyword;
    }

    public String getFound_post_date(){
        return Found_post_date;
    }

    public void setFound_post_date(String Found_post_date){
        this.Found_post_date = Found_post_date;
    }

//    public String getFound_thing(){
//        return Found_thing;
//    }
//
//    public void setFound_thing(String Found_thing){
//        this.Found_thing = Found_thing;
//    }

//    public String getFound_place(){
//        return Found_place;
//    }
//
//    public void setFound_place(String Found_place){
//        this.Found_place = Found_place;
//    }

    public String getFound_title(){
        return Found_title;
    }

    public void setFound_title(String Found_title){
        this.Found_title = Found_title;
    }

    public String getFound_image(){
        return Found_image;
    }

    public void setFound_image(String Found_image){
        this.Found_image = Found_image;
    }

    public found_list(String Found_number, String Found_content, double Found_similarity){
        this.Found_number = Found_number;
        this.Found_content = Found_content;
        this.Found_similarity = Found_similarity;
        this.Found_date = Found_date;
        this.Found_id = Found_id;
        this.Found_hits = Found_hits;
        this.Found_image = Found_image;
        this.Found_keyword = Found_keyword;
//        this.Found_thing = Found_thing;
        this.Found_title = Found_title;
//        this.Found_place = Found_place;
        this.Found_post_date = Found_post_date;

    }
}

