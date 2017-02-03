package fr.unice.polytech.qgl.qcd.action;

import fr.unice.polytech.qgl.qcd.database.Resource;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Mathias Chevalier
 * @school Polytech' Nice
 * @date 17/01/2016
 */
public class TransformTest {
    ActionExplorer reference;

    @Before
    public void setUp() throws Exception {
        this.reference = new Transform(Resource.WOOD, 6, Resource.QUARTZ, 11);
    }

    @Test
    public void transformEqual(){
        assertFalse(reference.equals(new Transform(Resource.WOOD, 6, Resource.ORE, 11)));
        assertTrue(reference.equals(new Transform(Resource.WOOD, 6, Resource.QUARTZ, 11)));
    }

    @Test
    public void ack(){
        reference.catchResponse("{ \"cost\": 5, \"extras\": { \"production\": 1, \"kind\": \"GLASS\" },\"status\": \"OK\" }");
        reference.analyze();
        assertTrue(reference.getKind().equals(Resource.GLASS));
        assertEquals(1, reference.getProduction());
    }
}