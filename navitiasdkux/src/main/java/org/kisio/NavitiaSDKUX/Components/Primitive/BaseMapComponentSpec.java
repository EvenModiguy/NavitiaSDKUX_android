package org.kisio.NavitiaSDKUX.Components.Primitive;

import android.os.Bundle;

import com.facebook.litho.ComponentContext;
import com.facebook.litho.annotations.MountSpec;
import com.facebook.litho.annotations.OnCreateMountContent;
import com.facebook.litho.annotations.OnMount;
import com.facebook.litho.annotations.Prop;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;
import java.util.Map;

@MountSpec
public class BaseMapComponentSpec {
    private static GoogleMap map;

    @OnCreateMountContent
    static MapView onCreateMountContent(ComponentContext c) {
        return new MapView(c);
    }

    @OnMount
    static void onMount(
        final ComponentContext c,
        final MapView mapView,
        @Prop final LatLng center,
        @Prop final Integer zoom,
        @Prop final LatLngBounds bounds,
        @Prop final List<PolylineOptions> polylines,
        @Prop final List<MarkerOptions> markers) {

        mapView.onCreate(new Bundle());
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                mapReady(c);
                initCenterAndZoom(center, zoom);
                initBounds(bounds);
                initPolylines(polylines);
                initMakers(markers);
            }
        });
        mapView.onResume();
    }

    static void mapReady(ComponentContext c) {
        try {
            MapsInitializer.initialize(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void initCenterAndZoom(LatLng center, Integer zoom) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(center, zoom);
        map.moveCamera(cameraUpdate);
    }

    static void initBounds(LatLngBounds bounds) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 50);
        map.moveCamera(cameraUpdate);
    }

    static void initPolylines(List<PolylineOptions> polylines) {
        if (polylines != null) {
            for (PolylineOptions polyline : polylines) {
                map.addPolyline(polyline);
            }
        }
    }

    static void initMakers(List<MarkerOptions> markers) {
        if (markers != null) {
            for (MarkerOptions marker : markers) {
                map.addMarker(marker);
            }
        }
    }
}
