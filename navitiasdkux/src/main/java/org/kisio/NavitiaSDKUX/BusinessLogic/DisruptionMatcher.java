package org.kisio.NavitiaSDKUX.BusinessLogic;

import org.kisio.NavitiaSDK.models.Disruption;

import java.util.List;

public class DisruptionMatcher {
    public static DisruptionLevel getLevel(Disruption disruption) {
        if (disruption.getSeverity() == null) {
            return DisruptionLevel.none;
        }
        if (disruption.getSeverity().getEffect() == null) {
            return DisruptionLevel.none;
        }
        switch (disruption.getSeverity().getEffect()) {
            case "NO_SERVICE":
                return DisruptionLevel.blocking;
            case "REDUCED_SERVICE":
            case "STOP_MOVED":
            case "DETOUR":
            case "SIGNIFICANT_DELAYS":
            case "ADDITIONAL_SERVICE":
            case "MODIFIED_SERVICE":
                return DisruptionLevel.nonblocking;
            case "OTHER_EFFECT":
            case "UNKNOWN_EFFECT":
                return DisruptionLevel.information;
            default:
                return DisruptionLevel.none;
        }
    }

    public static DisruptionLevel getHighestDisruptionLevel(List<Disruption> disruptions) {
        DisruptionLevel highestLevel = DisruptionLevel.none;
        if (disruptions != null && disruptions.size() > 0) {
            for (Disruption disruption : disruptions) {
                if (DisruptionMatcher.getLevel(disruption).ordinal() > highestLevel.ordinal()) {
                    highestLevel = DisruptionMatcher.getLevel(disruption);
                }
            }
        }
        return highestLevel;
    }
}
