package org.kisio.navitiasdkui.component;

import android.graphics.Color;

import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropDefault;

import org.kisio.navitiasdkui.component.primitive.BaseViewComponent;
import org.kisio.navitiasdkui.component.primitive.StylizedComponent;
import org.kisio.navitiasdkui.config.Configuration;

import java.util.HashMap;
import java.util.Map;

@LayoutSpec
public class FormComponentSpec {
    @PropDefault static final Map<String, Object> styles = new HashMap<>();

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop(optional = true) String testKey,
        @Prop(optional = true) Map<String, Object> styles,
        @Prop Component<?>[] children) {

        final ComponentLayout.ContainerBuilder builder = BaseViewComponent.create(c).testKey(testKey);
        for (Component<?> child : children) {
            builder.child(child);
        }
        final Map<String, Object> computedStyles = StylizedComponent.mergeStyles(formStyles, styles);
        final ComponentLayout.Builder styledBuilder = StylizedComponent.applyStyles(builder, computedStyles);
        return styledBuilder.build();
    }

    static Map<String, Object> formStyles = new HashMap<>();
    static {
        formStyles.put("backgroundColor", Color.WHITE);
        formStyles.put("borderRadius", Configuration.metrics.radius);
    }
}
