package com.saispark.marathi_video_status;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.facebook.ads.AdSettings;
import com.google.ads.consent.ConsentInformation;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import timber.log.Timber;

/**
 * Created by Tamim on 28/09/2017.
 */



public class App extends MultiDexApplication {
    private static App instance;

    @Override
    public void onCreate()
    {
        MultiDex.install(this);
        super.onCreate();
        instance = this;
        if (BuildConfig.DEBUG)
        {
            Timber.plant(new Timber.DebugTree());
        }
        ConsentInformation.getInstance(getApplicationContext()).addTestDevice("9DCE68D39C99EC7EE9F1A85E5BD890DF");
        AdSettings.addTestDevice("0ed3dfc8-a217-42a1-8ccb-84d91c81fa7c");
        Timber.i("Creating our Application");
    }

    public static App getInstance ()
    {
        return instance;
    }

    public static boolean hasNetwork ()
    {
        return instance.checkIfHasNetwork();
    }

    public boolean checkIfHasNetwork()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService( Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
