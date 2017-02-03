package fr.unice.polytech.qgl.qcd.brain.flyBrain.flyPhase;

import fr.unice.polytech.qgl.qcd.action.ActionExplorer;
import fr.unice.polytech.qgl.qcd.action.Echo;
import fr.unice.polytech.qgl.qcd.action.Fly;
import fr.unice.polytech.qgl.qcd.action.Scan;
import fr.unice.polytech.qgl.qcd.database.Direction;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by monierv on 01/03/16.
 */
public class FlyOverTest {

    private FlyOver phase;
    private FlyOver phase2;

    @Before
    public void beforeFlyOverTest() {
        phase = new FlyOver(Direction.EAST, false, false);
        phase2 = new FlyOver(Direction.SOUTH, true, false);
    }


    @Test
    public void testNextActionNoCreek() throws Exception {
        ActionExplorer currentAction = phase.nextAction(new Fly());
        assertTrue(currentAction.equals(new Scan()));

        /* ON AVANCE, ON SCAN FLY SUR LA TERRE */
        currentAction.catchResponse("{\"cost\": 2, \"extras\": { \"biomes\": [\"GLACIER\", \"ALPINE\"], \"creeks\": []}, \"status\": \"OK\"}");
        currentAction.analyze();
        currentAction = phase.nextAction(currentAction);
        assertTrue(currentAction.equals(new Fly()));
        assertFalse(phase.isPhaseDone());

        currentAction.catchResponse("{ \"cost\": 4, \"extras\": {}, \"status\": \"OK\" }");
        currentAction.analyze();
        currentAction = phase.nextAction(currentAction);
        assertTrue(currentAction.equals(new Scan()));
        assertFalse(phase.isPhaseDone());

        currentAction.catchResponse("{\"cost\": 2, \"extras\": { \"biomes\": [\"GLACIER\", \"ALPINE\"], \"creeks\": []}, \"status\": \"OK\"}");
        currentAction.analyze();
        currentAction = phase.nextAction(currentAction);
        assertTrue(currentAction.equals(new Fly()));
        assertFalse(phase.isPhaseDone());

        currentAction.catchResponse("{ \"cost\": 4, \"extras\": {}, \"status\": \"OK\" }");
        currentAction.analyze();
        currentAction = phase.nextAction(currentAction);
        assertTrue(currentAction.equals(new Scan()));
        assertFalse(phase.isPhaseDone());



        /* OOH ! ON SCAN DE L'OCEAN, CHECKONS QUAND MEME SI DEVANT IL Y A DE LA TERRE ;) */
        currentAction.catchResponse("{\"cost\": 2, \"extras\": { \"biomes\": [\"OCEAN\"], \"creeks\": []}, \"status\": \"OK\"}");
        currentAction.analyze();
        currentAction = phase.nextAction(currentAction);
        assertTrue(currentAction.equals(new Echo(Direction.EAST)));
        assertFalse(phase.isPhaseDone());

        currentAction.catchResponse("{ \"cost\": 1, \"extras\": { \"range\": 2, \"found\": \"GROUND\" }, \"status\": \"OK\" }");
        currentAction.analyze();
        currentAction = phase.nextAction(currentAction);
        assertTrue(currentAction.equals(new Fly()));
        assertFalse(phase.isPhaseDone());

        currentAction.catchResponse("{ \"cost\": 999999, \"extras\": {}, \"status\": \"OK\" }");
        currentAction.analyze();
        currentAction = phase.nextAction(currentAction);
        assertTrue(currentAction.equals(new Fly()));
        assertFalse(phase.isPhaseDone());

        currentAction.catchResponse("{ \"cost\": 999999, \"extras\": {}, \"status\": \"OK\" }");
        currentAction.analyze();
        currentAction = phase.nextAction(currentAction);
        assertTrue(currentAction.equals(new Fly()));
        assertFalse(phase.isPhaseDone());



        /* MEME CAS QUE PRECEDEMMENT SAUF QUE CETTE FOIS ON NE TROUVE PAS DE TERRE EN FACE -> ON TOURNE */
        currentAction.catchResponse("{ \"cost\": 4, \"extras\": {}, \"status\": \"OK\" }");
        currentAction.analyze();
        currentAction = phase.nextAction(currentAction);
        assertTrue(currentAction.equals(new Scan()));
        assertFalse(phase.isPhaseDone());

        currentAction.catchResponse("{\"cost\": 2, \"extras\": { \"biomes\": [\"OCEAN\"], \"creeks\": []}, \"status\": \"OK\"}");
        currentAction.analyze();
        currentAction = phase.nextAction(currentAction);
        assertTrue(currentAction.equals(new Echo(Direction.EAST)));
        assertFalse(phase.isPhaseDone());

        currentAction.catchResponse("{ \"cost\": 1, \"extras\": { \"range\": 2, \"found\": \"OUT_OF_RANGE\" }, \"status\": \"OK\" }");
        currentAction.analyze();
        currentAction = phase.nextAction(currentAction);
        assertTrue(currentAction.equals(new Echo(Direction.SOUTH)));
        assertFalse(phase.isPhaseDone());

        currentAction.catchResponse("{ \"cost\": 1, \"extras\": { \"range\": 20000, \"found\": \"GROUND\" }, \"status\": \"OK\" }");
        currentAction.analyze();
        currentAction = phase.nextAction(currentAction);
        assertTrue(currentAction.equals(new Scan()));
        assertTrue(phase.isPhaseDone());
    }

