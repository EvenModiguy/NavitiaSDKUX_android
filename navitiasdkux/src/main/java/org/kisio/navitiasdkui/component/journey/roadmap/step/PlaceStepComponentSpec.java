package org.kisio.navitiasdkui.component.journey.roadmap.step;

import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropDefault;
import com.facebook.litho.annotations.ResType;
import com.facebook.yoga.YogaAlign;
import com.facebook.yoga.YogaJustify;

import org.kisio.navitiasdkui.component.CardComponent;
import org.kisio.navitiasdkui.component.IconComponent;
import org.kisio.navitiasdkui.component.journey.roadmap.Roadmap3ColumnsLayout;
import org.kisio.navitiasdkui.component.journey.roadmap.step.Parts.TimePart;
import org.kisio.navitiasdkui.component.TextComponent;
import org.kisio.navitiasdkui.component.ViewComponent;
import org.kisio.navitiasdkui.config.Configuration;
import org.kisio.navitiasdkui.util.Color;

import java.util.HashMap;
import java.util.Map;

@LayoutSpec
public class PlaceStepComponentSpec {
    @PropDefault static final Map<String, Object> styles = new HashMap<>();

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop(optional = true) String testKey,
        @Prop(optional = true) Map<String, Object> styles,
        @Prop String placeType,
        @Prop String placeLabel,
        @Prop(resType = ResType.COLOR) int backgroundColor,
        @Prop String datetime) {

        Map<String, Object> containerStyles = new HashMap<>(containerBaseStyles);
        containerStyles.put("backgroundColor", backgroundColor);

        Map<String, Object> colorStyles = new HashMap<>();
        colorStyles.put("color", Color.contrastColor(backgroundColor));

        Map<String, Object> textStyles = new HashMap<>(textBaseStyles);
        textStyles.put("color", Color.contrastColor(backgroundColor));

        Map<String, Object> textStepStyles = new HashMap<>(textStepBaseStyles);
        textStepStyles.put("color", Color.contrastColor(backgroundColor));

        return CardComponent.create(c)
            .styles(styles)
            .child(
                ViewComponent.create(c).testKey(testKey)
                    .styles(containerStyles)
                    .children(new Component<?>[] {
                        Roadmap3ColumnsLayout.create(c)
                            .leftChildren(new Component[]{
                                TimePart.create(c)
                                    .dateTime(datetime)
                                    .labelStyles(colorStyles)
                                    .build()
                            })
                            .middleChildren(new Component[]{
                                ViewComponent.create(c)
                                    .styles(iconContainerStyles)
                                    .children(new Component<?>[] {
                                        IconComponent.create(c)
                                            .name("location-pin")
                                            .styles(colorStyles)
                                            .build()
                                    })
                                    .build()
                            })
                            .rightChildren(new Component[]{
                                ViewComponent.create(c)
                                    .styles(textContainerStyles)
                                    .children(new Component<?>[] {
                                        TextComponent.create(c)
                                            .text(placeType)
                                            .styles(textStepStyles)
                                            .build(),
                                        TextComponent.create(c)
                                            .text(placeLabel)
                                            .styles(textStyles)
                                            .build()
                                    })
                                    .build()
                            })
                            .build()
                    })
            ).buildWithLayout();
    }

    static Map<String, Object> containerBaseStyles = new HashMap<>();
    static {
        containerBaseStyles.put("paddingVertical", Configuration.metrics.margin);
    }

    static Map<String, Object> iconContainerStyles = new HashMap<>();
    static {
        iconContainerStyles.put("flexGrow", 1);
        iconContainerStyles.put("justifyContent", YogaJustify.CENTER);
    }

    static Map<String, Object> iconBaseStyles = new HashMap<>();
    static {
        iconBaseStyles.put("alignSelf", YogaAlign.CENTER);
    }

    static Map<String, Object> textBaseStyles = new HashMap<>();
    static {
        textBaseStyles.put("fontSize", Configuration.metrics.text);
    }

    static Map<String, Object> textStepBaseStyles = new HashMap<>(textBaseStyles);
    static {
        textStepBaseStyles.put("fontWeight", "bold");
    }

    static Map<String, Object> textContainerStyles = new HashMap<>();
    static {
        textContainerStyles.put("paddingHorizontal", 10);
    }
}
