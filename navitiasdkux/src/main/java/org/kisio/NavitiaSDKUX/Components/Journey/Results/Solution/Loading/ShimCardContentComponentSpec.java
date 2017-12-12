package org.kisio.NavitiaSDKUX.Components.Journey.Results.Solution.Loading;

import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.yoga.YogaAlign;
import com.facebook.yoga.YogaJustify;

import org.kisio.NavitiaSDKUX.Components.Primitive.BaseViewComponent;
import org.kisio.NavitiaSDKUX.Components.Primitive.HorizontalViewComponent;
import org.kisio.NavitiaSDKUX.Components.Primitive.StylizedComponent;
import org.kisio.NavitiaSDKUX.Components.SeparatorComponent;
import org.kisio.NavitiaSDKUX.Config.Configuration;

import java.util.HashMap;
import java.util.Map;

@LayoutSpec
class ShimCardContentComponentSpec {
    @OnCreateLayout
    static ComponentLayout onCreateLayout(ComponentContext c) {

        final ComponentLayout.ContainerBuilder builder = BaseViewComponent.create(c);
        builder
            .child(
                getJourneyHeaderComponent(c)
            )
            .child(
                SeparatorComponent.create(c)
            )
            .child(
                getJourneyFriezeComponent(c)
            )
            .child(
                ShimComponent.create(c)
                    .width(181)
                    .height(17)
            )
        ;
        return builder.build();
    }

    static ComponentLayout.ContainerBuilder getJourneyHeaderComponent(ComponentContext c) {
        final ComponentLayout.ContainerBuilder durationBuilder = BaseViewComponent.create(c);
        durationBuilder
            .child(
                ShimComponent.create(c)
                    .width(64)
                    .height(17)
            );


        final ComponentLayout.ContainerBuilder builder = HorizontalViewComponent.create(c);
        builder
            .child(
                ShimComponent.create(c)
                    .width(95)
                    .height(17)
            );

        return StylizedComponent.applyStyles(builder, journeyHeaderStyles);
    }

    static ComponentLayout.ContainerBuilder getJourneyFriezeComponent(ComponentContext c) {
        final ComponentLayout.ContainerBuilder builder = HorizontalViewComponent.create(c);
        builder
            .child(
                FriezeShimComponent.create(c)
                    .duration(87)
            )
            .child(
                FriezeShimComponent.create(c)
                    .duration(130)
            )
            .child(
                FriezeShimComponent.create(c)
                    .duration(115)
            )
        ;

        return StylizedComponent.applyStyles(builder, journeyFriezeStyle);
    }

    static Map<String, Object> journeyHeaderStyles = new HashMap<>();
    static {
        journeyHeaderStyles.put("alignItems", YogaAlign.CENTER);
        journeyHeaderStyles.put("justifyContent", YogaJustify.SPACE_BETWEEN);
        journeyHeaderStyles.put("height", 46);
    }

    static Map<String, Object> journeyFriezeStyle = new HashMap<>();
    static {
        journeyFriezeStyle.put("marginEnd", Configuration.metrics.margin * -1);
        journeyFriezeStyle.put("alignItems", YogaAlign.CENTER);
        journeyFriezeStyle.put("justifyContent", YogaJustify.SPACE_BETWEEN);
        journeyFriezeStyle.put("height", 80);
    }
}
