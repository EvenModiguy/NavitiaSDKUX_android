package org.kisio.navitiasdkui.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;

import org.kisio.navitiasdkui.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Navitia SDK UI
 *
 * Copyright \u00a9 2018 Kisio. All rights reserved.
 */
public class Configuration {
    public static String getToken(Activity activity) {
        try {
            ApplicationInfo ai = activity.getPackageManager().getApplicationInfo(activity.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            String token = bundle.getString("org.kisio.navitia.API_KEY");

            if (TextUtils.isEmpty(token) || token.equalsIgnoreCase("YOUR_API_KEY")) throw new RuntimeException();

            return token;
        } catch (PackageManager.NameNotFoundException | RuntimeException e) {
            Log.e("Navitia SDK UI", "Don't forget to configure <meta-data android:name=\"org.kisio.navitia.API_KEY\" android:value=\"YOUR_API_KEY\"/> in your AndroidManifest.xml file.");
            activity.finish();
            return null;
        }
    }

    public static int getColor(Context context, int wantedColor) {
        TypedValue typedValue = new TypedValue();
        TypedArray a = context.obtainStyledAttributes(typedValue.data, new int[] { wantedColor });
        int color = a.getColor(0, 0);

        a.recycle();

        return color;
    }
}
