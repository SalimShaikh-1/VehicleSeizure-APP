package org.sk.jih.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.sk.jih.MainApplication;


public class ConnectionDetector {

    public static boolean isConnectingToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager) MainApplication.appContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }
}