package fr.unice.polytech.qgl.qcd.action;

import fr.unice.polytech.qgl.qcd.database.Action;
import fr.unice.polytech.qgl.qcd.database.Direction;
import fr.unice.polytech.qgl.qcd.database.Resource;
import org.json.JSONException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Mathias Chevalier
 * @school Polytech' Nice
 * @date 14/11/2015
 */
public class ScoutTest {
    ActionExplorer reference;

    @org.junit.Before
    public void setUp() throws Exception {
        this.reference = new Scout(Direction.EAST);
    }

    @Test
    public void scoutAction() throws JSONException {
        assertFalse(reference.equals(new Scout(Direction.NORTH)));
        assertTrue(reference.equals(new Scout(Direction.EAST)));
        reference.catchResponse("{ \"cost\": 5, \"extras\": { \"altitude\": 1, \"resources\": [\"FUR\", \"WOOD\"] }, \"status\": \"OK\" }");
        reference.analyze();
        assertEquals(5, reference.getCost());
        assertEquals(1, reference.getAltitude());
        assertTrue(reference.contains(Resource.FUR));
        assertFalse(reference.contains(Resource.FISH));

        assertFalse(reference.getAction().equals(Action.heading));
        assertTrue(reference.getAction().equals(Action.scout));

    }
}