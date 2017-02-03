package fr.unice.polytech.qgl.qcd.database;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Monierv on 17/01/2016.
 */

public class CreekTest {

    @Test
    public void testCreek() {
        Creek creek = new Creek(17, 24, "qsdfghjklm");
        Creek creekEqual = new Creek(17, 24, "qsdfghjklm");
        Creek creekNotEqual = new Creek(-17, 24, "qsdfghjklm");
        Creek creekNotEqual2 = new Creek(17, 23, "qsdfghjklm");
        Creek creekNotEqual3 = new Creek(17, 24, "qsdfghjkl");

        assertTrue(creek.equals(creekEqual));
        assertFalse(creek.equals(creekNotEqual));
        assertFalse(creek.equals(creekNotEqual2));
        assertFalse(creek.equals(creekNotEqual3));
    }


}