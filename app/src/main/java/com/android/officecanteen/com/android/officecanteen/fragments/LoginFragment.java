package com.android.officecanteen.com.android.officecanteen.fragments;

import com.android.officecanteen.OfficeCanteenLauncher;
import com.android.officecanteen.R;
import com.android.officecanteen.com.android.officecanteen.BaseFragment;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v13.app.FragmentCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by inrsharm04 on 11/20/2016.
 */

public class LoginFragment extends BaseFragment implements View.OnClickListener {
    public static String TAG = LoginFragment.class.getName();
    private OfficeCanteenLauncher mHomeActivity;
    private EditText etUserName, etPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, null);
        etUserName = (EditText) view.findViewById(R.id.login_username);
        etPassword = (EditText) view.findViewById(R.id.login_password);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.login_remember_me);
        Button btnLogin = (Button) view.findViewById(R.id.login_sign_in);
        Button btnSignUp = (Button) view.findViewById(R.id.login_sign_up);
        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        return view;
    }

    @Override
    public void onFragmentVisibilityChange(String tagName) {
        if (TAG.equals(tagName)) {
            //TODO: Update toolbar of login fragment
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mHomeActivity = (OfficeCanteenLauncher) getActivity();
        mHomeActivity.addVisibilityChangeListener(this);
    }

    @Override
    public void onAttach(Activity context) {
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
        case R.id.login_sign_in:
            ParseUser user = ParseUser.getCurrentUser();
            if (user != null && !TextUtils.isEmpty(user.getSessionToken())){
                Toast.makeText(mHomeActivity, "Already Logged In", Toast.LENGTH_SHORT).show();
                return;
            }
            String username = etUserName.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                Toast.makeText(mHomeActivity, "Can not left blank", Toast.LENGTH_SHORT).show();
                return;
            }
            ParseUser.logInInBackground(username, password, new LogInCallback() {
                @Override
                public void done(ParseUser parseUser, ParseException e) {
                    if (parseUser != null) {
                        Toast.makeText(mHomeActivity, "Login Success", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mHomeActivity, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            break;
        case R.id.login_sign_up:
            mHomeActivity.replaceFragment(new SignUpFragment(), SignUpFragment.TAG, true);
            break;
        }
    }
}
