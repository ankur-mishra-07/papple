package com.ifocus.papple.manager;

import android.app.Activity;

import com.ifocus.papple.R;
import com.ifocus.papple.Utils.AndroidUtils;
import com.ifocus.papple.helpers.Constant;
import com.ifocus.papple.interfaces.IRetroTaskCompleteListener;
import com.ifocus.papple.interfaces.IUserAuthApi;
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
public class NetWorkHandler implements Callback<UserAuth> {

    private IRetroTaskCompleteListener mAsynclistener;
    private int serviceCode;
    private boolean isGetMethod = false;
    private Activity activity;
    private IUserAuthApi iUserAuthApi;
    private Retrofit retrofit;
    private Map<String, String> mapData;

    /**
     * Constructor for Network handler to get the related object
     */
    public NetWorkHandler(Activity activity, int serviceCode,
                          IRetroTaskCompleteListener retroTaskCompleteListener, Retrofit retroFit,
                          Map<String, String> mapData, boolean isGetMethod) {

        this.serviceCode = serviceCode;
        this.activity = activity;
        this.mAsynclistener = retroTaskCompleteListener;
        this.retrofit = retroFit;
        this.mapData = mapData;
        this.isGetMethod = isGetMethod;

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

    }

    /**
     * Call Post Web Service call
     */
    public void callPostRestService() {
        switch (serviceCode) {

            case Constant.AGENT_LOGIN:
                //using Retrofit for making web service call
                UserAuthGoogle userAuth = new UserAuthGoogle(mapData.get(activity.getString(R.string.email_)), mapData.get(activity.getString(R.string.idToken)));
                // prepare call in Retrofit 2.0
                iUserAuthApi = retrofit.create(IUserAuthApi.class);
                Call<UserAuth> call = iUserAuthApi.createUser(userAuth);
                call.enqueue(this);
                break;

        }

    }

    /**
     * RettroFit Response
     */
    @Override
    public void onResponse(Response<UserAuth> response, Retrofit retrofit) {
        if (response.body() != null) {
            UserAuth auth = response.body();
            if (!auth.getError()) {
                if (auth.getUser_id() != null) {
                    mAsynclistener.onResponseLanded(serviceCode, auth.getUser_type() + "," + auth.getUser_id());
                } else {
                    mAsynclistener.onFailureLanded(serviceCode, auth.getMessage());
                }
            } else {
                mAsynclistener.onFailureLanded(serviceCode, auth.getMessage());
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
