package org.kisio.navitiasdkui.component.journey.result.solution;

import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropDefault;

import org.kisio.NavitiaSDK.models.Disruption;
import org.kisio.NavitiaSDK.models.Section;
import org.kisio.navitiasdkui.business.SectionMatcher;
import org.kisio.navitiasdkui.component.journey.result.Parts.SeparatorPart;
import org.kisio.navitiasdkui.component.primitive.BaseViewComponent;
import org.kisio.navitiasdkui.component.primitive.HorizontalViewComponent;
import org.kisio.navitiasdkui.component.primitive.StylizedComponent;
import org.kisio.navitiasdkui.config.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@LayoutSpec
public class FriezePartSpec {
    @PropDefault static final Map<String, Object> styles = new HashMap<>();

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop(optional = true) String testKey,
        @Prop(optional = true) Map<String, Object> styles,
        @Prop List<Section> sections,
        @Prop List<Disruption> journeyDisruptions) {

        final ComponentLayout.ContainerBuilder builder = BaseViewComponent.create(c).testKey(testKey);
        builder
            .child(
                SeparatorPart.create(c)
            )
            .child(
                getSectionsComponent(c, sections, journeyDisruptions)
            );

        final ComponentLayout.Builder styledBuilder = StylizedComponent.applyStyles(builder, styles);
        return styledBuilder.build();
    }

    static ComponentLayout.Builder getSectionsComponent(ComponentContext c, List<Section> sections, List<Disruption> journeyDisruptions) {
        final ComponentLayout.ContainerBuilder builder = HorizontalViewComponent.create(c);
        for (Section section : sections) {
            if (section.getType().equals("public_transport") || section.getType().equals("street_network")) {
                List<Disruption> sectionDisruptions = new ArrayList<>();
                if (section.getType().equals("public_transport") && journeyDisruptions != null && journeyDisruptions.size() > 0) {
                    sectionDisruptions = SectionMatcher.getMatchingDisruptions(section, journeyDisruptions);
                }
                builder.child(getSectionComponents(c, section, sectionDisruptions));
            }
        }
        final Map<String, Object> computedStyles = StylizedComponent.mergeStyles(modeListStyles, styles);
        return StylizedComponent.applyStyles(builder, computedStyles);
    }

    static SectionSummaryPart.Builder getSectionComponents(ComponentContext c, Section section, List<Disruption> disruptions) {
        return SectionSummaryPart.create(c)
            .disruptions(disruptions)
            .section(section);
    }

    static Map<String, Object> modeListStyles = new HashMap<>();
    static {
        modeListStyles.put("paddingTop", Configuration.metrics.marginL);
        modeListStyles.put("paddingBottom", Configuration.metrics.marginL);
        modeListStyles.put("flexGrow", 1);
        modeListStyles.put("marginEnd", Configuration.metrics.margin * -1);
    }
}