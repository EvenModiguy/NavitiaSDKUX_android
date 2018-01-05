package org.kisio.NavitiaSDKUX.Components.Journey.Roadmap;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;

import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.LithoView;
import com.facebook.litho.Size;
import com.facebook.litho.SizeSpec;
import com.facebook.litho.annotations.MountSpec;
import com.facebook.litho.annotations.OnBind;
import com.facebook.litho.annotations.OnCreateMountContent;
import com.facebook.litho.annotations.OnMeasure;
import com.facebook.litho.annotations.Prop;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.kisio.NavitiaSDK.models.Journey;
import org.kisio.NavitiaSDK.models.Section;
import org.kisio.NavitiaSDKUX.Components.Journey.Roadmap.JourneyMapViewComponentParts.PlaceMarkerComponent;
import org.kisio.NavitiaSDKUX.Config.Configuration;
import org.kisio.NavitiaSDKUX.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by rabidi on 03/01/2018.
 */

@MountSpec
public class JourneyMapViewComponentSpec {
    private static List<Circle> intermediatePointsCircles = new ArrayList<>();

    @OnCreateMountContent
    static MapView onCreateMountContent(ComponentContext c) {
        return new MapView(c);
    }

    @OnMeasure
    static void onMeasure(
            ComponentContext context,
            ComponentLayout layout,
            int widthSpec,
            int heightSpec,
            Size size) {
        size.width = SizeSpec.getSize(widthSpec);
        size.height = (int) (0.4 * SizeSpec.getSize(heightSpec));
    }

