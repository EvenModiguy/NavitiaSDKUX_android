package org.kisio.NavitiaSDKUX.Components.Journey.Roadmap.Steps.PublicTransportStepComponentParts;

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
class DisruptionDescriptionPartSpec {
    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop List<Disruption> disruptions) {

        return ViewComponent.create(c)
            .styles(containerStyles)
            .children(getDisruptionComponents(c, disruptions))
            .buildWithLayout();
    }

    private static Component[] getDisruptionComponents(ComponentContext c, List<Disruption> disruptions) {
        List<Component> components = new ArrayList<>();

        for (Disruption disruption: disruptions) {
            components.add(getDisruptionComponent(c, disruption));
        }

        return components.toArray(new Component[components.size()]);
    }

    private static Component getDisruptionComponent(ComponentContext c, Disruption disruption) {
        List<Component> disruptionBlocks = new ArrayList<>();

        DisruptionLevel disruptionLevel = DisruptionMatcher.getLevel(disruption);
        Map<String, Object> causeStyles = new HashMap<>(causeBaseStyles);
        Integer disruptionLevelColor = Color.getColorFromHexadecimal(disruptionLevel.getLevelColor());
        causeStyles.put("color", disruptionLevelColor);
        Map<String, Object> iconStyles = new HashMap<>(iconBaseStyles);
        iconStyles.put("color", disruptionLevelColor);

        disruptionBlocks.add(HorizontalContainerComponent.create(c)
            .styles(disruptionTitleStyles)
            .children(new Component[]{
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

        String fromText = c.getString(R.string.from);
        String toText = c.getString(R.string.to_period);
        String undefinedToText = c.getString(R.string.until_further_notice);
        for (Period period : disruption.getApplicationPeriods()) {
            String beginText = String.format("%1$s %2$s", fromText, Metrics.shortDateText(new DateTime(Metrics.navitiaDate(period.getBegin()))));
            String endText = undefinedToText;
            if (period.getEnd() != null) {
                endText = String.format("%1$s %2$s", toText, Metrics.shortDateText(new DateTime(Metrics.navitiaDate(period.getEnd()))));
            }
            disruptionBlocks.add(TextComponent.create(c)
                .styles(disruptionPeriodStyles)
                .text(String.format("%1$s %2$s", beginText, endText))
                .build()
            );
        }

        Component[] disruptionBlocksArray = new Component[disruptionBlocks.size()];
        disruptionBlocksArray = disruptionBlocks.toArray(disruptionBlocksArray);
        return ViewComponent.create(c)
            .styles(containerStyles)
            .children(disruptionBlocksArray)
            .build();
    }

    static Map<String, Object> containerStyles = new HashMap<>();
    static {
        containerStyles.put("marginTop", Configuration.metrics.margin);
    }

    static Map<String, Object> disruptionTitleStyles = new HashMap<>();
    static {
        disruptionTitleStyles.put("alignItems", YogaAlign.CENTER);
    }

    static Map<String, Object> iconBaseStyles = new HashMap<>();
    static {
        iconBaseStyles.put("fontSize", 18);
    }

    static Map<String, Object> causeBaseStyles = new HashMap<>();
    static {
        causeBaseStyles.put("marginLeft", Configuration.metrics.marginS);
        causeBaseStyles.put("fontSize", Configuration.metrics.textS);
        causeBaseStyles.put("fontWeight", "bold");
    }

    static Map<String, Object> disruptionTextStyles = new HashMap<>();
    static {
        disruptionTextStyles.put("marginLeft", 22);
        disruptionTextStyles.put("marginTop", Configuration.metrics.margin);
        disruptionTextStyles.put("marginBottom", Configuration.metrics.margin);
        disruptionTextStyles.put("color", Configuration.colors.getGray());
        disruptionTextStyles.put("fontSize", Configuration.metrics.textS);
    }

    static Map<String, Object> disruptionPeriodStyles = new HashMap<>();
    static {
        disruptionPeriodStyles.put("marginLeft", 22);
        disruptionPeriodStyles.put("marginTop", Configuration.metrics.margin);
        disruptionPeriodStyles.put("fontSize", Configuration.metrics.textS);
        disruptionPeriodStyles.put("fontWeight", "bold");
        disruptionPeriodStyles.put("color", Configuration.colors.getDarkText());
    }
}
