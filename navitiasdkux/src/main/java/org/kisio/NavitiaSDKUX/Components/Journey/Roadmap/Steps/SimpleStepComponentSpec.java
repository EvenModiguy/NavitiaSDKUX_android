package org.kisio.NavitiaSDKUX.Components.Journey.Roadmap.Steps;

import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.yoga.YogaAlign;
import com.facebook.yoga.YogaJustify;

import org.kisio.NavitiaSDK.models.Section;
import org.kisio.NavitiaSDKUX.Components.Journey.Roadmap.Roadmap2ColumnsLayout;
import org.kisio.NavitiaSDKUX.Components.ModeComponent;
import org.kisio.NavitiaSDKUX.Components.TextComponent;
import org.kisio.NavitiaSDKUX.Components.ViewComponent;
import org.kisio.NavitiaSDKUX.Config.Configuration;

import java.util.HashMap;
import java.util.Map;

@LayoutSpec
public class SimpleStepComponentSpec {
    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop(optional = true) String testKey,
        @Prop Section section,
        @Prop CharSequence description) {

        return Roadmap2ColumnsLayout.create(c)
            .testKey(testKey)
            .leftChildren(new Component[]{
                ViewComponent.create(c)
                    .styles(modeContainerStyles)
                    .children(new Component[]{
                        ModeComponent.create(c)
                            .section(section)
                            .build()
                    })
                    .build()
            })
            .rightChildren(new Component[]{
                TextComponent.create(c)
                    .styles(instructionTextStyles)
                    .text(description)
                    .build()
            })
            .buildWithLayout();
    }

    static Map<String, Object> modeContainerStyles = new HashMap<>();
    static {
        modeContainerStyles.put("alignItems", YogaAlign.CENTER);
        modeContainerStyles.put("justifyContent", YogaJustify.CENTER);
        modeContainerStyles.put("flexGrow", 1);
    }

    static Map<String, Object> instructionTextStyles = new HashMap<>();
    static {
        instructionTextStyles.put("color", Configuration.colors.getDarkText());
        instructionTextStyles.put("fontSize", Configuration.metrics.text);
        instructionTextStyles.put("spacingMultiplier", 1.3);
    }
}
