package fr.unice.polytech.qgl.qcd.brain.flyBrain;

import fr.unice.polytech.qgl.qcd.action.ActionExplorer;
import fr.unice.polytech.qgl.qcd.action.Echo;
import fr.unice.polytech.qgl.qcd.database.Direction;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Monierv on 10/12/2015.
 */
public class FlyBrainTest {

    private FlyBrain flyBrain;

    @Before
    public void beforeFlyBrainTest() {
        flyBrain = new FlyBrain(Direction.NORTH);
    }

    @Test
    public void testNextAction() throws Exception {
        ActionExplorer currentAction;
        assertEquals(null, flyBrain.getIslandMap());

        currentAction = null;
        assertTrue(flyBrain.nextAction(currentAction).equals(new Echo(Direction.NORTH)));
        flyBrain.getFlyPhase().setPhaseDone();


    }

    @Test
    public void testRefreshDirection() {
        FlyBrain flybrain = new FlyBrain(Direction.NORTH);
    }


}