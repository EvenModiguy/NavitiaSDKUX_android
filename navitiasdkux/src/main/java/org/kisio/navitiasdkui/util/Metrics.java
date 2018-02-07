package org.kisio.navitiasdkui.util;

import com.facebook.litho.ComponentContext;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.kisio.NavitiaSDK.models.Path;
import org.kisio.navitiasdkui.config.Configuration;
import org.kisio.navitiasdkui.R;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * NavitiaSDKUX_android
 *
 * Copyright \u00a9 2018 Kisio. All rights reserved.
 */

public class Metrics {
    public static Date navitiaDate(String isoString) {
        final String[] timeData = isoString.split("T");
        final String year = timeData[0].substring(0, 4);
        final String month = timeData[0].substring(4, 6);
        final String day = timeData[0].substring(6, 8);
        final String hours = timeData[1].substring(0, 2);
        final String minutes = timeData[1].substring(2, 4);
        final String seconds = timeData[1].substring(4, 6);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.valueOf(year));
        cal.set(Calendar.MONTH, Integer.valueOf(month) - 1);
        cal.set(Calendar.DAY_OF_MONTH, Integer.valueOf(day));
        cal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hours));
        cal.set(Calendar.MINUTE, Integer.valueOf(minutes));
        cal.set(Calendar.SECOND, Integer.valueOf(seconds));
        cal.set(Calendar.MILLISECOND, 0);

        Date converted = cal.getTime();

        return converted;
    }

    public static String timeText(String isoString) {
        final String[] timeData = isoString.split("T");
        final String hours = timeData[1].substring(0, 2);
        final String minutes = timeData[1].substring(2, 4);

        return String.format("%1$s:%2$s", hours, minutes);
    }

    public static String shortDateText(DateTime datetime) {
        return DateTimeFormat.forStyle("S-").print(datetime);
    }

    public static String longDateText(DateTime datetime) {
        String pattern = Configuration.metrics.longDateFormat;

        return DateTimeFormat.forPattern(pattern).print(datetime);
    }

    public static String distanceText(ComponentContext c, Integer meters) {
        if (meters < 1000) {
            return String.format(Locale.getDefault(),"%d %s", meters, c.getString(R.string.units_meters));
        } else {
            return String.format(Locale.getDefault(),"%.1f %s", meters / 1000.f, c.getString(R.string.units_km));
        }
    }

    public static String durationText(ComponentContext c, Integer seconds) {
        return durationText(c, seconds, false);
    }

    public static String durationText(ComponentContext c, Integer seconds, Boolean useFullFormat) {
        if (seconds < 60) {
            return String.format("%1$s %2$s", c.getString(R.string.less_than_a), c.getString(R.string.units_minute));
        } else if (seconds < 120) {
            return String.format("1 %s", c.getString(R.string.units_minute));
        } else if (seconds < 3600) {
            Integer minutes = seconds / 60;
            return String.format(Locale.getDefault(), "%d %s", minutes, c.getString(R.string.units_minutes));
        } else {
            Integer hours = seconds / 3600;
            Integer remainingMinutes = (seconds / 60) - (hours * 60);
            String minutes = String.valueOf(remainingMinutes);
            if (remainingMinutes < 10) {
                minutes = String.format(Locale.getDefault(),"0%d", remainingMinutes);
            }

            if (useFullFormat) {
                if (hours > 1) {
                    if (remainingMinutes > 1) {
                        return String.format(c.getString(R.string.units_hours_and_minutes), hours, remainingMinutes);
                    } else {
                        return String.format(c.getString(R.string.units_hours_and_minute), hours, remainingMinutes);
                    }
                } else {
                    if (remainingMinutes > 1) {
                        return String.format(c.getString(R.string.units_hour_and_minutes), hours, remainingMinutes);
                    } else {
                        return String.format(c.getString(R.string.units_hour_and_minute), hours, remainingMinutes);
                    }
                }
            } else {
                return String.format(Locale.getDefault(),"%1$d%2$s%3$s", hours, c.getString(R.string.units_h), minutes);
            }
        }
    }

    public static Integer sectionLength(List<Path> paths) {
        int distance = 0;

        for (Path segment : paths) {
            distance += segment.getLength();
        }

        return distance;
    }
}
