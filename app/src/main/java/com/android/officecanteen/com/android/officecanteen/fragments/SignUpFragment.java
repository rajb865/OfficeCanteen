package com.android.officecanteen.com.android.officecanteen.fragments;

import com.android.officecanteen.OfficeCanteenLauncher;
import com.android.officecanteen.R;
import com.android.officecanteen.com.android.officecanteen.BaseFragment;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v13.app.FragmentCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpFragment extends BaseFragment implements View.OnClickListener, View.OnFocusChangeListener {

    public static String TAG = SignUpFragment.class.getName();
    private OfficeCanteenLauncher mHomeActivity;
    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 1;
    private String deviceId;
    private EditText etUserName, etPassword, etMobile, etLocation, etCompany;
    private String username, password, location, company, mobile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_up_fragment, null);
        if (ContextCompat.checkSelfPermission(mHomeActivity,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            FragmentCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE},
                    MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
        } else {
            getDeviceId();
        }
        etUserName = (EditText) view.findViewById(R.id.signup_username);
        etPassword = (EditText) view.findViewById(R.id.signup_password);
        etMobile = (EditText) view.findViewById(R.id.signup_mobile);
        etLocation = (EditText) view.findViewById(R.id.signup_location);
        etCompany = (EditText) view.findViewById(R.id.signup_company);
        etUserName.setOnFocusChangeListener(this);
        etPassword.setOnFocusChangeListener(this);
        etMobile.setOnFocusChangeListener(this);
        etLocation.setOnFocusChangeListener(this);
        etCompany.setOnFocusChangeListener(this);
        Button btnSignUp = (Button) view.findViewById(R.id.btn_sign_up);
        btnSignUp.setOnClickListener(this);
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
        case MY_PERMISSIONS_REQUEST_READ_PHONE_STATE: {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getDeviceId();
            } else {
                Log.d(TAG, "Permission not granted");
                deviceId = Settings.Secure.getString(getContext().getContentResolver(),
                        Settings.Secure.ANDROID_ID);
            }
            return;
        }
        }
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        mHomeActivity = (OfficeCanteenLauncher) getActivity();
        mHomeActivity.addVisibilityChangeListener(this);
    }

    @Override
    public void onFragmentVisibilityChange(String tagName) {
        if (TAG.equals(tagName)) {
            //TODO: Update toolbar of login fragment
        }
    }

    private void handleSignUpButton() {
        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(username);
        user.put("phone", mobile);
        user.put("isActivate", true);
        user.put("company", company);
        user.put("location", location);
        user.put("deviceId", deviceId);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(mHomeActivity, "Sign Up Success", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Sign Up Success");
                } else {
                    Toast.makeText(mHomeActivity, "Sign Up failed : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }

    private void getDeviceId() {
        TelephonyManager telephonyManager = (TelephonyManager) mHomeActivity.getSystemService(Context.TELEPHONY_SERVICE);
        deviceId = telephonyManager.getDeviceId();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mHomeActivity = (OfficeCanteenLauncher) getActivity();
        mHomeActivity.addVisibilityChangeListener(this);
    }

    @Override
    public void onDetach() {
        mHomeActivity.removeVisibilityChangeListener(this);
        super.onDetach();
    }

    @Override
    public void onResponseReceived(String response, String operationId, boolean isError) {

    }

    @Override
    public void onClick(View view) {
        View view1 = mHomeActivity.getCurrentFocus();
        if (view1 != null) {
            InputMethodManager imm = (InputMethodManager) mHomeActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        switch (view.getId()) {
        case R.id.btn_sign_up:
            ParseUser parseUser = ParseUser.getCurrentUser();
            if (parseUser != null) {
                parseUser.logOut();
            }
            username = etUserName.getText().toString().trim();
            password = etPassword.getText().toString().trim();
            mobile = etMobile.getText().toString().trim();
            company = etCompany.getText().toString().trim();
            location = etLocation.getText().toString().trim();

            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(location)
                    || TextUtils.isEmpty(company) || TextUtils.isEmpty(mobile)) {
                Toast.makeText(mHomeActivity, "Can not left blank", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
                Toast.makeText(mHomeActivity, "Enter valid email id", Toast.LENGTH_SHORT).show();
                return;
            }
            handleSignUpButton();
            break;
        }

    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (b || !(view instanceof EditText)) {
            return;
        }

        EditText et = (EditText) view;
        String text = et.getText().toString().trim();
        if (TextUtils.isEmpty(text)) {
            et.setError("This filed can not left blank");
            return;
        } else {
            et.setError(null);
        }
        switch (view.getId()) {
        case R.id.signup_username:
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
                etUserName.setError("Enter valid email address");
            } else {
                etUserName.setError(null);
            }
            username = text;
            break;
        }
    }
}
