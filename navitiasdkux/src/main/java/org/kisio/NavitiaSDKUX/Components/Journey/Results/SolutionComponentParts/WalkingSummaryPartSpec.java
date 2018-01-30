package org.kisio.NavitiaSDKUX.Components.Journey.Results.SolutionComponentParts;

import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropDefault;

import org.kisio.NavitiaSDKUX.Components.Primitive.HorizontalViewComponent;
import org.kisio.NavitiaSDKUX.Components.Primitive.StylizedComponent;
import org.kisio.NavitiaSDKUX.Components.TextComponent;
import org.kisio.NavitiaSDKUX.R;
import org.kisio.NavitiaSDKUX.Util.Metrics;

import java.util.HashMap;
import java.util.Map;

@LayoutSpec
public class WalkingSummaryPartSpec {
    @PropDefault static final Map<String, Object> styles = new HashMap<>();

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop(optional = true) String testKey,
        @Prop(optional = true) Map<String, Object> styles,
        @Prop Integer duration,
        @Prop Integer distance) {

        final ComponentLayout.ContainerBuilder builder = HorizontalViewComponent.create(c).testKey(testKey);
        builder
            .child(
                TextComponent.create(c)
                    .text(c.getString(R.string.with) + " ")
            )
            .child(
                TextComponent.create(c)
                    .text(Metrics.durationText(c, duration))
                    .styles(durationStyles)
            )
            .child(
                TextComponent.create(c)
                    .text(" " + c.getString(R.string.walking) + " (" + Metrics.distanceText(c, distance) + ")")
            )
        ;
        final ComponentLayout.Builder styledBuilder = StylizedComponent.applyStyles(builder, styles);
        return styledBuilder.build();
    }

    static Map<String, Object> durationStyles = new HashMap<>();
    static {
        durationStyles.put("fontWeight", "bold");
    }
}