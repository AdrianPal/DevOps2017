package fr.unice.polytech.qgl.qcd.brain.flyBrain.flyPhase;

import fr.unice.polytech.qgl.qcd.action.ActionExplorer;
import fr.unice.polytech.qgl.qcd.action.Echo;
import fr.unice.polytech.qgl.qcd.action.Fly;
import fr.unice.polytech.qgl.qcd.action.Heading;
import fr.unice.polytech.qgl.qcd.database.Direction;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Monierv on 10/12/2015.
 */
public class FirstHeadingTest {

    private FirstHeading firstHeading;

    private int distanceLeft;
    private int distanceRight;
    private Direction bestSide;

    @Before
    public void beforeFirstHeadingTest() {
        this.firstHeading = new FirstHeading(Direction.NORTH);
    }

    @Test
    public void testNextAction(){
        assertTrue(new Echo(Direction.EAST).equals(new Echo(Direction.EAST)));

        ActionExplorer currentAction = null;
        assertTrue(firstHeading.nextAction(currentAction).equals(new Echo(Direction.NORTH)));

        currentAction = new Echo(Direction.NORTH);
        currentAction.catchResponse("{ \"cost\": 1, \"extras\": { \"range\": 2, \"found\": \"OUT_OF_RANGE\" }, \"status\": \"OK\" }");
        currentAction.analyze();
        assertTrue(firstHeading.nextAction(currentAction).equals(new Echo(Direction.WEST)));

        currentAction = new Echo(Direction.WEST);
        currentAction.catchResponse("{ \"cost\": 1, \"extras\": { \"range\": 4, \"found\": \"OUT_OF_RANGE\" }, \"status\": \"OK\" }");
        currentAction.analyze();
        assertTrue(firstHeading.nextAction(currentAction).equals(new Echo(Direction.EAST)));

        currentAction = new Echo(Direction.EAST);
        currentAction.catchResponse("{ \"cost\": 1, \"extras\": { \"range\": 3, \"found\": \"OUT_OF_RANGE\" }, \"status\": \"OK\" }");
        currentAction.analyze();
        assertTrue(firstHeading.nextAction(currentAction).equals(new Heading(Direction.WEST)));

        assertFalse(firstHeading.isPhaseDone());

        currentAction = new Heading(Direction.WEST);
        currentAction.catchResponse("{ \"cost\": 1, \"extras\": {}, \"status\": \"OK\" }");
        currentAction.analyze();
        assertTrue(firstHeading.nextAction(currentAction).equals(new Fly()));

        assertTrue(firstHeading.isPhaseDone());
    }
}