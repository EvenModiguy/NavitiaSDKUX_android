package org.kisio.NavitiaSDKUX.component.journey.result.loading;

import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.yoga.YogaAlign;
import com.facebook.yoga.YogaJustify;

import org.kisio.NavitiaSDKUX.component.journey.result.Parts.SeparatorPart;
import org.kisio.NavitiaSDKUX.component.primitive.BaseViewComponent;
import org.kisio.NavitiaSDKUX.component.primitive.HorizontalViewComponent;
import org.kisio.NavitiaSDKUX.component.primitive.StylizedComponent;
import org.kisio.NavitiaSDKUX.config.Configuration;

import java.util.HashMap;
import java.util.Map;

@LayoutSpec
class ShimCardContentPartSpec {
    @OnCreateLayout
    static ComponentLayout onCreateLayout(ComponentContext c) {

        final ComponentLayout.ContainerBuilder builder = BaseViewComponent.create(c);
        builder
            .child(
                getJourneyHeaderComponent(c)
            )
            .child(
                SeparatorPart.create(c)
            )
            .child(
                getJourneyFriezeComponent(c)
            )
            .child(
                ShimPart.create(c)
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
                ShimPart.create(c)
                    .width(64)
                    .height(17)
            );


        final ComponentLayout.ContainerBuilder builder = HorizontalViewComponent.create(c);
        builder
            .child(
                ShimPart.create(c)
                    .width(95)
                    .height(17)
            );

        return StylizedComponent.applyStyles(builder, journeyHeaderStyles);
    }

    static ComponentLayout.ContainerBuilder getJourneyFriezeComponent(ComponentContext c) {
        final ComponentLayout.ContainerBuilder builder = HorizontalViewComponent.create(c);
        builder
            .child(
                FriezeShimPart.create(c)
                    .duration(87)
            )
            .child(
                FriezeShimPart.create(c)
                    .duration(130)
            )
            .child(
                FriezeShimPart.create(c)
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
