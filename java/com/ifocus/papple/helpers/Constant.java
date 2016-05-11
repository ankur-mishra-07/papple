package com.ifocus.papple.helpers;

/**
 * Created by Jarvis on 11/19/2015.
 */
public class Constant {

    public static final int AGENT_LOGIN = 1;
    public static final int AGENT_REGISTRATION = 2;
    public static final int FORGOT_PASSWORD = 3 ;
    public static final String PREF_NAME ="NIYO" ;
    public static final String PREF_NAME_AUTH = "AuthToken";

    public static final String DATA_BASE_NAME ="agentDB";
    public static final String CORPORATE_TABLE ="company";
    public static final String CUSTOMER_TABLE ="customerdata";
    public static final String AGENT_TABLE ="agentinfo";
    public static final String CARDKIT_TABLE ="kitTable";
    public static final int  DATA_BASE_VERSION = 1;
    public static final String KITID = "kit_id";
    public static final String CARDID = "card_id";
    public static final String UPLOADEDBY = "uploaded_by";
    public static final String APPROVEDBY = "approved_by";
    public static final String UPLOADEDAT = "uploaded_at";
    public static final String APPROOVEDAT = "approved_at";
    public static final String STATUS = "status";
    public static final String CARDASSIGNMENTS = "cardKitAssignments";

    public static final String CRNNO = "crn_number" ;


    public static final int SUCCESS_RESULT = 0;
    public static final int FAILURE_RESULT = 1;

    public static final int USE_ADDRESS_NAME = 1;
    public static final int USE_ADDRESS_LOCATION = 2;

    public static final String PACKAGE_NAME =
            "com.ifocus.papple";
    public static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";
    public static final String RESULT_DATA_KEY = PACKAGE_NAME + ".RESULT_DATA_KEY";
    public static final String RESULT_ADDRESS = PACKAGE_NAME + ".RESULT_ADDRESS";
    public static final String LOCATION_LATITUDE_DATA_EXTRA = PACKAGE_NAME + ".LOCATION_LATITUDE_DATA_EXTRA";
    public static final String LOCATION_LONGITUDE_DATA_EXTRA = PACKAGE_NAME + ".LOCATION_LONGITUDE_DATA_EXTRA";
    public static final String LOCATION_NAME_DATA_EXTRA = PACKAGE_NAME + ".LOCATION_NAME_DATA_EXTRA";
    public static final String FETCH_TYPE_EXTRA = PACKAGE_NAME + ".FETCH_TYPE_EXTRA";
    public static final int EVENT_CREATION = 4 ;
    public static final int GET_FEED = 5 ;
    public static final int RESOLVE = 6;
}
