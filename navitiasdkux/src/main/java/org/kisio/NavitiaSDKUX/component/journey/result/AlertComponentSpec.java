package org.kisio.NavitiaSDKUX.component.journey.result;

import android.graphics.Color;

import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropDefault;

import org.kisio.NavitiaSDKUX.component.ContainerComponent;
import org.kisio.NavitiaSDKUX.component.primitive.StylizedComponent;
import org.kisio.NavitiaSDKUX.component.TextComponent;

import java.util.HashMap;
import java.util.Map;

enum AlertStatus {
    info,
    success,
    warning,
    error
}

@LayoutSpec
public class AlertComponentSpec {
    @PropDefault static final Map<String, Object> styles = new HashMap<>();
    @PropDefault static final AlertStatus status = AlertStatus.info;

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop(optional = true) Map<String, Object> styles,
        @Prop(optional = true) AlertStatus status,
        @Prop String text) {

        Map<String, Object> computedContainerStyles = new HashMap<>();
        computedContainerStyles.put("backgroundColor", statusBackgroundColors.get(status));
        computedContainerStyles.put("borderColor", statusForegroundColors.get(status));
        computedContainerStyles.put("borderWidth", 1);

        Map<String, Object> computedTextStyles = new HashMap<>();
        computedTextStyles.put("color", statusForegroundColors.get(status));

        final ContainerComponent.Builder builder = ContainerComponent.create(c);
        builder
            .children(new Component<?>[]{
                TextComponent.create(c)
                    .text(text)
                    .styles(computedTextStyles)
                    .build()
            })
            .styles(computedContainerStyles);
        final ComponentLayout.Builder styledBuilder = StylizedComponent.applyStyles(builder.withLayout(), styles);
        return styledBuilder.build();
    }

    static Map<AlertStatus, Integer> statusBackgroundColors = new HashMap<>();
    static {
        statusBackgroundColors.put(AlertStatus.info, Color.parseColor("#30708E"));
        statusBackgroundColors.put(AlertStatus.success, Color.parseColor("#3D753D"));
        statusBackgroundColors.put(AlertStatus.warning, Color.parseColor("#896D3A"));
        statusBackgroundColors.put(AlertStatus.error, Color.parseColor("#A84442"));
    }

    static Map<AlertStatus, Integer> statusForegroundColors = new HashMap<>();
    static {
        statusForegroundColors.put(AlertStatus.info, Color.parseColor("#D8EDF7"));
        statusForegroundColors.put(AlertStatus.success, Color.parseColor("#DDEFD8"));
        statusForegroundColors.put(AlertStatus.warning, Color.parseColor("#FCF7E2"));
        statusForegroundColors.put(AlertStatus.error, Color.parseColor("#F2DDDD"));
    }
}