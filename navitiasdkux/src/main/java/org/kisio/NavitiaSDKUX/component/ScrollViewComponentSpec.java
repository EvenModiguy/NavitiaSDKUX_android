package org.kisio.NavitiaSDKUX.component;

import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropDefault;
import com.facebook.litho.widget.VerticalScroll;

import org.kisio.NavitiaSDKUX.component.primitive.StylizedComponent;

import java.util.HashMap;
import java.util.Map;

@LayoutSpec
public class ScrollViewComponentSpec {
    @PropDefault static final Map<String, Object> styles = new HashMap<>();

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop(optional = true) Map<String, Object> styles,
        @Prop Component<?> child) {

        final VerticalScroll.Builder builder = VerticalScroll.create(c);
        builder.childComponent(child);
        final Map<String, Object> computedStyles = StylizedComponent.mergeStyles(scrollViewStyles, styles);
        final ComponentLayout.Builder styledBuilder = StylizedComponent.applyStyles(builder.withLayout(), computedStyles);
        return styledBuilder.build();
    }

    static Map<String, Object> scrollViewStyles = new HashMap<>();
    static {
        scrollViewStyles.put("flexGrow", 1);
    }
}