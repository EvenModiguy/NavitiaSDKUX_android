package org.kisio.NavitiaSDKUX.Components.Journey.Roadmap.Sections.StreetNetwork;

import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropDefault;
import com.facebook.yoga.YogaAlign;

import org.kisio.NavitiaSDK.models.Section;
import org.kisio.NavitiaSDKUX.Components.Journey.Roadmap.Sections.PublicTransport.Description.ModeIconComponent;
import org.kisio.NavitiaSDKUX.Components.Journey.Roadmap.Sections.SectionRowLayoutComponent;
import org.kisio.NavitiaSDKUX.Components.TextComponent;
import org.kisio.NavitiaSDKUX.Components.ViewComponent;
import org.kisio.NavitiaSDKUX.Config.Configuration;

import java.util.HashMap;
import java.util.Map;

@LayoutSpec
public class DescriptionComponentSpec {
    @PropDefault
    static final Map<String, Object> styles = new HashMap<>();

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop(optional = true) String testKey,
        @Prop(optional = true) Map<String, Object> styles,
        @Prop Section section,
        @Prop String description) {

        final ViewComponent.Builder builder = ViewComponent.create(c)
            .testKey(testKey)
            .styles(styles)
            .children(new Component<?>[]{
                SectionRowLayoutComponent.create(c)
                    .styles(containerStyles)
                    .firstComponent(
                        ModeIconComponent.create(c)
                            .section(section)
                            .styles(modeIconStyles)
                            .build())
                    .thirdComponent(
                        TextComponent.create(c)
                            .text(description)
                            .styles(labelStyles)
                            .build())
                    .build()
            });

        return builder.buildWithLayout();
    }

    static Map<String, Object> containerStyles = new HashMap<>();
    static {
        containerStyles.put("paddingHorizontal", 4);
        containerStyles.put("paddingVertical", 24);
        containerStyles.put("alignItems", YogaAlign.CENTER);
    }

    static Map<String, Object> labelStyles = new HashMap<>();
    static {
        labelStyles.put("fontSize", 15);
        labelStyles.put("color", Configuration.colors.getDarkGray());
    }

    static Map<String, Object> modeIconStyles = new HashMap<>();
    static {
        modeIconStyles.put("alignItems", YogaAlign.CENTER);
    }
}
