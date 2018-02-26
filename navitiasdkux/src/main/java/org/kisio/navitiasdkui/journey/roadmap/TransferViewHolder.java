package org.kisio.navitiasdkui.journey.roadmap;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.TextView;

import org.kisio.navitiasdkui.R;
import org.kisio.navitiasdkui.journey.ListModel;

/**
 * View holder for a view representing a departure/arrival step.
 */
class TransferViewHolder extends RecyclerView.ViewHolder {
    private final TextView gIcon;
    private final TextView gDestination;
    private final TextView gWalkInfo;

    /**
     * Constructor
     *
     * @param v held view.
     */
    TransferViewHolder(View v) {
        super(v);
        gIcon = v.findViewById(R.id.listitem_transfer_icon);
        gDestination = v.findViewById(R.id.listitem_transfer_destination);
        gWalkInfo = v.findViewById(R.id.listitem_transfer_walk_info);
    }

    /**
     * Fill the held view.
     *
     * @param model Model to display.
     */
    void fillView(ListModel model) {
        gIcon.setText(model.getIcon());
        gDestination.setText(model.getDestination());

        String[] walkInfo = model.getWalkInfo();
        SpannableStringBuilder walkInfoStringBuilder = new SpannableStringBuilder();
        SpannableString styledWalkTime = new SpannableString(walkInfo[1]);
        styledWalkTime.setSpan(new StyleSpan(Typeface.BOLD), 0, walkInfo[1].length(), 0);
        walkInfoStringBuilder.append(walkInfo[0]);
        walkInfoStringBuilder.append(styledWalkTime);
        walkInfoStringBuilder.append(walkInfo[2]);
        gWalkInfo.setText(walkInfoStringBuilder);
    }
}
