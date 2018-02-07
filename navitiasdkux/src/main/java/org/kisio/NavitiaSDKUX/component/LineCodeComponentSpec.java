package org.kisio.NavitiaSDKUX.component;

import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropDefault;

import org.kisio.NavitiaSDK.models.Section;
import org.kisio.NavitiaSDKUX.component.primitive.BaseViewComponent;
import org.kisio.NavitiaSDKUX.component.primitive.StylizedComponent;
import org.kisio.NavitiaSDKUX.component.TextComponent;
import org.kisio.NavitiaSDKUX.component.ViewComponent;
import org.kisio.NavitiaSDKUX.config.Configuration;
import org.kisio.NavitiaSDKUX.util.Color;

import java.util.HashMap;
import java.util.Map;

@LayoutSpec
public class LineCodeComponentSpec {
    @PropDefault static final Map<String, Object> styles = new HashMap<>();

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop(optional = true) String testKey,
        @Prop(optional = true) Map<String, Object> styles,
        @Prop Section section) {

        final ComponentLayout.ContainerBuilder builder = BaseViewComponent.create(c).testKey(testKey);

        if (section.getDisplayInformations() != null && section.getDisplayInformations().getCode() != null) {
            String code = section.getDisplayInformations().getCode();
            Map<String, Object> codeStyles = new HashMap<>(codeBaseStyles);
            codeStyles.put("backgroundColor", Color.getColorFromHexadecimal(section.getDisplayInformations().getColor()));
            Map<String, Object> textStyles = new HashMap<>(textBaseStyles);
            textStyles.put("color", Color.getColorFromHexadecimal(section.getDisplayInformations().getTextColor()));

            builder.child(
                ViewComponent.create(c).children(new Component<?>[] {
                    TextComponent.create(c)
                        .text(code)
                        .styles(textStyles)
                        .build()
                })
            );

            final Map<String, Object> computedStyles = StylizedComponent.mergeStyles(codeStyles, styles);
            final ComponentLayout.Builder styledBuilder = StylizedComponent.applyStyles(builder, computedStyles);
            return styledBuilder.build();
        } else {
            return builder.build();
        }
    }

    static Map<String, Object> codeBaseStyles = new HashMap<>();
    static {
        codeBaseStyles.put("padding", 6);
    }

    static Map<String, Object> textBaseStyles = new HashMap<>();
    static {
        textBaseStyles.put("fontSize", Configuration.metrics.textS);
        textBaseStyles.put("fontWeight", "bold");
    }
}
