package org.kisio.NavitiaSDKUX.Components;

import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropDefault;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.kisio.NavitiaSDKUX.Components.Primitive.BaseMapComponent;
import org.kisio.NavitiaSDKUX.Components.Primitive.StylizedComponent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@LayoutSpec
public class MapComponentSpec {
    @PropDefault static final Map<String, Object> styles = new HashMap<>();
    @PropDefault static final Integer zoom = 15;
    @PropDefault static final LatLng center = new LatLng(0, 0);

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop(optional = true) Map<String, Object> styles,
        @Prop(optional = true) final LatLng center,
        @Prop(optional = true) final Integer zoom,
        @Prop(optional = true) final LatLngBounds bounds,
        @Prop(optional = true) final List<PolylineOptions> polylines,
        @Prop(optional = true) final List<MarkerOptions> markers) {

        final BaseMapComponent.Builder builder = BaseMapComponent.create(c)
            .center(center)
            .zoom(zoom)
            .bounds(bounds)
            .polylines(polylines)
            .markers(markers);

        final ComponentLayout.Builder styledBuilder = StylizedComponent.applyStyles(builder, styles);
        return styledBuilder.build();
    }
}
