package org.kisio.NavitiaSDKUX.Components.Journey.Roadmap.Sections;

import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropDefault;

import org.kisio.NavitiaSDK.models.Section;
import org.kisio.NavitiaSDKUX.BusinessLogic.SectionStopPointType;
import org.kisio.NavitiaSDKUX.Components.Journey.Roadmap.Sections.LineDiagram.StopPointIconComponent;
import org.kisio.NavitiaSDKUX.Components.Journey.Roadmap.Sections.StopPoint.PlaceComponent;
import org.kisio.NavitiaSDKUX.Components.Journey.Roadmap.Sections.StopPoint.TimeComponent;
import org.kisio.NavitiaSDKUX.Components.Primitive.StylizedComponent;

import java.util.HashMap;
import java.util.Map;

@LayoutSpec
public class StopPointComponentSpec {
    @PropDefault static final Map<String, Object> styles = new HashMap<>();

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop(optional = true) String testKey,
        @Prop(optional = true) Map<String, Object> styles,
        @Prop Section section,
        @Prop SectionStopPointType sectionWay,
        @Prop(optional = true) String time,
        @Prop Integer color) {

        String stopPointLabel;
        if (sectionWay == SectionStopPointType.departure) {
            stopPointLabel = section.getFrom().getName();
        } else {
            stopPointLabel = section.getTo().getName();
        }

        final SectionRowLayoutComponent.Builder builder = SectionRowLayoutComponent.create(c)
            .testKey(testKey)
            .firstComponent(
                TimeComponent.create(c)
                    .dateTime(time != null ? time : (sectionWay == SectionStopPointType.departure ? section.getDepartureDateTime() : section.getArrivalDateTime()))
                    .build())
            .secondComponent(
                StopPointIconComponent.create(c)
                    .color(color)
            )
            .thirdComponent(
                PlaceComponent.create(c)
                    .stopPointLabel(stopPointLabel)
            );
        
        final ComponentLayout.Builder styledBuilder = StylizedComponent.applyStyles(builder.withLayout(), styles);
        return styledBuilder.build();
    }
}
