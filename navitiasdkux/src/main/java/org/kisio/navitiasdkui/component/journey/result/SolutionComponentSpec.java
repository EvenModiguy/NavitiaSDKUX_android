package org.kisio.navitiasdkui.component.journey.result;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropDefault;

import org.kisio.NavitiaSDK.models.Disruption;
import org.kisio.NavitiaSDK.models.Journey;
import org.kisio.NavitiaSDK.models.Section;
import org.kisio.navitiasdkui.component.ActionComponent;
import org.kisio.navitiasdkui.component.CardComponent;
import org.kisio.navitiasdkui.component.journey.result.solution.RowPart;
import org.kisio.navitiasdkui.component.primitive.BaseViewComponent;
import org.kisio.navitiasdkui.component.primitive.StylizedComponent;
import org.kisio.navitiasdkui.config.Configuration;
import org.kisio.navitiasdkui.controller.JourneySolutionRoadmapActivity;
import org.kisio.navitiasdkui.util.JourneySolutionCache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import static org.kisio.navitiasdkui.util.Metrics.sectionLength;

@LayoutSpec
public class SolutionComponentSpec {
    @PropDefault static final Map<String, Object> styles = new HashMap<>();

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop(optional = true) String testKey,
        @Prop(optional = true) Map<String, Object> styles,
        @Prop final Journey journey,
        @Prop final List<Disruption> disruptions,
        @Prop Boolean isTouchable) {

        final Map<String, Object> computedStyles = StylizedComponent.mergeStyles(listStyles, styles);
        final Context context = c;
        final ComponentLayout.Builder styledBuilder;

        final CardComponent.Builder listRowBuilder = CardComponent.create(c).styles(computedStyles).child(
            RowPart.create(c)
                .departureTime(journey.getDepartureDateTime())
                .arrivalTime(journey.getArrivalDateTime())
                .totalDuration(journey.getDuration())
                .walkingDuration(journey.getDurations().getWalking())
                .walkingDistance(getWalkingDistance(journey.getSections()))
                .sections(journey.getSections())
                .journeyDisruptions(disruptions)
                .hasArrow(isTouchable)
                .build()
        );

        if (isTouchable) {
            ActionComponent.Builder actionBuilder = ActionComponent.create(c).testKey(testKey).actionToCall(new Callable<Void>() { public Void call() {
                final Intent intent = new Intent(context, JourneySolutionRoadmapActivity.class);
                JourneySolutionCache.getInstance().setCurrentJourney(journey);
                JourneySolutionCache.getInstance().setCurrentDisruptions(disruptions);
                context.startActivity(intent);
                return null;
            }}).child(listRowBuilder);

            styledBuilder = StylizedComponent.applyStyles(actionBuilder.withLayout(), new HashMap<String, Object>());
        } else {
            styledBuilder = BaseViewComponent.create(c).child(listRowBuilder);
        }

        return styledBuilder.build();
    }

    static Map<String, Object> listStyles = new HashMap<>();
    static {
        listStyles.put("backgroundColor", Color.WHITE);
        listStyles.put("padding", Configuration.metrics.marginL);
        listStyles.put("paddingTop", Configuration.metrics.marginS);
        listStyles.put("marginBottom", Configuration.metrics.margin);
    }

    static Integer getWalkingDistance(List<Section> sections) {
        int distance = 0;
        for (Section section : sections) {
            if (section.getType().equals("street_network") && section.getMode().equals("walking")) {
                distance += sectionLength(section.getPath());
            }
        }
        return distance;
    }
}