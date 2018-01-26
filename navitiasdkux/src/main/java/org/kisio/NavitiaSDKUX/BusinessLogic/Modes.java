package org.kisio.NavitiaSDKUX.BusinessLogic;

import org.kisio.NavitiaSDK.models.LinkSchema;
import org.kisio.NavitiaSDK.models.Section;

import java.util.List;

/**
 * NavitiaSDKUX_android
 *
 * Copyright \u00a9 2018 Kisio. All rights reserved.
 */
public class Modes {
    public static String getModeIcon(Section section) {
        switch (section.getType()) {
            case "public_transport":
                return getPhysicalMode(section).toLowerCase();
            case "transfer":
                return section.getTransferType();
            case "waiting":
                return section.getType();
            case "street_network":
                return getStreetNetworkMode(section).toLowerCase();
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
        if (section.getMode().equals("bike")) {
            if (section.getFrom().getPoi() != null && section.getFrom().getPoi().getProperties().containsKey("network")) {
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
