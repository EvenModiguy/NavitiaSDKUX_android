package org.kisio.NavitiaSDKUX.Components.Journey.Roadmap;

import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropDefault;

import org.kisio.NavitiaSDK.models.Disruption;
import org.kisio.NavitiaSDK.models.Section;
import org.kisio.NavitiaSDKUX.Components.ContainerComponent;
import org.kisio.NavitiaSDKUX.Components.Journey.Roadmap.Steps.PublicTransportStepComponent;
import org.kisio.NavitiaSDKUX.Components.Journey.Roadmap.Steps.SharedStepComponent;
import org.kisio.NavitiaSDKUX.Components.Journey.Roadmap.Steps.SimpleStepComponent;
import org.kisio.NavitiaSDKUX.Components.Primitive.BaseViewComponent;
import org.kisio.NavitiaSDKUX.Components.Primitive.StylizedComponent;
import org.kisio.NavitiaSDKUX.Config.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@LayoutSpec
public class StepComponentSpec {
    @PropDefault static final Map<String, Object> styles = new HashMap<>();
    @PropDefault static final List<Disruption> disruptions = new ArrayList<>();
    @PropDefault static final Boolean isBSS = false;

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop(optional = true) String testKey,
        @Prop(optional = true) Map<String, Object> styles,
        @Prop Section section,
        @Prop(optional = true) List<Disruption> disruptions,
        @Prop(optional = true) CharSequence description,
        @Prop(optional = true) Integer waitingTime,
        @Prop(optional = true) Boolean isBSS) {

        final ComponentLayout.ContainerBuilder builder = BaseViewComponent.create(c).testKey(testKey).child(
            ContainerComponent.create(c).styles(containerStyles).children(new Component<?>[] {
                getTypedSectionComponent(c, section, disruptions, description, waitingTime, isBSS)
            })
        );
        final ComponentLayout.Builder styledBuilder = StylizedComponent.applyStyles(builder, styles);
        return styledBuilder.build();
    }

    static Component<?> getTypedSectionComponent(ComponentContext c, Section section, List<Disruption> disruptions, CharSequence description, Integer waitingTime, Boolean isBSS) {
        switch (section.getType()) {
            case "public_transport":
                return PublicTransportStepComponent.create(c)
                    .disruptions(disruptions)
                    .section(section)
                    .waitingDuration(waitingTime)
                    .build();
            case "street_network":
                if (isBSS) {
                    return SharedStepComponent.create(c)
                        .section(section)
                        .description(description)
                        .build();
                } else {
                    return SimpleStepComponent.create(c)
                        .section(section)
                        .description(description)
                        .build();
                }
            case "transfer":
            default:
                return SimpleStepComponent.create(c)
                    .section(section)
                    .description(description)
                    .build();
        }
    }

    static Map<String, Object> containerStyles = new HashMap<>();
    static {
        containerStyles.put("paddingHorizontal", Configuration.metrics.marginS);
        containerStyles.put("paddingVertical", Configuration.metrics.margin);
    }
}
