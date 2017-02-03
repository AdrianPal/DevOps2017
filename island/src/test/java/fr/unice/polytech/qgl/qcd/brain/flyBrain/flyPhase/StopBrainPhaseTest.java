package fr.unice.polytech.qgl.qcd.brain.flyBrain.flyPhase;

import fr.unice.polytech.qgl.qcd.action.Echo;
import fr.unice.polytech.qgl.qcd.action.Scan;
import fr.unice.polytech.qgl.qcd.action.Stop;
import fr.unice.polytech.qgl.qcd.database.Direction;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Monierv on 29/02/2016.
 */
public class StopBrainPhaseTest {

    private EndPhase phase;
    private EndPhase phase2;

    @Before
    public void beforeReachLandTest() {
        phase = new EndPhase(true);
        phase2 = new EndPhase(false);
    }

    @Test
    public void testNextAction() throws Exception {
        assertTrue(phase.nextAction(new Scan()).equals(new Scan()));
        assertTrue(phase2.nextAction(new Echo(Direction.SOUTH)).equals(new Stop()));
    }
}