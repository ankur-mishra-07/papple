
package com.ifocus.papple.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ifocus.papple.helpers.StringHelpers;
import com.ifocus.papple.model.PostResult;
import com.ifocus.papple.model.UserDetails;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ifocus on 12-01-2016.
 */
public class DBUserDetailsHelper extends SQLiteOpenHelper {
    protected static final String DB_NAME = "UserDetails.db";
    protected static final int DB_VERSION = 4;

    public final String COLUMN_COUNT = "columnCount";
    public final String ROW_COUNT = "rowCount";
    //Main: User details table
    protected static final String TABLE_USERS = "users";
    public static final String USER_COLUMN_ID = "id";
    public static final String USER_COLUMN_USERNAME = "username";
    public static final String USER_COLUMN_PASSWORD = "password";
    public static final String USER_COLUMN_EMAIL = "email";
    public static final String USER_COLUMN_FIRSTNAME = "firstname";
    public static final String USER_COLUMN_LASTNAME = "lastname";
    public static final String USER_COLUMN_USERTYPE = "usertype";
    public static final String USER_COLUMN_WARD_NUMBER = "ward_number";
    public static final String USER_COLUMN_BIRTHDATE = "birthdate";
    public static final String USER_COLUMN_MARTIAL_STATUS = "martial_status";
    public static final String USER_COLUMN_BLOOD_GROUP = "blood_group";
    public static final String USER_COLUMN_PROOF_TYPE = "proof_type";
    public static final String USER_COLUMN_PROOF_DETAIL = "proof_detail";
    public static final String USER_COLUMN_PHONE_NUMBER = "phone_number";
    public final String USER_COLUMN_WARD = "ward";

    //Relational: User Types Table
    public static final String USERTYPES_TYPE_GENERAL = "public";

    protected static final String TABLE_POSTS = "posts";

    public static final String COLUMN_DETAILS = "details";
    public static final String COLUMN_UPCOUNT = "up_count";
    public static final String COLUMN_SPAM = "spam";
    public static final String COLUMN_DOWNCOUNT = "down_count";
    public static final String COLUMN_TIME = "time";
    public static final String POST_COLUMN_ID = "post_id";

    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_TOPIC = "topic";
    private static final String COLUMN_PICURL = "pic_url";
    private Context context;


    public DBUserDetailsHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String userDetailsSql = "CREATE TABLE " + TABLE_USERS
                + "("
                + USER_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + USER_COLUMN_USERNAME + " TEXT NOT NULL, "
                + USER_COLUMN_FIRSTNAME + " STRING,"
                + USER_COLUMN_LASTNAME + " STRING,"
                + USER_COLUMN_EMAIL + " TEXT NOT NULL,"
                + USER_COLUMN_PASSWORD + " TEXT NOT NULL,"
                + USER_COLUMN_BIRTHDATE + " INTEGER(8),"
                + USER_COLUMN_BLOOD_GROUP + " INTEGER,"
                + USER_COLUMN_MARTIAL_STATUS + " TINYINT(1),"
                + USER_COLUMN_PROOF_TYPE + " INTEGER NOT NULL,"
                + USER_COLUMN_PROOF_DETAIL + " TEXT NOT NULL,"
                + USER_COLUMN_USERTYPE + " INTEGER NOT NULL DEFAULT " + USERTYPES_TYPE_GENERAL + ","
                + USER_COLUMN_WARD + " TEXT NOT NULL,"
                + USER_COLUMN_PHONE_NUMBER + " TEXT NOT NULL"
                + ")";


        String postTable = "CREATE TABLE " + TABLE_POSTS
                + "("
                + POST_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + USER_COLUMN_USERNAME + " TEXT NOT NULL, "
                + COLUMN_DETAILS + " STRING,"
                + COLUMN_UPCOUNT + " STRING,"
                + COLUMN_DOWNCOUNT + " STRING,"
                + COLUMN_SPAM + " STRING,"
                + COLUMN_TOPIC + "  TEXT NOT NULL,"
                + COLUMN_CATEGORY + " STRING,"
                + COLUMN_PICURL + " STRING,"
                + COLUMN_TIME + " STRING"
                + ")";

