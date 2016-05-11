package com.ifocus.papple.Utils;

import android.app.Activity;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ifocus.papple.helpers.PreferenceHelper;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;

import java.io.IOException;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by Jarvis on 11/19/2015.
 */
public class RetroService {
    public static Retrofit restAdapter;
    public static PreferenceHelper pHelper;
    public static OkHttpClient okClient;
    public static Gson gson;

    public static Retrofit returnRestadapter(Activity activity) {

        //Initiallizing PreferenceHelper class
        pHelper = new PreferenceHelper(activity);

        /**
         * Initializing the Gson and OkHtttp
         */
        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        okClient = new OkHttpClient();
        okClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());
                Response.Builder request = response.newBuilder();
                request.addHeader("Authorization", "");
                return response;
            }
        });

        String credentials = "" + ":" + "";
        final String basic =
                "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);

        //Retrofit section start from here...
        return restAdapter = new Retrofit.Builder()
                .baseUrl(UrlConstants.BASE_URL)
                .client(okClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }


}
