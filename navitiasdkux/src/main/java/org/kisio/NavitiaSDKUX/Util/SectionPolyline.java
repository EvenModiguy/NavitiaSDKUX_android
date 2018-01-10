package org.kisio.NavitiaSDKUX.Util;


import com.google.android.gms.maps.model.LatLng;

import org.kisio.NavitiaSDK.models.Section;

import java.util.List;

/**
 * Represents a section of a polyline drawn on the map.
 */
public class SectionPolyline {

    public static final String MODE_WALKING = "walking";
    public static final String MODE_BIKE = "bike";
    public static final String MODE_CAR = "car";
    public static final String TYPE_PUBLIC_TRANSPORT = "public_transport";

    private List<LatLng> sectionPathCoordinates;
    private String mode;
    private String type;
    private String lineColor;

    public SectionPolyline(List<LatLng> sectionPathCoordinates, Section section) {
        this.sectionPathCoordinates = sectionPathCoordinates;
        this.mode = section.getMode();
        this.type = section.getType();
        this.lineColor = section.getDisplayInformations() != null ?
                section.getDisplayInformations().getColor() : null;
    }

    public List<LatLng> getSectionPathCoordinates() {
        return sectionPathCoordinates;
    }

    public String getMode() {
        return mode;
    }

    public String getType() {
        return type;
    }

    public String getLineColor() {
        return lineColor;
    }
}
