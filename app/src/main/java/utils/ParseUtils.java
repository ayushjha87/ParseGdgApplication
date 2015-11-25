package utils;


import android.util.Log;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class ParseUtils {

    public static void syncParseInstallation() {

        ParseQuery<ParseInstallation> parseQuery = ParseInstallation.getQuery();
        parseQuery.whereEqualTo("User", ParseUser.getCurrentUser());
        Log.v("Parse", " Parse Installation Query :" + ParseUser.getCurrentUser());
        parseQuery.getFirstInBackground(new GetCallback<ParseInstallation>() {
            @Override
            public void done(ParseInstallation parseInstallation, ParseException e) {
                Log.v("Parse", " Parse Installation Query Exception :" + e);
                if (e != null) {
                    ParseInstallation installation = ParseInstallation.getCurrentInstallation();
                    installation.put("User", ParseUser.getCurrentUser());
                    installation.saveInBackground();
                }
            }
        });
        subscribeToUserUniqueChannel();
    }


    public static void subscribeToUserUniqueChannel() {

        if (ParseUser.getCurrentUser() != null) {
            ParsePush.subscribeInBackground("user_" + ParseUser.getCurrentUser().getObjectId(),
                    new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Log.v("com.parse.push", "successfully subscribed to the broadcast channel.");
                            } else {
                                Log.v("com.parse.push", "failed to subscribe for push", e);
                            }
                        }
                    });
        }
    }

}
