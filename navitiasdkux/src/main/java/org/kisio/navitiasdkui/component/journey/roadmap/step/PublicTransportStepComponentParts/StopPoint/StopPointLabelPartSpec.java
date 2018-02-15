package org.kisio.navitiasdkui.component.journey.roadmap.step.PublicTransportStepComponentParts.StopPoint;

import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropDefault;

import org.kisio.navitiasdkui.component.primitive.StylizedComponent;
import org.kisio.navitiasdkui.component.TextComponent;
import org.kisio.navitiasdkui.component.ViewComponent;
import org.kisio.navitiasdkui.util.Configuration;

import java.util.HashMap;
import java.util.Map;

@LayoutSpec
public class StopPointLabelPartSpec {
    @PropDefault
    static final Map<String, Object> styles = new HashMap<>();

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop(optional = true) String testKey,
        @Prop(optional = true) Map<String, Object> styles,
        @Prop String stopPointLabel) {

        final Map<String, Object> computedContainerStyles = StylizedComponent.mergeStyles(containerStyles, styles);

        final ViewComponent.Builder builder = ViewComponent.create(c)
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
        containerStyles.put("paddingHorizontal", Configuration.metrics.marginS);
    }

    static Map<String, Object> labelStyles = new HashMap<>();
    static {
        labelStyles.put("color", Configuration.colors.getDarkText());
        labelStyles.put("fontWeight", "bold");
        labelStyles.put("fontSize", Configuration.metrics.text);
    }
}
