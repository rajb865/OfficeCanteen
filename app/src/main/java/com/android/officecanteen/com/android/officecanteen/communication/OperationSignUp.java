package com.android.officecanteen.com.android.officecanteen.communication;

import com.parse.ParseException;
import com.parse.ParseUser;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by inrsharm04 on 11/20/2016.
 */

public class OperationSignUp implements ICommunicationOperation {
    public static String OPERATION_ID = OperationSignUp.class.getSimpleName();
    public static String PREFIX = "com.android.officecanteen.com.android.officecanteen.communication.OperationSignUp";
    public static String EXTRA_KEY_USERNAME = PREFIX + "user_name";
    public static String EXTRA_KEY_PASSWORD = PREFIX + "password";
    public static String EXTRA_KEY_EMAIL_ID = PREFIX + "email_id";
    public static String EXTRA_KEY_PHONE = PREFIX + "phone";
    public static String EXTRA_KEY_COMPANY = PREFIX + "company";
    public static String EXTRA_KEY_IS_ACTIVATE = PREFIX + "is_activate";

    private String userName = null;
    private String password = null;
    private String emailId = null;
    private String phone = null;
    private String company = null;
    boolean isActivate = false;

    @Override
    public Result preprocess(Bundle extra) {
        if (null == extra) {
            return Result.noParamsFailure();
        }
        userName = extra.getString(EXTRA_KEY_USERNAME);
        password = extra.getString(EXTRA_KEY_PASSWORD);
        emailId = extra.getString(EXTRA_KEY_EMAIL_ID);
        phone = extra.getString(EXTRA_KEY_PHONE);
        company = extra.getString(EXTRA_KEY_COMPANY);
        isActivate = extra.getBoolean(EXTRA_KEY_IS_ACTIVATE);
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password) || TextUtils.isEmpty(emailId)
                || TextUtils.isEmpty(phone) || TextUtils.isEmpty(company) || !TextUtils.isDigitsOnly(phone)) {
            return Result.noParamsFailure();
        }
        return Result.furtherProcessing();
    }

    @Override
    public Result process() {
        Result result = Result.completed();
        ParseUser user = new ParseUser();
        user.setUsername(userName);
        user.setPassword(password);
        user.setEmail(emailId);
        user.put(EXTRA_KEY_PHONE, phone);
        user.put(EXTRA_KEY_IS_ACTIVATE, isActivate);
        user.put(EXTRA_KEY_COMPANY, company);
        try {
            user.signUp();
        } catch (ParseException e) {
            Log.e(OPERATION_ID, e.getMessage());
            result = Result.fromException(e);
        }

        return result;
    }

    @Override
    public String getOperationId() {
        return OPERATION_ID;
    }
}
