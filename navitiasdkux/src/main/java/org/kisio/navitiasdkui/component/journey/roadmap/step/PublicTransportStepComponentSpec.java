package org.kisio.navitiasdkui.component.journey.roadmap.step;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;

import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.yoga.YogaAlign;

import org.kisio.NavitiaSDK.models.Disruption;
import org.kisio.NavitiaSDK.models.Section;
import org.kisio.navitiasdkui.business.Modes;
import org.kisio.navitiasdkui.business.SectionStopPointType;
import org.kisio.navitiasdkui.component.CardComponent;
import org.kisio.navitiasdkui.component.ContainerComponent;
import org.kisio.navitiasdkui.component.HorizontalContainerComponent;
import org.kisio.navitiasdkui.component.journey.roadmap.Roadmap2ColumnsLayout;
import org.kisio.navitiasdkui.component.journey.roadmap.step.Parts.ModeIconPart;
import org.kisio.navitiasdkui.component.journey.roadmap.step.PublicTransportStepComponentParts.DetailsPart;
import org.kisio.navitiasdkui.component.journey.roadmap.step.PublicTransportStepComponentParts.DisruptionDescriptionPart;
import org.kisio.navitiasdkui.component.journey.roadmap.step.PublicTransportStepComponentParts.LineDiagram.PlainPart;
import org.kisio.navitiasdkui.component.journey.roadmap.step.PublicTransportStepComponentParts.StopPointPart;
import org.kisio.navitiasdkui.component.journey.roadmap.step.PublicTransportStepComponentParts.WaitingPart;
import org.kisio.navitiasdkui.component.LineCodeWithDisruptionStatusComponent;
import org.kisio.navitiasdkui.component.TextComponent;
import org.kisio.navitiasdkui.component.ViewComponent;
import org.kisio.navitiasdkui.config.Configuration;
import org.kisio.navitiasdkui.R;
import org.kisio.navitiasdkui.util.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@LayoutSpec
public class PublicTransportStepComponentSpec {
    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop(optional = true) String testKey,
        @Prop(optional = true) Integer waitingDuration,
        @Prop Section section,
        @Prop List<Disruption> disruptions) {

        return CardComponent.create(c).child(
            ViewComponent.create(c).testKey(testKey)
                .styles(containerStyles)
                .children(new Component[]{
                    ViewComponent.create(c)
                        .children(new Component[]{
                            Roadmap2ColumnsLayout.create(c)
                                .leftChildren(new Component[]{
                                    ModeIconPart.create(c)
                                        .section(section)
                                        .build()
                                })
                                .rightChildren(
                                    getRightChildren(c, disruptions, section, waitingDuration)
                                )
                                .build()
                        })
                        .build(),
                    ViewComponent.create(c)
                        .styles(diagramContainerStyles)
                        .children(new Component[]{
                            PlainPart.create(c)
                                .color(Color.getColorFromHexadecimal(section.getDisplayInformations().getColor()))
                                .build(),
                            ViewComponent.create(c)
                                .children(new Component[]{
                                    StopPointPart.create(c)
                                        .color(Color.getColorFromHexadecimal(section.getDisplayInformations().getColor()))
                                        .section(section)
                                        .sectionWay(SectionStopPointType.departure)
                                        .build(),
                                    ContainerComponent.create(c)
                                        .styles(bodyContainerStyles)
                                        .children(new Component[] {
                                            DetailsPart.create(c)
                                                .section(section)
                                                .build()
                                        })
                                        .build(),
                                    StopPointPart.create(c)
                                        .color(Color.getColorFromHexadecimal(section.getDisplayInformations().getColor()))
                                        .section(section)
                                        .sectionWay(SectionStopPointType.arrival)
                                        .build()
                                })
                                .build()
                        })
                        .build()
                })
        ).buildWithLayout();
    }

    @NonNull
    private static Component[] getRightChildren(ComponentContext c, List<Disruption> disruptions, Section section, Integer waitingDuration) {
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();

        String at = String.format("%s ", c.getString(R.string.at));
        SpannableString atSpannableString = new SpannableString(at);
        stringBuilder.append(atSpannableString);

        String departure = String.format("%s\n", section.getFrom().getName());
        SpannableString departureSpannableString = new SpannableString(departure);
        departureSpannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, departure.length(), 0);
        stringBuilder.append(departureSpannableString);

        String inDirection = String.format("%s ", c.getString(R.string.in_the_direction_of));
        SpannableString inDirectionSpannableString = new SpannableString(inDirection);
        stringBuilder.append(inDirectionSpannableString);

        String direction = section.getDisplayInformations().getDirection();
        SpannableString directionSpannableString = new SpannableString(direction);
        directionSpannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, direction.length(), 0);
        stringBuilder.append(directionSpannableString);

        List<Component> rightChildrenList = new ArrayList<>();
        rightChildrenList.addAll(Arrays.asList(
            HorizontalContainerComponent.create(c)
                .styles(modeLineLabelStyles)
                .children(new Component[]{
                    TextComponent.create(c)
                        .text(String.format("%1$s %2$s", c.getString(R.string.take_the), Modes.getPhysicalMode(section)))
                        .styles(modeStyles)
                        .build(),
                    LineCodeWithDisruptionStatusComponent.create(c)
                        .disruptions(disruptions)
                        .section(section)
                        .build()
                })
                .build(),
            TextComponent.create(c)
                .styles(instructionTextStyles)
                .text(stringBuilder)
                .build()
        ));

        if (disruptions.size() > 0) {
            rightChildrenList.add(
                DisruptionDescriptionPart.create(c)
                    .disruptions(disruptions)
                    .build()
            );
        }

        if (waitingDuration != null && waitingDuration > 0) {
            rightChildrenList.add(
                WaitingPart.create(c)
                    .duration(waitingDuration)
                    .build()
            );
        }

        return rightChildrenList.toArray(new Component[rightChildrenList.size()]);
    }

    static Map<String, Object> diagramContainerStyles = new HashMap<>();
    static {
        diagramContainerStyles.put("marginTop", 20);
    }

    static Map<String, Object> containerStyles = new HashMap<>();
    static {
        containerStyles.put("paddingVertical", Configuration.metrics.margin);
    }

    static Map<String, Object> modeLineLabelStyles = new HashMap<>();
    static {
        modeLineLabelStyles.put("alignItems", YogaAlign.CENTER);
    }

    static Map<String, Object> instructionTextStyles = new HashMap<>();
    static {
        instructionTextStyles.put("color", Configuration.colors.getDarkText());
        instructionTextStyles.put("fontSize", Configuration.metrics.text);
        instructionTextStyles.put("spacingMultiplier", 1.3);
    }

    static Map<String, Object> modeStyles = new HashMap<>(instructionTextStyles);
    static {
        modeStyles.put("marginRight", 5);
    }

    static Map<String, Object> textSpacerStyles = new HashMap<>();
    static {
        textSpacerStyles.put("marginBottom", Configuration.metrics.marginS);
    }

    static Map<String, Object> bodyContainerStyles = new HashMap<>();
    static {
        bodyContainerStyles.put("paddingVertical", Configuration.metrics.margin);
        bodyContainerStyles.put("paddingHorizontal", 0);
    }

    static Map<String, Object> modeIconStyles = new HashMap<>();
    static {
        modeIconStyles.put("alignItems", YogaAlign.CENTER);
        modeIconStyles.put("marginTop", 14);
    }
}
