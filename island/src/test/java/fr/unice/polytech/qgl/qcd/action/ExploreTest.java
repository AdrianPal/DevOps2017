package fr.unice.polytech.qgl.qcd.action;

import fr.unice.polytech.qgl.qcd.database.Resource;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Mathias Chevalier
 * @school Polytech' Nice
 * @date 15/11/2015
 */
public class ExploreTest {
    ActionExplorer reference;

    @Before
    public void setUp() throws Exception {
        this.reference = new Explore();
    }

    @Test
    public void equalsExplore(){
        reference.equals(new Explore());
    }
    @Test
    public void ack() throws JSONException {
        reference.catchResponse(" {\n" +
                "    \"cost\": 8,\n" +
                "    \"extras\": {\n" +
                "      \"resources\": [\n" +
                "        {\n" +
                "          \"amount\": \"MEDIUM\",\n" +
                "          \"resource\": \"FISH\",\n" +
                "          \"cond\": \"FAIR\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"amount\": \"HIGH\",\n" +
                "          \"resource\": \"FLOWER\",\n" +
                "          \"cond\": \"FAIR\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"pois\": [{\n" +
                "        \"kind\": \"Creek\",\n" +
                "        \"id\": \"a6f01071-b94d-4a28-9544-f6779540983c\"\n" +
                "      }]\n" +
                "    },\n" +
                "    \"status\": \"OK\"\n" +
                "  }");
        reference.analyze();
        assertTrue(reference.getResourcesList().contains(Resource.FISH));
        assertTrue(reference.contains(Resource.FISH));
        assertTrue(reference.getResourcesList().contains(Resource.FLOWER));
        assertTrue(reference.contains(Resource.FLOWER));
        assertFalse(reference.getResourcesList().contains(Resource.WOOD));
        assertFalse(reference.contains(Resource.WOOD));
        assertTrue(reference.getPois().contains("a6f01071-b94d-4a28-9544-f6779540983c"));
    }
}