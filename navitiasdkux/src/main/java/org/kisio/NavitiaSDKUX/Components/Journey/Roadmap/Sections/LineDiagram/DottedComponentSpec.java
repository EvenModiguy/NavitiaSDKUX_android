package org.kisio.NavitiaSDKUX.Components.Journey.Roadmap.Sections.LineDiagram;

import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.yoga.YogaPositionType;

import org.kisio.NavitiaSDKUX.Components.Primitive.StylizedComponent;
import org.kisio.NavitiaSDKUX.Components.Primitive.BaseViewComponent;

import java.util.HashMap;
import java.util.Map;

@LayoutSpec
public class DottedComponentSpec {
    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop Integer color) {

        final ComponentLayout.ContainerBuilder builder = BaseViewComponent.create(c);

        Map<String, Object> lineStyles = new HashMap<>(lineBaseStyles);
        lineStyles.put("borderColor", color);

        final ComponentLayout.Builder styledBuilder = StylizedComponent.applyStyles(builder, lineStyles);
        return styledBuilder.build();
    }

    static Map<String, Object> lineBaseStyles = new HashMap<>();
    static {
        lineBaseStyles.put("position", YogaPositionType.ABSOLUTE);
        lineBaseStyles.put("start", 58);
        lineBaseStyles.put("top", 16);
        lineBaseStyles.put("bottom", 16);
        lineBaseStyles.put("width", 20);
        lineBaseStyles.put("borderLeftWidth", 4);
    }
}
