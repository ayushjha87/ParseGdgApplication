package com.gdg.parse.parseapplication;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import java.util.Arrays;
import java.util.List;

import domain.User;

public class MainActivity extends Activity {

    private final String PUBLIC_PROFILE = "public_profile";
    private final String EMAIL = "email";
    private Dialog progressDialog;
    private Button mFbButton;
    private List<String> permissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpViews();
        initParse();
    }

    private void initParse() {
        ParseUser currentUser = ParseUser.getCurrentUser();
        if ((currentUser != null) && ParseFacebookUtils.isLinked(currentUser)) {
            showUserPhoneNumberActivity();
        }
    }

    private void setUpViews() {
        mFbButton = (Button) findViewById(R.id.facebook_login);
        mFbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoginClick(v);
            }
        });
        permissions = Arrays.asList(PUBLIC_PROFILE, EMAIL);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

    public void onLoginClick(View v) {
        progressDialog = ProgressDialog.show(MainActivity.this, getResources().getString(R.string.facebook_login), getResources().getString(R.string.login_message), true);
        ParseFacebookUtils.logInWithReadPermissionsInBackground(this, permissions, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException err) {
                progressDialog.dismiss();
                if (user == null) {
                    Log.d("TAG", "Uh oh. The user cancelled the Facebook login.");
                } else if (user.isNew()) {
                    Log.d("TAG", "User signed up and logged in through Facebook!");
                    showUserPhoneNumberActivity();
                } else {
                    Log.d("TAG", "User logged in through Facebook!");
                    showUserPhoneNumberActivity();
                }
            }
        });
    }

    private void showUserPhoneNumberActivity() {
        ParseUser currentUser = ParseUser.getCurrentUser();
        String phoneNumber = currentUser.getString(User.PHONE_NUMBER);
        if (phoneNumber != null) {
            finish();
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        } else {
            finish();
            Intent intent = new Intent(this, GetPhoneNumberActivity.class);
            startActivity(intent);
        }
    }
}
