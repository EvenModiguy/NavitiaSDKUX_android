package org.kisio.NavitiaSDKUX.Components.Journey.Roadmap.Sections.StopPoint;

import android.graphics.Color;

import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropDefault;

import org.kisio.NavitiaSDKUX.Components.ContainerComponent;
import org.kisio.NavitiaSDKUX.Components.Primitive.StylizedComponent;
import org.kisio.NavitiaSDKUX.Components.TextComponent;
import org.kisio.NavitiaSDKUX.Config.Configuration;

import java.util.HashMap;
import java.util.Map;

@LayoutSpec
public class PlaceComponentSpec {
    @PropDefault
    static final Map<String, Object> styles = new HashMap<>();

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop(optional = true) String testKey,
        @Prop(optional = true) Map<String, Object> styles,
        @Prop String stopPointLabel) {

        final Map<String, Object> computedContainerStyles = StylizedComponent.mergeStyles(containerStyles, styles);

        final ContainerComponent.Builder builder = ContainerComponent.create(c)
            .testKey(testKey)
            .styles(computedContainerStyles)
            .children(new Component<?>[] {
                TextComponent.create(c)
                    .styles(labelStyles)
                    .text(stopPointLabel)
                    .build()
            }
        );

        final ComponentLayout.Builder styledBuilder = StylizedComponent.applyStyles(builder.withLayout(), computedContainerStyles);
        return styledBuilder.build();
    }

    static Map<String, Object> containerStyles = new HashMap<>();
    static {
        containerStyles.put("paddingHorizontal", 5);
    }

    static Map<String, Object> labelStyles = new HashMap<>();
    static {
        labelStyles.put("color", Configuration.colors.getDarkText());
        labelStyles.put("fontWeight", "bold");
        labelStyles.put("fontSize", 17);
    }
}
