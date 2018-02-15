package org.kisio.navitiasdkui.component.journey.result;

import android.graphics.Color;

import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropDefault;

import org.kisio.navitiasdkui.component.CardComponent;
import org.kisio.navitiasdkui.component.journey.result.loading.ShimCardContentPart;
import org.kisio.navitiasdkui.component.primitive.StylizedComponent;
import org.kisio.navitiasdkui.util.Configuration;

import java.util.HashMap;
import java.util.Map;

@LayoutSpec
public class LoadingComponentSpec {
    @PropDefault
    static final Map<String, Object> styles = new HashMap<>();

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop(optional = true) Map<String, Object> styles) {

        final CardComponent.Builder builder = CardComponent.create(c);
        builder
            .child(
                ShimCardContentPart.create(c)
            )
            .styles(listStyles);
        final ComponentLayout.Builder styledBuilder = StylizedComponent.applyStyles(builder.withLayout(), styles);
        return styledBuilder.build();
    }

    static Map<String, Object> listStyles = new HashMap<>();
    static {
        listStyles.put("backgroundColor", Color.WHITE);
        listStyles.put("padding", Configuration.metrics.marginL);
        listStyles.put("paddingTop", 9);
        listStyles.put("marginBottom", Configuration.metrics.margin);
    }
}





