package org.kisio.navitiasdkui.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Navitia SDK UI
 *
 * Copyright \u00a9 2018 Kisio. All rights reserved.
 */
public class Configuration {
    public static final String TIME_FORMAT = "HH:mm";
    public static final String LONG_DATE_TIME_FORMAT = "EEE d MMM - HH:mm";

    public static String getToken(Context context) {
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            String token = bundle.getString("org.kisio.navitia.API_KEY");

            if (TextUtils.isEmpty(token) || token.equalsIgnoreCase("YOUR_API_KEY")) throw new Exception();

            return token;
        } catch (Exception e) {
            Log.e("Navitia SDK UI", "Don't forget to configure <meta-data android:name=\"org.kisio.navitia.API_KEY\" android:value=\"YOUR_API_KEY\"/> in your AndroidManifest.xml file.");
            return null;
        }
    }

    public static class colors {
        static Integer primary = Color.parseColor("#666666");
        static Integer primaryText = Color.parseColor("#ffffff");
        static Integer secondary = Color.parseColor("#f1f1f1");
        static Integer secondaryText = Color.parseColor("#ffffff");
        static Integer tertiary = Color.parseColor("#40958e");
        static Integer tertiaryText = Color.parseColor("#ffffff");
        static Integer brightText = Color.parseColor("#ffffff");
        static Integer darkText = Color.parseColor("#000000");
        static Integer white = Color.parseColor("#ffffff");
        static Integer lighterGray = Color.parseColor("#f1f1f1");
        static Integer lightGray = Color.parseColor("#cdcdcd");
        static Integer gray = Color.parseColor("#9a9a9a");
        static Integer darkGray = Color.parseColor("#404040");
        static Integer darkerGray = Color.parseColor("#202020");
        static Integer origin = Color.parseColor("#00bb75");
        static Integer destination = Color.parseColor("#b00353");
        static Integer transparentBlack = Color.parseColor("#bb000000");

        public static Integer getPrimary() {
            return primary;
        }

        public static void setPrimary(Integer primary) {
            colors.primary = primary;
            colors.primaryText = org.kisio.navitiasdkui.util.Color.contrastColor(primary);
        }

        public static Integer getPrimaryText() {
            return primaryText;
        }

        public static Integer getSecondary() {
            return secondary;
        }

        public static void setSecondary(Integer secondary) {
            colors.secondary = secondary;
            colors.secondaryText = org.kisio.navitiasdkui.util.Color.contrastColor(secondary);
        }

        public static Integer getSecondaryText() {
            return secondaryText;
        }

        public static Integer getTertiary() {
            return tertiary;
        }

        public static void setTertiary(Integer tertiary) {
            colors.tertiary = tertiary;
            colors.tertiaryText = org.kisio.navitiasdkui.util.Color.contrastColor(tertiary);
        }

        public static Integer getTertiaryText() {
            return tertiaryText;
        }

        public static Integer getBrightText() {
            return brightText;
        }

        public static void setBrightText(Integer brightText) {
            colors.brightText = brightText;
        }

        public static Integer getDarkText() {
            return darkText;
        }

        public static void setDarkText(Integer darkText) {
            colors.darkText = darkText;
        }

        public static Integer getWhite() {
            return white;
        }

        public static Integer getLighterGray() {
            return lighterGray;
        }

        public static Integer getLightGray() {
            return lightGray;
        }

        public static Integer getGray() {
            return gray;
        }

        public static Integer getDarkGray() {
            return darkGray;
        }

        public static Integer getDarkerGray() {
            return darkerGray;
        }

        public static Integer getOrigin() {
            return origin;
        }

        public static void setOrigin(Integer origin) {
            colors.origin = origin;
        }

        public static Integer getDestination() {
            return destination;
        }

        public static void setDestination(Integer destination) {
            colors.destination = destination;
        }

        public static Integer getTransparentBlack() {
            return transparentBlack;
        }
    }
}
