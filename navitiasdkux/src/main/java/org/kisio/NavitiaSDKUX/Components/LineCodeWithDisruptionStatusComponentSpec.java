package org.kisio.NavitiaSDKUX.Components;

import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.yoga.YogaPositionType;

import org.kisio.NavitiaSDK.models.Disruption;
import org.kisio.NavitiaSDK.models.Section;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@LayoutSpec
public class LineCodeWithDisruptionStatusComponentSpec {
    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop(optional = true) String testKey,
        @Prop(optional = true) Map<String, Object> styles,
        @Prop Section section,
        @Prop List<Disruption> disruptions) {

        Component disruptionBadgeComponent = ViewComponent.create(c).build();
        if (disruptions != null && disruptions.size() > 0) {
            disruptionBadgeComponent = DisruptionBadgeComponent.create(c)
                .disruptions(disruptions)
                .styles(disruptionBadgeStyles)
                .build();
        }

        return ViewComponent.create(c)
            .children(new Component<?>[] {
                LineCodeComponent.create(c)
                    .section(section)
                    .build(),
                disruptionBadgeComponent
            }).buildWithLayout();
    }

    static Map<String, Object> disruptionBadgeStyles = new HashMap<>();
    static {
        disruptionBadgeStyles.put("position", YogaPositionType.ABSOLUTE);
        disruptionBadgeStyles.put("top", -9);
        disruptionBadgeStyles.put("end", -11);
    }
}
