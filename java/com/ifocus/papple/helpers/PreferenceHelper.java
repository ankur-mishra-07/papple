package com.ifocus.papple.helpers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Preference Helper to Save Data locally
 */
public class PreferenceHelper {
    /*
        private static final String FIRST_TIME ="first_time" ;*/
    private SharedPreferences app_prefs;
    private final String USER_ID = "user_id";
    private final String DISPLAY_NAME = "displayName";
    private final String LOGIN_ROLE = "AUTH_TOKEN";/*
    private SharedPreferences auth_app_prefs;
	private final String CRN_NUMBER = "crn";
	private final String ACCOUNT_COUNT = "account_count";
	private final String SELECTED_ITEM = "seleted_item";
	private final String AUTH_TOKEN = "AUTH_TOKEN";
	private final String EMAIL = "email";
	private final String PASSWORD = "password";
	private final String DEVICE_TOKEN = "device_token";
	private final String SESSION_TOKEN = "session_token";
	private final String REQUEST_ID = "request_id";
	private final String REQUEST_TIME = "request_time";
	private final String REQUEST_LATITUDE = "request_latitude";
	private final String REQUEST_LONGITUDE = "request_longitude";
	private final String LOGIN_BY = "login_by";
	private final String SOCIAL_ID = "social_id";
	private final String PAYMENT_MODE = "payment_mode";
	private final String DEFAULT_CARD = "default_card";
	private final String DEFAULT_CARD_NO = "default_card_no";
	private final String DEFAULT_CARD_TYPE = "default_card_type";
	private final String BASE_PRICE = "base_cost";
	private final String DISTANCE_PRICE = "distance_cost";
	private final String USER_PIC = "userPic";
	private final String TIME_PRICE = "time_cost";
	private final String IS_TRIP_STARTED = "is_trip_started";
	private final String HOME_ADDRESS = "home_address";
	private final String WORK_ADDRESS = "work_address";
	private final String PICKUP_ADDRESS = "pickup_address";
	private final String DEST_LAT = "dest_lat";
	private final String DEST_LNG = "dest_lng";*/
    private final String LOGIN_ENABLED = "LOGIN_ENABLED";/*
    private final String REQUEST_TYPE = "request_type";
	private final String OPT_VERIFIED = "otp_verified";
	private final String USER_NAME = "user_name";*/

    private Context context;


    public PreferenceHelper(Context context) {
        app_prefs = context.getSharedPreferences(Constant.PREF_NAME,
                Context.MODE_PRIVATE);
        this.context = context;
    }

    public void putUserId(String userId) {
        Editor edit = app_prefs.edit();
        edit.putString(USER_ID, userId);
        edit.commit();
    }

    public String getUserId() {
        return app_prefs.getString(USER_ID, "");

    }

    public void setDisplayName(String displayName) {
        Editor edit = app_prefs.edit();
        edit.putString(DISPLAY_NAME, displayName);
        edit.commit();
    }

    public String getDisplayName() {
        return app_prefs.getString(DISPLAY_NAME, null);

    }

    public void setLoginRole(String firstTime) {
        Editor edit = app_prefs.edit();
        edit.putString(LOGIN_ROLE, firstTime);
        edit.commit();
    }

