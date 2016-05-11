package com.ifocus.papple.model;

import java.lang.reflect.Field;
import java.sql.Date;

/**
 * Created by iFocus_2 on 14-01-2016.
 */
public class UserDetails {
    private int ID = -1;
    public int MARTIAL_STATUS_MARRIED = 2;
    public int MARTIAL_STATUS_SINGLE = 1;

    private String emailAddress;
    private String username;
    private String hashedPassword;
    private String firstName;
    private String lastName;
    private String bloodGroup;
    private String proofType;
    private String proofDetail;
    private String userType;
    private String wardNumber;
    private String wardTitle;
    private Date birthDate;
    private int martialStatus;
    private String phoneNumber;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getProofType() {
        return proofType;
    }

    public void setProofType(String proofType) {
        this.proofType = proofType;
    }

    public String getProofDetail() {
        return proofDetail;
    }

    public void setProofDetail(String proofDetail) {
        this.proofDetail = proofDetail;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getWardNumber() {
        return wardNumber;
    }

    public void setWardNumber(String wardNumber) {
        this.wardNumber = wardNumber;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public int getMartialStatus() {
        return martialStatus;
    }

    public void setMartialStatus(int martialStatus) {
        this.martialStatus = martialStatus;
    }


    public String getWardTitle() {
        return wardTitle;
    }

    public void setWardTitle(String wardTitle) {
        this.wardTitle = wardTitle;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public String toString(){
        Field[] fields = this.getClass().getDeclaredFields();
        String propString = "";
        for(int i=0; i<fields.length; i++){
            Object value = "null";
            try{
                value = fields[i].get(this);
            }catch (IllegalAccessException e){
                e.printStackTrace();
            }
            propString += fields[i].getName() + " = " + String.valueOf(value) + "; ";
        }

        return propString;
    }

}

