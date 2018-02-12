package org.kisio.navitiasdkui.journey.roadmap;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.kisio.navitiasdkui.R;
import org.kisio.navitiasdkui.journey.ListModel;

/**
 * View holder for a view representing a departure/arrival step.
 */
class DepartureArrivalViewHolder extends RecyclerView.ViewHolder {
    private final TextView gTime;
    private final TextView gType;
    private final TextView gAddress;

    /**
     * Constructor
     *
     * @param v held view.
     */
    DepartureArrivalViewHolder(View v) {
        super(v);
        gTime = v.findViewById(R.id.listitem_departure_arrival_time);
        gType = v.findViewById(R.id.listitem_departure_arrival_type);
        gAddress = v.findViewById(R.id.listitem_departure_arrival_address);
    }

    /**
     * Fill the held view.
     *
     * @param model Model to display.
     */
    void fillView(ListModel model) {
        gTime.setText(model.getTime());
        gType.setText(model.getType());
        gAddress.setText(model.getAddress());
    }
}
