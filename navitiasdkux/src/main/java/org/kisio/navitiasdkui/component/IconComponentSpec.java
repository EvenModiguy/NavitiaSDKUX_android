package org.kisio.navitiasdkui.component;

import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropDefault;

import org.kisio.navitiasdkui.component.primitive.StylizedComponent;

import java.util.HashMap;
import java.util.Map;

@LayoutSpec
public class IconComponentSpec {
    @PropDefault static final Map<String, Object> styles = new HashMap<>();
    @PropDefault static final String name = "";

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop(optional = true) Map<String, Object> styles,
        @Prop(optional = true) String name) {

        Map<String, Object> iconStyles = new HashMap<>(iconBaseStyles);
        iconStyles.put("fontFamily", StylizedComponent.getFont(c,"sdk_icons"));
        final Map<String, Object> computedStyles = StylizedComponent.mergeStyles(iconStyles, styles);

        return TextComponent.create(c)
            .styles(computedStyles)
            .text(Icons.fontString(name))
            .buildWithLayout();
    }

    static Map<String, Object> iconBaseStyles = new HashMap<>();
    static {
        iconBaseStyles.put("fontSize", 24);
    }
}
