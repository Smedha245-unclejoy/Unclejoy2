package com.example.medhasingh.unclejoy2;

/**
 * Created by medha singh on 6/20/2016.
 */
public class Config {
    public static final String LOGIN_URL = "https://ujapi.herokuapp.com/api/v1/login";

    //Keys for email and password as defined in our $_POST['key'] in login.php
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_TOKEN="authentication_token";
    public static final String KEY_NAME="name";
    public static final String KEY_ID="id";
    public static final String KEY_CATEGORY="store_category_id";
    public static final String KEY_SUB_CATEGORY="store_sub_category_id";
    public static final String KEY_PHOTO="attachmnet";
    public static final String KEY_DATE="date";
    public static final String KEY_TIME="time";
    public static final String Key_BOOKING_ID="id";
    public static final String KEY_STORE_ID="ied";
    public static final String KEY_SIGN_ID="id";

    //If server response is equal to this that means login is successful
    public static final String LOGIN_SUCCESS = "success";

    //Keys for Sharedpreferences
    //This would be the name of our shared preferences
    public static final String SHARED_PREF_NAME = "myloginapp";
    public static final String SHARED_USER_ID="id";
    public static final String SHARED_USER_NAME="name";
    //This would be used to store the email of current logged in user
    public static final String EMAIL_SHARED_PREF = "email";
    //For authentication token storage
    public static final String TOKEN_SHARED_PREF="authentication_token";
    public static final String SHARED_DATE="date";
    public static final String SHARED_TIME="time";
    public static final String SHARED_STORE_CATEGORY="store_category_id";
    public static final String SHARED_STORE_SUB_CATEGORY="store_sub_category_id";
    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";
    public static final String SIGNED_UP="signed";
    public static final String GET_STARTED="get";
    public static final String SHARED_PHOTO="attachment";
    public static final String SHARED_SIGN_ID="id";
    public static final String SHARED_BOOKING_ID="id";
    public static final String SHARED_STORE_ID="ied";

}
