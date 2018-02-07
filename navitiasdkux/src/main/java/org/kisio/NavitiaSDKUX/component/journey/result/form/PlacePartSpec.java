package org.kisio.NavitiaSDKUX.component.journey.result.form;

import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropDefault;

import org.kisio.NavitiaSDKUX.component.primitive.StylizedComponent;
import org.kisio.NavitiaSDKUX.component.TextComponent;
import org.kisio.NavitiaSDKUX.config.Configuration;

import java.util.HashMap;
import java.util.Map;

@LayoutSpec
public class PlacePartSpec {
    @PropDefault
    static final Map<String, Object> styles = new HashMap<>();
    @PropDefault static final String name = "";

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop(optional = true) Map<String, Object> styles,
        @Prop(optional = true) String name) {
        final Map<String, Object> computedStyles = StylizedComponent.mergeStyles(nameStyles, styles);

        return TextComponent.create(c)
            .text(name)
            .styles(computedStyles)
            .buildWithLayout();
    }

    static Map<String, Object> nameStyles = new HashMap<>();
    static {
        nameStyles.put("fontWeight", "bold");
        nameStyles.put("marginEnd", Configuration.metrics.marginS);
    }
}