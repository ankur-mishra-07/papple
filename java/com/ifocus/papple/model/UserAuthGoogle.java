package com.ifocus.papple.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Jarvis on 12/15/2015.
 */


/**
 * POJO for parsing the User auth response
 */
public class UserAuthGoogle {
    @SerializedName("email")
    String email;

    @SerializedName("password")
    String password;

    public UserAuthGoogle(String email, String password) {
        this.email = email;
        this.password = password;
    }
}