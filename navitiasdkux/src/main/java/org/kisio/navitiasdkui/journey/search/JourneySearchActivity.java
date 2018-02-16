package org.kisio.navitiasdkui.journey.search;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import org.kisio.NavitiaSDK.NavitiaConfiguration;
import org.kisio.NavitiaSDK.NavitiaSDK;
import org.kisio.NavitiaSDK.apis.JourneysApi;
import org.kisio.NavitiaSDK.invokers.ApiCallback;
import org.kisio.NavitiaSDK.invokers.ApiException;
import org.kisio.NavitiaSDK.models.Disruption;
import org.kisio.NavitiaSDK.models.Journey;
import org.kisio.NavitiaSDK.models.Journeys;
import org.kisio.NavitiaSDK.models.LinkSchema;
import org.kisio.NavitiaSDK.models.Section;
import org.kisio.navitiasdkui.R;
import org.kisio.navitiasdkui.journey.ListModel;
import org.kisio.navitiasdkui.journey.roadmap.RoadmapActivity;
import org.kisio.navitiasdkui.util.Configuration;
import org.kisio.navitiasdkui.util.Helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.kisio.navitiasdkui.util.Constant.VIEW_TYPE_EMPTY_STATE;
import static org.kisio.navitiasdkui.util.Constant.VIEW_TYPE_HEADER;
import static org.kisio.navitiasdkui.util.Constant.VIEW_TYPE_LOADING;

public class JourneySearchActivity extends AppCompatActivity implements ResultAdapter.ClickListener {
    public final static String INTENT_PARAM = JourneySearchActivity.class.getSimpleName();

    private RecyclerView gSolutions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_search);

        Toolbar toolbar = findViewById(R.id.journey_search_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(Configuration.getColor(this, R.attr.colorPrimary));
        toolbar.setElevation(0);
        toolbar.setTitle(R.string.journeys);

        final JourneysRequest request = getIntent().getParcelableExtra(INTENT_PARAM);
        if (request != null) {
            launchRequest(request);
        }

        gSolutions = findViewById(R.id.journey_search_solutions);
        gSolutions.setLayoutManager(new LinearLayoutManager(this));
        showLoading();
    }

    @Override
    public void onSolutionClick(ListModel solution) {
        Intent intent = null;
        if (solution.isCarpool()) {
            String href = solution.getHref();
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

                                showResult(classicJourneys, carpoolingJourneys, result.getDisruptions());
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

    private void showLoading() {
        List<ListModel> loadingListModel = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            loadingListModel.add(new ListModel(VIEW_TYPE_LOADING));
        }
        loadingListModel.add(new ListModel(VIEW_TYPE_HEADER));
        for (int i = 0; i < 2; i++) {
            loadingListModel.add(new ListModel(VIEW_TYPE_LOADING));
        }

        gSolutions.setAdapter(new ResultAdapter(loadingListModel, this));
    }

    private void showError() {
        List<ListModel> errorListModel = new ArrayList<>();
        ListModel classicEmptyState = new ListModel();
        ListModel carpoolEmptyState = new ListModel();

        classicEmptyState
                .isCarpool(false)
                .setViewType(VIEW_TYPE_EMPTY_STATE)
        ;
        carpoolEmptyState
                .isCarpool(true)
                .setViewType(VIEW_TYPE_EMPTY_STATE)
        ;

        errorListModel.add(classicEmptyState);
        errorListModel.add(new ListModel(VIEW_TYPE_HEADER));
        errorListModel.add(carpoolEmptyState);

        gSolutions.setAdapter(new ResultAdapter(errorListModel, this));
    }

    private void showResult(List<Journey> classicJourneys, List<Journey> carpoolingJourneys, List<Disruption> disruptions) {
        List<ListModel> resultListModel = new ArrayList<>();

        if (!classicJourneys.isEmpty()) {
            for (Journey journey : classicJourneys) {
                ListModel classicJourney = new ListModel();
                Helper.Duration duration = Helper.formatTravelDuration(this, journey.getDurations().getTotal());
                List<Section> sections = journey.getSections();
                Integer walkingDuration = journey.getDurations().getWalking();

                classicJourney
                        .setTravelTime(Helper.formatTravelTime(journey.getDepartureDateTime(), journey.getArrivalDateTime()))
                        .setTravelDuration(duration.getValue())
                        .isTravelDurationIsLessThanAHour(duration.isLessThanAnHour())
                        .hasArrow(true)
                        .isCarpool(false)
                ;
                if (sections.size() > 1 || walkingDuration > 0) {
                    classicJourney.setWalkInfo(Helper.formatWalkInfo(this, sections, walkingDuration));
                }
                resultListModel.add(classicJourney);
            }
        } else {
            ListModel classicEmptyState = new ListModel();
            classicEmptyState
                    .isCarpool(false)
                    .setViewType(VIEW_TYPE_EMPTY_STATE)
            ;
            resultListModel.add(classicEmptyState);
        }
        resultListModel.add(new ListModel(VIEW_TYPE_HEADER));
        if (!carpoolingJourneys.isEmpty()) {
            for (Journey journey : carpoolingJourneys) {
                ListModel carpoolingJourney = new ListModel();
                Helper.Duration duration = Helper.formatTravelDuration(this, journey.getDurations().getTotal());
                List<Section> sections = journey.getSections();
                Integer walkingDuration = journey.getDurations().getWalking();

                carpoolingJourney
                        .setTravelTime(Helper.formatTravelTime(journey.getDepartureDateTime(), journey.getArrivalDateTime()))
                        .setTravelDuration(duration.getValue())
                        .isTravelDurationIsLessThanAHour(duration.isLessThanAnHour())
                        .hasArrow(true)
                        .isCarpool(false)
                ;
                if (sections.size() > 1 || walkingDuration > 0) {
                    carpoolingJourney.setWalkInfo(Helper.formatWalkInfo(this, sections, walkingDuration));
                }
                resultListModel.add(carpoolingJourney);
            }
        } else {
            ListModel carpoolEmptyState = new ListModel();
            carpoolEmptyState
                    .isCarpool(true)
                    .setViewType(VIEW_TYPE_EMPTY_STATE)
            ;
            resultListModel.add(carpoolEmptyState);
        }

        gSolutions.setAdapter(new ResultAdapter(resultListModel, this));
    }
}
