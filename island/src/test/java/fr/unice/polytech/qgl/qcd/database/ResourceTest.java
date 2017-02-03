package fr.unice.polytech.qgl.qcd.database;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Monz on 19/03/2016.
 */
public class ResourceTest {

    double SECURE_COEFF = 1.1;

    @Test
    public void testGetResourceName() throws Exception {
        assertEquals("RUM",Resource.RUM.getResourceName());
    }

    @Test
    public void testIsManufactured() throws Exception {
        assertEquals(true,Resource.RUM.isManufactured());
        assertEquals(false,Resource.WOOD.isManufactured());
    }
}