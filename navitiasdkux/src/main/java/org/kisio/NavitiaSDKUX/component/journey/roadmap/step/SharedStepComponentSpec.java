package org.kisio.NavitiaSDKUX.component.journey.roadmap.step;

import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;

import org.kisio.NavitiaSDK.models.Section;
import org.kisio.NavitiaSDKUX.component.CardComponent;
import org.kisio.NavitiaSDKUX.component.journey.roadmap.Roadmap2ColumnsLayout;
import org.kisio.NavitiaSDKUX.component.journey.roadmap.step.Parts.ModeIconPart;
import org.kisio.NavitiaSDKUX.component.TextComponent;
import org.kisio.NavitiaSDKUX.config.Configuration;

import java.util.HashMap;
import java.util.Map;

@LayoutSpec
public class SharedStepComponentSpec {
    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop(optional = true) String testKey,
        @Prop Section section,
        @Prop CharSequence description) {

        return CardComponent.create(c).child(
            Roadmap2ColumnsLayout.create(c)
                .styles(containerStyles)
                .testKey(testKey)
                .leftChildren(new Component[]{
                    ModeIconPart.create(c)
                        .section(section)
                        .build()
                })
                .rightChildren(new Component[]{
                    TextComponent.create(c)
                        .styles(instructionTextStyles)
                        .text(description)
                        .build()
                })
        ).buildWithLayout();
    }

    static Map<String, Object> containerStyles = new HashMap<>();
    static {
        containerStyles.put("paddingVertical", Configuration.metrics.margin);
    }

    static Map<String, Object> instructionTextStyles = new HashMap<>();
    static {
        instructionTextStyles.put("color", Configuration.colors.getDarkText());
        instructionTextStyles.put("fontSize", Configuration.metrics.text);
        instructionTextStyles.put("spacingMultiplier", 1.3);
    }
}
