package com.android.officecanteen.com.android.officecanteen.app;

import com.parse.Parse;

import android.app.Application;
import android.content.Context;

/**
 * Created by inrsharm04 on 11/19/2016.
 */

public class OfficeCanteenApp extends Application {

    private static Context appContext;

    /**
     * Common getter for application context
     *
     * @return - application context
     */
    public static Context getAppContext() {
        return appContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("Vnxgbdwt9WTFsjMABJ71MLElSZGgeKEJv0SVCudf")
                .clientKey("LvVz5pB0tDJREwMKvZPIGeYjg1H1ejmoBagOPEKo")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
