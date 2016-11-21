package com.android.officecanteen.com.android.officecanteen;

import android.app.Fragment;

/**
 * Created by inrsharm04 on 10/31/2015.
 */
public abstract class BaseFragment extends Fragment {

    public abstract void onFragmentVisibilityChange(String tagName);

    public abstract void onResponseReceived(String response, String operationId, boolean isError);
}
