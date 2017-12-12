package org.kisio.NavitiaSDKUX.Components.Journey.Roadmap.Sections.LineDiagram;

import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropDefault;
import com.facebook.yoga.YogaAlign;
import com.facebook.yoga.YogaJustify;
import com.facebook.yoga.YogaPositionType;

import org.kisio.NavitiaSDKUX.Components.IconComponent;
import org.kisio.NavitiaSDKUX.Components.Primitive.BaseViewComponent;
import org.kisio.NavitiaSDKUX.Components.Primitive.StylizedComponent;

import java.util.HashMap;
import java.util.Map;

@LayoutSpec
public class StopPointIconComponentSpec {
    @PropDefault static Integer outerFontSize = 18;
    @PropDefault static Integer innerFontSize = 12;

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop Integer color,
        @Prop(optional = true) Integer outerFontSize,
        @Prop(optional = true) Integer innerFontSize) {

        final ComponentLayout.ContainerBuilder builder = BaseViewComponent.create(c);

        builder
            .child(
                getOuterCircleContainer(c, color, outerFontSize)
            )
            .child(
                getInnerCircleContainer(c, innerFontSize)
            );

        final ComponentLayout.Builder styledBuilder = StylizedComponent.applyStyles(builder, stopPointIconContainerStyle);
        return styledBuilder.build();
    }

    static private ComponentLayout.Builder getOuterCircleContainer(ComponentContext c, Integer color, Integer outerFontSize) {
        final ComponentLayout.ContainerBuilder builder = BaseViewComponent.create(c);

        Map<String, Object> outerCircleStyle = new HashMap<>();
        outerCircleStyle.put("color", color);
        outerCircleStyle.put("fontSize", outerFontSize);

        builder.child(
            IconComponent.create(c)
                .name("circle-filled")
                .styles(outerCircleStyle)
        );

        return StylizedComponent.applyStyles(builder, circleContainerStyle);
    }

    static private ComponentLayout.Builder getInnerCircleContainer(ComponentContext c, Integer innerFontSize) {
        final ComponentLayout.ContainerBuilder builder = BaseViewComponent.create(c);

        innerCircleStyle.put("fontSize", innerFontSize);

        builder.child(
            IconComponent.create(c)
                .name("circle-filled")
                .styles(innerCircleStyle)
        );

        return StylizedComponent.applyStyles(builder, circleContainerStyle);
    }

    static Map<String, Object> stopPointIconContainerStyle = new HashMap<>();
    static {
        stopPointIconContainerStyle.put("flexGrow", 1);
        stopPointIconContainerStyle.put("alignSelf", YogaAlign.STRETCH);
    }

    static Map<String, Object> circleContainerStyle = new HashMap<>();
    static {
        circleContainerStyle.put("position", YogaPositionType.ABSOLUTE);
        circleContainerStyle.put("start", 0);
        circleContainerStyle.put("top", 0);
        circleContainerStyle.put("end", 0);
        circleContainerStyle.put("bottom", 0);
        circleContainerStyle.put("alignItems", YogaAlign.CENTER);
        circleContainerStyle.put("justifyContent", YogaJustify.CENTER);
    }

    static Map<String, Object> innerCircleStyle = new HashMap<>();
    static {
        innerCircleStyle.put("color", android.graphics.Color.WHITE);
    }
}
