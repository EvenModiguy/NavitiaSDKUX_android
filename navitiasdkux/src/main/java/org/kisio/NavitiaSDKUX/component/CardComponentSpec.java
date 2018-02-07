package org.kisio.NavitiaSDKUX.component;

import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropDefault;
import com.facebook.litho.widget.Card;

import org.kisio.NavitiaSDKUX.component.primitive.StylizedComponent;

import java.util.HashMap;
import java.util.Map;

@LayoutSpec
public class CardComponentSpec {
    @PropDefault
    static final Map<String, Object> styles = new HashMap<>();

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop(optional = true) Map<String, Object> styles,
        @Prop Component<?> child) {

        final Card.Builder builder = Card.create(c)
            .cornerRadiusDip(0);
        builder.content(child);
        final ComponentLayout.Builder styledBuilder = StylizedComponent.applyStyles(builder.withLayout(), styles);
        return styledBuilder.build();
    }
}