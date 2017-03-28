package com.example.boopathyraj.lims;

/**
 * Created by Boopathyraj on 3/28/2017.
 */import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Prasad Thangavel on 12-12-2016.
 */
public class ChatFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "ChatFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        storeToken(refreshedToken);
    }

    private void storeToken(String token) {
        //saving the token on shared preferences
        SharedPrefManager.getInstance(getApplicationContext()).saveDeviceToken(token);
    }
}
