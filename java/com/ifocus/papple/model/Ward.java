package com.ifocus.papple.model;

/**
 * Created by iFocus_2 on 18-01-2016.
 */

public class Ward{

    private String number;
    private String title;

    public Ward(String number, String title){
        this.number = number;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

}
