package org.kisio.navitiasdkui.util;

import android.text.TextUtils;

/**
 * NavitiaSDKUX_android
 *
 * Copyright \u00a9 2018 Kisio. All rights reserved.
 */

public class Color {
    public static Integer getColorFromHexadecimal(String hex) {
        return !TextUtils.isEmpty(hex) ? android.graphics.Color.parseColor("#" + hex) : android.graphics.Color.GRAY;
    }

    public static Integer contrastColor(Integer color) {
        return contrastColor(color, Configuration.colors.getBrightText(), Configuration.colors.getDarkText());
    }

    /**
     * Two colors provide good color visibility if the brightness difference and the color difference between the two colors are greater than a set range
     * The range for color brightness difference is 125.
     * The range for color difference is 500.
     *
     * @param color
     * @param brightColor
     * @param darkColor
     * @return
     */
    public static Integer contrastColor(Integer color, Integer brightColor, Integer darkColor) {
        final double colorBrightness = brightness(color);
        final double darkColorBrightness = brightness(darkColor);
        final double darkColorDifference = difference(color, darkColor);
        final double darkColorBrightnessDifference = Math.max(colorBrightness, darkColorBrightness) - Math.min(colorBrightness, darkColorBrightness);
        final double brightColorBrightness = brightness(brightColor);
        final double brightColorDifference = difference(color, brightColor);
        final double brightColorBrightnessDifference = Math.max(colorBrightness, brightColorBrightness) - Math.min(colorBrightness, brightColorBrightness);

        Integer brightColorPonderation = 0;
        Integer darkColorPonderation = 0;

        if (brightColorBrightnessDifference > 125) {
            brightColorPonderation += 1;
        }
        if (darkColorBrightnessDifference > 125) {
            darkColorPonderation += 1;
        }

        if (brightColorDifference > 500) {
            brightColorPonderation += 1;
        }
        if (darkColorDifference > 500) {
            darkColorPonderation += 1;
        }

        if (brightColorPonderation == darkColorPonderation) {
            // On equal ponderation, return most brightness diff color
            return brightColorBrightnessDifference > darkColorBrightnessDifference ? brightColor : darkColor;
        } else {
            return brightColorPonderation > darkColorPonderation ? brightColor : darkColor;
        }
    }

    /**
     * Brightness color from https://www.w3.org/TR/AERT#color-contrast
     *
     * @param color
     * @return
     */
    static double brightness(Integer color) {
        final int r = android.graphics.Color.red(color);
        final int g = android.graphics.Color.green(color);
        final int b = android.graphics.Color.blue(color);

        // https://www.w3.org/TR/AERT#color-contrast
        return ((r * 299) + (g * 587) + (b * 114)) / 1000;
    }

    /**
     * Color difference from https://www.w3.org/TR/AERT#color-contrast
     *
     * @param color1
     * @param color2
     * @return
     */
    static double difference(Integer color1, Integer color2) {
        final int r1 = android.graphics.Color.red(color1);
        final int g1 = android.graphics.Color.green(color1);
        final int b1 = android.graphics.Color.blue(color1);
        final int r2 = android.graphics.Color.red(color2);
        final int g2 = android.graphics.Color.green(color2);
        final int b2 = android.graphics.Color.blue(color2);

        return (Math.max(r1, r2) - Math.min(r1, r2)) + (Math.max(g1, g2) - Math.min(g1, g2)) + (Math.max(b1, b2) - Math.min(b1, b2));
    }
}
