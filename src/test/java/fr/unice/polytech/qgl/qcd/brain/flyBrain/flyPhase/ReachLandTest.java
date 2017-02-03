package fr.unice.polytech.qgl.qcd.brain.flyBrain.flyPhase;

import fr.unice.polytech.qgl.qcd.action.ActionExplorer;
import fr.unice.polytech.qgl.qcd.action.Echo;
import fr.unice.polytech.qgl.qcd.action.Fly;
import fr.unice.polytech.qgl.qcd.database.Direction;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Created by Monierv on 13/12/2015.
 */
public class ReachLandTest {

    private ReachLand reachLand;

    @Before
    public void beforeReachLandTest() {
        reachLand = new ReachLand(Direction.NORTH, Direction.WEST);
    }

    @Test
    public void testNextAction() throws Exception {

        ActionExplorer currentAction = reachLand.nextAction(null);
        assertTrue(currentAction.equals(new Echo(Direction.NORTH)));

        currentAction.catchResponse("{ \"cost\": 1, \"extras\": { \"range\": 2, \"found\": \"GROUND\" }, \"status\": \"OK\" }");
        currentAction.analyze();
        currentAction = reachLand.nextAction(currentAction);
        assertTrue(currentAction.equals(new Fly()));

        assertFalse(reachLand.isPhaseDone());

        currentAction.catchResponse("{ \"cost\": 2, \"extras\": {}, \"status\": \"OK\" }");
        currentAction.analyze();
        currentAction = reachLand.nextAction(currentAction);
        assertTrue(currentAction.equals(new Fly()));

        assertFalse(reachLand.isPhaseDone());

        currentAction.catchResponse("{ \"cost\": 2, \"extras\": {}, \"status\": \"OK\" }");
        currentAction.analyze();
        currentAction = reachLand.nextAction(currentAction);
        assertTrue(currentAction.equals(new Fly()));

        assertTrue(reachLand.isPhaseDone());
    }


}