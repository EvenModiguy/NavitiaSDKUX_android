package org.kisio.navitiasdkui.util;

import android.content.Context;
import android.text.TextUtils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.kisio.NavitiaSDK.models.LinkSchema;
import org.kisio.NavitiaSDK.models.Path;
import org.kisio.NavitiaSDK.models.Section;
import org.kisio.navitiasdkui.R;

import java.util.List;
import java.util.Locale;

import static org.kisio.navitiasdkui.util.Constant.BIKE_KEY;
import static org.kisio.navitiasdkui.util.Constant.NETWORK_KEY;
import static org.kisio.navitiasdkui.util.Constant.PUBLIC_TRANSPORT_KEY;
import static org.kisio.navitiasdkui.util.Constant.STREET_NETWORK_KEY;
import static org.kisio.navitiasdkui.util.Constant.TRANSFER_KEY;
import static org.kisio.navitiasdkui.util.Constant.WAITING_KEY;

public abstract class Helper {

    public static boolean arrayIsEmpty(List list) {
        return list != null && list.size() > 0;
    }

    public static String formatTime(String time) {
        DateTime dateTime = DateTime.parse(time, DateTimeFormat.forPattern("YYYYMMdd'T'HHmmss"));
        String hours = dateTime.hourOfDay().get() < 10 ? String.format(Locale.getDefault(), "0%d", dateTime.hourOfDay().get()) : dateTime.hourOfDay().getAsText();
        String minutes = dateTime.minuteOfHour().get() < 10 ? String.format(Locale.getDefault(), "0%d", dateTime.minuteOfHour().get()) : dateTime.minuteOfHour().getAsText();

        return String.format("%1$s:%2$s", hours, minutes);
    }

    public static String formatTravelTime(String startTime, String endTime) {
        return String.format("%1$s - %2$s", formatTime(startTime), formatTime(endTime));
    }

    public static String formatTravelDuration(Context context, Integer seconds) {
        if (seconds >= 3600) {
            return formatDuration(context, seconds, false);
        } else {
            return formatDuration(context, seconds, false, true);
        }
    }

    public static String[] formatWalkInfo(Context context, List<Section> sections, Integer distance) {
        return new String[] {
                context.getResources().getString(R.string.with),
                formatDuration(context, formatWalkingDistance(sections), false), // bold
                String.format(" %1$s (%2$s)", context.getString(R.string.walking), formatDistance(context, distance))
        };
    }

    public static String formatDuration(Context context, Integer seconds, boolean useFullFormat) {
        return formatDuration(context, seconds, useFullFormat, false);
    }

    public static String formatDuration(Context context, Integer seconds, boolean useFullFormat, boolean shortMinute) {
        if (seconds < 60) {
            return String.format("%1$s %2$s", context.getString(R.string.less_than_a), context.getString(R.string.units_minute));
        } else if (seconds < 120) {
            return String.format("1 %s", context.getString(R.string.units_minute));
        } else if (seconds < 3600) {
            int minutes = seconds / 60;
            String minuteLabel = shortMinute ? context.getString(R.string.units_minutes_short) : context.getString(R.string.units_minutes);
            return String.format(Locale.getDefault(), "%d %s", minutes, minuteLabel);
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

    public static class Color {
        public static Integer getColorFromHexadecimal(String hex) {
            return !TextUtils.isEmpty(hex) ? android.graphics.Color.parseColor("#" + hex) : android.graphics.Color.GRAY;
        }

        public static Integer getLineCodeTextColor(Context context, String hexTextColor, String hexLineColor) {
            if (TextUtils.isEmpty(hexLineColor)) {
                hexLineColor = "#000000";
            }

            if (TextUtils.isEmpty(hexTextColor)) {
                return contrastColor(context, android.graphics.Color.parseColor("#" + hexLineColor));
            } else {
                return android.graphics.Color.parseColor("#" + hexTextColor);
            }
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
        public static int contrastColor(Context context, int color) {
            int brightColor = context.getResources().getColor(R.color.brightText);
            int darkColor = context.getResources().getColor(R.color.darkText);

            double colorBrightness = brightness(color);
            double darkColorBrightness = brightness(darkColor);
            double darkColorDifference = difference(color, darkColor);
            double darkColorBrightnessDifference = Math.max(colorBrightness, darkColorBrightness) - Math.min(colorBrightness, darkColorBrightness);
            double brightColorBrightness = brightness(brightColor);
            double brightColorDifference = difference(color, brightColor);
            double brightColorBrightnessDifference = Math.max(colorBrightness, brightColorBrightness) - Math.min(colorBrightness, brightColorBrightness);

            int brightColorPonderation = 0;
            int darkColorPonderation = 0;

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

    public static class Mode {
        public static String iconString(Context context, String name) {
            if (name.equalsIgnoreCase("walking")) {
                return context.getResources().getString(context.getResources().getIdentifier("walking_logo", "string", context.getPackageName()));
            } else if (context.getResources().getIdentifier(name, "string", context.getPackageName()) == 0) {
                return "\uffff";
            } else {
                return context.getResources().getString(context.getResources().getIdentifier(name, "string", context.getPackageName()));
            }
        }

        public static String getModeIcon(Section section) {
            switch (section.getType()) {
                case PUBLIC_TRANSPORT_KEY:
                    return getPhysicalMode(section).toLowerCase();
                case STREET_NETWORK_KEY:
                    return getStreetNetworkMode(section).toLowerCase();
                case TRANSFER_KEY:
                    return section.getTransferType();
                case WAITING_KEY:
                    return section.getType();
                default:
                    return section.getMode();
            }
        }

        public static String getPhysicalMode(Section section) {
            final String id = getPhysicalModeId(section.getLinks());
            final String[] modeData = id.split(":");
            return (modeData.length > 1) ? modeData[1] : "";
        }

        private static String getStreetNetworkMode(Section section) {
            if (section.getMode().equals(BIKE_KEY)) {
                if (section.getFrom().getPoi() != null && section.getFrom().getPoi().getProperties().containsKey(NETWORK_KEY)) {
                    return "bss";
                }
            }

            return section.getMode();
        }

        private static String getPhysicalModeId(List<LinkSchema> links) {
            for (LinkSchema link : links) {
                if (link.getType().equals("physical_mode")) {
                    return link.getId();
                }
            }
            return "<not_found>";
        }
    }

}
