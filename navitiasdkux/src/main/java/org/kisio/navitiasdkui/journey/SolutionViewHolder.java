package org.kisio.navitiasdkui.journey;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.kisio.NavitiaSDK.models.Disruption;
import org.kisio.NavitiaSDK.models.Section;
import org.kisio.navitiasdkui.R;
import org.kisio.navitiasdkui.journey.roadmap.SectionMatcher;
import org.kisio.navitiasdkui.util.Helper;

import java.util.ArrayList;
import java.util.List;

import static org.kisio.navitiasdkui.util.Constant.PUBLIC_TRANSPORT_KEY;
import static org.kisio.navitiasdkui.util.Constant.STREET_NETWORK_KEY;

/**
 * View holder for a view representing an journey result.
 */
public class SolutionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final TextView gTravelTime;
    private final TextView gTravelDuration;
    private final LinearLayout gFrieze;
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
    public SolutionViewHolder(View v) {
        super(v);
        gTravelTime = v.findViewById(R.id.listitem_solution_travel_time);
        gTravelDuration = v.findViewById(R.id.listitem_solution_travel_duration);
        gFrieze = v.findViewById(R.id.listitem_solution_frieze);
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
    public void setClickListener(ClickListener listener) {
        this.mListener = listener;
    }

    /**
     * Fill the held view.
     *
     * @param model Model to display.
     * @param context context of the activity.
     */
    public void fillView(ListModel model, Context context) {
        gTravelTime.setText(model.getTravelTime());
        gTravelDuration.setText(String.format("%1$s%2$s", model.getTravelDuration(), context.getResources().getString(R.string.arrow_right)));

        for (Section section : model.getSections()) {
            int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, context.getResources().getDisplayMetrics());

            String modeLogo = Helper.Mode.getModeIcon(section);
            SpannableString modeLogoSpannable = new SpannableString(modeLogo);
            modeLogoSpannable.setSpan(new AbsoluteSizeSpan(size, true), 0, modeLogo.length(), 0);
            //stringBuilder.append(modeLogoSpannable);
            TextView mode = new TextView(context);
            mode.setText(modeLogoSpannable);

            gFrieze.addView(mode);

            if (section.getDisplayInformations() != null && section.getDisplayInformations().getCode() != null) {
                if (section.getType().equals(PUBLIC_TRANSPORT_KEY) || section.getType().equals(STREET_NETWORK_KEY)) {
                    List<Disruption> sectionDisruptions = new ArrayList<>();
                    if (section.getType().equals(PUBLIC_TRANSPORT_KEY) && Helper.arrayIsEmpty(model.getDisruptions())) {
                        sectionDisruptions = SectionMatcher.getMatchingDisruptions(section, model.getDisruptions());
                    }
                }
                // TODO : Handle Disruptions

                TextView line = new TextView(context);
                line.setText(section.getDisplayInformations().getCode());
                line.setBackgroundColor(Helper.Color.getColorFromHexadecimal(section.getDisplayInformations().getColor()));
                line.setTextColor(Helper.Color.getLineCodeTextColor(
                        context,
                        section.getDisplayInformations().getTextColor(),
                        section.getDisplayInformations().getColor()
                ));
                gFrieze.addView(line);
            }
        }

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
