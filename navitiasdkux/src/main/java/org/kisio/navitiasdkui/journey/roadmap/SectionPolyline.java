package org.kisio.navitiasdkui.journey.roadmap;

import android.graphics.Color;

import com.google.android.gms.maps.model.LatLng;

import org.kisio.NavitiaSDK.models.Section;
import org.kisio.navitiasdkui.util.Helper;

import java.util.ArrayList;
import java.util.List;

import static org.kisio.navitiasdkui.util.Constant.PUBLIC_TRANSPORT_KEY;
import static org.kisio.navitiasdkui.util.Constant.STREET_NETWORK_KEY;
import static org.kisio.navitiasdkui.util.Constant.TRANSFER_KEY;

/**
 * Represents a section of a polyline drawn on the map.
 */
public class SectionPolyline {
    private List<LatLng> sectionPathCoordinates;
    private String mode;
    private String type;
    private int color;
    private int width;

    public SectionPolyline(Section section) {
        this.sectionPathCoordinates = new ArrayList<>();
        for (List<Float> coordinate : section.getGeojson().getCoordinates()) {
            sectionPathCoordinates.add(new LatLng(coordinate.get(1), coordinate.get(0)));
        }

        this.mode = section.getMode();
        this.type = section.getType();
        if (type.equalsIgnoreCase(PUBLIC_TRANSPORT_KEY)) {
            String lineColor = section.getDisplayInformations().getColor();
            this.color = Helper.Color.getColorFromHexadecimal(lineColor);
            this.width = 25;
        } else {
            this.color = type.equalsIgnoreCase(STREET_NETWORK_KEY) ||
                    type.equalsIgnoreCase(TRANSFER_KEY) ?
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
