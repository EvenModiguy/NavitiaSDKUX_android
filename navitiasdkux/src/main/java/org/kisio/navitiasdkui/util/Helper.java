package org.kisio.navitiasdkui.util;

import android.content.Context;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.kisio.NavitiaSDK.models.Path;
import org.kisio.NavitiaSDK.models.Section;
import org.kisio.navitiasdkui.R;

import java.util.List;
import java.util.Locale;

public abstract class Helper {

    public static class Duration {
        private String value;
        private boolean isLessThanAnHour;

        public Duration(String value, boolean isLessThanAnHour) {
            this.value = value;
            this.isLessThanAnHour = isLessThanAnHour;
        }

        public String getValue() {
            return value;
        }

        public boolean isLessThanAnHour() {
            return isLessThanAnHour;
        }
    }

    public static String formatTime(String time) {
        DateTime dateTime = DateTime.parse(time, DateTimeFormat.forPattern("YYYYMMdd'T'HHmmss"));
        String hours = dateTime.hourOfDay().get() < 10 ? String.format(Locale.getDefault(), "0%d", dateTime.hourOfDay().get()) : dateTime.hourOfDay().getAsText();
        String minutes = dateTime.minuteOfHour().get() < 10 ? String.format(Locale.getDefault(), "0%d", dateTime.minuteOfHour().get()) : dateTime.minuteOfHour().getAsText();

        return String.format("%1$s:%2$s", hours, minutes);
    }

    public static String formatTravelTime(String startTime, String endTime) {
        return String.format("%1$s - %2$s", startTime, endTime);
    }

    public static Duration formatTravelDuration(Context context, Integer seconds) {
        if (seconds >= 3600) {
            return new Duration(formatDuration(context, seconds, false), false);
        } else {
            return new Duration(String.valueOf((int) Math.ceil(seconds / 60)), true);
        }
    }

    public static String[] formatWalkInfo(Context context, List<Section> sections, Integer distance) {
        return new String[] {
                context.getResources().getString(R.string.with),
                formatDuration(context, formatWalkingDistance(sections), false), // bold
                String.format(" %1$s (%2$s)", context.getString(R.string.walking), formatDistance(context, distance))
        };
    }

    public static String formatDuration(Context context, Integer seconds, Boolean useFullFormat) {
        if (seconds < 60) {
            return String.format("%1$s %2$s", context.getString(R.string.less_than_a), context.getString(R.string.units_minute));
        } else if (seconds < 120) {
            return String.format("1 %s", context.getString(R.string.units_minute));
        } else if (seconds < 3600) {
            Integer minutes = seconds / 60;
            return String.format(Locale.getDefault(), "%d %s", minutes, context.getString(R.string.units_minutes));
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
                        return String.format(context.getString(R.string.units_hours_and_minutes), hours, remainingMinutes);
                    } else {
                        return String.format(context.getString(R.string.units_hours_and_minute), hours, remainingMinutes);
                    }
                } else {
                    if (remainingMinutes > 1) {
                        return String.format(context.getString(R.string.units_hour_and_minutes), hours, remainingMinutes);
                    } else {
                        return String.format(context.getString(R.string.units_hour_and_minute), hours, remainingMinutes);
                    }
                }
            } else {
                return String.format(Locale.getDefault(),"%1$d%2$s%3$s", hours, context.getString(R.string.units_h), minutes);
            }
        }
    }

    public static String formatDistance(Context context, Integer meters) {
        if (meters < 1000) {
            return String.format(Locale.getDefault(),"%d %s", meters, context.getString(R.string.units_meters));
        } else {
            return String.format(Locale.getDefault(),"%.1f %s", meters / 1000.f, context.getString(R.string.units_km));
        }
    }


    public static Integer formatWalkingDistance(List<Section> sections) {
        int distance = 0;
        for (Section section : sections) {
            if (section.getType().equals("street_network") && section.getMode().equals("walking")) {
                distance += sectionLength(section.getPath());
            }
        }
        return distance;
    }

    private static int sectionLength(List<Path> paths) {
        int distance = 0;

        for (Path segment : paths) {
            distance += segment.getLength();
        }

        return distance;
    }
}
