package fr.unice.polytech.qgl.qcd.action;

import org.json.JSONException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Mathias Chevalier
 * @school Polytech' Nice
 * @date 14/11/2015
 */
public class LandTest {
    ActionExplorer reference;

    @org.junit.Before
    public void setUp() throws Exception {
        this.reference = new Land("creekBonsoir", 3);
    }

    @Test
    public void landAction() throws JSONException {
        assertFalse(reference.equals(new Land("creekBonjour", 3)));
        assertTrue(reference.equals(new Land("creekBonsoir", 3)));
        assertEquals(3, reference.getPeople());
        assertEquals("creekBonsoir", reference.getCreek());
    }
}