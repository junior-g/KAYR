package com.example.abis.kayr.ExternalConnectionCheck;

import android.content.Context;
import 	android.net.ConnectivityManager;
import android.widget.Toast;

/**
 * Created by abis.
 */

public class InternetConnection {

    Context context;

    public void setContext(Context context) {
        this.context = context;
        isNetworkConnected();
    }
    private void isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        Toast.makeText(context, "No InternetConnection ", Toast.LENGTH_LONG).show();
    }
}
