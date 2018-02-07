package org.kisio.NavitiaSDKUX.util;

import org.kisio.NavitiaSDK.models.Disruption;
import org.kisio.NavitiaSDK.models.Journey;

import java.util.List;

public class JourneySolutionCache {
    static private JourneySolutionCache instance = new JourneySolutionCache();
    private JourneySolutionCache() {}
    static public JourneySolutionCache getInstance() {
        return instance;
    }

    private Journey currentJourney = null;
    private List<Disruption> currentDisruptions = null;


    public Journey getCurrentJourney() {
        return currentJourney;
    }

    public void setCurrentJourney(Journey currentJourney) {
        this.currentJourney = currentJourney;
    }

    public List<Disruption> getCurrentDisruptions() {
        return currentDisruptions;
    }

    public void setCurrentDisruptions(List<Disruption> currentDisruptions) {
        this.currentDisruptions = currentDisruptions;
    }
}
