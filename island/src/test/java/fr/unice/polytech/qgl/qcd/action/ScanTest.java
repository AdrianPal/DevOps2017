package fr.unice.polytech.qgl.qcd.action;

import fr.unice.polytech.qgl.qcd.database.Biome;
import org.json.JSONException;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author Mathias Chevalier
 * @school Polytech' Nice
 * @date 14/11/2015
 */
public class ScanTest {
    ActionExplorer reference;

    @org.junit.Before
    public void setUp() throws Exception {
        this.reference = new Scan();
    }

    @Test
    public void createScanAction() throws JSONException{
        assertTrue(reference.equals(new Scan()));
        reference.catchResponse("{\"cost\": 2, \"extras\": { \"biomes\": [\"BEACH\"], \"creeks\": [\"id\"]}, \"status\": \"OK\"}");
        reference.analyze();
        assertTrue(reference.getBiomeList().contains(Biome.BEACH));
        boolean testCreek = false;
        for (int i=0; i < reference.getCreeks().length; i++){
            if (reference.getCreeks()[i].equals("id")) testCreek = true;
        }
        assertTrue(testCreek);
    }

}