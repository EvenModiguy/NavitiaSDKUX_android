package org.kisio.NavitiaSDKUX.Components.Journey.Roadmap;

import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropDefault;

import org.kisio.NavitiaSDKUX.Components.Primitive.BaseViewComponent;
import org.kisio.NavitiaSDKUX.Components.Primitive.HorizontalViewComponent;
import org.kisio.NavitiaSDKUX.Components.Primitive.StylizedComponent;

import java.util.HashMap;
import java.util.Map;

@LayoutSpec
public class Roadmap3ColumnsLayoutSpec {
    @PropDefault static final Map<String, Object> styles = new HashMap<>();

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop(optional = true) String testKey,
        @Prop(optional = true) Map<String, Object> styles,
        @Prop(optional = true) Component<?>[] leftChildren,
        @Prop(optional = true) Component<?>[] middleChildren,
        @Prop(optional = true) Component<?>[] rightChildren) {
        final ComponentLayout.ContainerBuilder builder = HorizontalViewComponent.create(c).testKey(testKey);

        final ComponentLayout.ContainerBuilder builderLeftColumn = BaseViewComponent.create(c);
        if (leftChildren != null) {
            for (Component<?> child : leftChildren) {
                builderLeftColumn.child(child);
            }
        }
        builder.child(StylizedComponent.applyStyles(builderLeftColumn, leftColumnStyles));

        final ComponentLayout.ContainerBuilder builderMiddleColumn = BaseViewComponent.create(c);
        if (middleChildren != null) {
            for (Component<?> child : middleChildren) {
                builderMiddleColumn.child(child);
            }
        }
        builder.child(StylizedComponent.applyStyles(builderMiddleColumn, middleColumnStyles));

        final ComponentLayout.ContainerBuilder builderRightColumn = BaseViewComponent.create(c);
        if (rightChildren != null) {
            for (Component<?> child : rightChildren) {
                builderRightColumn.child(child);
            }
        }
        builder.child(StylizedComponent.applyStyles(builderRightColumn, rightColumnStyles));

        final ComponentLayout.Builder styledBuilder = StylizedComponent.applyStyles(builder, styles);
        return styledBuilder.build();
    }

    static Map<String, Object> leftColumnStyles = new HashMap<>();
    static {
        leftColumnStyles.put("width", 50);
    }

    static Map<String, Object> middleColumnStyles = new HashMap<>();
    static {
        middleColumnStyles.put("width", 20);
    }

    static Map<String, Object> rightColumnStyles = new HashMap<>();
    static {
        rightColumnStyles.put("flexGrow", 1);
        rightColumnStyles.put("flexShrink", 1);
    }
}
