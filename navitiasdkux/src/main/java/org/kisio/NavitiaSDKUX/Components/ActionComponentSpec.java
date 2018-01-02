package org.kisio.NavitiaSDKUX.Components;

import android.util.Log;
import android.view.View;

import com.facebook.litho.ClickEvent;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.FromEvent;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.OnEvent;
import com.facebook.litho.annotations.Prop;

import org.kisio.NavitiaSDKUX.Components.Primitive.BaseViewComponent;

import java.util.concurrent.Callable;

@LayoutSpec
public class ActionComponentSpec {
    private static final String TAG = ActionComponentSpec.class.getName();

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @Prop(optional = true) String testKey,
        @Prop Component<?> child,
        @Prop Callable<?> actionToCall) {

        final ComponentLayout.ContainerBuilder builder = BaseViewComponent.create(c).testKey(testKey);

        return builder
            .child(child)
            .clickHandler(ActionComponent.onClick(c))
            .build();
    }

    @OnEvent(ClickEvent.class)
    static void onClick(ComponentContext c, @FromEvent View view, @Prop Callable<?> actionToCall) {
        try {
            actionToCall.call();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
