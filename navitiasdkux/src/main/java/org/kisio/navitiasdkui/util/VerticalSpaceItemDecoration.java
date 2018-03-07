package org.kisio.navitiasdkui.util;

import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import static org.kisio.navitiasdkui.util.Constant.VIEW_TYPE_SOLUTION;

public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {
    private final int verticalSpaceHeight;

    public VerticalSpaceItemDecoration(Resources res, int height) {
        this.verticalSpaceHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                height, res.getDisplayMetrics());
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        if (parent.getAdapter().getItemViewType(position) == VIEW_TYPE_SOLUTION) {
            if (position == 0) {
                outRect.top = verticalSpaceHeight;
            }
            outRect.bottom = verticalSpaceHeight;
        }
    }
}