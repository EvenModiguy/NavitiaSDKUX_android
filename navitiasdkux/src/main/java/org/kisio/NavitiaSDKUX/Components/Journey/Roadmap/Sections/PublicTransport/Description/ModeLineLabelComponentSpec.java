package org.kisio.NavitiaSDKUX.Components.Journey.Roadmap.Sections.PublicTransport.Description;

import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.yoga.YogaAlign;

import org.kisio.NavitiaSDK.models.Disruption;
import org.kisio.NavitiaSDK.models.Section;
import org.kisio.NavitiaSDKUX.BusinessLogic.Modes;
import org.kisio.NavitiaSDKUX.Components.LineCodeWithDisruptionStatusComponent;
import org.kisio.NavitiaSDKUX.Components.Primitive.HorizontalViewComponent;
import org.kisio.NavitiaSDKUX.Components.Primitive.StylizedComponent;
import org.kisio.NavitiaSDKUX.Components.TextComponent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@LayoutSpec
class ModeLineLabelComponentSpec {
    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop Section section,
        @Prop List<Disruption> disruptions) {

        final ComponentLayout.ContainerBuilder builder = HorizontalViewComponent.create(c);

        builder
            .child(
                TextComponent.create(c)
                    .text(Modes.getPhysicalMode(section))
                    .styles(modeStyles))
            .child(
                LineCodeWithDisruptionStatusComponent.create(c)
                    .disruptions(disruptions)
                    .section(section));

        final ComponentLayout.Builder styledBuilder = StylizedComponent.applyStyles(builder, containerStyles);
        return styledBuilder.build();
    }

    static Map<String, Object> containerStyles = new HashMap<>();
    static {
        containerStyles.put("alignItems", YogaAlign.CENTER);
    }

    static Map<String, Object> modeStyles = new HashMap<>();
    static {
        modeStyles.put("fontSize", 15);
        modeStyles.put("marginRight", 5);
    }
}