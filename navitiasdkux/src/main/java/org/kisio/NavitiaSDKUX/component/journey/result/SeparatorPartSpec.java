package org.kisio.NavitiaSDKUX.component.journey.result;

import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropDefault;

import org.kisio.NavitiaSDKUX.component.primitive.BaseViewComponent;
import org.kisio.NavitiaSDKUX.component.primitive.StylizedComponent;
import org.kisio.NavitiaSDKUX.config.Configuration;

import java.util.HashMap;
import java.util.Map;

@LayoutSpec
public class SeparatorPartSpec {
    @PropDefault static final Map<String, Object> styles = new HashMap<>();

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop(optional = true) Map<String, Object> styles) {

        final ComponentLayout.ContainerBuilder builder = BaseViewComponent.create(c);
        final Map<String, Object> computedStyles = StylizedComponent.mergeStyles(lineStyles, styles);
        final ComponentLayout.Builder styledBuilder = StylizedComponent.applyStyles(builder, computedStyles);
        return styledBuilder.build();
    }

    static Map<String, Object> lineStyles = new HashMap<>();
    static {
        lineStyles.put("heightPx", 1);
        lineStyles.put("backgroundColor", Configuration.colors.getSecondary());
    }
}
