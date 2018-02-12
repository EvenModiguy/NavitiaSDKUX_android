package org.kisio.navitiasdkui.journey.search;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.kisio.navitiasdkui.R;

class HeaderViewHolder extends RecyclerView.ViewHolder {
    private final TextView gTitle;

    /**
     * Constructor
     *
     * @param v held view.
     */
    HeaderViewHolder(View v) {
        super(v);
        gTitle = v.findViewById(R.id.listitem_solution_travel_time);
    }

    /**
     * Fill the held view.
     */
    void fillView() {
        gTitle.setText(R.string.carpooling);
    }
}
