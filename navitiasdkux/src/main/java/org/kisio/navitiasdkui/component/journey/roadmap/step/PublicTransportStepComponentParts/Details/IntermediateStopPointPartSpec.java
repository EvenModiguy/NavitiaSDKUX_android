package org.kisio.navitiasdkui.component.journey.roadmap.step.PublicTransportStepComponentParts.Details;

import android.text.TextUtils;

import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropDefault;

import org.kisio.NavitiaSDK.models.StopDateTime;
import org.kisio.navitiasdkui.component.journey.roadmap.Roadmap3ColumnsLayout;
import org.kisio.navitiasdkui.component.journey.roadmap.step.PublicTransportStepComponentParts.LineDiagram.StopPointIconPart;
import org.kisio.navitiasdkui.component.primitive.BaseViewComponent;
import org.kisio.navitiasdkui.component.primitive.StylizedComponent;
import org.kisio.navitiasdkui.component.TextComponent;
import org.kisio.navitiasdkui.config.Configuration;

import java.util.HashMap;
import java.util.Map;

@LayoutSpec
class IntermediateStopPointPartSpec {
    @PropDefault
    static final Map<String, Object> styles = new HashMap<>();

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop(optional = true) String testKey,
        @Prop(optional = true) Map<String, Object> styles,
        @Prop StopDateTime stopDateTime,
        @Prop Integer color) {

        final ComponentLayout.ContainerBuilder builder = BaseViewComponent.create(c).testKey(testKey);

        builder.child(
            Roadmap3ColumnsLayout.create(c)
                .styles(containerStyles)
                .middleChildren(new Component[]{
                    StopPointIconPart.create(c)
                        .color(color)
                        .outerFontSize(13)
                        .innerFontSize(0)
                        .build()
                })
                .rightChildren(new Component[]{
                    TextComponent.create(c)
                        .styles(stopPointLabelStyles)
                        .text(stopDateTime.getStopPoint().getLabel())
                        .build()
                })
        );

        final ComponentLayout.Builder styledBuilder = StylizedComponent.applyStyles(builder, styles);
        return styledBuilder.build();
    }

    static Map<String, Object> containerStyles = new HashMap<>();
    static {
        containerStyles.put("paddingBottom", Configuration.metrics.margin);
    }

    static Map<String, Object> stopPointLabelStyles = new HashMap<>();
    static {
        stopPointLabelStyles.put("fontSize", Configuration.metrics.textS);
        stopPointLabelStyles.put("fontWeight", "bold");
        stopPointLabelStyles.put("color", Configuration.colors.getDarkerGray());
        stopPointLabelStyles.put("paddingLeft", 5);
        stopPointLabelStyles.put("paddingRight", 10);
        stopPointLabelStyles.put("maxLines", 1);
        stopPointLabelStyles.put("ellipsis", TextUtils.TruncateAt.END);
    }
}
