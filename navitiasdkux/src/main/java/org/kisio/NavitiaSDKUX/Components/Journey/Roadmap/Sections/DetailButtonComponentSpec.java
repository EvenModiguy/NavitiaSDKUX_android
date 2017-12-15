package org.kisio.NavitiaSDKUX.Components.Journey.Roadmap.Sections;

import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropDefault;
import com.facebook.yoga.YogaAlign;

import org.kisio.NavitiaSDKUX.Components.HorizontalContainerComponent;
import org.kisio.NavitiaSDKUX.Components.IconComponent;
import org.kisio.NavitiaSDKUX.Components.Primitive.StylizedComponent;
import org.kisio.NavitiaSDKUX.Components.Primitive.BaseViewComponent;
import org.kisio.NavitiaSDKUX.Components.TextComponent;
import org.kisio.NavitiaSDKUX.Config.Configuration;

import java.util.HashMap;
import java.util.Map;

@LayoutSpec
class DetailButtonComponentSpec {
    @PropDefault static final Map<String, Object> styles = new HashMap<>();
    @PropDefault static final String text = "Détails";

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop(optional = true) String testKey,
        @Prop(optional = true) Map<String, Object> styles,
        @Prop(optional = true) String text,
        @Prop Boolean collapsed) {

        final ComponentLayout.ContainerBuilder builder = BaseViewComponent.create(c).testKey(testKey).child(
            HorizontalContainerComponent.create(c)
                .styles(detailsHeaderContainerStyle)
                .children(new Component<?>[] {
                    TextComponent.create(c)
                        .styles(detailsHeaderTitleStyle)
                        .text(text)
                        .build(),
                    IconComponent.create(c)
                        .styles(collapserWayIconStyles)
                        .name(collapsed ? "arrow-details-down" : "arrow-details-up")
                        .build()
                })
        );

        final ComponentLayout.Builder styledBuilder = StylizedComponent.applyStyles(builder, styles);
        return styledBuilder.build();
    }

    static Map<String, Object> detailsHeaderContainerStyle = new HashMap<>();
    static {
        detailsHeaderContainerStyle.put("paddingHorizontal", 5);
        detailsHeaderContainerStyle.put("marginBottom", 10);
        detailsHeaderContainerStyle.put("alignItems", YogaAlign.CENTER);
    }

    static Map<String, Object> collapserWayIconStyles = new HashMap<>();
    static {
        collapserWayIconStyles.put("color", Configuration.colors.getGray());
        collapserWayIconStyles.put("fontSize", 12);
        collapserWayIconStyles.put("marginRight", 5);
    }

    static Map<String, Object> detailsHeaderTitleStyle = new HashMap<>();
    static {
        detailsHeaderTitleStyle.put("color", Configuration.colors.getGray());
        detailsHeaderTitleStyle.put("fontSize", 13);
        detailsHeaderTitleStyle.put("marginRight", 5);
    }
}
