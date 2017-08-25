package org.kisio.NavitiaSDKUX.Components;

import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropDefault;
import com.facebook.yoga.YogaAlign;

import org.kisio.NavitiaSDKUX.Components.Primitive.ButtonComponent;
import org.kisio.NavitiaSDKUX.Components.Primitive.HorizontalViewComponent;
import org.kisio.NavitiaSDKUX.Components.Primitive.StylizedComponent;
import org.kisio.NavitiaSDKUX.Components.Primitive.ViewComponent;
import org.kisio.NavitiaSDKUX.Config.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * NavitiaSDKUX_android
 *
 * Created by Johan Rouve on 24/08/2017.
 * Copyright © 2017 Kisio. All rights reserved.
 */

@LayoutSpec
public class AutocompleteInputComponentSpec {
    @PropDefault static final Map<String, Object> styles = new HashMap<>();
    @PropDefault static final String icon = "";
    @PropDefault static final Integer iconColor = Configuration.colors.tertiary;
    @PropDefault static final String placeName = "";

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop(optional = true) Map<String, Object> styles,
        @Prop(optional = true) String icon,
        @Prop(optional = true) Integer iconColor,
        @Prop(optional = true) String placeName) {

        final ComponentLayout.ContainerBuilder builder = ButtonComponent.create(c);
        builder.child(
            ViewComponent.create(c)
                .child(
                    AutocompleteInputComponentSpec.getRowComponent(c, icon, iconColor, placeName)
                )
        );

        final ComponentLayout.Builder styledBuilder = StylizedComponent.applyStyles(builder, styles);
        return styledBuilder.build();
    }

    static ComponentLayout.Builder getRowComponent(ComponentContext c, String icon, Integer iconColor, String placeName) {
        final ComponentLayout.ContainerBuilder builder = HorizontalViewComponent.create(c);
        builder
        .child(
            AutocompleteInputComponentSpec.getIconComponent(c, icon, iconColor)
        )
        .child(
            AutocompleteInputComponentSpec.getLabelComponent(c, placeName)
        );
        final Map<String, Object> computedStyles = StylizedComponent.mergeStyles(containerStyles, styles);
        return StylizedComponent.applyStyles(builder, computedStyles);
    }

    static IconComponent.Builder getIconComponent(ComponentContext c, String icon, Integer iconColor) {
        Map<String, Object> iconComputedStyles = new HashMap<>(iconStyles);
        iconComputedStyles.put("color", iconColor);

        return IconComponent.create(c)
            .name(icon)
            .styles(iconComputedStyles);
    }

    static ComponentLayout.Builder getLabelComponent(ComponentContext c, String placeName) {
        final PlaceComponent.Builder labelBuilder = PlaceComponent.create(c);

        return ViewComponent.create(c)
            .child(
                labelBuilder.name(placeName)
            );
    }

    static Map<String, Object> containerStyles = new HashMap<>();
    static {
        containerStyles.put("padding", 12);
        containerStyles.put("alignItems", YogaAlign.CENTER);
        containerStyles.put("paddingRight", 8);
    }

    static Map<String, Object> iconStyles = new HashMap<>();
    static {
        iconStyles.put("width", 32);
        iconStyles.put("fontSize", 26);
    }
}
