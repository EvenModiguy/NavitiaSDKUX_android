package org.kisio.navitiasdkui.journey.search;

import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;

import org.kisio.NavitiaSDK.models.Journey;
import org.kisio.NavitiaSDK.models.LinkSchema;
import org.kisio.NavitiaSDK.models.Section;
import org.kisio.navitiasdkui.R;
import org.kisio.navitiasdkui.journey.ListModel;
import org.kisio.navitiasdkui.journey.roadmap.RoadmapActivity;

public class JourneySearchActivity extends AppCompatActivity implements ResultAdapter.ClickListener {
    public final static String INTENT_PARAM = JourneySearchActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setBackgroundColor(fetchPrimaryColor());
        toolbar.setElevation(0);
        toolbar.setTitle(R.string.journeys);

        final JourneysRequest request = getIntent().getParcelableExtra(INTENT_PARAM);
        if (request != null) {
            // Launch network request
        }
    }

    @Override
    public void onSolutionClick(ListModel solution) {
        Intent intent = null;
        if (solution.isCarpool()) {
            String href = getCarpoolDeepLink(journey);
            if (href != null) {
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(href));
            }
        } else {
            intent = new Intent(this, RoadmapActivity.class);
        }
        startActivity(intent);
    }

    private int fetchPrimaryColor() {
        TypedValue typedValue = new TypedValue();
        TypedArray a = this.obtainStyledAttributes(typedValue.data, new int[] { R.attr.colorPrimary });
        int color = a.getColor(0, 0);

        a.recycle();

        return color;
    }

    private String getCarpoolDeepLink(Journey journey) {
        for (Section section : journey.getSections()) {
            if (section.getType().equalsIgnoreCase("street_network") && section.getMode().equalsIgnoreCase("ridesharing")) {
                for (Section ridesharingSection : section.getRidesharingJourneys().get(0).getSections()) {
                    if (ridesharingSection.getType().equalsIgnoreCase("ridesharing")) {
                        for (LinkSchema linkSchema : ridesharingSection.getLinks()) {
                            if (linkSchema.getType().equalsIgnoreCase("ridesharing_ad")) {
                                return linkSchema.getHref();
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
}
