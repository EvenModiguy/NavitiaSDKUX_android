package org.kisio.navitiasdkui;

import android.app.Application;

import com.facebook.soloader.SoLoader;

public class NavitiaSDKUX extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        SoLoader.init(this, false);
    }
}
