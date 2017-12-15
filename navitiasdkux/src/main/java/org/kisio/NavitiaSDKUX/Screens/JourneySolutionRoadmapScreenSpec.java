package org.kisio.NavitiaSDKUX.Screens;

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
import org.kisio.NavitiaSDKUX.Components.ContainerComponent;
import org.kisio.NavitiaSDKUX.Components.Journey.Results.SolutionComponent;
import org.kisio.NavitiaSDKUX.Components.Journey.Roadmap.SectionComponent;
import org.kisio.NavitiaSDKUX.Components.ListRowComponent;
import org.kisio.NavitiaSDKUX.Components.ListViewComponent;
import org.kisio.NavitiaSDKUX.Components.Primitive.BaseViewComponent;
import org.kisio.NavitiaSDKUX.Components.ScrollViewComponent;
import org.kisio.NavitiaSDKUX.Config.Configuration;
import org.kisio.NavitiaSDKUX.R;
import org.kisio.NavitiaSDKUX.Util.Metrics;

import java.util.*;

@LayoutSpec
public class JourneySolutionRoadmapScreenSpec {
    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop Journey journey,
        @Prop List<Disruption> disruptions) {

        return BaseViewComponent.create(c).testKey("roadmap").child(
            ContainerComponent.create(c)
                .styles(headerStyles)
                .children(new Component<?>[]{})
                .build()
        ).child(
            ContainerComponent.create(c)
                .styles(summaryStyles)
                .testKey("summary")
                .children(new Component<?>[]{
                    SolutionComponent.create(c)
                        .journey(journey)
                        .disruptions(disruptions)
                        .isTouchable(false)
                        .build()})
                .build()
        ).child(
            ScrollViewComponent.create(c).child(ListViewComponent.create(c).children(
                getJourneySectionComponents(c, journey, disruptions)
            ).build())
        ).build();
    }

    static Component<?>[] getJourneySectionComponents(ComponentContext c, Journey journey, List<Disruption> disruptions) {
        List<Component<?>> components = new ArrayList<>();

        int index = 0;
        for (Section section : journey.getSections()) {
            if (Arrays.asList( "street_network", "public_transport", "transfer").contains(section.getType())) {
                SectionComponent.Builder sectionComponentBuilder = SectionComponent.create(c)
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
                            SectionMatcher.getMatchingDisruptions(section, disruptions, new Date())
                        );
                    }
                } else if (section.getType().equals("street_network")) {
                    String mode = section.getMode();
                    String network = null;
                    if (section.getFrom().getPoi() != null && section.getFrom().getPoi().getProperties().containsKey("network")) {
                        network = section.getFrom().getPoi().getProperties().get("network");
                        sectionComponentBuilder.departureTime(journey.getSections().get(index - 1).getDepartureDateTime());
                        sectionComponentBuilder.arrivalTime(journey.getSections().get(index + 1).getArrivalDateTime());
                    }
                    Integer distance = Metrics.sectionLength(section.getPath());
                    sectionComponentBuilder.description(getDistanceLabel(c, network, mode, distance));
                }
                components.add(ListRowComponent.create(c).child(
                    sectionComponentBuilder.build()
                ).build());
            }
            index++;
        }

        return components.toArray(new Component<?>[components.size()]);
    }

    private static String getDistanceLabel(ComponentContext c, String network, String mode, Integer distance) {
        String distanceText = Metrics.distanceText(c, distance);
        String distanceLabel = "";

        switch (mode) {
            case "walking":
                final String walkingStringTemplate = c.getString(R.string.component_Journey_Roadmap_Sections_StreetNetwork_Description_mode_walking);
                distanceLabel += String.format(walkingStringTemplate, distanceText);
                break;
            case "bike":
                if (network == null) {
                    final String bikeStringTemplate = c.getString(R.string.component_Journey_Roadmap_Sections_StreetNetwork_Description_mode_bike);
                    distanceLabel += String.format(bikeStringTemplate, distanceText);
                } else {
                    final String bssStringTemplate = c.getString(R.string.component_Journey_Roadmap_Sections_StreetNetwork_Description_mode_bss);
                    distanceLabel += String.format(bssStringTemplate, distanceText, network);
                }
                break;
            case "car":
                final String carStringTemplate = c.getString(R.string.component_Journey_Roadmap_Sections_StreetNetwork_Description_mode_car);
                distanceLabel += String.format(carStringTemplate, distanceText);
                break;
            default:
                break;
        }

        return distanceLabel;
    }

    static Map<String, Object> headerStyles = new HashMap<>();
    static {
        headerStyles.put("backgroundColor", Configuration.colors.getTertiary());
        headerStyles.put("paddingTop", 40);
    }

    static Map<String, Object> summaryStyles = new HashMap<>();
    static {
        summaryStyles.put("marginTop", -60);
    }
}
