package com.ifocus.papple.model;


/**
 * Created by Jarvis on 11/19/2015.
 */

import com.google.gson.annotations.SerializedName;

/**
 * POJO for creating the user auth web service
 */
public final class UserAuth {

    @SerializedName("error")
    private Boolean error;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @SerializedName("message")
    private String message;
    @SerializedName("code")
    private String code;
    @SerializedName("user_type")
    private String user_type;
    @SerializedName("user_id")
    private String user_id;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }



    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

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




   /* public final Success success;

    public UserAuth(Success success) {
        this.success = success;
    }
    public static final class Success {
        public String firstname;
        public String lastname;
        public String username;
        public String email;
        public String bloodGroup;
        public String proofType;
        public String proofDetail;
        public String role;
        public Integer wardId;
        public String phoneNumber;
        public Boolean isMarried;
        public String dateOfBirth;
        public String createdAt;
        public String updatedAt;

        public Success(String firstname, String lastname, String username, String email, String bloodGroup, String proofType, String proofDetail, String role, Integer wardId, String phoneNumber, Boolean isMarried, String dateOfBirth, String createdAt, String updatedAt) {
            this.firstname = firstname;
            this.lastname = lastname;
            this.username = username;
            this.email = email;
            this.bloodGroup = bloodGroup;
            this.proofType = proofType;
            this.proofDetail = proofDetail;
            this.role = role;
            this.wardId = wardId;
            this.phoneNumber = phoneNumber;
            this.isMarried = isMarried;
            this.dateOfBirth = dateOfBirth;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }
    }*/
}