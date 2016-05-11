package com.ifocus.papple.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by iFocus_2 on 25-01-2016.
 */
public class SignupResult {

    @SerializedName("error")
    private Boolean error;
    @SerializedName("message")
    private String message;
    @SerializedName("code")
    private String code;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getUid() {
        return message;
    }

    public void setUid(String uid) {
        this.message = uid;
    }

    public String getUser() {
        return code;
    }

    public void setUser(String code) {
        this.code = code;
    }

}
