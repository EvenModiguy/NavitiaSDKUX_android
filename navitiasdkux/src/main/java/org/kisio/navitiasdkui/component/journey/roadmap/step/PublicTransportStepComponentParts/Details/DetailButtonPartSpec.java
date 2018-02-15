package org.kisio.navitiasdkui.component.journey.roadmap.step.PublicTransportStepComponentParts.Details;

import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropDefault;
import com.facebook.yoga.YogaAlign;

import org.kisio.navitiasdkui.component.HorizontalContainerComponent;
import org.kisio.navitiasdkui.component.IconComponent;
import org.kisio.navitiasdkui.component.primitive.BaseViewComponent;
import org.kisio.navitiasdkui.component.primitive.StylizedComponent;
import org.kisio.navitiasdkui.component.TextComponent;
import org.kisio.navitiasdkui.util.Configuration;
import org.kisio.navitiasdkui.R;

import java.util.HashMap;
import java.util.Map;

@LayoutSpec
class DetailButtonPartSpec {
    @PropDefault static final Map<String, Object> styles = new HashMap<>();

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop(optional = true) String testKey,
        @Prop(optional = true) Map<String, Object> styles,
        @Prop(optional = true) String text,
        @Prop Boolean collapsed) {

        if (text == null) {
            text = c.getString(R.string.details);
        }

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
        detailsHeaderContainerStyle.put("paddingHorizontal", Configuration.metrics.marginS);
        detailsHeaderContainerStyle.put("alignItems", YogaAlign.CENTER);
    }

    static Map<String, Object> collapserWayIconStyles = new HashMap<>();
    static {
        collapserWayIconStyles.put("color", Configuration.colors.getGray());
        collapserWayIconStyles.put("fontSize", Configuration.metrics.textS);
        collapserWayIconStyles.put("marginRight", Configuration.metrics.marginS);
    }

    static Map<String, Object> detailsHeaderTitleStyle = new HashMap<>();
    static {
        detailsHeaderTitleStyle.put("color", Configuration.colors.getGray());
        detailsHeaderTitleStyle.put("fontSize", Configuration.metrics.textS);
        detailsHeaderTitleStyle.put("marginRight", Configuration.metrics.marginS);
    }
}
