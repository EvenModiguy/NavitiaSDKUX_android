package org.kisio.NavitiaSDKUX.Components.Journey.Roadmap.Steps.PublicTransportStepComponentParts;

import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropDefault;

import org.kisio.NavitiaSDK.models.Section;
import org.kisio.NavitiaSDKUX.BusinessLogic.SectionStopPointType;
import org.kisio.NavitiaSDKUX.Components.Journey.Roadmap.Roadmap3ColumnsLayout;
import org.kisio.NavitiaSDKUX.Components.Journey.Roadmap.Steps.Parts.TimePart;
import org.kisio.NavitiaSDKUX.Components.Journey.Roadmap.Steps.PublicTransportStepComponentParts.LineDiagram.StopPointIconPart;
import org.kisio.NavitiaSDKUX.Components.Journey.Roadmap.Steps.PublicTransportStepComponentParts.StopPoint.StopPointLabelPart;

import java.util.HashMap;
import java.util.Map;

@LayoutSpec
public class StopPointPartSpec {
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

        return Roadmap3ColumnsLayout.create(c)
            .styles(styles)
            .testKey(testKey)
            .leftChildren(new Component[]{
                TimePart.create(c)
                    .dateTime(time != null ? time : (sectionWay == SectionStopPointType.departure ? section.getDepartureDateTime() : section.getArrivalDateTime()))
                    .build()
            })
            .middleChildren(new Component[]{
                StopPointIconPart.create(c)
                    .color(color)
                    .build()
            })
            .rightChildren(new Component[]{
                StopPointLabelPart.create(c)
                    .stopPointLabel(stopPointLabel)
                    .build()
            })
            .buildWithLayout();
    }
}
