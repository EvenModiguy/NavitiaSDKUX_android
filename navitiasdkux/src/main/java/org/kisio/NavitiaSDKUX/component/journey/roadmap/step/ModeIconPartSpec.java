package org.kisio.NavitiaSDKUX.component.journey.roadmap.step;

import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.yoga.YogaAlign;

import org.kisio.NavitiaSDK.models.Section;
import org.kisio.NavitiaSDKUX.component.ModeComponent;
import org.kisio.NavitiaSDKUX.component.primitive.BaseViewComponent;
import org.kisio.NavitiaSDKUX.component.primitive.StylizedComponent;

import java.util.HashMap;
import java.util.Map;

@LayoutSpec
class ModeIconPartSpec {
    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop(optional = true) String testKey,
        @Prop Section section) {

        final ComponentLayout.ContainerBuilder builder = BaseViewComponent.create(c).testKey(testKey);

        builder.child(
            ModeComponent.create(c)
                .section(section)
        );

        final ComponentLayout.Builder styledBuilder = StylizedComponent.applyStyles(builder, containerStyles);
        return styledBuilder.build();
    }

    static Map<String, Object> containerStyles = new HashMap<>();
    static {
        containerStyles.put("marginTop", 14);
        containerStyles.put("alignItems", YogaAlign.CENTER);
    }
}
