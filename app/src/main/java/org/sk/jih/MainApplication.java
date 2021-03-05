package org.sk.jih;

import android.app.Application;
import android.content.Context;


public class MainApplication extends Application {
    public static Context appContext;
    public static final String LOG_TAG = "RaisoniPortalHOD";
    public static final boolean _writeToFile = true;
    public static final boolean _writeToConsole = true;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
    }
}