package fr.unice.polytech.qgl.qcd.brain.flyBrain.flyPhase;

import fr.unice.polytech.qgl.qcd.action.*;
import fr.unice.polytech.qgl.qcd.database.Direction;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Monierv on 02/03/2016.
 */
public class GoBackTest {

    private GoBack phase;
    private GoBack phase2;

    @Before
    public void beforeFlyOverTest() {
        phase = new GoBack(Direction.EAST, false, true);
        phase2 = new GoBack(Direction.SOUTH, true, false);
    }


    @Test
    public void testNextAction1() throws Exception {
        ActionExplorer currentAction = phase.nextAction(new Fly());
        assertTrue(currentAction.equals(new Heading(Direction.SOUTH)));

        /* ON A TROUVE UNE CREEK, DONC ON TOURNE, FLY * 2, ET ON RETOURNE */
        currentAction.catchResponse("{ \"cost\": 4, \"extras\": {}, \"status\": \"OK\" }");
        currentAction.analyze();
        currentAction = phase.nextAction(currentAction);
        assertTrue(currentAction.equals(new Fly()));
        assertFalse(phase.isPhaseDone());

        currentAction.catchResponse("{ \"cost\": 4, \"extras\": {}, \"status\": \"OK\" }");
        currentAction.analyze();
        currentAction = phase.nextAction(currentAction);
        assertTrue(currentAction.equals(new Fly()));
        assertFalse(phase.isPhaseDone());

        currentAction.catchResponse("{ \"cost\": 4, \"extras\": {}, \"status\": \"OK\" }");
        currentAction.analyze();
        currentAction = phase.nextAction(currentAction);
        assertTrue(currentAction.equals(new Heading(Direction.WEST)));
        assertFalse(phase.isPhaseDone());

        /* ON ECHO TOUT DROIT VOIR SI LA TERRE EST LOIN -> ici non*/
        currentAction.catchResponse("{ \"cost\": 4, \"extras\": {}, \"status\": \"OK\" }");
        currentAction.analyze();
        currentAction = phase.nextAction(currentAction);
        assertTrue(currentAction.equals(new Echo(Direction.WEST)));
        assertFalse(phase.isPhaseDone());

        currentAction.catchResponse("{ \"cost\": 1, \"extras\": { \"range\": 2, \"found\": \"GROUND\" }, \"status\": \"OK\" }");
        currentAction.analyze();
        currentAction = phase.nextAction(currentAction);
        assertTrue(currentAction.equals(new Scan()));
        assertTrue(phase.isPhaseDone());

    }

    @Test
    public void testNextAction2() throws Exception {
        ActionExplorer currentAction = phase2.nextAction(new Fly());
        assertTrue(currentAction.equals(new Heading(Direction.EAST)));

        /* ON A PAS TROUVE DE CREEK, DONC ON TOURNE ET ON RETOURNE */
        currentAction.catchResponse("{ \"cost\": 4, \"extras\": {}, \"status\": \"OK\" }");
        currentAction.analyze();
        currentAction = phase2.nextAction(currentAction);
        assertTrue(currentAction.equals(new Heading(Direction.NORTH)));
        assertFalse(phase2.isPhaseDone());

        /* ON ECHO TOUT DROIT VOIR SI LA TERRE EST LOIN -> ici oui */
        currentAction.catchResponse("{ \"cost\": 4, \"extras\": {}, \"status\": \"OK\" }");
        currentAction.analyze();
        currentAction = phase2.nextAction(currentAction);
        assertTrue(currentAction.equals(new Echo(Direction.NORTH)));
        assertFalse(phase2.isPhaseDone());

        currentAction.catchResponse("{ \"cost\": 1, \"extras\": { \"range\": 5, \"found\": \"GROUND\" }, \"status\": \"OK\" }");
        currentAction.analyze();
        currentAction = phase2.nextAction(currentAction);
        assertTrue(currentAction.equals(new Fly()));
        assertFalse(phase2.isPhaseDone());

        currentAction.catchResponse("{ \"cost\": 4, \"extras\": {}, \"status\": \"OK\" }");
        currentAction.analyze();
        currentAction = phase2.nextAction(currentAction);
        assertTrue(currentAction.equals(new Fly()));
        assertFalse(phase2.isPhaseDone());

        currentAction.catchResponse("{ \"cost\": 4, \"extras\": {}, \"status\": \"OK\" }");
        currentAction.analyze();
        currentAction = phase2.nextAction(currentAction);
        assertTrue(currentAction.equals(new Fly()));
        assertFalse(phase2.isPhaseDone());

        currentAction.catchResponse("{ \"cost\": 4, \"extras\": {}, \"status\": \"OK\" }");
        currentAction.analyze();
        currentAction = phase2.nextAction(currentAction);
        assertTrue(currentAction.equals(new Fly()));
        assertFalse(phase2.isPhaseDone());

        currentAction.catchResponse("{ \"cost\": 4, \"extras\": {}, \"status\": \"OK\" }");
        currentAction.analyze();
        currentAction = phase2.nextAction(currentAction);
        assertTrue(currentAction.equals(new Fly()));
        assertFalse(phase2.isPhaseDone());

        currentAction.catchResponse("{ \"cost\": 4, \"extras\": {}, \"status\": \"OK\" }");
        currentAction.analyze();
        currentAction = phase2.nextAction(currentAction);
        assertTrue(currentAction.equals(new Echo(Direction.NORTH)));
        assertFalse(phase2.isPhaseDone());

        //maintenant la terre est bien proche
        currentAction.catchResponse("{ \"cost\": 1, \"extras\": { \"range\": 1, \"found\": \"GROUND\" }, \"status\": \"OK\" }");
        currentAction.analyze();
        currentAction = phase2.nextAction(currentAction);
        assertTrue(currentAction.equals(new Scan()));
        assertTrue(phase2.isPhaseDone());
    }
}