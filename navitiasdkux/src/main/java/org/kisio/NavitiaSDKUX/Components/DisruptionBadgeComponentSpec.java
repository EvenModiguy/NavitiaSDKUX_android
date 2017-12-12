package org.kisio.NavitiaSDKUX.Components;

import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropDefault;
import com.facebook.yoga.YogaPositionType;

import org.kisio.NavitiaSDK.models.Disruption;
import org.kisio.NavitiaSDKUX.BusinessLogic.DisruptionLevel;
import org.kisio.NavitiaSDKUX.BusinessLogic.DisruptionMatcher;
import org.kisio.NavitiaSDKUX.Util.Color;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@LayoutSpec
public class DisruptionBadgeComponentSpec {
    @PropDefault static final Map<String, Object> styles = new HashMap<>();

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop(optional = true) String testKey,
        @Prop(optional = true) Map<String, Object> styles,
        @Prop List<Disruption> disruptions) {

        DisruptionLevel highestDisruptionLevel = DisruptionMatcher.getHighestDisruptionLevel(disruptions);
        iconStyles.put("color", Color.getColorFromHexadecimal(highestDisruptionLevel.getLevelColor()));

        return ViewComponent.create(c)
            .styles(styles)
            .children(new Component<?>[] {
                IconComponent.create(c)
                    .styles(circleStyles)
                    .name("circle-filled")
                    .build(),
                IconComponent.create(c)
                    .styles(iconStyles)
                    .name(highestDisruptionLevel.getIconName())
                    .build(),
            })
            .buildWithLayout();
    }

    static Map<String, Object> circleStyles = new HashMap<>();
    static {
        circleStyles.put("color", Color.getColorFromHexadecimal("FFFFFF"));
        circleStyles.put("fontSize", 18);
    }

    static Map<String, Object> iconStyles = new HashMap<>();
    static {
        iconStyles.put("fontSize", 16);
        iconStyles.put("position", YogaPositionType.ABSOLUTE);
        iconStyles.put("top", 0);
        iconStyles.put("end", 0);
    }
}
