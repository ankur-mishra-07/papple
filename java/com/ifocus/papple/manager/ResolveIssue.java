package com.ifocus.papple.manager;

import android.app.Activity;

import com.ifocus.papple.R;
import com.ifocus.papple.Utils.AndroidUtils;
import com.ifocus.papple.helpers.Constant;
import com.ifocus.papple.interfaces.IRetroTaskCompleteListener;
import com.ifocus.papple.interfaces.IUserAuthApi;
import com.ifocus.papple.model.EventCreator;
import com.ifocus.papple.model.Resolve;
import com.ifocus.papple.model.SignupResult;

import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by iFocus_2 on 19-04-2016.
 */
public class ResolveIssue implements Callback<SignupResult> {

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
    public ResolveIssue(Activity activity, int serviceCode,
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

            case Constant.RESOLVE:
                //using Retrofit for making web service call
                Resolve event = new Resolve(
                        mapData.get(activity.getString(R.string.user_id)),
                        mapData.get(activity.getString(R.string.post_id))
                );
                // prepare call in Retrofit 2.0
                iUserAuthApi = retrofit.create(IUserAuthApi.class);
                Call<SignupResult> call = iUserAuthApi.resolveIssue(event);
                call.enqueue(this);
                break;

        }

    }

    /**
     * RettroFit Response
     */
    @Override
    public void onResponse(Response<SignupResult> response, Retrofit retrofit) {
        mAsynclistener.onResponseLanded(serviceCode, response.toString());
    }

    /**
     * RettroFit Failure
     */

    @Override
    public void onFailure(Throwable throwable) {
        mAsynclistener.onFailureLanded(serviceCode, throwable.getMessage());
    }
}
