package org.kisio.NavitiaSDKUX.Components.Journey.Roadmap.Sections.StopPoint;

import android.graphics.Color;

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
import org.kisio.NavitiaSDKUX.Components.Primitive.ViewComponent;
import org.kisio.NavitiaSDKUX.Components.TextComponent;
import org.kisio.NavitiaSDKUX.Config.Configuration;

import java.util.HashMap;
import java.util.Map;

import static org.kisio.NavitiaSDKUX.Util.Metrics.timeText;

@LayoutSpec
class TimeComponentSpec {
    @PropDefault
    static final Map<String, Object> styles = new HashMap<>();

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop(optional = true) String testKey,
        @Prop(optional = true) Map<String, Object> styles,
        @Prop String dateTime) {

        final ComponentLayout.ContainerBuilder builder = ViewComponent.create(c).testKey(testKey).child(
            ContainerComponent.create(c)
                .styles(containerStyles)
                .children(new Component<?>[] {
                    ContainerComponent.create(c)
                        .styles(labelWrapperStyles)
                        .build(),
                    TextComponent.create(c)
                        .styles(labelStyles)
                        .text(timeText(dateTime))
                        .build(),
                    ContainerComponent.create(c)
                        .styles(labelWrapperStyles)
                        .build()
            })
        );
        final ComponentLayout.Builder styledBuilder = StylizedComponent.applyStyles(builder, styles);
        return styledBuilder.build();
    }

    static Map<String, Object> containerStyles = new HashMap<>();
    static {
        containerStyles.put("backgroundColor", Color.RED);
        containerStyles.put("flexGrow", 1);
        containerStyles.put("alignItems", YogaAlign.CENTER);
        containerStyles.put("justifyContent", YogaJustify.CENTER);
    }

    static Map<String, Object> labelWrapperStyles = new HashMap<>();
    static {
        labelWrapperStyles.put("flexGrow", 1);
        labelWrapperStyles.put("backgroundColor", Color.BLUE);
    }

    static Map<String, Object> labelStyles = new HashMap<>();
    static {
        labelStyles.put("color", Configuration.colors.getDarkText());
        labelStyles.put("fontSize", 12);
        labelStyles.put("backgroundColor", Color.GREEN);
        // Equivalent of number of lines in android
    }
}
