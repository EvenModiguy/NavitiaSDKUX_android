package org.kisio.NavitiaSDKUX.config;

import android.graphics.Color;

import java.util.HashMap;
import java.util.Map;

/**
 * NavitiaSDKUX_android
 *
 * Copyright \u00a9 2018 Kisio. All rights reserved.
 */

public class Configuration {
    public static String token = "";

    public static class metrics {
        public static Integer space = 4;
        public static Integer radius = 5;
        public static Integer marginS = 4;
        public static Integer margin = 8;
        public static Integer marginL = 16;
        public static Integer textS = 13;
        public static Integer text = 17;
        public static String timeFormat = "HH:mm";
        public static String longDateFormat = "EEE d MMM - HH:mm";
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
            colors.primaryText = org.kisio.NavitiaSDKUX.util.Color.contrastColor(primary);
        }

        public static Integer getPrimaryText() {
            return primaryText;
        }

        public static Integer getSecondary() {
            return secondary;
        }

        public static void setSecondary(Integer secondary) {
            colors.secondary = secondary;
            colors.secondaryText = org.kisio.NavitiaSDKUX.util.Color.contrastColor(secondary);
        }

        public static Integer getSecondaryText() {
            return secondaryText;
        }

        public static Integer getTertiary() {
            return tertiary;
        }

        public static void setTertiary(Integer tertiary) {
            colors.tertiary = tertiary;
            colors.tertiaryText = org.kisio.NavitiaSDKUX.util.Color.contrastColor(tertiary);
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

    public static Map<String, String> iconFontCodes = new HashMap<>();
    static {
        iconFontCodes.put("address", "\uea02");
        iconFontCodes.put("administrative-region", "\uea03");
        iconFontCodes.put("air", "\uea04");
        iconFontCodes.put("arrow-details-down", "\uea05");
        iconFontCodes.put("arrow-details-up", "\uea06");
        iconFontCodes.put("arrow-direction-left", "\uea07");
        iconFontCodes.put("arrow-direction-right", "\uea08");
        iconFontCodes.put("arrow-direction-straight", "\uea09");
        iconFontCodes.put("arrow-left-long", "\uea0a");
        iconFontCodes.put("arrow-right-long", "\uea0b");
        iconFontCodes.put("arrow-right", "\uea0c");
        iconFontCodes.put("bike", "\uea0d");
        iconFontCodes.put("ferry", "\uea0e");
        iconFontCodes.put("bss", "\uea0f");
        iconFontCodes.put("bus", "\uea10");
        iconFontCodes.put("calendar", "\uea11");
        iconFontCodes.put("car", "\uea12");
        iconFontCodes.put("clock", "\uea13");
        iconFontCodes.put("coach", "\uea14");
        iconFontCodes.put("destination", "\uea15");
        iconFontCodes.put("direction", "\uea16");
        iconFontCodes.put("edit", "\uea17");
        iconFontCodes.put("funicular", "\uea18");
        iconFontCodes.put("geolocation", "\uea19");
        iconFontCodes.put("home", "\uea1a");
        iconFontCodes.put("location-pin", "\uea15");
        iconFontCodes.put("metro", "\uea1b");
        iconFontCodes.put("notice", "\uea1c");
        iconFontCodes.put("origin", "\uea1d");
        iconFontCodes.put("poi", "\uea1e");
        iconFontCodes.put("realtime", "\uea1f");
        iconFontCodes.put("stop", "\uea21");
        iconFontCodes.put("localtrain", "\uea23");
        iconFontCodes.put("rapidtransit", "\uea23");
        iconFontCodes.put("longdistancetrain", "\uea23");
        iconFontCodes.put("tramway", "\uea24");
        iconFontCodes.put("walking", "\uea25");
        iconFontCodes.put("work", "\uea26");
        iconFontCodes.put("circle-filled", "\uea27");
        iconFontCodes.put("circle", "\uea28");
        iconFontCodes.put("disruption-blocking", "\uea29");
        iconFontCodes.put("disruption-nonblocking", "\uea2a");
        iconFontCodes.put("disruption-information", "\uea2b");
    }
}
