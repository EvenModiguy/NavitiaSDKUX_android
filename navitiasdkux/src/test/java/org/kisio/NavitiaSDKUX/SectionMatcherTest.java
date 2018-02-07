package org.kisio.NavitiaSDKUX;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.junit.Before;
import org.junit.Test;
import org.kisio.NavitiaSDK.models.Disruption;
import org.kisio.NavitiaSDK.models.Journeys;
import org.kisio.NavitiaSDK.models.Section;
import org.kisio.NavitiaSDKUX.business.SectionMatcher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SectionMatcherTest {
    private Journeys journeysResponse;

    @Before
    public void setup() {
        try {
            Path jsonFilePath = Paths.get(System.getProperty("user.dir") + "/src/test/java/org/kisio/NavitiaSDKUX/journeysWithDisruptionsResponse.json");
            Gson gson = new Gson();
            journeysResponse = gson.fromJson(new String(Files.readAllBytes(jsonFilePath), "UTF-8"), Journeys.class);
        } catch (IOException e) {
            assertTrue("Test file not loaded : " + e.getMessage(), false);
        } catch (JsonSyntaxException e) {
            assertTrue("JSON parsing error : " + e.getMessage(), false);
        }
    }

    @Test
    public void testExtensionSectionWithValidDisruptionsAndIncludedDate() throws Exception {
        Section section = journeysResponse.getJourneys().get(0).getSections().get(1);
        List<Disruption> matchingDisruptions = SectionMatcher.getMatchingDisruptions(
            section,
            journeysResponse.getDisruptions()
        );

        assertEquals(matchingDisruptions.size(), 1);
        assertEquals(matchingDisruptions.get(0).getCause(), "Travaux");
        assertEquals(matchingDisruptions.get(0).getStatus(), Disruption.StatusEnum.ACTIVE);
        assertEquals(matchingDisruptions.get(0).getMessages().get(0).getText(), "En raison de travaux rue Henri Dunant et rue Chevalier de Kermelec, du lundi 02 octobre 2017 au vendredi 01 décembre 2017, la ligne 10 est déviée dans les deux sens.   Les arrêts \"J.Loth\" sont reportés aux arrêts \"Savina\" - Non Accessible PMR  L?arrêt \"Sécurité Sociale\" en direction de Kervouyec est reporté au poteau provisoire situé rue de la République. - Non Accessible PMR");
        assertEquals(matchingDisruptions.get(0).getApplicationPeriods().get(0).getBegin(), "20170928T140500");
        assertEquals(matchingDisruptions.get(0).getApplicationPeriods().get(0).getEnd(), "20171201T194459");
    }

    @Test
    public void testExtensionSectionWithEmptyDisruptions() throws Exception {
        Section section = journeysResponse.getJourneys().get(0).getSections().get(1);
        List<Disruption> matchingDisruptions = SectionMatcher.getMatchingDisruptions(
            section,
            new ArrayList<Disruption>()
        );

        assertEquals(matchingDisruptions.size(), 0);
    }

    @Test
    public void testExtensionSectionWithNullDisruptions() throws Exception {
        Section section = journeysResponse.getJourneys().get(0).getSections().get(1);
        List<Disruption> matchingDisruptions = SectionMatcher.getMatchingDisruptions(
            section,
            null
        );

        assertEquals(matchingDisruptions.size(), 0);
    }
}
