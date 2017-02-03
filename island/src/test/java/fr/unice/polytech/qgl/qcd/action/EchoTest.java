package fr.unice.polytech.qgl.qcd.action;

import fr.unice.polytech.qgl.qcd.database.Direction;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Mathias Chevalier
 * @school Polytech' Nice
 * @date 14/11/2015
 */
public class EchoTest {
    ActionExplorer referenceNorth;
    ActionExplorer referenceEast;


    @Before
    public void setUp() throws Exception {
        this.referenceNorth = new Echo(Direction.NORTH);
        this.referenceEast = new Echo(Direction.EAST);
    }

    @Test
    public void createEchoAction()throws JSONException {
        ActionExplorer echoActionN = new Echo(Direction.NORTH);
        Echo echoEchoE = new Echo(Direction.EAST);
        assertTrue(referenceNorth.equals(echoActionN));
        assertTrue(referenceNorth.getDirection().equals(echoActionN.getDirection()));
        assertTrue(referenceEast.equals(echoEchoE));
        assertTrue(referenceEast.getDirection().equals(echoEchoE.getDirection()));
    }

    @Test
    public void ackTest() throws Exception {
        referenceNorth.catchResponse("{ \"cost\": 1, \"extras\": { \"range\": 2, \"found\": \"GROUND\" }, \"status\": \"OK\" }");
        referenceNorth.analyze();
        assertEquals(1, referenceNorth.getCost());
        assertEquals(2, referenceNorth.getRange());
        assertEquals("GROUND", referenceNorth.getFound());
        assertTrue(referenceNorth.isGround());
    }
}