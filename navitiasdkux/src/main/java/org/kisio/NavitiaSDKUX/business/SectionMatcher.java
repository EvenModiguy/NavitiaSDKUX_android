package org.kisio.NavitiaSDKUX.business;

import org.kisio.NavitiaSDK.models.Disruption;
import org.kisio.NavitiaSDK.models.LinkSchema;
import org.kisio.NavitiaSDK.models.Section;

import java.util.ArrayList;
import java.util.List;

public class SectionMatcher {
    public static List<Disruption> getMatchingDisruptions(Section section, List<Disruption> disruptions) {
        if (disruptions == null || section.getDisplayInformations() == null || section.getDisplayInformations().getLinks() == null) {
            return new ArrayList<>();
        }

        List<String> linkIdsWithDisruption = new ArrayList<>();
        for (LinkSchema linkSchema: section.getDisplayInformations().getLinks()) {
            if (linkSchema.getType().equals("disruption") && linkSchema.getId() != null) {
                linkIdsWithDisruption.add(linkSchema.getId());
            }
        }

        List<Disruption> disruptionsResult = new ArrayList<>();
        for (Disruption disruption: disruptions) {
            if (disruption.getId() != null && linkIdsWithDisruption.contains(disruption.getId()) && disruption.getApplicationPeriods() != null) {
                disruptionsResult.add(disruption);
            }
        }

        return disruptionsResult;
    }
}