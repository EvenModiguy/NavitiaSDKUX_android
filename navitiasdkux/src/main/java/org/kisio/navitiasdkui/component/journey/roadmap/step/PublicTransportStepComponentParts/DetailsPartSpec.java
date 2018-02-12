package org.kisio.navitiasdkui.component.journey.roadmap.step.PublicTransportStepComponentParts;

import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.StateValue;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateInitialState;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.OnUpdateState;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropDefault;
import com.facebook.litho.annotations.State;

import org.kisio.NavitiaSDK.models.Section;
import org.kisio.NavitiaSDK.models.StopDateTime;
import org.kisio.navitiasdkui.component.ActionComponent;
import org.kisio.navitiasdkui.component.journey.roadmap.Roadmap3ColumnsLayout;
import org.kisio.navitiasdkui.component.journey.roadmap.step.PublicTransportStepComponentParts.Details.DetailButtonPart;
import org.kisio.navitiasdkui.component.journey.roadmap.step.PublicTransportStepComponentParts.Details.IntermediateStopPointPart;
import org.kisio.navitiasdkui.component.primitive.BaseViewComponent;
import org.kisio.navitiasdkui.component.primitive.StylizedComponent;
import org.kisio.navitiasdkui.component.ViewComponent;
import org.kisio.navitiasdkui.config.Configuration;
import org.kisio.navitiasdkui.R;
import org.kisio.navitiasdkui.util.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;

@LayoutSpec
class DetailsPartSpec {
    @PropDefault static final Map<String, Object> styles = new HashMap<>();

    @OnCreateInitialState
    static void createInitialState(ComponentContext c, StateValue<Boolean> collapsed) {
        collapsed.set(true);
    }

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        final ComponentContext c,
        @State Boolean collapsed,
        @Prop(optional = true) String testKey,
        @Prop(optional = true) Map<String, Object> styles,
        @Prop Section section) {

        final ComponentLayout.ContainerBuilder builder = BaseViewComponent.create(c).testKey(testKey);

        if (section.getStopDateTimes() != null && section.getStopDateTimes().size() > 2) {
            ViewComponent.Builder intermediateStopsComponent;

            if (collapsed) {
                intermediateStopsComponent = ViewComponent.create(c);
            } else {
                intermediateStopsComponent = getIntermediateStops(c, section);
            }

            builder
                .child(
                    ActionComponent.create(c).actionToCall(new Callable<Void>() { public Void call() {
                        DetailsPart.updateCollapsedAsync(c);
                        return null;
                    }}).child(
                        Roadmap3ColumnsLayout.create(c)
                            .rightChildren(new Component[]{
                                DetailButtonPart.create(c)
                                    .collapsed(collapsed)
                                    .text(String.format(Locale.getDefault(),"%d %s",
                                            section.getStopDateTimes().size() - 1, c.getString(R.string.stops)))
                                    .build()
                            })
                    )
                ).child(
                    intermediateStopsComponent
                );

            final ComponentLayout.Builder styledBuilder = StylizedComponent.applyStyles(builder, styles);
            return styledBuilder.build();
        } else {
            return builder.build();
        }
    }

    @OnUpdateState
    static void updateCollapsed(StateValue<Boolean> collapsed) {
        collapsed.set(!collapsed.get());
    }

    static ViewComponent.Builder getIntermediateStops(ComponentContext c, Section section) {
        int lastIndex = section.getStopDateTimes().size() - 1;
        int index = 0;
        List<Component> components = new ArrayList<>();
        for (StopDateTime stopDateTime : section.getStopDateTimes()) {
            if (index > 0 && index < lastIndex) {
                components.add(
                    IntermediateStopPointPart.create(c)
                        .stopDateTime(stopDateTime)
                        .color(Color.getColorFromHexadecimal(section.getDisplayInformations().getColor()))
                        .build()
                );
            }
            index++;
        }

        return ViewComponent.create(c)
            .styles(intermediateStopsStyles)
            .children(components.toArray(new Component[components.size()]));
    }

    static Map<String, Object> intermediateStopsStyles = new HashMap<>();
    static {
        intermediateStopsStyles.put("marginTop", Configuration.metrics.marginL);
    }
}