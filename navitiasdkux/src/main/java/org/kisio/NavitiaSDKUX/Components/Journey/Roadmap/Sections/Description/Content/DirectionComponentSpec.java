package org.kisio.NavitiaSDKUX.Components.Journey.Roadmap.Sections.Description.Content;

import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.yoga.YogaAlign;

import org.kisio.NavitiaSDK.models.Section;
import org.kisio.NavitiaSDKUX.Components.IconComponent;
import org.kisio.NavitiaSDKUX.Components.Primitive.HorizontalViewComponent;
import org.kisio.NavitiaSDKUX.Components.Primitive.StylizedComponent;
import org.kisio.NavitiaSDKUX.Components.TextComponent;

import java.util.HashMap;
import java.util.Map;

@LayoutSpec
class DirectionComponentSpec {
    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop Section section) {

        final ComponentLayout.ContainerBuilder builder = HorizontalViewComponent.create(c);

        builder
            .child(
                IconComponent.create(c)
                    .name("direction")
                    .styles(iconStyles))
            .child(
                TextComponent.create(c)
                    .text(section.getDisplayInformations().getDirection())
                    .styles(directionStyles));

        final ComponentLayout.Builder styledBuilder = StylizedComponent.applyStyles(builder, containerStyles);
        return styledBuilder.build();
    }

    static Map<String, Object> containerStyles = new HashMap<>();
    static {
        containerStyles.put("alignItems", YogaAlign.CENTER);
    }

    static Map<String, Object> iconStyles = new HashMap<>();
    static {
        iconStyles.put("fontSize", 12);
        iconStyles.put("marginRight", 5);
    }

    static Map<String, Object> directionStyles = new HashMap<>();
    static {
        directionStyles.put("fontSize", 15);
    }
}
