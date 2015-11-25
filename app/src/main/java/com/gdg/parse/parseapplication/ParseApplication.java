package com.gdg.parse.parseapplication;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;

public class ParseApplication extends Application {

    static final String TAG = "MyApp";

    @Override
    public void onCreate() {
        super.onCreate();

        FacebookSdk.sdkInitialize(getApplicationContext());

        Parse.initialize(this,
                "xtyLYuMO6GwMKLDekXQUe5yjdPNsQB2DfiqOjKtj",
                "4VfScWpc5gdgZYlebhWwcjPyGNHcuo9yvkfvAEIN"
        );

        ParseFacebookUtils.initialize(this);
    }
}
