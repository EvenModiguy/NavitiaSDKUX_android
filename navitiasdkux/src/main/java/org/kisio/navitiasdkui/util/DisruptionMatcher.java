package org.kisio.navitiasdkui.util;

import android.content.res.Resources;

import org.kisio.NavitiaSDK.models.Disruption;
import org.kisio.navitiasdkui.R;

import java.util.List;

public class DisruptionMatcher {
    public static int getLevel(Resources resources, Disruption disruption) {
        if (disruption.getSeverity() == null) {
            return resources.getColor(R.color.disruptionDefault);
        }
        if (disruption.getSeverity().getEffect() == null) {
            return resources.getColor(R.color.disruptionDefault);
        }
        switch (disruption.getSeverity().getEffect()) {
            case "NO_SERVICE":
                return resources.getColor(R.color.disruptionBlocking);
            case "REDUCED_SERVICE":
            case "STOP_MOVED":
            case "DETOUR":
            case "SIGNIFICANT_DELAYS":
            case "ADDITIONAL_SERVICE":
            case "MODIFIED_SERVICE":
                return resources.getColor(R.color.disruptionNonNlocking);
            case "OTHER_EFFECT":
            case "UNKNOWN_EFFECT":
                return resources.getColor(R.color.disruptionInformation);
            default:
                return resources.getColor(R.color.disruptionDefault);
        }
    }

    /*public static DisruptionLevel getHighestDisruptionLevel(List<Disruption> disruptions) {
        DisruptionLevel highestLevel = DisruptionLevel.none;
        if (disruptions != null && disruptions.size() > 0) {
            for (Disruption disruption : disruptions) {
                if (DisruptionMatcher.getLevel(disruption).ordinal() > highestLevel.ordinal()) {
                    highestLevel = DisruptionMatcher.getLevel(disruption);
                }
            }
        }
        return highestLevel;
    }*/
}
