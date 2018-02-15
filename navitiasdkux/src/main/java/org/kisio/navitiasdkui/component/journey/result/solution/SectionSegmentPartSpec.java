package org.kisio.navitiasdkui.component.journey.result.solution;

import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropDefault;

import org.kisio.NavitiaSDK.models.Section;
import org.kisio.navitiasdkui.component.primitive.BaseViewComponent;
import org.kisio.navitiasdkui.component.primitive.StylizedComponent;
import org.kisio.navitiasdkui.util.Configuration;
import org.kisio.navitiasdkui.util.Color;

import java.util.HashMap;
import java.util.Map;

@LayoutSpec
public class SectionSegmentPartSpec {
    @PropDefault static final Map<String, Object> styles = new HashMap<>();

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop(optional = true) String testKey,
        @Prop(optional = true) Map<String, Object> styles,
        @Prop Section section) {

        Integer color = Configuration.colors.getDarkerGray();
        if (section.getDisplayInformations() != null) {
            color = Color.getColorFromHexadecimal(section.getDisplayInformations().getColor());
        }

        final ComponentLayout.ContainerBuilder builder = BaseViewComponent.create(c).testKey(testKey);
        Map<String, Object> containerStyles = new HashMap<>(containerBaseStyles);
        containerStyles.put("backgroundColor", color);
        final Map<String, Object> computedStyles = StylizedComponent.mergeStyles(containerStyles, styles);
        final ComponentLayout.Builder styledBuilder = StylizedComponent.applyStyles(builder, computedStyles);
        return styledBuilder.build();
    }

    static Map<String, Object> containerBaseStyles = new HashMap<>();
    static {
        containerBaseStyles.put("height", 5);
    }
}
