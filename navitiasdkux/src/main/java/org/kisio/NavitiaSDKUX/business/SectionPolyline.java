package org.kisio.NavitiaSDKUX.business;

import android.graphics.Color;

import com.google.android.gms.maps.model.LatLng;

import org.kisio.NavitiaSDK.models.Section;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a section of a polyline drawn on the map.
 */
public class SectionPolyline {
    public static final String MODE_WALKING = "walking";
    public static final String TYPE_STREET_NETWORK = "street_network";
    private static final String TYPE_TRANSFER = "transfer";
    private static final String TYPE_PUBLIC_TRANSPORT = "public_transport";

    private List<LatLng> sectionPathCoordinates;
    private String mode;
    private String type;
    private int color;
    private int width;

    SectionPolyline(Section section) {
        this.sectionPathCoordinates = new ArrayList<>();
        for (List<Float> coordinate : section.getGeojson().getCoordinates()) {
            sectionPathCoordinates.add(new LatLng(coordinate.get(1), coordinate.get(0)));
        }

        this.mode = section.getMode();
        this.type = section.getType();
        if (type.equalsIgnoreCase(SectionPolyline.TYPE_PUBLIC_TRANSPORT)) {
            String lineColor = section.getDisplayInformations().getColor();
            this.color = org.kisio.NavitiaSDKUX.util.Color.getColorFromHexadecimal(lineColor);
            this.width = 25;
        } else {
            this.color = type.equalsIgnoreCase(SectionPolyline.TYPE_STREET_NETWORK) ||
                    type.equalsIgnoreCase(SectionPolyline.TYPE_TRANSFER)?
                    Color.GRAY : Color.BLACK;
            this.width = 15;
        }
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

    public int getColor() {
        return color;
    }

    public int getWidth() {
        return width;
    }
}
