package com.ifocus.papple.interfaces;

/**
 * Created by Jarvis on 11/19/2015.
 */

/**
 * For Providing the Response from Retrofit to respective Classes
 */
public interface IRetroTaskCompleteListener {
    void onResponseLanded(int serviceCode , String Response);
    void onFailureLanded(int serviceCode, String Failure);
}
