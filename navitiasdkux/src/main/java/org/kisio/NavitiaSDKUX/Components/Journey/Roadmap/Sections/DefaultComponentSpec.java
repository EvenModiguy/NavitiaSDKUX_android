package org.kisio.NavitiaSDKUX.Components.Journey.Roadmap.Sections;

import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropDefault;

import org.kisio.NavitiaSDK.models.Section;
import org.kisio.NavitiaSDKUX.Components.Primitive.BaseViewComponent;
import org.kisio.NavitiaSDKUX.Components.Primitive.StylizedComponent;
import org.kisio.NavitiaSDKUX.Components.SeparatorComponent;
import org.kisio.NavitiaSDKUX.Components.TextComponent;
import org.kisio.NavitiaSDKUX.Config.Configuration;
import org.kisio.NavitiaSDKUX.Util.Metrics;

import java.util.HashMap;
import java.util.Map;

@LayoutSpec
public class DefaultComponentSpec {
    @PropDefault
    static final Map<String, Object> styles = new HashMap<>();

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop(optional = true) String testKey,
        @Prop(optional = true) Map<String, Object> styles,
        @Prop Section section) {

        String fromText = "";
        String toText = "";
        if (section.getDepartureDateTime() != null && section.getFrom() != null) {
            fromText = Metrics.timeText(section.getDepartureDateTime()) + " : " + section.getFrom().getName();
        }
        if (section.getArrivalDateTime() != null && section.getTo() != null) {
            toText = Metrics.timeText(section.getArrivalDateTime()) + " : " + section.getTo().getName();
        }

        final ComponentLayout.ContainerBuilder builder = BaseViewComponent.create(c).testKey(testKey).child(
            TextComponent.create(c)
                .styles(typeStyles)
                .text(section.getType())
                .build()
        ).child(
            SeparatorComponent.create(c)
                .styles(separatorStyles)
                .build()
        ).child(
            TextComponent.create(c)
                .text(fromText)
                .build()
        ).child(
            TextComponent.create(c)
                .text(toText)
                .build()
        );

        final Map<String, Object> computedStyle = StylizedComponent.mergeStyles(containerStyles, styles);
        final ComponentLayout.Builder styledBuilder = StylizedComponent.applyStyles(builder, computedStyle);
        return styledBuilder.build();
    }

    static Map<String, Object> containerStyles = new HashMap<>();
    static {
        containerStyles.put("padding", Configuration.metrics.margin);
    }

    static Map<String, Object> separatorStyles = new HashMap<>();
    static {
        separatorStyles.put("marginBottom", Configuration.metrics.margin);
    }

    static Map<String, Object> typeStyles = new HashMap<>();
    static {
        typeStyles.put("fontWeight", "bold");
    }
}