        db.execSQL(postTable);
        db.execSQL(userDetailsSql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POSTS);
        this.onCreate(db);
    }

    public UserDetails getUserDetail(String whereField, String whereValue) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_USERS + " WHERE " + whereField + "='" + whereValue + "'";
        Log.d("UserDetails", sql);
        Cursor cursor;
        UserDetails userDetails = new UserDetails();

        try {
            cursor = db.rawQuery(sql, null);
            cursor.moveToFirst();
            Log.d("USERDETAILS", userDetails.toString());
            userDetails.setID(cursor.getInt(cursor.getColumnIndex(USER_COLUMN_ID)));
            userDetails.setEmailAddress(cursor.getString(cursor.getColumnIndex(USER_COLUMN_EMAIL)));
            userDetails.setUsername(cursor.getString(cursor.getColumnIndex(USER_COLUMN_USERNAME)));
            userDetails.setHashedPassword(cursor.getString(cursor.getColumnIndex(USER_COLUMN_PASSWORD)));
            userDetails.setFirstName(cursor.getString(cursor.getColumnIndex(USER_COLUMN_FIRSTNAME)));
            userDetails.setLastName(cursor.getString(cursor.getColumnIndex(USER_COLUMN_LASTNAME)));
            userDetails.setBirthDate(new Date(cursor.getLong(cursor.getColumnIndex(USER_COLUMN_BIRTHDATE))));
            userDetails.setBloodGroup(cursor.getString(cursor.getColumnIndex(USER_COLUMN_BLOOD_GROUP)));

            userDetails.setMartialStatus(cursor.getInt(cursor.getColumnIndex(USER_COLUMN_MARTIAL_STATUS)));

//            userDetails.setWardNumber(ward.getNumber());
            userDetails.setWardTitle(cursor.getString(cursor.getColumnIndex(USER_COLUMN_WARD)));

            userDetails.setProofType(cursor.getString(cursor.getColumnIndex(USER_COLUMN_PROOF_TYPE)));

            userDetails.setProofDetail(cursor.getString(cursor.getColumnIndex(USER_COLUMN_PROOF_DETAIL)));
            userDetails.setPhoneNumber(cursor.getString(cursor.getColumnIndex(USER_COLUMN_PHONE_NUMBER)));

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("UserDetails", userDetails.toString());
        return userDetails;
    }

    public Map<String, Object> getSpecificDetail(String field, String whereField, String whereValue) {

        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT " + field + " FROM " + TABLE_USERS;
        sql += (whereField == null || whereField.equals("")) ? "" : " WHERE " + whereField + "= '" + whereValue + "'";

        Cursor cursor;
        Map<String, Object> blobs = new HashMap<>();
        int columnCount = 0;
        int rowCount = 0;
        try {
            cursor = db.rawQuery(sql, null);
            cursor.moveToFirst();
            columnCount = cursor.getColumnCount();
            rowCount = cursor.getCount();

            for (int i = 0; i < columnCount; i++) {
                blobs.put(cursor.getColumnName(i), cursor.getBlob(i));
            }
            blobs.put(COLUMN_COUNT, columnCount);
            blobs.put(ROW_COUNT, rowCount);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return blobs;
    }


    public long insertUser(String username,
                           String password,
                           String email,
                           String firstname,
                           String lastname,
                           Calendar birthDate,
                           String bloodGroup,
                           String martialStatus,
                           String proofType,
                           String proofDetail,
                           String userType,
                           String wardNumber,
                           String phoneNumber) {
        username = username.trim();
        email = email.trim();

        if (username.equals("") || password.equals("") || email.equals("")) {
            throw new Error("The following fields cannot be empty: Username, Password and Email");
        }

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(USER_COLUMN_USERNAME, username);
        contentValues.put(USER_COLUMN_PASSWORD, StringHelpers.hashString(StringHelpers.HASH_METHOD_MD5, password));
        contentValues.put(USER_COLUMN_EMAIL, email);
        contentValues.put(USER_COLUMN_FIRSTNAME, firstname);
        contentValues.put(USER_COLUMN_LASTNAME, lastname);
        contentValues.put(USER_COLUMN_BIRTHDATE, String.valueOf(birthDate.get(Calendar.YEAR)) +
                String.valueOf(birthDate.get(Calendar.MONTH) + 1) + String.valueOf(birthDate.get(Calendar.DAY_OF_MONTH)));
        contentValues.put(USER_COLUMN_BLOOD_GROUP, bloodGroup);
        contentValues.put(USER_COLUMN_MARTIAL_STATUS, martialStatus);
        contentValues.put(USER_COLUMN_PROOF_TYPE, proofType);
        contentValues.put(USER_COLUMN_PROOF_DETAIL, proofDetail);
        contentValues.put(USER_COLUMN_USERTYPE, String.valueOf(userType));
        contentValues.put(USER_COLUMN_WARD, wardNumber);
        contentValues.put(USER_COLUMN_PHONE_NUMBER, phoneNumber);

        return db.insert(TABLE_USERS, null, contentValues);
    }

    public int updateUserDetail(String column,
                                String value,
                                String constraint,
                                String constraintValue,
                                SQLiteDatabase db) throws Exception {

        if (column == USER_COLUMN_BIRTHDATE)
            throw new Exception("Cannot update BirthDate. Please use the specialized method");

        if (db == null)
            db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(column, value);

        return db.update(TABLE_USERS, contentValues, constraint + "=?", new String[]{String.valueOf(constraintValue)});
    }


    public int updateBirthDate(Calendar birthDate, String constraint, String constraintValue, SQLiteDatabase db) {

        if (db == null)
            db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_COLUMN_BIRTHDATE, String.valueOf(birthDate.get(Calendar.YEAR)) +
                String.valueOf(birthDate.get(Calendar.MONTH) + 1) + String.valueOf(birthDate.get(Calendar.DAY_OF_MONTH)));

        return db.update(TABLE_USERS, contentValues, constraint + "=?", new String[]{String.valueOf(constraintValue)});

    }


    public ArrayList<PostResult> getAllPosts() {
        ArrayList<PostResult> retrivePosts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_POSTS;
        try {
            Cursor cursor = db.rawQuery(sql, null);

            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                PostResult post = new PostResult();
                post.setUSERNAME(cursor.getString(cursor.getColumnIndex(USER_COLUMN_USERNAME)));
                post.setDETAILS(cursor.getString(cursor.getColumnIndex(COLUMN_DETAILS)));
                post.setUPCOUNT(cursor.getString(cursor.getColumnIndex(COLUMN_UPCOUNT)));
                post.setDOWNCOUNT(cursor.getString(cursor.getColumnIndex(COLUMN_DOWNCOUNT)));
                post.setSPAM(cursor.getString(cursor.getColumnIndex(COLUMN_SPAM)));
                post.setTIME(cursor.getString(cursor.getColumnIndex(COLUMN_TIME)));
                post.setTOPIC(cursor.getString(cursor.getColumnIndex(COLUMN_TOPIC)));
                post.setCATEGORY(cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY)));
                post.setPICPATH(cursor.getString(cursor.getColumnIndex(COLUMN_PICURL)));
                retrivePosts.add(post);
            }

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Collections.reverse(retrivePosts);
        return retrivePosts;
    }

    /*INSERT POST TABLE DATA*/
    public long insertPosts(String USERNAME, String DETAILS, String UPCOUNT,
                            String DOWNCOUNT, String SPAM, String TIME, String topic, String category, String picUrl) {
        long dbInsert = 0;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(USER_COLUMN_USERNAME, USERNAME);
            contentValues.put(COLUMN_DETAILS, DETAILS);
            contentValues.put(COLUMN_UPCOUNT, UPCOUNT);
            contentValues.put(COLUMN_DOWNCOUNT, DOWNCOUNT);
            contentValues.put(COLUMN_SPAM, SPAM);
            contentValues.put(COLUMN_TIME, TIME);
            contentValues.put(COLUMN_TOPIC, topic);
            contentValues.put(COLUMN_CATEGORY, category);
            contentValues.put(COLUMN_PICURL, picUrl);
            dbInsert = db.insert(TABLE_POSTS, null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dbInsert;
    }
}
