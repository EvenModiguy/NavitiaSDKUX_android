package org.kisio.NavitiaSDKUX.Components.Journey.Results;


import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropDefault;

import org.joda.time.DateTime;
import org.kisio.NavitiaSDKUX.Components.Primitive.ButtonComponent;
import org.kisio.NavitiaSDKUX.Components.Primitive.StylizedComponent;
import org.kisio.NavitiaSDKUX.Components.TextComponent;
import org.kisio.NavitiaSDKUX.Config.Configuration;
import org.kisio.NavitiaSDKUX.R;
import org.kisio.NavitiaSDKUX.Util.Metrics;

import java.util.HashMap;
import java.util.Map;

@LayoutSpec
public class DateTimeButtonComponentSpec {
    @PropDefault static final Map<String, Object> styles = new HashMap<>();
    @PropDefault static final String datetimeRepresents = "departure";

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop(optional = true) String testKey,
        @Prop(optional = true) Map<String, Object> styles,
        @Prop DateTime datetime,
        @Prop(optional = true) String datetimeRepresents) {

        final ComponentLayout.ContainerBuilder builder = ButtonComponent.create(c).testKey(testKey);
        builder
            .child(
                TextComponent.create(c)
                    .styles(textStyles)
                    .text(c.getString(datetimeRepresentsLabel.get(datetimeRepresents)) + " " + Metrics.longDateText(datetime))
            );
        final Map<String, Object> computedStyles = StylizedComponent.mergeStyles(buttonStyles, styles);
        final ComponentLayout.Builder styledBuilder = StylizedComponent.applyStyles(builder, computedStyles);
        return styledBuilder.build();
    }

    static Map<String, Integer> datetimeRepresentsLabel = new HashMap<>();
    static {
        datetimeRepresentsLabel.put("departure", R.string.departure_with_colon);
        datetimeRepresentsLabel.put("arrival", R.string.arrival_with_colon);
    }

    static Map<String, Object> buttonStyles = new HashMap<>();
    static {
        buttonStyles.put("paddingTop", Configuration.metrics.marginL);
        buttonStyles.put("paddingLeft", 0);
        buttonStyles.put("paddingRight", 0);
        buttonStyles.put("paddingBottom", Configuration.metrics.margin);
    }

    static Map<String, Object> textStyles = new HashMap<>();
    static {
        textStyles.put("color", Configuration.colors.getTertiaryText());
        textStyles.put("fontWeight", "bold");
    }
}
