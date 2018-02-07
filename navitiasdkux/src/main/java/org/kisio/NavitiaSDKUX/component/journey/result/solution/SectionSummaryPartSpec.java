package org.kisio.NavitiaSDKUX.component.journey.result.solution;

import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropDefault;

import org.kisio.NavitiaSDK.models.Disruption;
import org.kisio.NavitiaSDK.models.Section;
import org.kisio.NavitiaSDKUX.component.LineCodeWithDisruptionStatusComponent;
import org.kisio.NavitiaSDKUX.component.ModeComponent;
import org.kisio.NavitiaSDKUX.component.primitive.BaseViewComponent;
import org.kisio.NavitiaSDKUX.component.primitive.HorizontalViewComponent;
import org.kisio.NavitiaSDKUX.component.primitive.StylizedComponent;
import org.kisio.NavitiaSDKUX.config.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@LayoutSpec
public class SectionSummaryPartSpec {
    @PropDefault static final Map<String, Object> styles = new HashMap<>();

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop(optional = true) String testKey,
        @Prop(optional = true) Map<String, Object> styles,
        @Prop Section section,
        @Prop List<Disruption> disruptions) {

        final ComponentLayout.ContainerBuilder builder = BaseViewComponent.create(c).testKey(testKey);
        Map<String, Object> containerStyles = new HashMap<>(containerBaseStyles);
        containerStyles.put("flexGrow", section.getDuration());
        builder
            .child(
                getSymbolComponents(c, section, disruptions)
            )
            .child(
                SectionSegmentPart.create(c)
                    .section(section)
            );

        final Map<String, Object> computedStyles = StylizedComponent.mergeStyles(containerStyles, styles);
        final ComponentLayout.Builder styledBuilder = StylizedComponent.applyStyles(builder, computedStyles);
        return styledBuilder.build();
    }

    static ComponentLayout.ContainerBuilder getSymbolComponents(ComponentContext c, Section section, List<Disruption> disruptions) {
        final ComponentLayout.ContainerBuilder builder = HorizontalViewComponent.create(c);
        builder.child(
            ModeComponent.create(c)
                .section(section)
                .styles(modeStyles)
        ).child(
            LineCodeWithDisruptionStatusComponent.create(c)
                .disruptions(disruptions)
                .section(section)
        );
        return StylizedComponent.applyStyles(builder, viewStyles);
    }

    static Map<String, Object> containerBaseStyles = new HashMap<>();
    static {
        containerBaseStyles.put("marginEnd", Configuration.metrics.margin);
    }

    static Map<String, Object> viewStyles = new HashMap<>();
    static {
        viewStyles.put("marginBottom", 12);
    }

    static Map<String, Object> modeStyles = new HashMap<>();
    static {
        modeStyles.put("marginRight", Configuration.metrics.marginS);
        modeStyles.put("height", 28);
    }
}
