package org.kisio.NavitiaSDKUX.Components.Journey.Results.LoadingComponentParts;

import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropDefault;

import org.kisio.NavitiaSDKUX.Components.Primitive.BaseViewComponent;
import org.kisio.NavitiaSDKUX.Components.Primitive.StylizedComponent;
import org.kisio.NavitiaSDKUX.Config.Configuration;

import java.util.HashMap;
import java.util.Map;

@LayoutSpec
class ShimPartSpec {
    @PropDefault
    static final Map<String, Object> styles = new HashMap<>();

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop(optional = true) Map<String, Object> styles,
        @Prop(optional = true) Integer width,
        @Prop(optional = true) Integer height) {

        final ComponentLayout.ContainerBuilder builder = BaseViewComponent.create(c);

        Map<String, Object> shimStyles = new HashMap<>(shimBaseStyles);
        if (width != null) {
            shimStyles.put("width", width);
        }
        if (height != null) {
            shimStyles.put("height", height);
        }

        final Map<String, Object> computedStyles = StylizedComponent.mergeStyles(shimStyles, styles);
        final ComponentLayout.Builder styledBuilder = StylizedComponent.applyStyles(builder, computedStyles);
        return styledBuilder.build();
    }

    static Map<String, Object> shimBaseStyles = new HashMap<>();
    static {
        shimBaseStyles.put("backgroundColor", Configuration.colors.getLighterGray());
    }
}
