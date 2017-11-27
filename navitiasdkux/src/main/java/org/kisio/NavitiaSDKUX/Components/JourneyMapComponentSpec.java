package org.kisio.NavitiaSDKUX.Components;

import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropDefault;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;

import org.kisio.NavitiaSDK.models.Journey;
import org.kisio.NavitiaSDK.models.Place;
import org.kisio.NavitiaSDK.models.Section;
import org.kisio.NavitiaSDK.models.SectionGeoJsonSchema;
import org.kisio.NavitiaSDK.models.StopDateTime;
import org.kisio.NavitiaSDKUX.Util.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@LayoutSpec
public class JourneyMapComponentSpec {
    @PropDefault
    static final Map<String, Object> styles = new HashMap<>();

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop Journey journey,
        @Prop(optional = true) Map<String, Object> styles) {

        return MapComponent.create(c)
            .styles(styles)
            .bounds(getBoundsFromSections(journey.getSections()))
            .polylines(getPolylinesFromSections(journey.getSections()))
            .buildWithLayout();
    }

    private static LatLngBounds getBoundsFromSections(List<Section> sections) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(getLatLngFromPlace(sections.get(0).getFrom()));
        for (Section section : sections) {
            builder.include(getLatLngFromPlace(section.getTo()));
        }

        return builder.build();
    }

    private static LatLng getLatLngFromPlace(Place place) {
        if (place != null) {
            switch (place.getEmbeddedType()) {
                case "stop_point":
                    return new LatLng(
                        Double.valueOf(place.getStopPoint().getCoord().getLat()),
                        Double.valueOf(place.getStopPoint().getCoord().getLon())
                    );
                case "stop_area":
                    return new LatLng(
                        Double.valueOf(place.getStopArea().getCoord().getLat()),
                        Double.valueOf(place.getStopArea().getCoord().getLon())
                    );
                case "address":
                    return new LatLng(
                        Double.valueOf(place.getAddress().getCoord().getLat()),
                        Double.valueOf(place.getAddress().getCoord().getLon())
                    );
                case "administrative_region":
                    return new LatLng(
                        Double.valueOf(place.getAdministrativeRegion().getCoord().getLat()),
                        Double.valueOf(place.getAdministrativeRegion().getCoord().getLon())
                    );
                case "poi":
                    return new LatLng(
                        Double.valueOf(place.getPoi().getCoord().getLat()),
                        Double.valueOf(place.getPoi().getCoord().getLon())
                    );
            }
        }
        return new LatLng(0, 0);
    }

    private static List<PolylineOptions> getPolylinesFromSections(List<Section> sections) {
        List<PolylineOptions> polylines = new ArrayList<>();
        for (Section section : sections) {
            PolylineOptions options = new PolylineOptions();

            // Line style
            if (section.getType().equals("street_network")) {
                List<PatternItem> patterns = new ArrayList<>();
                patterns.add(new Dash(18));
                patterns.add(new Gap(12));
                options.pattern(patterns);
            }
            options.startCap(new RoundCap());
            options.endCap(new RoundCap());
            options.width(12);

            // Line color
            options.color(getColorFromSection(section));

            // Points
            List<LatLng> points = getPolylinePointsFromSection(section);
            for (LatLng point : points) {
                options.add(point);
            }

            polylines.add(options);
        }
        return polylines;
    }

    private static List<LatLng> getPolylinePointsFromSection(Section section) {
        if (section.getType().equals("public_transport")) {
            return getPolylinePointsFromPublicTransport(section.getStopDateTimes());
        } else {
            return getPolylinePointsFromStreetNetwork(section.getGeojson());
        }
    }

    private static List<LatLng> getPolylinePointsFromStreetNetwork(SectionGeoJsonSchema geoJsonSchema) {
        List<LatLng> points = new ArrayList<>();
        List<List<Float>> coords = geoJsonSchema.getCoordinates();
        for (List<Float> coord : coords) {
            points.add(new LatLng(coord.get(1), coord.get(0)));
        }
        return points;
    }

    private static List<LatLng> getPolylinePointsFromPublicTransport(List<StopDateTime> stopDateTimes) {
        List<LatLng> points = new ArrayList<>();
        for (StopDateTime stopDateTime : stopDateTimes) {
            points.add(new LatLng(
                Double.valueOf(stopDateTime.getStopPoint().getCoord().getLat()),
                Double.valueOf(stopDateTime.getStopPoint().getCoord().getLon())
            ));
        }
        return points;
    }

    private static int getColorFromSection(Section section) {
        if (section.getType().equals("public_transport")) {
            return Color.getColorFromHexadecimal(section.getDisplayInformations().getColor());
        }
        return Color.getColorFromHexadecimal("888888");
    }
}
