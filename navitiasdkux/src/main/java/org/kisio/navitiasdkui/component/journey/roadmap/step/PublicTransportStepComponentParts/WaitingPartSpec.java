package org.kisio.navitiasdkui.component.journey.roadmap.step.PublicTransportStepComponentParts;

import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.yoga.YogaJustify;

import org.kisio.navitiasdkui.component.HorizontalContainerComponent;
import org.kisio.navitiasdkui.component.IconComponent;
import org.kisio.navitiasdkui.component.TextComponent;
import org.kisio.navitiasdkui.component.ViewComponent;
import org.kisio.navitiasdkui.util.Configuration;
import org.kisio.navitiasdkui.R;
import org.kisio.navitiasdkui.util.Metrics;

import java.util.HashMap;
import java.util.Map;

@LayoutSpec
public class WaitingPartSpec {
    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop(optional = true) String testKey,
        @Prop Integer duration) {

        final ViewComponent.Builder builder = ViewComponent.create(c)
            .testKey(testKey)
            .styles(containerStyles)
            .children(new Component[]{
                HorizontalContainerComponent.create(c)
                    .children(new Component[]{
                        ViewComponent.create(c)
                            .styles(iconContainerStyles)
                            .children(new Component[] {
                                IconComponent.create(c)
                                    .name("clock")
                                    .styles(iconStyles)
                                    .build()
                            })
                            .build(),
                        TextComponent.create(c)
                            .styles(labelStyles)
                            .text(String.format("%1$s %2$s", c.getString(R.string.wait), Metrics.durationText(c, duration, true)))
                            .build()
                    })
                    .build()
            });

        return builder.buildWithLayout();
    }

    static Map<String, Object> containerStyles = new HashMap<>();
    static {
        containerStyles.put("marginTop", Configuration.metrics.margin);
    }

    static Map<String, Object> iconContainerStyles = new HashMap<>();
    static {
        iconContainerStyles.put("justifyContent", YogaJustify.CENTER);
    }

    static Map<String, Object> iconStyles = new HashMap<>();
    static {
        iconStyles.put("color", Configuration.colors.getGray());
        iconStyles.put("fontSize", 18);
    }

    static Map<String, Object> labelStyles = new HashMap<>();
    static {
        labelStyles.put("fontSize", Configuration.metrics.textS);
        labelStyles.put("color", Configuration.colors.getGray());
        labelStyles.put("padding", 6);
    }
}
