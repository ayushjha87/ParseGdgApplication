package com.gdg.parse.parseapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsAuthButton;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import domain.User;

public class GetPhoneNumberActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_phone_number);
        DigitsAuthButton digitsButton = (DigitsAuthButton) findViewById(R.id.auth_button);
        digitsButton.setCallback(new AuthCallback() {
            @Override
            public void success(DigitsSession digitsSession, String s) {
                Log.v(""," Verified Phone Number from Digit :" + s + "::" + digitsSession.getId());
                Digits.getSessionManager().clearActiveSession();
                if (s != null) {
                    addUserPhoneNumberToDB(s);
                    showUserDetailsActivity();
                }
            }

            @Override
            public void failure(DigitsException e) {

            }
        });
    }

    private void addUserPhoneNumberToDB(String phoneNumber) {
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            currentUser.put(User.PHONE_NUMBER, phoneNumber);
            currentUser.saveInBackground(new SaveCallback() {
                public void done(ParseException e) {
                    if (e == null) {
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void showUserDetailsActivity() {
        finish();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
