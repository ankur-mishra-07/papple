package com.ifocus.papple.model;

/**
 * Created by iFocus_2 on 21-04-2016.
 */

import com.google.gson.annotations.SerializedName;

/**
 * Created by iFocus_2 on 25-01-2016.
 */
public class EventResult {

    @SerializedName("error")
    private Boolean error;
    @SerializedName("message")
    private String message;
    @SerializedName("code")
    private String code;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPost_type() {
        return post_type;
    }

    public void setPost_type(String post_type) {
        this.post_type = post_type;
    }

    @SerializedName("post_type")
    private String post_type;
    @SerializedName("user_id")
    private String user_id;

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
