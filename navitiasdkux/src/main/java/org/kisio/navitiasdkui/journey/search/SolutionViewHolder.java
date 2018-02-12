package org.kisio.navitiasdkui.journey.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.kisio.navitiasdkui.R;

/**
 * View holder for a view representing an journey result.
 */
class SolutionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final TextView gTravelTime;
    private final TextView gTravelDuration;
    private final TextView gWalkInfo;

    private ClickListener mListener;

    /**
     * Interface allowing the adapter to overload the processing performed when a click occurs on the view.
     */
    public interface ClickListener {
        void onClick();
    }

    /**
     * Constructor
     *
     * @param v held view.
     */
    SolutionViewHolder(View v) {
        super(v);
        gTravelTime = v.findViewById(R.id.listitem_solution_travel_time);
        gTravelDuration = v.findViewById(R.id.listitem_solution_travel_duration);
        gWalkInfo = v.findViewById(R.id.listitem_solution_walk_info);
        v.setOnClickListener(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onClick(View v) {
        mListener.onClick();
    }

    /**
     * Sets the click listener using by the adapter.
     *
     * @param listener click listener to be registered.
     */
    void setClickListener(ClickListener listener) {
        this.mListener = listener;
    }

    /**
     * Fill the held view.
     *
     * @param model Model to display.
     * @param context context of the activity if needed for setting data into views
     */
    void fillView(ResultModel model, Context context) {
        gTravelTime.setText(model.getTravelTime());
        gTravelDuration.setText(model.getWalkInfo());
        gWalkInfo.setText(model.getWalkInfo());

    }
}
