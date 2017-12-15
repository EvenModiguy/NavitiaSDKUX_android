package org.kisio.NavitiaSDKUX.Components.Journey.Roadmap.Sections.PublicTransport.Description;

import android.text.Html;
import android.text.TextUtils;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.yoga.YogaAlign;

import org.joda.time.DateTime;
import org.kisio.NavitiaSDK.models.Disruption;
import org.kisio.NavitiaSDK.models.Period;
import org.kisio.NavitiaSDK.models.Section;
import org.kisio.NavitiaSDKUX.BusinessLogic.DisruptionLevel;
import org.kisio.NavitiaSDKUX.BusinessLogic.DisruptionMatcher;
import org.kisio.NavitiaSDKUX.Components.HorizontalContainerComponent;
import org.kisio.NavitiaSDKUX.Components.IconComponent;
import org.kisio.NavitiaSDKUX.Components.TextComponent;
import org.kisio.NavitiaSDKUX.Components.ViewComponent;
import org.kisio.NavitiaSDKUX.Config.Configuration;
import org.kisio.NavitiaSDKUX.R;
import org.kisio.NavitiaSDKUX.Util.Color;
import org.kisio.NavitiaSDKUX.Util.Metrics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@LayoutSpec
class DisruptionDescriptionComponentSpec {
    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop Section section,
        @Prop List<Disruption> disruptions) {

        final ViewComponent.Builder builder = ViewComponent.create(c);

        return builder
            .styles(containerStyles)
            .children(getDisruptionComponents(c, disruptions))
            .buildWithLayout();
    }

    private static Component<?>[] getDisruptionComponents(ComponentContext c, List<Disruption> disruptions) {
        List<Component<?>> components = new ArrayList<>();

        for (Disruption disruption: disruptions) {
            components.add(getDisruptionComponent(c, disruption));
        }

        return components.toArray(new Component<?>[components.size()]);
    }

    private static Component<?> getDisruptionComponent(ComponentContext c, Disruption disruption) {
        List<Component> disruptionBlocks = new ArrayList<>();

        DisruptionLevel disruptionLevel = DisruptionMatcher.getLevel(disruption);
        Map<String, Object> causeStyles = new HashMap<>(causeBaseStyles);
        Integer disruptionLevelColor = Color.getColorFromHexadecimal(disruptionLevel.getLevelColor());
        causeStyles.put("color", disruptionLevelColor);
        Map<String, Object> iconStyles = new HashMap<>(iconBaseStyles);
        iconStyles.put("color", disruptionLevelColor);

        disruptionBlocks.add(HorizontalContainerComponent.create(c)
            .styles(disruptionTitleStyles)
            .children(new Component<?>[]{
                IconComponent.create(c)
                    .styles(iconStyles)
                    .name(disruptionLevel.getIconName())
                    .build(),
                TextComponent.create(c)
                    .styles(causeStyles)
                    .text(disruption.getCause())
                    .build()
            })
            .build()
        );

        if (disruption.getMessages() != null && disruption.getMessages().size() > 0 && !TextUtils.isEmpty(disruption.getMessages().get(0).getText())) {
            disruptionBlocks.add(TextComponent.create(c)
                .styles(disruptionTextStyles)
                .text(Html.fromHtml(disruption.getMessages().get(0).getText()).toString())
                .build()
            );
        }

        String fromText = c.getString(R.string.component_Journey_Roadmap_Sections_PublicTransport_Description_Disruption_Description_Period_from);
        String toText = c.getString(R.string.component_Journey_Roadmap_Sections_PublicTransport_Description_Disruption_Description_Period_to);
        String undefinedToText = c.getString(R.string.component_Journey_Roadmap_Sections_PublicTransport_Description_Disruption_Description_Period_to_fallback);
        for (Period period : disruption.getApplicationPeriods()) {
            String beginText = fromText + " " + Metrics.shortDateText(new DateTime(Metrics.navitiaDate(period.getBegin())));
            String endText = undefinedToText;
            if (period.getEnd() != null) {
                endText = toText + " " + Metrics.shortDateText(new DateTime(Metrics.navitiaDate(period.getEnd())));
            }
            disruptionBlocks.add(TextComponent.create(c)
                .styles(disruptionPeriodStyles)
                .text(beginText + " " + endText)
                .build()
            );
        }

        Component[] disruptionBlocksArray = new Component[disruptionBlocks.size()];
        disruptionBlocksArray = disruptionBlocks.toArray(disruptionBlocksArray);
        return ViewComponent.create(c)
            .children(disruptionBlocksArray)
            .build();
    }

    static Map<String, Object> containerStyles = new HashMap<>();
    static {
        containerStyles.put("marginTop", 26);
    }

    static Map<String, Object> disruptionTitleStyles = new HashMap<>();
    static {
        disruptionTitleStyles.put("alignItems", YogaAlign.CENTER);
    }

    static Map<String, Object> iconBaseStyles = new HashMap<>();
    static {
        iconBaseStyles.put("fontSize", 14);
    }

    static Map<String, Object> causeBaseStyles = new HashMap<>();
    static {
        causeBaseStyles.put("marginLeft", 4);
        causeBaseStyles.put("fontSize", 12);
        causeBaseStyles.put("fontWeight", "bold");
    }

    static Map<String, Object> disruptionTextStyles = new HashMap<>();
    static {
        disruptionTextStyles.put("marginLeft", 18);
        disruptionTextStyles.put("marginTop", 13);
        disruptionTextStyles.put("marginBottom", 6);
        disruptionTextStyles.put("color", Configuration.colors.getGray());
        disruptionTextStyles.put("fontSize", 12);
    }

    static Map<String, Object> disruptionPeriodStyles = new HashMap<>();
    static {
        disruptionPeriodStyles.put("marginLeft", 18);
        disruptionPeriodStyles.put("marginTop", 6);
        disruptionPeriodStyles.put("fontSize", 12);
        disruptionPeriodStyles.put("fontWeight", "bold");
        disruptionPeriodStyles.put("color", Configuration.colors.getDarkText());
    }
}