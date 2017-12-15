package org.kisio.NavitiaSDKUX.Components.Journey.Roadmap.Sections.StopPoint;

import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropDefault;
import com.facebook.yoga.YogaAlign;
import com.facebook.yoga.YogaJustify;

import org.kisio.NavitiaSDKUX.Components.ContainerComponent;
import org.kisio.NavitiaSDKUX.Components.Primitive.StylizedComponent;
import org.kisio.NavitiaSDKUX.Components.TextComponent;
import org.kisio.NavitiaSDKUX.Config.Configuration;

import java.util.HashMap;
import java.util.Map;

import static org.kisio.NavitiaSDKUX.Util.Metrics.timeText;

@LayoutSpec
class TimeComponentSpec {
    @PropDefault static final Map<String, Object> styles = new HashMap<>();

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop(optional = true) String testKey,
        @Prop(optional = true) Map<String, Object> styles,
        @Prop String dateTime) {

        final ContainerComponent.Builder builder = ContainerComponent.create(c)
            .styles(containerStyles)
            .testKey(testKey)
            .children(new Component<?>[] {
                ContainerComponent.create(c)
                    .styles(paddingCenteringStyle)
                    .build(),
                TextComponent.create(c)
                    .styles(labelStyles)
                    .text(timeText(dateTime))
                    .build(),
                ContainerComponent.create(c)
                    .styles(paddingCenteringStyle)
                    .build()
        });
        final ComponentLayout.Builder styledBuilder = StylizedComponent.applyStyles(builder.withLayout(), styles);
        return styledBuilder.build();
    }

    static Map<String, Object> containerStyles = new HashMap<>();
    static {
        containerStyles.put("padding", 0);
        containerStyles.put("flexGrow", 1);
        containerStyles.put("alignItems", YogaAlign.CENTER);
        containerStyles.put("justifyContent", YogaJustify.CENTER);
    }

    static Map<String, Object> paddingCenteringStyle = new HashMap<>();
    static {
        paddingCenteringStyle.put("flexGrow", 1);
        paddingCenteringStyle.put("padding", 0);
    }

    static Map<String, Object> labelStyles = new HashMap<>();
    static {
        labelStyles.put("color", Configuration.colors.getDarkText());
        labelStyles.put("fontSize", 13);
    }
}
