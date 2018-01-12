package org.kisio.NavitiaSDKUX.BusinessLogic;

import com.google.android.gms.maps.model.LatLng;

import org.kisio.NavitiaSDK.models.Journey;
import org.kisio.NavitiaSDK.models.Section;

import java.util.ArrayList;
import java.util.List;

public class JourneyPathElements {
    private List<LatLng> journeyPolylineCoords = new ArrayList<>();
    private List<SectionPolyline> sectionPolylines = new ArrayList<>();
    private List<LatLng> intermediatePointsCirclesCoords = new ArrayList<>();

    public JourneyPathElements (Journey journey) {
        initJourneyPathElements(journey);
    }

    private void initJourneyPathElements(Journey journey) {
        for (Section section : journey.getSections()) {
            if (section.getGeojson() != null) {
                SectionPolyline sectionPolyline = new SectionPolyline(section);
                sectionPolylines.add(sectionPolyline);

                List<List<Float>> sectionGeoJSONCoordinates = section.getGeojson().getCoordinates();
                intermediatePointsCirclesCoords.add(new LatLng(sectionGeoJSONCoordinates.get(sectionGeoJSONCoordinates.size() - 1).get(1), sectionGeoJSONCoordinates.get(sectionGeoJSONCoordinates.size() - 1).get(0)));
                journeyPolylineCoords.addAll(sectionPolyline.getSectionPathCoordinates());
            }
        }
        if (intermediatePointsCirclesCoords.size() > 0) {
            intermediatePointsCirclesCoords.remove(intermediatePointsCirclesCoords.size() - 1);
        }
    }

    public List<LatLng> getJourneyPolylineCoords() {
        return journeyPolylineCoords;
    }

    public List<SectionPolyline> getSectionPolylines() {
        return sectionPolylines;
    }

    public List<LatLng> getIntermediatePointsCirclesCoords() {
        return intermediatePointsCirclesCoords;
    }
}
