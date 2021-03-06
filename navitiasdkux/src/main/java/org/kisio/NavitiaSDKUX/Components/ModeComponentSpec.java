package org.kisio.NavitiaSDKUX.Components;

import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropDefault;

import org.kisio.NavitiaSDK.models.Section;
import org.kisio.NavitiaSDKUX.BusinessLogic.Modes;
import org.kisio.NavitiaSDKUX.Components.Primitive.StylizedComponent;
import org.kisio.NavitiaSDKUX.Config.Configuration;

import java.util.HashMap;
import java.util.Map;

@LayoutSpec
public class ModeComponentSpec {
    @PropDefault static final Map<String, Object> styles = new HashMap<>();

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop(optional = true) Map<String, Object> styles,
        @Prop Section section) {

        final Map<String, Object> computedStyles = StylizedComponent.mergeStyles(iconStyles, styles);

        return IconComponent.create(c)
            .name(Modes.getModeIcon(section))
            .styles(computedStyles)
            .buildWithLayout();
    }

    static Map<String, Object> iconStyles = new HashMap<>();
    static {
        iconStyles.put("color", Configuration.colors.getDarkerGray());
    }
}