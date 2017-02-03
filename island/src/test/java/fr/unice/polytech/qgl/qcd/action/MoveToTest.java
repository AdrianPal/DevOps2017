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
public class MoveToTest {
    ActionExplorer reference;

    @org.junit.Before
    public void setUp() throws Exception {
        this.reference = new MoveTo(Direction.EAST);
    }

    @Test
    public void moveToAction() throws JSONException {
        assertFalse(reference.equals(new MoveTo(Direction.NORTH)));
        assertEquals(Direction.EAST, reference.getDirection());
    }

    @Test
    public void testDirection() {
        ActionExplorer moveToE = new MoveTo(Direction.EAST);
        ActionExplorer moveToN = new MoveTo(Direction.NORTH);

        assertTrue(moveToE.getDirection().equals(Direction.EAST));
        assertTrue(moveToN.getDirection().equals(Direction.NORTH));
        assertFalse(moveToE.getDirection().equals(Direction.NORTH));
        assertFalse(moveToE.getDirection().equals(moveToN.getDirection()));



    }

    @Test
    public void testEquals() {
        assertTrue(reference.equals(new MoveTo(Direction.EAST)));
        assertTrue(new MoveTo(Direction.EAST).equals(new MoveTo(Direction.EAST)));
        assertFalse(new MoveTo(Direction.EAST).equals(new MoveTo(Direction.SOUTH)));
        assertFalse(new MoveTo(Direction.EAST).equals(new MoveTo(Direction.WEST)));
    }
}