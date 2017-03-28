package com.example.boopathyraj.lims;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

/**
 * Created by Boopathyraj on 2/2/2017.
 */
public class LibraryApplication extends Application {
    public static Context context;

    public static LibraryApplication get(Context context){
        return (LibraryApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Stetho.initializeWithDefaults(this);
    }
}
