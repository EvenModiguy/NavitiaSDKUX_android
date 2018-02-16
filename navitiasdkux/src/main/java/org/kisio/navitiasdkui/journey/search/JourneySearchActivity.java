package org.kisio.navitiasdkui.journey.search;

import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;

import org.kisio.NavitiaSDK.NavitiaConfiguration;
import org.kisio.NavitiaSDK.NavitiaSDK;
import org.kisio.NavitiaSDK.apis.JourneysApi;
import org.kisio.NavitiaSDK.invokers.ApiCallback;
import org.kisio.NavitiaSDK.invokers.ApiException;
import org.kisio.NavitiaSDK.models.Journey;
import org.kisio.NavitiaSDK.models.Journeys;
import org.kisio.NavitiaSDK.models.LinkSchema;
import org.kisio.NavitiaSDK.models.Section;
import org.kisio.navitiasdkui.R;
import org.kisio.navitiasdkui.journey.ListModel;
import org.kisio.navitiasdkui.journey.roadmap.RoadmapActivity;
import org.kisio.navitiasdkui.util.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JourneySearchActivity extends AppCompatActivity implements ResultAdapter.ClickListener {
    public final static String INTENT_PARAM = JourneySearchActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setBackgroundColor(Configuration.getColor(this, R.attr.colorPrimary));
        toolbar.setElevation(0);
        toolbar.setTitle(R.string.journeys);

        final JourneysRequest request = getIntent().getParcelableExtra(INTENT_PARAM);
        if (request != null) {
            launchRequest(request);
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

    private void launchRequest(JourneysRequest request) {
        try {
            NavitiaSDK navitiaSDK = new NavitiaSDK(new NavitiaConfiguration(Configuration.getToken(this)));
            final JourneysApi.JourneysRequestBuilder journeysRequestBuilder = navitiaSDK.journeysApi.newJourneysRequestBuilder();

            journeysRequestBuilder
                    .withFrom(request.getOriginId())
                    .withTo(request.getDestinationId())
                    .withDatetime(request.getDatetime())
                    .withDatetimeRepresents(request.getDatetimeRepresents())
                    .withAllowedId(request.getAllowedId())
                    .withForbiddenUris(request.getForbiddenUris())
                    .withFirstSectionMode(request.getFirstSectionModes())
                    .withLastSectionMode(request.getLastSectionModes())
                    .withCount(request.getCount())
                    .withMinNbJourneys(request.getMinNbJourneys())
                    .withMaxNbJourneys(request.getMaxNbJourneys())
            ;

            journeysRequestBuilder
                    .get(new ApiCallback<Journeys>() {
                        @Override
                        public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                            showError();
                        }

                        @Override
                        public void onSuccess(Journeys result, int statusCode, Map<String, List<String>> responseHeaders) {
                            if (result.getError() == null && result.getJourneys() != null) {
                                List<Journey> classicJourneys = new ArrayList<>();
                                List<Journey> carpoolingJourneys = new ArrayList<>();
                                for (Journey journey : result.getJourneys()) {
                                    if (journey.getTags() != null && journey.getTags().contains("ridesharing")) {
                                        carpoolingJourneys.add(journey);
                                    } else {
                                        classicJourneys.add(journey);
                                    }
                                }

                                showResult();
                            } else {
                                showError();
                            }
                        }

                        @Override
                        public void onUploadProgress(long bytesWritten, long contentLength, boolean done) { }

                        @Override
                        public void onDownloadProgress(long bytesRead, long contentLength, boolean done) { }
                    });
        } catch (Exception e) {
            showError();
            e.printStackTrace();
        }
    }

    private void showError() {

    }

    private void showResult() {

    }
}
