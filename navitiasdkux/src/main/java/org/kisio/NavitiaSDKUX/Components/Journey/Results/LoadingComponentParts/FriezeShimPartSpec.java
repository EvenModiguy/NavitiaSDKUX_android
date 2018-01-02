package org.kisio.NavitiaSDKUX.Components.Journey.Results.LoadingComponentParts;

import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;

import org.kisio.NavitiaSDKUX.Components.Primitive.StylizedComponent;
import org.kisio.NavitiaSDKUX.Config.Configuration;

import java.util.HashMap;
import java.util.Map;

@LayoutSpec
class FriezeShimPartSpec {
    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop Integer duration) {

        final ShimPart.Builder builder = ShimPart.create(c);

        Map<String, Object> shimStyles = new HashMap<>(shimBaseStyles);
        shimStyles.put("flexGrow", duration);

        final ComponentLayout.Builder styledBuilder = StylizedComponent.applyStyles(builder.withLayout(), shimStyles);
        return styledBuilder.build();
    }

    static Map<String, Object> shimBaseStyles = new HashMap<>();
    static {
        shimBaseStyles.put("height", 45);
        shimBaseStyles.put("marginEnd", Configuration.metrics.margin);
    }
}
