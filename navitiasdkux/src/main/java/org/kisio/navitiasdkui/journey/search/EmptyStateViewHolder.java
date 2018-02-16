package org.kisio.navitiasdkui.journey.search;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.kisio.navitiasdkui.R;
import org.kisio.navitiasdkui.journey.ListModel;

class EmptyStateViewHolder extends RecyclerView.ViewHolder {
    private final TextView gMessage;

    /**
     * Constructor
     *
     * @param v held view.
     */
    EmptyStateViewHolder(View v) {
        super(v);
        gMessage = v.findViewById(R.id.listitem_empty_state_message);
    }

    /**
     * Fill the held view.
     *
     * @param model Model to display.
     */
    void fillView(ListModel model) {
        gMessage.setText(model.isCarpool() ? R.string.no_carpooling_options_found : R.string.no_journey_found);
    }
}

