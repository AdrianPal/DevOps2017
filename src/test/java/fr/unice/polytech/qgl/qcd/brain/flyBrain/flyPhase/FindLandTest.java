package fr.unice.polytech.qgl.qcd.brain.flyBrain.flyPhase;

import fr.unice.polytech.qgl.qcd.action.ActionExplorer;
import fr.unice.polytech.qgl.qcd.action.Echo;
import fr.unice.polytech.qgl.qcd.action.Fly;
import fr.unice.polytech.qgl.qcd.action.Heading;
import fr.unice.polytech.qgl.qcd.database.Direction;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Monierv on 10/12/2015.
 */
public class FindLandTest {

    private FindLand findLand;

    @Before
    public void beforeFindLandTest() {
        this.findLand = new FindLand(Direction.NORTH, Direction.NORTH);
    }

    @Test
    public void testNextAction(){
        ActionExplorer currentAction = null;
        assert(findLand.nextAction(currentAction).equals(new Echo(Direction.NORTH)));

        currentAction = new Echo(Direction.NORTH);
        currentAction.catchResponse("{ \"cost\": 1, \"extras\": { \"range\": 20, \"found\": \"OUT_OF_RANGE\" }, \"status\": \"OK\" }");
        assert(findLand.nextAction(currentAction).equals(new Fly()));

        currentAction.catchResponse("{ \"cost\": 2, \"extras\": {}, \"status\": \"OK\" }");
        assert(findLand.nextAction(currentAction).equals(new Echo(Direction.NORTH)));

        currentAction.catchResponse("{ \"cost\": 1, \"extras\": { \"range\": 20, \"found\": \"OUT_OF_RANGE\" }, \"status\": \"OK\" }");
        assert(findLand.nextAction(currentAction).equals(new Fly()));

        currentAction.catchResponse("{ \"cost\": 2, \"extras\": {}, \"status\": \"OK\" }");
        assert(findLand.nextAction(currentAction).equals(new Echo(Direction.NORTH)));

        currentAction = new Echo(Direction.NORTH);
        currentAction.catchResponse("{ \"cost\": 1, \"extras\": { \"range\": 20, \"found\": \"GROUND\" }, \"status\": \"OK\" }");
        currentAction.analyze();
        assertTrue(findLand.nextAction(currentAction).equals(new Heading(Direction.NORTH)));
    }
}