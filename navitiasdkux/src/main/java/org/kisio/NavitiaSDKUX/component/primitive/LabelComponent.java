package org.kisio.NavitiaSDKUX.component.primitive;

import android.support.annotation.AttrRes;
import android.support.annotation.StyleRes;

import com.facebook.litho.ComponentContext;
import com.facebook.litho.widget.Text;

/**
 * NavitiaSDKUX_android
 *
 * Copyright \u00a9 2018 Kisio. All rights reserved.
 */

public class LabelComponent {
    private LabelComponent() {
    }

    public static Text.Builder create(
        ComponentContext c,
        @AttrRes int defStyleAttr,
        @StyleRes int defStyleRes) {
        return Text.create(c, defStyleAttr, defStyleRes)
            .textSizeSp(14);
    }

    public static Text.Builder create(ComponentContext c) {
        return create(c, 0, 0);
    }
}
