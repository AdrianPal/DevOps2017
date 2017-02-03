package fr.unice.polytech.qgl.qcd.action;

import org.json.JSONException;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author Mathias Chevalier
 * @school Polytech' Nice
 * @date 12/11/2015
 */
public class FlyTest {
    ActionExplorer reference;

    @org.junit.Before
    public void setUp() throws Exception {
        this.reference = new Fly();
    }

    @Test
    public void createFlyAction()throws JSONException{
        Fly fly = new Fly();
        assertTrue(reference.equals(fly));
    }
}