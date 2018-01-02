package org.kisio.NavitiaSDKUX;

import org.junit.Test;
import org.kisio.NavitiaSDK.models.Disruption;
import org.kisio.NavitiaSDK.models.Severity;
import org.kisio.NavitiaSDKUX.BusinessLogic.DisruptionLevel;
import org.kisio.NavitiaSDKUX.BusinessLogic.DisruptionMatcher;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DisruptionMatcherTest {
    @Test
    public void testDisruptionWithSeverityNull() throws Exception {
        Disruption disruption = new Disruption();
        disruption.setSeverity(null);

        assertEquals(DisruptionLevel.none, DisruptionMatcher.getLevel(disruption));
    }

    @Test
    public void testDisruptionWithSeverityEffectNull() throws Exception {
        Disruption disruption = new Disruption();
        Severity severity = new Severity();
        severity.setEffect(null);
        disruption.setSeverity(severity);

        assertEquals(DisruptionLevel.none, DisruptionMatcher.getLevel(disruption));
    }

    @Test
    public void testDisruptionWithNewSeverityEffect() throws Exception {
        Disruption disruption = new Disruption();
        Severity severity = new Severity();
        severity.setEffect("UMADBRO?");
        disruption.setSeverity(severity);

        assertEquals(DisruptionLevel.none, DisruptionMatcher.getLevel(disruption));
    }

    @Test
    public void testDisruptionWithSeverityEffectNoService() throws Exception {
        Disruption disruption = new Disruption();
        Severity severity = new Severity();
        severity.setEffect("NO_SERVICE");
        disruption.setSeverity(severity);

        assertEquals(DisruptionLevel.blocking, DisruptionMatcher.getLevel(disruption));
    }

    @Test
    public void testDisruptionWithSeverityEffectReducedService() throws Exception {
        Disruption disruption = new Disruption();
        Severity severity = new Severity();
        severity.setEffect("REDUCED_SERVICE");
        disruption.setSeverity(severity);

        assertEquals(DisruptionLevel.nonblocking, DisruptionMatcher.getLevel(disruption));
    }

    @Test
    public void testDisruptionWithSeverityEffectStopMoved() throws Exception {
        Disruption disruption = new Disruption();
        Severity severity = new Severity();
        severity.setEffect("STOP_MOVED");
        disruption.setSeverity(severity);

        assertEquals(DisruptionLevel.nonblocking, DisruptionMatcher.getLevel(disruption));
    }

    @Test
    public void testDisruptionWithSeverityEffectDetour() throws Exception {
        Disruption disruption = new Disruption();
        Severity severity = new Severity();
        severity.setEffect("DETOUR");
        disruption.setSeverity(severity);

        assertEquals(DisruptionLevel.nonblocking, DisruptionMatcher.getLevel(disruption));
    }

    @Test
    public void testDisruptionWithSeverityEffectSignificantDelays() throws Exception {
        Disruption disruption = new Disruption();
        Severity severity = new Severity();
        severity.setEffect("SIGNIFICANT_DELAYS");
        disruption.setSeverity(severity);

        assertEquals(DisruptionLevel.nonblocking, DisruptionMatcher.getLevel(disruption));
    }

    @Test
    public void testDisruptionWithSeverityEffectAdditionalService() throws Exception {
        Disruption disruption = new Disruption();
        Severity severity = new Severity();
        severity.setEffect("ADDITIONAL_SERVICE");
        disruption.setSeverity(severity);

        assertEquals(DisruptionLevel.nonblocking, DisruptionMatcher.getLevel(disruption));
    }

    @Test
    public void testDisruptionWithSeverityEffectModifiedService() throws Exception {
        Disruption disruption = new Disruption();
        Severity severity = new Severity();
        severity.setEffect("MODIFIED_SERVICE");
        disruption.setSeverity(severity);

        assertEquals(DisruptionLevel.nonblocking, DisruptionMatcher.getLevel(disruption));
    }

    @Test
    public void testDisruptionWithSeverityEffectOtherEffect() throws Exception {
        Disruption disruption = new Disruption();
        Severity severity = new Severity();
        severity.setEffect("OTHER_EFFECT");
        disruption.setSeverity(severity);

        assertEquals(DisruptionLevel.information, DisruptionMatcher.getLevel(disruption));
    }

    @Test
    public void testDisruptionWithSeverityEffectUnknownEffect() throws Exception {
        Disruption disruption = new Disruption();
        Severity severity = new Severity();
        severity.setEffect("UNKNOWN_EFFECT");
        disruption.setSeverity(severity);

        assertEquals(DisruptionLevel.information, DisruptionMatcher.getLevel(disruption));
    }

    @Test
    public void testHighestLevelIsBlocking() throws Exception {
        List<Disruption> disruptions = new ArrayList<>();
        disruptions.add(new Disruption());
        disruptions.add(new Disruption());
        disruptions.add(new Disruption());

        Severity blockingSeverity = new Severity();
        blockingSeverity.setEffect("NO_SERVICE");

        Severity nonblockingSeverity = new Severity();
        nonblockingSeverity.setEffect("DETOUR");

        disruptions.get(0).setSeverity(blockingSeverity);
        disruptions.get(1).setSeverity(nonblockingSeverity);
        disruptions.get(2).setSeverity(nonblockingSeverity);

        assertEquals(DisruptionLevel.blocking, DisruptionMatcher.getHighestDisruptionLevel(disruptions));
    }

    @Test
    public void testHighestLevelIsNonBlocking() throws Exception {
        List<Disruption> disruptions = new ArrayList<>();
        disruptions.add(new Disruption());
        disruptions.add(new Disruption());
        disruptions.add(new Disruption());

        Severity infoSeverity = new Severity();
        infoSeverity.setEffect("OTHER_EFFECT");

        Severity nonblockingSeverity = new Severity();
        nonblockingSeverity.setEffect("DETOUR");

        disruptions.get(0).setSeverity(infoSeverity);
        disruptions.get(1).setSeverity(nonblockingSeverity);
        disruptions.get(2).setSeverity(infoSeverity);

        assertEquals(DisruptionLevel.nonblocking, DisruptionMatcher.getHighestDisruptionLevel(disruptions));
    }

    @Test
    public void testHighestLevelIsInfo() throws Exception {
        List<Disruption> disruptions = new ArrayList<>();
        disruptions.add(new Disruption());
        disruptions.add(new Disruption());
        disruptions.add(new Disruption());

        Severity infoSeverity = new Severity();
        infoSeverity.setEffect("OTHER_EFFECT");

        disruptions.get(0).setSeverity(infoSeverity);
        disruptions.get(1).setSeverity(infoSeverity);
        disruptions.get(2).setSeverity(infoSeverity);

        assertEquals(DisruptionLevel.information, DisruptionMatcher.getHighestDisruptionLevel(disruptions));
    }
}
