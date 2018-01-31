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

public class ButtonComponent extends StylizedComponent {
    private ButtonComponent() {
    }

    public static ComponentLayout.ContainerBuilder create(
        ComponentContext c,
        @AttrRes int defStyleAttr,
        @StyleRes int defStyleRes) {
        ComponentLayout.ContainerBuilder builder = Row.create(c, defStyleAttr, defStyleRes);
        return builder;
    }

    public static ComponentLayout.ContainerBuilder create(ComponentContext c) {
        return create(c, 0, 0);
    }
}
