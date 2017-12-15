package org.kisio.NavitiaSDKUX.Components.Journey.Roadmap.Sections;

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
import com.facebook.litho.annotations.PropDefault;
import com.facebook.yoga.YogaAlign;

import org.kisio.NavitiaSDK.models.Disruption;
import org.kisio.NavitiaSDK.models.Section;
import org.kisio.NavitiaSDKUX.BusinessLogic.Modes;
import org.kisio.NavitiaSDKUX.BusinessLogic.SectionStopPointType;
import org.kisio.NavitiaSDKUX.Components.ContainerComponent;
import org.kisio.NavitiaSDKUX.Components.HorizontalContainerComponent;
import org.kisio.NavitiaSDKUX.Components.Journey.Roadmap.Roadmap2ColumnsLayout;
import org.kisio.NavitiaSDKUX.Components.Journey.Roadmap.Sections.LineDiagram.PlainComponent;
import org.kisio.NavitiaSDKUX.Components.Journey.Roadmap.Sections.PublicTransport.Description.DisruptionDescriptionComponent;
import org.kisio.NavitiaSDKUX.Components.Journey.Roadmap.Sections.PublicTransport.Description.ModeIconComponent;
import org.kisio.NavitiaSDKUX.Components.Journey.Roadmap.Sections.PublicTransport.DetailsComponent;
import org.kisio.NavitiaSDKUX.Components.LineCodeWithDisruptionStatusComponent;
import org.kisio.NavitiaSDKUX.Components.Primitive.StylizedComponent;
import org.kisio.NavitiaSDKUX.Components.TextComponent;
import org.kisio.NavitiaSDKUX.Components.ViewComponent;
import org.kisio.NavitiaSDKUX.Config.Configuration;
import org.kisio.NavitiaSDKUX.R;
import org.kisio.NavitiaSDKUX.Util.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@LayoutSpec
public class PublicTransportComponentSpec {
    @PropDefault static final Map<String, Object> styles = new HashMap<>();

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop(optional = true) String testKey,
        @Prop(optional = true) Map<String, Object> styles,
        @Prop(optional = true) Integer waitingDuration,
        @Prop Section section,
        @Prop List<Disruption> disruptions) {

        return ViewComponent.create(c).testKey(testKey)
            .styles(StylizedComponent.mergeStyles(containerStyles, styles))
            .children(new Component[]{
                ViewComponent.create(c)
                    .children(new Component[]{
                        Roadmap2ColumnsLayout.create(c)
                            .leftChildren(new Component[]{
                                ModeIconComponent.create(c)
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
                        PlainComponent.create(c)
                            .color(Color.getColorFromHexadecimal(section.getDisplayInformations().getColor()))
                            .build(),
                        ViewComponent.create(c)
                            .children(new Component[]{
                                StopPointComponent.create(c)
                                    .color(Color.getColorFromHexadecimal(section.getDisplayInformations().getColor()))
                                    .section(section)
                                    .sectionWay(SectionStopPointType.departure)
                                    .build(),
                                ContainerComponent.create(c)
                                    .styles(bodyContainerStyles)
                                    .children(new Component[] {
                                        DetailsComponent.create(c)
                                            .section(section)
                                            .build()
                                    })
                                    .build(),
                                StopPointComponent.create(c)
                                    .color(Color.getColorFromHexadecimal(section.getDisplayInformations().getColor()))
                                    .section(section)
                                    .sectionWay(SectionStopPointType.arrival)
                                    .build()
                            })
                            .build()
                    })
                    .build()
            })
            .buildWithLayout();
    }

    @NonNull
    private static Component[] getRightChildren(ComponentContext c, List<Disruption> disruptions, Section section, Integer waitingDuration) {
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();

        String at = c.getString(R.string.component_Journey_Roadmap_Sections_PublicTransport_instructions_at) + " ";
        SpannableString atSpannableString = new SpannableString(at);
        stringBuilder.append(atSpannableString);

        String departure = section.getFrom().getName() + "\n";
        SpannableString departureSpannableString = new SpannableString(departure);
        departureSpannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, departure.length(), 0);
        stringBuilder.append(departureSpannableString);

        String inDirection = c.getString(R.string.component_Journey_Roadmap_Sections_PublicTransport_instructions_direction) + " ";
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
                        .text(c.getString(R.string.component_Journey_Roadmap_Sections_PublicTransport_instructions_take) + " " + Modes.getPhysicalMode(section))
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
                DisruptionDescriptionComponent.create(c)
                    .disruptions(disruptions)
                    .build()
            );
        }

        if (waitingDuration != null && waitingDuration > 0) {
            rightChildrenList.add(
                WaitingComponent.create(c)
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
        containerStyles.put("paddingVertical", 10);
    }

    static Map<String, Object> modeLineLabelStyles = new HashMap<>();
    static {
        modeLineLabelStyles.put("alignItems", YogaAlign.CENTER);
    }

    static Map<String, Object> instructionTextStyles = new HashMap<>();
    static {
        instructionTextStyles.put("color", Configuration.colors.getDarkText());
        instructionTextStyles.put("fontSize", 17);
        instructionTextStyles.put("spacingMultiplier", 1.3);
    }

    static Map<String, Object> modeStyles = new HashMap<>(instructionTextStyles);
    static {
        modeStyles.put("marginRight", 5);
    }

    static Map<String, Object> textSpacerStyles = new HashMap<>();
    static {
        textSpacerStyles.put("marginBottom", 5);
    }

    static Map<String, Object> bodyContainerStyles = new HashMap<>();
    static {
        bodyContainerStyles.put("paddingVertical", 12);
        bodyContainerStyles.put("paddingHorizontal", 0);
    }
}
