package com.ifocus.papple.interfaces;

import com.ifocus.papple.model.Announcement;
import com.ifocus.papple.model.EventCreator;
import com.ifocus.papple.model.FeedData;
import com.ifocus.papple.model.Resolve;
import com.ifocus.papple.model.SignupResult;
import com.ifocus.papple.model.UserAuth;
import com.ifocus.papple.model.UserAuthGoogle;
import com.ifocus.papple.model.UserSignup;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;

/**
 * Created by Jarvis on 11/19/2015.
 */


/**
 * Generating the interface for the making web service calls with annotations
 */
public interface IUserAuthApi {

    /**
     * For User Login
     */
    @POST("login.php")
    Call<UserAuth> createUser(@Body UserAuthGoogle user);

/*  *//**/

    /**
     * For getting User Registered
     *//**//**/
    @POST("register.php")
    Call<SignupResult> registerUser(@Body UserSignup signupdata);

    /**
     * For getting Card Kit List
     *//**//**/
    @GET("GetFeedList.php")
    Call<FeedData> getFeeds();


    /**
     * For getting User Registered
     *//**//**/
    @POST("EventCreation.php")
    Call<SignupResult> crateEvent(@Body EventCreator eventType);


    /**
     * For Resolving the posted issue
     *//**//**/
    @POST("updateIssueStatus.php")
    Call<SignupResult> resolveIssue(@Body Resolve event);


 /*
    *//**
     * For getting Corporate List
     *//*

    @GET("/niyo/agent/v1/corporates")
    Call<List<CorporateInfoPage>> getCorporateInfo();

    *//**
     * For getting Card Kit List
     *//*

    @GET("/niyo/agent/v1/cardKits")
    Call<List<CardKit>> getCardList();


    @GET("/niyo/agent/v1/customers")
    Call<List<CustomerList>> getCustomerList();


    @GET("/niyo/agent/v1/customers")
    Call<List<CustomerList>> getCustomerListbyMobileNumber(@Query("mobile") String mobile);*/
}
