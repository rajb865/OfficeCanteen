package com.android.officecanteen;

import com.android.officecanteen.com.android.officecanteen.BaseFragment;
import com.android.officecanteen.com.android.officecanteen.communication.CommunicationNotifier;
import com.android.officecanteen.com.android.officecanteen.communication.ICommunicationOperation;
import com.android.officecanteen.com.android.officecanteen.communication.OperationSignUp;
import com.android.officecanteen.com.android.officecanteen.fragments.LoginFragment;
import com.android.officecanteen.com.android.officecanteen.fragments.SignUpFragment;
import com.parse.ParseUser;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.ArrayList;

public class OfficeCanteenLauncher extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {
    private final AllTimeLoginReceiver allTimeReceiver = new AllTimeLoginReceiver();
    private ArrayList<BaseFragment> mVisibilityChangeListeners = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office_canteen_launcher);
        registerReceiver(this.allTimeReceiver, getAllTimeIntentFilter(), null,
                null);

        ParseUser parseUser = ParseUser.getCurrentUser();
        if (parseUser == null || TextUtils.isEmpty(parseUser.getSessionToken())){
            // User is not logged in
            replaceFragment(new LoginFragment(), LoginFragment.TAG, false);
        }else{
            //Home screen fragment
            parseUser.logOut();
        }


    }

    /**
     * Creates Intent filter for all time broadcast handler.
     *
     * @return intentFilter
     */
    private IntentFilter getAllTimeIntentFilter() {
        final IntentFilter filter = new IntentFilter();

        filter.addAction(CommunicationNotifier.ACTION_OPERATION_COMPLETED);

        return filter;
    }

    @Override
    public void onBackStackChanged() {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null)
            for (int i = 0; i < mVisibilityChangeListeners.size(); i++) {
                mVisibilityChangeListeners.get(i).onFragmentVisibilityChange(fragmentManager.findFragmentById(R.id.home_activity_content_view).getTag());
            }
    }

    public void addVisibilityChangeListener(BaseFragment fragmentVisibilityChangeListener) {
        mVisibilityChangeListeners.add(fragmentVisibilityChangeListener);
    }

    public void removeVisibilityChangeListener(BaseFragment fragmentVisibilityChangeListener) {
        mVisibilityChangeListeners.remove(fragmentVisibilityChangeListener);
    }

    public void replaceFragment(Fragment fragment, String tag, boolean isAddToBackStack) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.home_activity_content_view, fragment, tag);
        if (isAddToBackStack) {
            fragmentTransaction.addToBackStack(tag);
        }
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();
        if (count > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Receiver to catch all time actions like app state changes.
     */
    private class AllTimeLoginReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(final Context context, final Intent intent) {
            handleAction(intent);
        }

    }

    private void handleAction(Intent intent) {
        if (null == intent) {
            return;
        }

        final String action = intent.getAction();
        String operationId = intent.getStringExtra(CommunicationNotifier.EXTRA_KEY_OPERATION);
        BaseFragment listener = getListener(operationId);
        if (null == listener) {
            return;
        }
        try {
            if (CommunicationNotifier.ACTION_OPERATION_COMPLETED.equals(action)) {
                final int resultCode = intent.getIntExtra(
                        CommunicationNotifier.EXTRA_KEY_RESULT_CODE,
                        ICommunicationOperation.Result.Code.UNDEFINED);

                if (resultCode != ICommunicationOperation.Result.Code.COMPLETED) {
                    String errorMessage = intent.getStringExtra(CommunicationNotifier.EXTRA_KEY_RESULT_ERROR_MESSAGE);
                    listener.onResponseReceived(errorMessage, operationId, true);
                    //show error dialog
                } else {
                    String responseString = intent.getStringExtra(CommunicationNotifier.EXTRA_KEY_RESULT_RESPONSE_STRING);
                    listener.onResponseReceived(responseString, operationId, false);
                }
            } else {
                Toast.makeText(OfficeCanteenLauncher.this, "Some error occurred, Please try after some time. ", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception exc) {
            //exception handling
            Toast.makeText(OfficeCanteenLauncher.this, "Some error occurred, Please try after some time. ", Toast.LENGTH_SHORT).show();
        }
    }

    private BaseFragment getListener(String operationId) {
        BaseFragment listener = null;
        if (OperationSignUp.OPERATION_ID.equalsIgnoreCase(operationId)) {
            listener = (BaseFragment) getFragmentManager().findFragmentByTag(SignUpFragment.TAG);
        }
        return listener;
    }
}
