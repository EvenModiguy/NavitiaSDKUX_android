package org.kisio.NavitiaSDKUX.Screens;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;

import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;

import org.kisio.NavitiaSDK.models.Disruption;
import org.kisio.NavitiaSDK.models.Journey;
import org.kisio.NavitiaSDK.models.Section;
import org.kisio.NavitiaSDKUX.BusinessLogic.SectionMatcher;
import org.kisio.NavitiaSDKUX.Components.Journey.Results.SolutionComponent;
import org.kisio.NavitiaSDKUX.Components.Journey.Roadmap.JourneyMapViewComponent;
import org.kisio.NavitiaSDKUX.Components.Journey.Roadmap.StepComponent;
import org.kisio.NavitiaSDKUX.Components.Journey.Roadmap.Steps.PlaceStepComponent;
import org.kisio.NavitiaSDKUX.Components.ListViewComponent;
import org.kisio.NavitiaSDKUX.Components.Primitive.BaseViewComponent;
import org.kisio.NavitiaSDKUX.Components.ScrollViewComponent;
import org.kisio.NavitiaSDKUX.Config.Configuration;
import org.kisio.NavitiaSDKUX.R;
import org.kisio.NavitiaSDKUX.Util.Metrics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@LayoutSpec
public class JourneySolutionRoadmapScreenSpec {
    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop Journey journey,
        @Prop List<Disruption> disruptions,
        @Prop Bundle savedInstanceState) {

        return BaseViewComponent.create(c).child(
                JourneyMapViewComponent.create(c).savedInstanceState(savedInstanceState).journey(journey).build()
        ).child(ScrollViewComponent.create(c).child(ListViewComponent.create(c).children(
                getJourneySectionComponents(c, journey, disruptions)
        ).build())).build();
    }

    private static Component<?>[] getJourneySectionComponents(ComponentContext c, Journey journey, List<Disruption> disruptions) {
        List<Component<?>> components = new ArrayList<>();
        components.add(SolutionComponent.create(c)
                .journey(journey)
                .disruptions(disruptions)
                .isTouchable(false)
                .build());

        int index = 0;
        final int lastIndex = journey.getSections().size() - 1;
        for (Section section : journey.getSections()) {
            if (index == 0) {
                components.add(
                    PlaceStepComponent.create(c)
                        .styles(originSectionStyles)
                        .datetime(journey.getDepartureDateTime())
                        .placeType(c.getString(R.string.departure_with_colon))
                        .placeLabel(section.getFrom().getName())
                        .backgroundColor(Configuration.colors.getOrigin())
                        .build()
                );
            }

            if (Arrays.asList( "street_network", "public_transport", "transfer" ).contains(section.getType())) {
                StepComponent.Builder sectionComponentBuilder = StepComponent.create(c)
                    .key("journey_roadmap_section_" + index)
                    .section(section);

                if (section.getType().equals("public_transport")) {
                    if (index > 0) {
                        Section prevSection = journey.getSections().get(index - 1);
                        if (prevSection.getType().equals("waiting")) {
                            sectionComponentBuilder.waitingTime(prevSection.getDuration());
                        }
                    }

                    if (disruptions != null && disruptions.size() > 0) {
                        sectionComponentBuilder.disruptions(
                            SectionMatcher.getMatchingDisruptions(section, disruptions)
                        );
                    }
                } else if (section.getType().equals("street_network")) {
                    String mode = section.getMode();
                    String network = null;
                    if (index > 0) {
                        Section prevSection = journey.getSections().get(index - 1);
                        if (prevSection.getType().equals("bss_rent")) {
                            network = "";
                            if (section.getFrom().getPoi() != null && section.getFrom().getPoi().getProperties().containsKey("network")) {
                                network = section.getFrom().getPoi().getProperties().get("network");
                            }
                            sectionComponentBuilder.isBSS(true);
                        }
                    }
                    sectionComponentBuilder.description(
                        getDescriptionLabel(c, mode, section.getDuration(), section.getTo().getName(), network, section.getFrom().getName())
                    );
                } else if (section.getType().equals("transfer")) {
                    String mode = section.getTransferType();
                    sectionComponentBuilder.description(
                        getDescriptionLabel(c, mode, section.getDuration(), section.getTo().getName())
                    );
                }
                components.add(sectionComponentBuilder.build());
            }

            if (index == lastIndex) {
                components.add(
                    PlaceStepComponent.create(c)
                        .styles(destinationSectionStyles)
                        .datetime(journey.getArrivalDateTime())
                        .placeType(c.getString(R.string.arrival_with_colon))
                        .placeLabel(section.getTo().getName())
                        .backgroundColor(Configuration.colors.getDestination())
                        .build()
                );
            }

            index++;
        }

        return components.toArray(new Component<?>[components.size()]);
    }

    private static CharSequence getDescriptionLabel(ComponentContext c, String mode, Integer duration, String toLabel) {
        return getDescriptionLabel(c, mode, duration, toLabel, null, null);
    }

    private static CharSequence getDescriptionLabel(ComponentContext c, String mode, Integer duration, String toLabel, String network, String fromLabel) {
        String durationText = Metrics.durationText(c, duration, true);
        SpannableStringBuilder descriptionLabel = new SpannableStringBuilder();

        if (network != null) {
            // bss mode
            String takeStringTemplate = c.getString(R.string.take_a_bike_at) + " ";
            String take = String.format(takeStringTemplate, network);
            SpannableString takeSpannableString = new SpannableString(take);
            descriptionLabel.append(takeSpannableString);

            SpannableString departureSpannableString = new SpannableString(fromLabel);
            departureSpannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, fromLabel.length(), 0);
            descriptionLabel.append(departureSpannableString);

            String inDirection = " " + c.getString(R.string.to) + " ";
            SpannableString inDirectionSpannableString = new SpannableString(inDirection);
            descriptionLabel.append(inDirectionSpannableString);
        } else {
            String to = c.getString(R.string.to_with_uppercase) + " ";
            SpannableString toSpannableString = new SpannableString(to);
            descriptionLabel.append(toSpannableString);
        }

        SpannableString toSpannableString = new SpannableString(toLabel);
        toSpannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, toLabel.length(), 0);
        descriptionLabel.append(toSpannableString);

        String durationString = "\n";
        switch (mode) {
            case "walking":
                String walkingStringTemplate = c.getString(R.string.a_time_walk);
                durationString += String.format(walkingStringTemplate, durationText);
                break;
            case "bike":
                final String bikeStringTemplate = c.getString(R.string.a_time_ride);
                durationString += String.format(bikeStringTemplate, durationText);
                break;
            case "car":
                final String carStringTemplate = c.getString(R.string.a_time_drive);
                durationString += String.format(carStringTemplate, durationText);
                break;
            default:
                break;
        }
        SpannableString durationSpannableString = new SpannableString(durationString);
        descriptionLabel.append(durationSpannableString);


        return descriptionLabel;
    }

    static Map<String, Object> destinationSectionStyles = new HashMap<>();
    static {
        destinationSectionStyles.put("marginTop", Configuration.metrics.margin);
    }

    static Map<String, Object> originSectionStyles = new HashMap<>();
    static {
        originSectionStyles.put("marginBottom", Configuration.metrics.margin);
    }
}
