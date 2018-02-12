package org.kisio.navitiasdkui.journey.roadmap;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.kisio.navitiasdkui.R;
import org.kisio.navitiasdkui.journey.ListModel;

class TransitViewHolder extends RecyclerView.ViewHolder {
    private final TextView gIcon;
    private final TextView gTransit;
    private final TextView gLine;
    private final TextView gOrigin;
    private final TextView gDisruption;
    private final TextView gDisruptionTime;
    private final TextView gWait;
    private final TextView gStartTime;
    private final TextView gEndTime;
    private final TextView gDestination;
    private final RecyclerView gStations;
    // New Recycler view for stations

    /**
     * Constructor
     *
     * @param v held view.
     */
    TransitViewHolder(View v) {
        super(v);
        gIcon = v.findViewById(R.id.listitem_transit_icon);
        gTransit = v.findViewById(R.id.listitem_transit_transit);
        gLine = v.findViewById(R.id.listitem_transit_line);
        gOrigin = v.findViewById(R.id.listitem_transit_origin);
        gDisruption = v.findViewById(R.id.listitem_transit_disruption);
        gDisruptionTime = v.findViewById(R.id.listitem_transit_disruption_time);
        gWait = v.findViewById(R.id.listitem_transit_wait);
        gStartTime = v.findViewById(R.id.listitem_transit_start_time);
        gEndTime = v.findViewById(R.id.listitem_transit_end_time);
        gDestination = v.findViewById(R.id.listitem_transit_destination);
        gStations = v.findViewById(R.id.listitem_transit_stations);
    }

    /**
     * Fill the held view.
     *
     * @param model Model to display.
     * @param context context of the activity.
     */
    void fillView(ListModel model, Context context) {
        gIcon.setText(model.getIcon());
        gTransit.setText(model.getTransit());
        gLine.setText(model.getLine());
        gOrigin.setText(model.getOrigin());
        gDisruption.setText(model.getDisruption());
        gDisruptionTime.setText(model.getDisruptionTime());
        gWait.setText(model.getWait());
        gStartTime.setText(model.getStartTime());
        gEndTime.setText(model.getEndTime());
        gDestination.setText(model.getDestination());

        //gStations.setText(model.getWalkInfo());
    }
}
