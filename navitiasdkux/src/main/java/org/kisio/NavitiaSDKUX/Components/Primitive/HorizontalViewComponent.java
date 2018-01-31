package org.kisio.NavitiaSDKUX.Components.Primitive;

import android.support.annotation.AttrRes;
import android.support.annotation.StyleRes;

import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.Row;

/**
 * NavitiaSDKUX_android
 *
 * Copyright \u00a9 2018 Kisio. All rights reserved.
 */

public class HorizontalViewComponent {
    private HorizontalViewComponent() {
    }

    public static ComponentLayout.ContainerBuilder create(
        ComponentContext c,
        @AttrRes int defStyleAttr,
        @StyleRes int defStyleRes) {
        return Row.create(c, defStyleAttr, defStyleRes);
    }

    public static ComponentLayout.ContainerBuilder create(ComponentContext c) {
        return create(c, 0, 0);
    }
}