    @OnBind
    static void onBind(
            final ComponentContext context,
            final MapView mapView,
            @Prop Bundle savedInstanceState,
            @Prop final Journey journey) {
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @SuppressWarnings("unchecked")
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                mapView.onResume();
                googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                    @Override
                    public void onCameraIdle() {
                        redrawIntermediatePointCircles(googleMap);
                    }
                });
                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        return true;
                    }
                });

                HashMap<String, Object> journeyPathElements = getJourneyPathElements(journey);
                List<LatLng> journeyPolylineCoords = (List<LatLng>) journeyPathElements.get("journey_polyline_coords");
                List<List<LatLng>> sectionsPolylinesCoords = (List<List<LatLng>>) journeyPathElements.get("sections_polylines_coords");
                List<LatLng> intermediatePointsCirclesCoords = (List<LatLng>) journeyPathElements.get("intermediate_points_circles_coords");

                for (List<LatLng> sectionPolylineCoords : sectionsPolylinesCoords) {
                    PolylineOptions polylineOptions = new PolylineOptions().width(15).color(Color.BLACK).zIndex(1);
                    polylineOptions.addAll(sectionPolylineCoords);
                    googleMap.addPolyline(polylineOptions);
                }

                intermediatePointsCircles.clear();
                for (LatLng centerCoord : intermediatePointsCirclesCoords) {
                    CircleOptions circleOptions = new CircleOptions().center(centerCoord)
                            .strokeColor(Color.BLACK)
                            .strokeWidth(8)
                            .fillColor(Color.WHITE)
                            .radius(getCircleRadiusDependingOnCurrentCameraZoom(googleMap.getCameraPosition().zoom))
                            .zIndex(2);
                    intermediatePointsCircles.add(googleMap.addCircle(circleOptions));
                }

                MarkerOptions departureMarkerOptions = new MarkerOptions()
                        .position(getJourneyDepartureCoordinates(journey))
                        .icon(BitmapDescriptorFactory.fromBitmap(getPlaceMarkerIcon(context, context.getString(R.string.component_JourneyMapViewComponent_departure), Configuration.colors.getOrigin())));
                googleMap.addMarker(departureMarkerOptions);
                MarkerOptions arrivalMarkerOptions = new MarkerOptions()
                        .position(getJourneyArrivalCoordinates(journey))
                        .icon(BitmapDescriptorFactory.fromBitmap(getPlaceMarkerIcon(context, context.getString(R.string.component_JourneyMapViewComponent_arrival), Configuration.colors.getDestination())));
                googleMap.addMarker(arrivalMarkerOptions);

                zoomToPolyline(googleMap, journeyPolylineCoords, false);
            }
        });
    }

    private static HashMap<String, Object> getJourneyPathElements(Journey journey) {
        List<LatLng> journeyPolylineCoords = new ArrayList<>();
        List<List<LatLng>> sectionsPolylinesCoords = new ArrayList<>();
        List<LatLng> intermediatePointsCirclesCoords = new ArrayList<>();
        for (Section section : journey.getSections()) {
            if (section.getGeojson() != null) {
                List<List<Float>> sectionGeoJSONCoordinates = section.getGeojson().getCoordinates();
                List<LatLng> sectionPathCoordinates = new ArrayList<>();
                for (List<Float> coordinate : sectionGeoJSONCoordinates) {
                    sectionPathCoordinates.add(new LatLng(coordinate.get(1), coordinate.get(0)));
                }
                sectionsPolylinesCoords.add(sectionPathCoordinates);
                intermediatePointsCirclesCoords.add(new LatLng(sectionGeoJSONCoordinates.get(sectionGeoJSONCoordinates.size() - 1).get(1), sectionGeoJSONCoordinates.get(sectionGeoJSONCoordinates.size() - 1).get(0)));
                journeyPolylineCoords.addAll(sectionPathCoordinates);
            }
        }
        if (intermediatePointsCirclesCoords.size() > 0) {
            intermediatePointsCirclesCoords.remove(intermediatePointsCirclesCoords.size() - 1);
        }

        HashMap<String, Object> journeyPathElements = new HashMap<>();
        journeyPathElements.put("journey_polyline_coords", journeyPolylineCoords);
        journeyPathElements.put("sections_polylines_coords", sectionsPolylinesCoords);
        journeyPathElements.put("intermediate_points_circles_coords", intermediatePointsCirclesCoords);
        return journeyPathElements;
    }

    private static LatLng getJourneyDepartureCoordinates(Journey journey) {
        List<List<Float>> firstSectionCoordinates = journey.getSections().get(0).getGeojson().getCoordinates();
        return new LatLng(firstSectionCoordinates.get(0).get(1), firstSectionCoordinates.get(0).get(0));
    }

    private static LatLng getJourneyArrivalCoordinates(Journey journey) {
        List<List<Float>> lastSectionCoordinates = journey.getSections().get(journey.getSections().size() - 1).getGeojson().getCoordinates();
        return new LatLng(lastSectionCoordinates.get(lastSectionCoordinates.size() - 1).get(1), lastSectionCoordinates.get(lastSectionCoordinates.size() - 1).get(0));
    }

    private static void zoomToPolyline(GoogleMap googleMap, List<LatLng> polylineCoords, boolean animated) {
        int mapPadding = 180;
        LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
        for (LatLng latLngPoint : polylineCoords) {
            boundsBuilder.include(latLngPoint);
        }
        LatLngBounds latLngBounds = boundsBuilder.build();

        if (animated) {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, mapPadding));
        }
        else {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, mapPadding));
        }
    }

    private static double getCircleRadiusDependingOnCurrentCameraZoom(float cameraZoom) {
        double altitudeReferenceValue = 10000;
        int circleRadiusReferenceValue = 180;
        return getCameraAltitude(cameraZoom)/altitudeReferenceValue * circleRadiusReferenceValue;
    }

    private static void redrawIntermediatePointCircles(GoogleMap googleMap) {
        List<CircleOptions> updatedIntermediatePointsCircles = new ArrayList<>();
        for (Circle circle : intermediatePointsCircles) {
            circle.remove();
            updatedIntermediatePointsCircles.add(new CircleOptions().center(circle.getCenter())
                    .strokeColor(Color.BLACK)
                    .strokeWidth(8)
                    .fillColor(Color.WHITE)
                    .radius(getCircleRadiusDependingOnCurrentCameraZoom(googleMap.getCameraPosition().zoom))
                    .zIndex(2));
        }

        intermediatePointsCircles.clear();
        for (CircleOptions circleOptions : updatedIntermediatePointsCircles) {
            intermediatePointsCircles.add(googleMap.addCircle(circleOptions));
        }
    }

    private static float getCameraAltitude(float mapZoom) {
        return (float)(.05 * ((591657550.5 / (Math.pow(2, mapZoom - 1))) / 2)) * ((float)(Math.cos(Math.toRadians(85.362 / 2))) / (float)(Math.sin(Math.toRadians(85.362 / 2))));
    }

    private static Bitmap getPlaceMarkerIcon(ComponentContext context, String markerTitle, Integer markerColor) {
        LithoView placeMarkerView = LithoView.create(context, PlaceMarkerComponent.create(context).markerTitle(markerTitle).markerColor(markerColor).build());
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context.getBaseContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        placeMarkerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        placeMarkerView.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        placeMarkerView.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        placeMarkerView.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(placeMarkerView.getMeasuredWidth(), placeMarkerView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        placeMarkerView.draw(canvas);
        return bitmap;
    }
}
