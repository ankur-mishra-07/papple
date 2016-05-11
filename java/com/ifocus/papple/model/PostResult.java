package com.ifocus.papple.model;

/**
 * Created by iFocus_2 on 18-01-2016.
 */
public class PostResult {
    private String USERNAME;
    private String DETAILS;
    private String UPCOUNT;
    private String SPAM;
    private String DOWNCOUNT;
    private String TIME;
    private String TOPIC;
    private String PICPATH;

    public String getPICPATH() {
        return PICPATH;
    }

    public void setPICPATH(String PICPATH) {
        this.PICPATH = PICPATH;
    }

    public String getCATEGORY() {
        return CATEGORY;
    }

    public void setCATEGORY(String CATEGORY) {
        this.CATEGORY = CATEGORY;
    }

    private String CATEGORY;

    public String getTOPIC() {
        return TOPIC;
    }

    public void setTOPIC(String TOPIC) {
        this.TOPIC = TOPIC;
    }


    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getDETAILS() {
        return DETAILS;
    }

    public void setDETAILS(String DETAILS) {
        this.DETAILS = DETAILS;
    }

    public String getUPCOUNT() {
        return UPCOUNT;
    }

    public void setUPCOUNT(String UPCOUNT) {
        this.UPCOUNT = UPCOUNT;
    }

    public String getSPAM() {
        return SPAM;
    }

    public void setSPAM(String SPAM) {
        this.SPAM = SPAM;
    }

    public String getDOWNCOUNT() {
        return DOWNCOUNT;
    }

    public void setDOWNCOUNT(String DOWNCOUNT) {
        this.DOWNCOUNT = DOWNCOUNT;
    }

    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }

}