    @Test
    public void testNextActionCreek() throws Exception {
        ActionExplorer currentAction = phase2.nextAction(new Fly());
        assertTrue(currentAction.equals(new Scan()));

        /* ON AVANCE, ON SCAN FLY SUR LA TERRE ET ON TROUVE UNE CRIQUE (hourra!) */
        currentAction.catchResponse("{\"cost\": 2, \"extras\": { \"biomes\": [\"GLACIER\", \"ALPINE\"], \"creeks\": [idQuelconque]}, \"status\": \"OK\"}");
        currentAction.analyze();
        currentAction = phase2.nextAction(currentAction);
        assertTrue(currentAction.equals(new Scan()));
        assertFalse(phase2.isPhaseDone());

        /* ON SCAN DONC TOUS LES 4 FLYs */
        currentAction.catchResponse("{\"cost\": 2, \"extras\": { \"biomes\": [\"GLACIER\", \"ALPINE\"], \"creeks\": [idQuelconque]}, \"status\": \"OK\"}");
        currentAction.analyze();
        currentAction = phase2.nextAction(currentAction);
        assertTrue(currentAction.equals(new Fly()));
        assertFalse(phase2.isPhaseDone());

        currentAction.catchResponse("{\"cost\": 2, \"extras\": {}, \"status\": \"OK\"}");
        currentAction.analyze();
        currentAction = phase2.nextAction(currentAction);
        assertTrue(currentAction.equals(new Fly()));
        assertFalse(phase2.isPhaseDone());

        currentAction.catchResponse("{ \"cost\": 4, \"extras\": {}, \"status\": \"OK\" }");
        currentAction.analyze();
        currentAction = phase2.nextAction(currentAction);
        assertTrue(currentAction.equals(new Fly()));
        assertFalse(phase2.isPhaseDone());

        currentAction.catchResponse("{ \"cost\": 7, \"extras\": {}, \"status\": \"OK\" }");
        currentAction.analyze();
        currentAction = phase2.nextAction(currentAction);
        assertTrue(currentAction.equals(new Fly()));
        assertFalse(phase2.isPhaseDone());

        currentAction.catchResponse("{ \"cost\": 7, \"extras\": {}, \"status\": \"OK\" }");
        currentAction.analyze();
        currentAction = phase2.nextAction(currentAction);
        assertTrue(currentAction.equals(new Scan()));
        assertFalse(phase2.isPhaseDone());


        /* ON SCAN DE L'OCEAN ET ON NE TROUVE PAS DE TERRE EN FACE -> ENDPHASE */
        currentAction.catchResponse("{\"cost\": 2, \"extras\": { \"biomes\": [\"OCEAN\"], \"creeks\": []}, \"status\": \"OK\"}");
        currentAction.analyze();
        currentAction = phase2.nextAction(currentAction);
        assertTrue(currentAction.equals(new Echo(Direction.SOUTH)));
        assertFalse(phase2.isPhaseDone());

        currentAction.catchResponse("{ \"cost\": 1, \"extras\": { \"range\": 25, \"found\": \"OUT_OF_RANGE\" }, \"status\": \"OK\" }");
        currentAction.analyze();
        currentAction = phase2.nextAction(currentAction);
        assertTrue(currentAction.equals(new Echo(Direction.EAST)));
        assertFalse(phase2.isPhaseDone());

        currentAction.catchResponse("{ \"cost\": 1, \"extras\": { \"range\": 25, \"found\": \"OUT_OF_RANGE\" }, \"status\": \"OK\" }");
        currentAction.analyze();
        currentAction = phase2.nextAction(currentAction);
        assertTrue(currentAction.equals(new Scan()));
        assertTrue(phase2.isPhaseDone());
    }
}
