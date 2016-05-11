package com.ifocus.papple.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by iFocus_2 on 25-01-2016.
 */
public class UserSignup {
    @SerializedName("user_name")
    String user_name;

    @SerializedName("user_type")
    String user_type;

    @SerializedName("email")
    String email;

    @SerializedName("password")
    String password;

    @SerializedName("first_name")
    String first_name;

    @SerializedName("last_name")
    String last_name;

    @SerializedName("ward_name")
    String ward_name;

    @SerializedName("birth_date")
    String birth_date;

    @SerializedName("marital_status")
    String marital_status;

    @SerializedName("blood_group")
    String blood_group;

    @SerializedName("proof_type")
    String proof_type;

    @SerializedName("phone_number")
    String phone_number;




    public UserSignup(String email, String username, String password, String first_name, String last_name, String phone_number,
                      String proof_number, String user_type, String ward_name, String prompt_martialstatus,
                      String prompt_bloodgroup, String prompt_prooftype) {

        this.email = email;
        this.user_name = username;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone_number = phone_number;
        this.proof_type = proof_number;
//        this.ward_number = ward_number;
        this.user_type = user_type;
        this.ward_name = ward_name;
        this.marital_status = prompt_martialstatus;
        this.blood_group = prompt_bloodgroup;
        this.proof_type = prompt_prooftype;

    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getWard_name() {
        return ward_name;
    }

    public void setWard_name(String ward_name) {
        this.ward_name = ward_name;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getMarital_status() {
        return marital_status;
    }

    public void setMarital_status(String marital_status) {
        this.marital_status = marital_status;
    }

    public String getBlood_group() {
        return blood_group;
    }

    public void setBlood_group(String blood_group) {
        this.blood_group = blood_group;
    }

    public String getProof_details() {
        return proof_type;
    }

    public void setProof_details(String proof_details) {
        this.proof_type = proof_details;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

//    public String getWard_number() {
//        return ward_number;
//    }
//
//    public void setWard_number(String ward_number) {
//        this.ward_number = ward_number;
//    }

}
