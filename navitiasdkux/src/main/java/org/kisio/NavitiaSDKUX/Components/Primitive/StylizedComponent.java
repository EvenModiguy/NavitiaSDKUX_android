package org.kisio.NavitiaSDKUX.Components.Primitive;

import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.Log;

import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.widget.Text;
import com.facebook.yoga.YogaAlign;
import com.facebook.yoga.YogaEdge;
import com.facebook.yoga.YogaJustify;
import com.facebook.yoga.YogaPositionType;

import java.util.HashMap;
import java.util.Map;

/**
 * NavitiaSDKUX_android
 *
 * Copyright \u00a9 2018 Kisio. All rights reserved.
 */

public class StylizedComponent {
    public static ComponentLayout.ContainerBuilder applyStyles(ComponentLayout.ContainerBuilder builder, Map<String, Object> styles) {
        Map<String, Object> notHandledStyles = new HashMap<>(styles);

        for (String key : styles.keySet()) {
            final Object value = styles.get(key);
            Boolean handled = true;
            switch (key) {
                case "alignItems":
                    builder.alignItems((YogaAlign) value);
                    break;
                case "justifyContent":
                    builder.justifyContent((YogaJustify) value);
                    break;
                default:
                    handled = false;
            }

            if (handled) {
                notHandledStyles.remove(key);
            }
        }

        return (ComponentLayout.ContainerBuilder) StylizedComponent.applyStyles((ComponentLayout.Builder) builder, notHandledStyles);
    }

    public static ComponentLayout.Builder applyStyles(ComponentLayout.Builder builder, Map<String, Object> styles) {
        for (String key : styles.keySet()) {
            final Object value = styles.get(key);
            switch (key) {
                case "padding":
                    builder.paddingDip(YogaEdge.ALL, (int) value);
                    break;
                case "paddingTop":
                    builder.paddingDip(YogaEdge.TOP, (int) value);
                    break;
                case "paddingRight":
                    builder.paddingDip(YogaEdge.RIGHT, (int) value);
                    break;
                case "paddingBottom":
                    builder.paddingDip(YogaEdge.BOTTOM, (int) value);
                    break;
                case "paddingLeft":
                    builder.paddingDip(YogaEdge.LEFT, (int) value);
                    break;
                case "paddingHorizontal":
                    builder.paddingDip(YogaEdge.HORIZONTAL, (int) value);
                    break;
                case "paddingVertical":
                    builder.paddingDip(YogaEdge.VERTICAL, (int) value);
                    break;
                case "margin":
                    builder.marginDip(YogaEdge.ALL, (int) value);
                    break;
                case "marginTop":
                    builder.marginDip(YogaEdge.TOP, (int) value);
                    break;
                case "marginRight":
                    builder.marginDip(YogaEdge.RIGHT, (int) value);
                    break;
                case "marginBottom":
                    builder.marginDip(YogaEdge.BOTTOM, (int) value);
                    break;
                case "marginLeft":
                    builder.marginDip(YogaEdge.LEFT, (int) value);
                    break;
                case "marginEnd":
                    builder.marginDip(YogaEdge.END, (int) value);
                    break;
                case "borderRadius":
                    // not supported;
                    break;
                case "position":
                    builder.positionType((YogaPositionType) value);
                    break;
                case "start":
                    builder.positionDip(YogaEdge.START, (int) value);
                    break;
                case "bottom":
                    builder.positionDip(YogaEdge.BOTTOM, (int) value);
                    break;
                case "top":
                    builder.positionDip(YogaEdge.TOP, (int) value);
                    break;
                case "end":
                    builder.positionDip(YogaEdge.END, (int) value);
                    break;
                case "width":
                    builder.widthDip((int) value);
                    break;
                case "height":
                    builder.heightDip((int) value);
                    break;
                case "heightPx":
                    builder.heightPx((int) value);
                    break;
                case "flexGrow":
                    builder.flexGrow((int) value);
                    break;
                case "flexShrink":
                    builder.flexShrink((int) value);
                    break;
                case "backgroundColor":
                    builder.backgroundColor((int) value);
                    break;
                case "borderColor":
                    builder.borderColor((int) value);
                    break;
                case "borderWidth":
                    builder.borderWidthDip(YogaEdge.ALL, (int) value);
                    break;
                case "borderLeftWidth":
                    builder.borderWidthDip(YogaEdge.LEFT, (int) value);
                    break;
                case "borderRightWidth":
                    builder.borderWidthDip(YogaEdge.RIGHT, (int) value);
                    break;
                case "borderTopWidth":
                    builder.borderWidthDip(YogaEdge.TOP, (int) value);
                    break;
                case "borderBottomWidth":
                    builder.borderWidthDip(YogaEdge.BOTTOM, (int) value);
                    break;
                case "alignSelf":
                    builder.alignSelf((YogaAlign) value);
                    break;
                default:
                    Log.d("LayoutBuilder", key + " not handle");
            }
        }

        return builder;
    }

    public static ComponentLayout.Builder applyStyles(Text.Builder builder, Map<String, Object> styles) {
        Map<String, Object> notHandledStyles = new HashMap<>(styles);

        for (String key : styles.keySet()) {
            final Object value = styles.get(key);
            Boolean handled = true;
            switch (key) {
                case "color":
                    builder.textColor((int) value);
                    break;
                case "fontFamily":
                    builder.typeface((Typeface) value);
                    break;
                case "fontSize":
                    builder.textSizeSp(new Float(value.toString()));
                    break;
                case "spacingMultiplier":
                    builder.spacingMultiplier(new Float(value.toString()));
                    break;
                case "fontWeight":
                    builder.textStyle(Typeface.BOLD);
                    break;
                case "maxLines":
                    builder.maxLines((int) value);
                    break;
                case "ellipsis":
                    builder.ellipsize((TextUtils.TruncateAt) value);
                    break;
                default:
                    handled = false;
            }

            if (handled) {
                notHandledStyles.remove(key);
            }
        }

        return StylizedComponent.applyStyles(builder.withLayout(), notHandledStyles);
    }

    public static Map<String, Object> mergeStyles(Map<String, Object> defaultStyles, Map<String, Object> styles) {
        Map<String, Object> baseStyles = new HashMap<>(defaultStyles);
        for (String key : styles.keySet()) {
            baseStyles.put(key, styles.get(key));
        }

        return baseStyles;
    }

    public static Typeface getFont(ComponentContext c, String fontName) {
        return Typeface.createFromAsset(c.getAssets(),  "fonts/" + fontName + ".ttf");
    }
}
