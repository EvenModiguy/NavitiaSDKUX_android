package org.kisio.NavitiaSDKUX.Components.Journey.Roadmap.Sections.Description.Details;

import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropDefault;

import org.kisio.NavitiaSDK.models.StopDateTime;
import org.kisio.NavitiaSDKUX.Components.Journey.Roadmap.Sections.Diagram.LineDiagramForIntermediateStopPointComponent;
import org.kisio.NavitiaSDKUX.Components.Journey.Roadmap.Sections.Diagram.LineDiagramStopPointIconComponent;
import org.kisio.NavitiaSDKUX.Components.Journey.Roadmap.Sections.LayoutComponent;
import org.kisio.NavitiaSDKUX.Components.Primitive.StylizedComponent;
import org.kisio.NavitiaSDKUX.Components.Primitive.ViewComponent;

import java.util.HashMap;
import java.util.Map;

@LayoutSpec
class IntermediateStopPointComponentSpec {
    @PropDefault
    static final Map<String, Object> styles = new HashMap<>();

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop(optional = true) String testKey,
        @Prop(optional = true) Map<String, Object> styles,
        @Prop StopDateTime stopDateTime,
        @Prop String color) {

        final ComponentLayout.ContainerBuilder builder = ViewComponent.create(c).testKey(testKey);

        builder.child(
            LayoutComponent.create(c)
                .secondComponent(
                    LineDiagramForIntermediateStopPointComponent.create(c)
                        .color(color)
                        .build()
                )
        );

        final ComponentLayout.Builder styledBuilder = StylizedComponent.applyStyles(builder, styles);
        return styledBuilder.build();
    }
}