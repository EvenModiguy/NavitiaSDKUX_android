package org.kisio.navitiasdkui.screen;

import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.StateValue;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateInitialState;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.OnUpdateState;
import com.facebook.litho.annotations.Param;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.State;

import org.joda.time.DateTime;
import org.kisio.NavitiaSDK.NavitiaConfiguration;
import org.kisio.NavitiaSDK.NavitiaSDK;
import org.kisio.NavitiaSDK.apis.JourneysApi;
import org.kisio.NavitiaSDK.invokers.ApiCallback;
import org.kisio.NavitiaSDK.invokers.ApiException;
import org.kisio.NavitiaSDK.models.Journey;
import org.kisio.NavitiaSDK.models.Journeys;
import org.kisio.navitiasdkui.component.ContainerComponent;
import org.kisio.navitiasdkui.component.journey.result.AlertComponent;
import org.kisio.navitiasdkui.component.journey.result.DateTimeButtonComponent;
import org.kisio.navitiasdkui.component.journey.result.FormComponent;
import org.kisio.navitiasdkui.component.journey.result.LoadingComponent;
import org.kisio.navitiasdkui.component.journey.result.SolutionComponent;
import org.kisio.navitiasdkui.component.ListViewComponent;
import org.kisio.navitiasdkui.component.primitive.BaseViewComponent;
import org.kisio.navitiasdkui.component.ScreenHeaderComponent;
import org.kisio.navitiasdkui.component.ScrollViewComponent;
import org.kisio.navitiasdkui.config.Configuration;
import org.kisio.navitiasdkui.controller.JourneySolutionsInParameters;
import org.kisio.navitiasdkui.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@LayoutSpec
public class JourneySolutionsScreenSpec {
    @OnCreateInitialState
    static void createInitialState(
        ComponentContext c,
        StateValue<JourneySolutionsInParameters> parameters,
        StateValue<Journeys> journeys,
        StateValue<Boolean> loaded,
        StateValue<Boolean> error,
        @Prop JourneySolutionsInParameters initParameters) {

        parameters.set(initParameters);
        loaded.set(false);
        error.set(false);

        final NavitiaConfiguration navitiaConfiguration = new NavitiaConfiguration(Configuration.token);
        try {
            final NavitiaSDK navitiaSDK = new NavitiaSDK(navitiaConfiguration);
            retrieveJourneys(c, navitiaSDK, parameters.get());
        } catch (Exception e) {
            error.set(true);
            loaded.set(true);
            e.printStackTrace();
        }
    }

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
        ComponentContext c,
        @State JourneySolutionsInParameters parameters,
        @State Journeys journeys,
        @State Boolean loaded,
        @State Boolean error) {

        Component<?>[] journeyComponent;
        if (journeys != null) {
            journeyComponent = getJourneyComponent(c, journeys);
        } else if (error){
            journeyComponent = new Component<?>[]{
                AlertComponent.create(c)
                    .text(c.getString(R.string.no_journey_found))
                    .build()
            };
        } else {
            journeyComponent = new Component<?>[]{
                LoadingComponent.create(c).build(),
                LoadingComponent.create(c).build(),
                LoadingComponent.create(c).build(),
                LoadingComponent.create(c).build(),
            };
        }

        return BaseViewComponent.create(c)
            .child(
                ScreenHeaderComponent.create(c)
                    .styles(headerStyles)
                    .children(new Component<?>[]{
                        ContainerComponent.create(c)
                            .children(new Component<?>[]{
                            FormComponent.create(c)
                                .origin(parameters.originLabel.isEmpty()? parameters.originId : parameters.originLabel)
                                .destination(parameters.destinationLabel.isEmpty()? parameters.destinationId : parameters.destinationLabel)
                                .build(),
                                getDatetimeButtonBuilder(c, parameters),
                        }).build()
                    })
            )
            .child(
                ScrollViewComponent.create(c)
                    .child(
                        ListViewComponent.create(c)
                            .children(journeyComponent)
                    )
            )
            .build()
        ;
    }

    private static Component<DateTimeButtonComponent> getDatetimeButtonBuilder(ComponentContext c, JourneySolutionsInParameters parameters) {
        final DateTimeButtonComponent.Builder builder = DateTimeButtonComponent.create(c);
        DateTime datetime = parameters.datetime;
        if (datetime == null) {
            datetime = DateTime.now();
        }
        builder.datetime(datetime);
        if (parameters.datetimeRepresents != null) {
            builder.datetimeRepresents(parameters.datetimeRepresents);
        } else {
            builder.datetimeRepresents("departure");
        }
        return builder.build();
    }

    static Component<?>[] getJourneyComponent(ComponentContext c, Journeys journeys) {
        List<Component<?>> components = new ArrayList<>();
        Integer index = 1;
        for (Journey journey : journeys.getJourneys()) {
            components.add(
                SolutionComponent.create(c)
                    .testKey("result-" + index)
                    .journey(journey)
                    .disruptions(journeys.getDisruptions())
                    .isTouchable(true)
                    .build()
            );
            index++;
        }

        return components.toArray(new Component<?>[components.size()]);
    }

    static Map<String, Object> headerStyles = new HashMap<>();
    static {
        headerStyles.put("backgroundColor", Configuration.colors.getTertiary());
    }

    // State Update

    @OnUpdateState
    static void updateJourneys(StateValue<Journeys> journeys, StateValue<Boolean> loaded, StateValue<JourneySolutionsInParameters> parameters, @Param Journeys result) {
        journeys.set(result);
        loaded.set(true);

        if (result.getJourneys().isEmpty() == false) {
            final Journey journey = result.getJourneys().get(0);

            if (journey.getSections().isEmpty() == false) {
                final JourneySolutionsInParameters parametersValue = parameters.get();
                if (parametersValue.originLabel.isEmpty()) {
                    parametersValue.originLabel = journey.getSections().get(0).getFrom().getName();
                }
                if (parametersValue.destinationLabel.isEmpty()) {
                    parametersValue.destinationLabel = journey.getSections().get(journey.getSections().size() - 1).getFrom().getName();
                }
                parameters.set(parametersValue);
            }
        }
    }

    @OnUpdateState
    static void updateError(StateValue<Boolean> error, StateValue<Boolean> loaded, @Param Boolean hasError) {
        error.set(hasError);
        loaded.set(true);
    }

    // Http

    static void retrieveJourneys(final ComponentContext c, NavitiaSDK navitiaSDK, JourneySolutionsInParameters parameters) {
        try {
            final JourneysApi.JourneysRequestBuilder journeysRequestBuilder = navitiaSDK.journeysApi.newJourneysRequestBuilder();

            if (parameters.originId != null) {
                journeysRequestBuilder.withFrom(parameters.originId);
            }
            if (parameters.destinationId != null) {
                journeysRequestBuilder.withTo(parameters.destinationId);
            }
            if (parameters.datetime != null) {
                journeysRequestBuilder.withDatetime(parameters.datetime);
            }
            if (parameters.datetimeRepresents != null) {
                journeysRequestBuilder.withDatetimeRepresents(parameters.datetimeRepresents);
            }
            if (parameters.allowedId != null) {
                journeysRequestBuilder.withAllowedId(parameters.allowedId);
            }
            if (parameters.forbiddenUris != null) {
                journeysRequestBuilder.withForbiddenUris(parameters.forbiddenUris);
            }
            if (parameters.firstSectionModes != null) {
                journeysRequestBuilder.withFirstSectionMode(parameters.firstSectionModes);
            }
            if (parameters.lastSectionModes != null) {
                journeysRequestBuilder.withLastSectionMode(parameters.lastSectionModes);
            }
            if (parameters.count != null) {
                journeysRequestBuilder.withCount(parameters.count);
            }
            if (parameters.minNbJourneys != null) {
                journeysRequestBuilder.withMinNbJourneys(parameters.minNbJourneys);
            }
            if (parameters.maxNbJourneys != null) {
                journeysRequestBuilder.withMaxNbJourneys(parameters.maxNbJourneys);
            }

            journeysRequestBuilder
                .get(new ApiCallback<Journeys>() {
                    @Override
                    public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                        JourneySolutionsScreen.updateErrorAsync(c, true);
                    }

                    @Override
                    public void onSuccess(Journeys result, int statusCode, Map<String, List<String>> responseHeaders) {
                        if (result.getError() != null) {
                            JourneySolutionsScreen.updateErrorAsync(c, true);
                        } else {
                            JourneySolutionsScreen.updateJourneysAsync(c, result);
                        }
                    }

                    @Override
                    public void onUploadProgress(long bytesWritten, long contentLength, boolean done) { }

                    @Override
                    public void onDownloadProgress(long bytesRead, long contentLength, boolean done) { }
                });
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }
}
