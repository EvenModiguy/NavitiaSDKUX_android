package org.kisio.navitiasdkui.component.journey.result.solution;

import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropDefault;

import org.kisio.navitiasdkui.component.IconComponent;
import org.kisio.navitiasdkui.component.primitive.HorizontalViewComponent;
import org.kisio.navitiasdkui.component.primitive.StylizedComponent;
import org.kisio.navitiasdkui.component.TextComponent;
import org.kisio.navitiasdkui.util.Configuration;
import org.kisio.navitiasdkui.util.Metrics;

import java.util.HashMap;
import java.util.Map;

@LayoutSpec
public class DurationPartSpec {
    @PropDefault static final Map<String, Object> styles = new HashMap<>();
    @PropDefault static final Integer seconds = 0;
    @PropDefault static final Boolean hasArrow = false;

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop(optional = true) String testKey,
        @Prop(optional = true) Map<String, Object> styles,
        @Prop(optional = true) Integer seconds,
        @Prop(optional = true) Boolean hasArrow) {

        final ComponentLayout.ContainerBuilder builder = HorizontalViewComponent.create(c).testKey(testKey);


        if (seconds >= 3600) {
            final String text = Metrics.durationText(c, seconds);
            builder
                .child(
                    TextComponent.create(c)
                        .text(text)
                        .styles(digitsStyles)
                );
        } else {
            builder
                .child(
                    TextComponent.create(c)
                        .text(String.valueOf((int) Math.ceil(seconds / 60)))
                        .styles(digitsStyles)
                ).child(
                    TextComponent.create(c)
                        .text("min")
                        .styles(abbrStyles)
                );
        }

        if (hasArrow) {
            builder.child(
                IconComponent.create(c)
                    .name("arrow-right")
                    .styles(arrowStyles)
            );
        }

        final ComponentLayout.Builder styledBuilder = StylizedComponent.applyStyles(builder, styles);
        return styledBuilder.build();
    }

    static Map<String, Object> digitsStyles = new HashMap<>();
    static {
        digitsStyles.put("color", Configuration.colors.getTertiary());
        digitsStyles.put("fontWeight", "bold");
        digitsStyles.put("paddingRight", Configuration.metrics.marginS);
    }

    static Map<String, Object> abbrStyles = new HashMap<>();
    static {
        abbrStyles.put("color", Configuration.colors.getTertiary());
        abbrStyles.put("paddingRight", Configuration.metrics.margin);
    }

    static Map<String, Object> arrowStyles = new HashMap<>();
    static {
        arrowStyles.put("color", Configuration.colors.getTertiary());
        arrowStyles.put("fontSize", 16);
        arrowStyles.put("marginRight", Configuration.metrics.marginS * -1);
    }
}
