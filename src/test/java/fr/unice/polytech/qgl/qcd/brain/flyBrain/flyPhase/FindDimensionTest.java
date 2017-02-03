package fr.unice.polytech.qgl.qcd.brain.flyBrain.flyPhase;

import fr.unice.polytech.qgl.qcd.action.*;
import fr.unice.polytech.qgl.qcd.database.Direction;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Monierv on 29/02/2016.
 */
public class FindDimensionTest {

    private FindDimension phase;

    @Before
    public void beforeFindDimensionTest() {
        phase = new FindDimension(Direction.NORTH, Direction.WEST);
    }


    @Test
    public void testNextAction() throws Exception {
        ActionExplorer currentAction = phase.nextAction(new Scan());

        assertTrue(phase.getMissingDimension() == -1);
        assertTrue(currentAction.equals(new Echo(Direction.NORTH)));
        assertFalse(currentAction.equals(new Echo(Direction.SOUTH)));

        currentAction.catchResponse("{ \"cost\": 1, \"extras\": { \"range\": 5, \"found\": \"GROUND\" }, \"status\": \"OK\" }");
        currentAction.analyze();
        currentAction = phase.nextAction(currentAction);
        assertTrue(currentAction.equals(new Fly()));

        assertFalse(phase.isPhaseDone());

        currentAction.catchResponse("{ \"cost\": 2, \"extras\": {}, \"status\": \"OK\" }");
        currentAction.analyze();
        currentAction = phase.nextAction(currentAction);
        assertTrue(currentAction.equals(new Echo(Direction.NORTH)));

        assertFalse(phase.isPhaseDone());

        currentAction.catchResponse("{ \"cost\": 1, \"extras\": { \"range\": 5, \"found\": \"OUT_OF_RANGE\" }, \"status\": \"OK\" }");
        currentAction.analyze();
        currentAction = phase.nextAction(currentAction);
        assertTrue(currentAction.equals(new Heading(Direction.NORTH)));

        assertFalse(phase.isPhaseDone());

        currentAction.catchResponse("{ \"cost\": 29022016, \"extras\": {}, \"status\": \"OK\" }");
        currentAction.analyze();
        currentAction = phase.nextAction(currentAction);
        assertTrue(currentAction.equals(new Heading(Direction.EAST)));

        assertTrue(phase.isPhaseDone());
        assertTrue(phase.getMissingDimension() == 7); //5 (echo) + 1 (currentCase) + 1 (first heading)
    }
}