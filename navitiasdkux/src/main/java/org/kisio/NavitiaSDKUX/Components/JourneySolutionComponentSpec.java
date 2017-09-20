package org.kisio.NavitiaSDKUX.Components;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropDefault;

import org.kisio.NavitiaSDK.models.Journey;
import org.kisio.NavitiaSDK.models.Path;
import org.kisio.NavitiaSDK.models.Section;
import org.kisio.NavitiaSDKUX.Components.Primitive.StylizedComponent;
import org.kisio.NavitiaSDKUX.Components.Primitive.ViewComponent;
import org.kisio.NavitiaSDKUX.Config.Configuration;
import org.kisio.NavitiaSDKUX.Controllers.JourneySolutionRoadmapActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * NavitiaSDKUX_android
 *
 * Created by Johan Rouve on 28/08/2017.
 * Copyright © 2017 Kisio. All rights reserved.
 */

@LayoutSpec
public class JourneySolutionComponentSpec {
    @PropDefault static final Map<String, Object> styles = new HashMap<>();

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop(optional = true) String testKey,
        @Prop(optional = true) Map<String, Object> styles,
        @Prop final Journey journey,
        @Prop Boolean isTouchable) {

        final Map<String, Object> computedStyles = StylizedComponent.mergeStyles(listStyles, styles);
        final Context context = c;
        final ComponentLayout.Builder styledBuilder;

        final ListRowComponent.Builder listRowBuilder = ListRowComponent.create(c).styles(computedStyles).child(
            JourneySolutionRowComponent.create(c)
                .departureTime(journey.getDepartureDateTime())
                .arrivalTime(journey.getArrivalDateTime())
                .totalDuration(journey.getDuration())
                .walkingDuration(journey.getDurations().getWalking())
                .walkingDistance(getWalkingDistance(journey.getSections()))
                .sections(journey.getSections())
                .build()
        );

        if (isTouchable) {
            ActionComponent.Builder actionBuilder = ActionComponent.create(c).testKey(testKey).actionToCall(new Callable<Void>() { public Void call() {
                final Intent intent = new Intent(context, JourneySolutionRoadmapActivity.class);
                intent.putExtra("journey", journey);
                context.startActivity(intent);
                return null;
            }}).child(listRowBuilder);

            styledBuilder = StylizedComponent.applyStyles(actionBuilder.withLayout(), new HashMap<String, Object>());
        } else {
            styledBuilder = ViewComponent.create(c).child(listRowBuilder);
        }

        return styledBuilder.build();
    }

    static Map<String, Object> listStyles = new HashMap<>();
    static {
        listStyles.put("backgroundColor", Color.WHITE);
        listStyles.put("padding", Configuration.metrics.marginL);
        listStyles.put("paddingTop", 4);
        listStyles.put("marginBottom", Configuration.metrics.margin);
        /*
        listStyles.put("shadowRadius", 2);
        listStyles.put("shadowOpacity", 0.12);
        listStyles.put("shadowOffset", new int[]{0, 0});
        listStyles.put("shadowColor", Color.BLACK);
        */
    }

    static Integer getWalkingDistance(List<Section> sections) {
        int distance = 0;
        for (Section section : sections) {
            if (section.getType().equals("street_network") && section.getMode().equals("walking")) {
                for (Path segment : section.getPath()) {
                    distance += segment.getLength();
                }
            }
        }
        return distance;
    }
}