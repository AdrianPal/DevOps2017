package fr.unice.polytech.qgl.qcd.action;

import fr.unice.polytech.qgl.qcd.database.Direction;
import org.json.JSONException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Mathias Chevalier
 * @school Polytech' Nice
 * @date 14/11/2015
 */
public class HeadingTest {
    ActionExplorer referenceW;

    @org.junit.Before
    public void setUp() throws Exception {
        this.referenceW = new Heading(Direction.WEST);
    }

    @Test
    public void headingAction() throws JSONException {
        assertFalse(referenceW.equals(new Heading(Direction.EAST)));
        assertTrue(referenceW.equals(new Heading(Direction.WEST)));
        referenceW.catchResponse("{ \"cost\": 4, \"extras\": {}, \"status\": \"OK\" }");
        referenceW.analyze();
        assertEquals(4, referenceW.getCost());
    }

    @Test
    public void testEquals() {
        assertTrue(new Heading(Direction.SOUTH).equals(new Heading(Direction.SOUTH)));
        assertFalse(new Heading(Direction.EAST).equals(new Heading(Direction.NORTH)));
    }

}