    public String getLoginRole() {
        return app_prefs.getString(LOGIN_ROLE, "");
    }

/*
    public void PreferenceHelperAuthToken(){
		auth_app_prefs = context.getSharedPreferences(Constant.PREF_NAME_AUTH,
				Context.MODE_PRIVATE);
	}

	public void putAuthToken(String authToken) {
		Editor edit = auth_app_prefs.edit();
		edit.putString(AUTH_TOKEN, authToken);
		edit.commit();
	}

	public String getAuth() {
		return auth_app_prefs.getString(AUTH_TOKEN, null);
	}


	public void putEmail(String email) {
		Editor edit = app_prefs.edit();
		edit.putString(EMAIL, email);
		edit.commit();
	}

	public String getEmail() {
		return app_prefs.getString(EMAIL, null);
	}

	public void putOtpVerified(int otp) {
		Editor edit = app_prefs.edit();
		edit.putInt(OPT_VERIFIED, otp);
		edit.commit();
	}

	public int getOtpVerified() {
		return app_prefs.getInt(OPT_VERIFIED, 0);
	}

	public void putPassword(String password) {
		Editor edit = app_prefs.edit();
		edit.putString(PASSWORD, password);
		edit.commit();
	}

	public String getPassword() {
		return app_prefs.getString(PASSWORD, null);
	}

	public void putBasePrice(float price) {
		Editor edit = app_prefs.edit();
		edit.putFloat(BASE_PRICE, price);
		edit.commit();
	}

	public float getBasePrice() {
		return app_prefs.getFloat(BASE_PRICE, 0f);
	}

	public void putDistancePrice(float price) {
		Editor edit = app_prefs.edit();
		edit.putFloat(DISTANCE_PRICE, price);
		edit.commit();
	}

	public void putUserPic(String profilePic) {
		Editor edit = app_prefs.edit();
		edit.putString(USER_PIC, profilePic);
		edit.commit();
	}

	public String getUserPic() {
		return app_prefs.getString(USER_PIC, null);
	}


	public float getDistancePrice() {
		return app_prefs.getFloat(DISTANCE_PRICE, 0f);
	}

	public void putTimePrice(float price) {
		Editor edit = app_prefs.edit();
		edit.putFloat(TIME_PRICE, price);
		edit.commit();
	}

	public float getTimePrice() {
		return app_prefs.getFloat(TIME_PRICE, 0f);
	}

	public void putSocialId(String id) {
		Editor edit = app_prefs.edit();
		edit.putString(SOCIAL_ID, id);
		edit.commit();
	}

	public String getSocialId() {
		return app_prefs.getString(SOCIAL_ID, null);
	}


	public void putDeviceToken(String deviceToken) {
		Editor edit = app_prefs.edit();
		edit.putString(DEVICE_TOKEN, deviceToken);
		edit.commit();
	}

	public String getDeviceToken() {
		return app_prefs.getString(DEVICE_TOKEN, null);

	}

	public void putSessionToken(String sessionToken) {
		Editor edit = app_prefs.edit();
		edit.putString(SESSION_TOKEN, sessionToken);
		edit.commit();
	}

	public String getSessionToken() {
		return app_prefs.getString(SESSION_TOKEN, null);

	}
*/


    public void putLogintrue(boolean b) {
        Editor edit = app_prefs.edit();
        edit.putBoolean(LOGIN_ENABLED, b);
        edit.commit();
    }


    public boolean getLogintrue() {
        return app_prefs.getBoolean(LOGIN_ENABLED, false);
    }

	/*
    public void putCRN(String crn) {
		Editor edit = app_prefs.edit();
		edit.putString(CRN_NUMBER, crn);
		edit.commit();
	}

	public String getCRNNumber() {
		return app_prefs.getString(CRN_NUMBER, null);

	}

	public void setAccountCount(int count) {
		Editor edit = app_prefs.edit();
		edit.putInt(ACCOUNT_COUNT, count);
		edit.commit();
	}

	public Integer getAccountCount() {
		return app_prefs.getInt(ACCOUNT_COUNT, 0);

	}

	public String getUserName() {
		return app_prefs.getString(USER_NAME, null);
	}


	public void putUserName(String userName) {
		Editor edit = app_prefs.edit();
		edit.putString(USER_NAME, userName);
		edit.commit();
	}

	public void setSpinnerItem(String position) {
		Editor edit = app_prefs.edit();
		edit.putString(SELECTED_ITEM, position);
		edit.commit();
	}

	public String getSpinnerItem() {
		return app_prefs.getString(SELECTED_ITEM, null);
	}
*/
}
