package com.gdg.parse.parseapplication;

import android.app.Application;

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.facebook.FacebookSdk;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import io.fabric.sdk.android.Fabric;

public class ParseApplication extends Application {

    static final String TAG = "MyApp";
    private AuthCallback authCallback;

    @Override
    public void onCreate() {
        super.onCreate();

        FacebookSdk.sdkInitialize(getApplicationContext());

        Parse.initialize(this,
                "xtyLYuMO6GwMKLDekXQUe5yjdPNsQB2DfiqOjKtj",
                "4VfScWpc5gdgZYlebhWwcjPyGNHcuo9yvkfvAEIN"
        );

        ParseFacebookUtils.initialize(this);

        TwitterAuthConfig authConfig = new TwitterAuthConfig("xmsmYO1t65dap93MsvYr9CETx", "POPrcPFF4rcjbk6zAwuUgdXl1BWipB3q0gaKZGrGghrq63YHYT");
        Fabric.with(this, new TwitterCore(authConfig), new Digits());
        authCallback = new AuthCallback() {
            @Override
            public void success(DigitsSession session, String phoneNumber) {
                // Do something with the session
            }

            @Override
            public void failure(DigitsException exception) {
                // Do something on failure
            }
        };
    }
    public AuthCallback getAuthCallback(){
        return authCallback;
    }
}
