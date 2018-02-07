package org.kisio.NavitiaSDKUX.component.journey.roadmap;

import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.yoga.YogaAlign;
import org.kisio.NavitiaSDKUX.component.ContainerComponent;
import org.kisio.NavitiaSDKUX.component.IconComponent;
import org.kisio.NavitiaSDKUX.component.TextComponent;
import org.kisio.NavitiaSDKUX.config.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rabidi on 05/01/2018.
 */

@LayoutSpec
public class PlaceMarkerComponentSpec {

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
            ComponentContext c,
            @Prop String markerTitle,
            @Prop Integer markerColor) {

        Map<String, Object> markerIconStyles = new HashMap<>();
        markerIconStyles.put("color", markerColor);
        markerIconStyles.put("fontSize", 25);

        ContainerComponent.Builder markerComponentBuilder = ContainerComponent.create(c).styles(containerStyles).children(new Component<?>[] {
                TextComponent.create(c).text(markerTitle).styles(textStyles).build(),
                IconComponent.create(c).name("location-pin").styles(markerIconStyles).build()
        });
        return markerComponentBuilder.buildWithLayout();
    }

    static Map<String, Object> containerStyles = new HashMap<>();
    static {
        containerStyles.put("padding", 0);
        containerStyles.put("alignItems", YogaAlign.CENTER);
    }

    static Map<String, Object> textStyles = new HashMap<>();
    static {
        textStyles.put("fontSize", 15);
        textStyles.put("color", Configuration.colors.getWhite());
        textStyles.put("backgroundColor", Configuration.colors.getTransparentBlack());
        textStyles.put("paddingHorizontal", 5);
        textStyles.put("paddingBottom", 2);
        textStyles.put("marginBottom", 2);
    }
}
