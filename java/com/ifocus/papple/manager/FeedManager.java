package com.ifocus.papple.manager;

import android.app.Activity;

import com.ifocus.papple.R;
import com.ifocus.papple.Utils.AndroidUtils;
import com.ifocus.papple.helpers.Constant;
import com.ifocus.papple.interfaces.IRetroTaskCompleteListener;
import com.ifocus.papple.interfaces.IUserAuthApi;
import com.ifocus.papple.interfaces.iFeedData;
import com.ifocus.papple.model.FeedData;
import com.ifocus.papple.model.UserAuth;
import com.ifocus.papple.model.UserAuthGoogle;

import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Jarvis on 11/19/2015.
 */

/**
 * Class for handling the retrofit Web service call
 */
public class FeedManager implements Callback<FeedData> {

    private IRetroTaskCompleteListener mAsynclistener;
    private iFeedData ifeed;
    private int serviceCode;
    private boolean isGetMethod = false;
    private Activity activity;
    private IUserAuthApi iUserAuthApi;
    private Retrofit retrofit;
    private Map<String, String> mapData;

    /**
     * Constructor for Network handler to get the related object
     */
    public FeedManager(Activity activity, int serviceCode,
                       IRetroTaskCompleteListener retroTaskCompleteListener, Retrofit retroFit,
                       Map<String, String> mapData, boolean isGetMethod, iFeedData ifeed) {

        this.serviceCode = serviceCode;
        this.activity = activity;
        this.mAsynclistener = retroTaskCompleteListener;
        this.retrofit = retroFit;
        this.mapData = mapData;
        this.isGetMethod = isGetMethod;
        this.ifeed = ifeed;

        if (AndroidUtils.isNetworkAvailable(activity)) {
            if (!isGetMethod) {
                callPostRestService();
            } else {
                callGetRestService();
            }
        } else {
            AndroidUtils.showToast(activity.getString(R.string.networkError), activity);
        }
    }

    private void callGetRestService() {
        switch (serviceCode) {

            case Constant.GET_FEED:
                // prepare call in Retrofit 2.0
                iUserAuthApi = retrofit.create(IUserAuthApi.class);
                Call<FeedData> call = iUserAuthApi.getFeeds();
                call.enqueue(this);
                break;

        }
    }

    /**
     * Call Post Web Service call
     */
    public void callPostRestService() {

    }

    /**
     * RettroFit Response
     */
    @Override
    public void onResponse(Response<FeedData> response, Retrofit retrofit) {
        if (response.body() != null) {
            FeedData feedData = response.body();
            if (!feedData.getError()) {
                if (feedData.getCode() != null) {
                    ifeed.onFeedLanded(feedData);
                } else {
                    mAsynclistener.onFailureLanded(serviceCode, feedData.getMessage());
                }
            } else {
                mAsynclistener.onFailureLanded(serviceCode,feedData.getMessage());
            }
        } else {
            mAsynclistener.onFailureLanded(serviceCode, response.message());
        }
    }

    /**
     * RettroFit Failure
     */
    @Override
    public void onFailure(Throwable throwable) {
        mAsynclistener.onFailureLanded(serviceCode, throwable.getMessage());
    }
}
