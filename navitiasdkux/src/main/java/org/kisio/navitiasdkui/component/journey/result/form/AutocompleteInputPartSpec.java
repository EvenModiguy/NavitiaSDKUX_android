package org.kisio.navitiasdkui.component.journey.result.form;

import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropDefault;
import com.facebook.yoga.YogaAlign;

import org.kisio.navitiasdkui.component.IconComponent;
import org.kisio.navitiasdkui.component.primitive.BaseViewComponent;
import org.kisio.navitiasdkui.component.primitive.ButtonComponent;
import org.kisio.navitiasdkui.component.primitive.HorizontalViewComponent;
import org.kisio.navitiasdkui.component.primitive.StylizedComponent;
import org.kisio.navitiasdkui.util.Configuration;

import java.util.HashMap;
import java.util.Map;

@LayoutSpec
public class AutocompleteInputPartSpec {
    @PropDefault static final Map<String, Object> styles = new HashMap<>();
    @PropDefault static final Integer iconColor = Configuration.colors.getTertiary();
    @PropDefault static final String placeName = "";

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop(optional = true) String testKey,
        @Prop(optional = true) Map<String, Object> styles,
        @Prop(optional = true) String icon,
        @Prop(optional = true) Integer iconColor,
        @Prop(optional = true) String placeName) {

        final ComponentLayout.ContainerBuilder builder = ButtonComponent.create(c).testKey(testKey);
        builder.child(
            BaseViewComponent.create(c)
                .child(
                    AutocompleteInputPartSpec.getRowComponent(c, icon, iconColor, placeName)
                )
        );

        final ComponentLayout.Builder styledBuilder = StylizedComponent.applyStyles(builder, styles);
        return styledBuilder.build();
    }

    static ComponentLayout.Builder getRowComponent(ComponentContext c, String icon, Integer iconColor, String placeName) {
        final ComponentLayout.ContainerBuilder builder = HorizontalViewComponent.create(c);
        builder
        .child(
            getIconComponent(c, icon, iconColor)
        )
        .child(
            getLabelComponent(c, placeName)
        );
        final Map<String, Object> computedStyles = StylizedComponent.mergeStyles(containerStyles, styles);
        return StylizedComponent.applyStyles(builder, computedStyles);
    }

    static IconComponent.Builder getIconComponent(ComponentContext c, String icon, Integer iconColor) {
        Map<String, Object> iconComputedStyles = new HashMap<>(iconStyles);
        iconComputedStyles.put("color", iconColor);

        return IconComponent.create(c)
            .name("location-pin")
            .styles(iconComputedStyles);
    }

    static ComponentLayout.Builder getLabelComponent(ComponentContext c, String placeName) {
        final PlacePart.Builder labelBuilder = PlacePart.create(c);

        return BaseViewComponent.create(c)
            .child(
                labelBuilder.name(placeName)
            );
    }

    static Map<String, Object> containerStyles = new HashMap<>();
    static {
        containerStyles.put("padding", Configuration.metrics.marginS * 3);
        containerStyles.put("alignItems", YogaAlign.CENTER);
        containerStyles.put("paddingRight", Configuration.metrics.margin);
    }

    static Map<String, Object> iconStyles = new HashMap<>();
    static {
        iconStyles.put("width", 32);
        iconStyles.put("fontSize", 26);
    }
}
