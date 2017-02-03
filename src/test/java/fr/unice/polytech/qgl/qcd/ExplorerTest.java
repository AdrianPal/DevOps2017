package fr.unice.polytech.qgl.qcd;

import fr.unice.polytech.qgl.qcd.action.Echo;
import fr.unice.polytech.qgl.qcd.action.Stop;
import fr.unice.polytech.qgl.qcd.database.Direction;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Monierv on 17/01/2016.
 */
public class ExplorerTest {

    @Test
    public void testExplorer() throws Exception {
        Explorer explorer = new Explorer();
        Explorer explorerFailed = new Explorer();

        /*** INITIALIZATION ***/
        explorer.initialize("{\n" +
                "    \"heading\": \"E\",\n" +
                "    \"men\": 15,\n" +
                "    \"contracts\": [\n" +
                "      {\n" +
                "        \"amount\": 10,\n" +
                "        \"resource\": \"FLOWER\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"amount\": 300,\n" +
                "        \"resource\": \"QUARTZ\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"amount\": 1000,\n" +
                "        \"resource\": \"WOOD\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"budget\": 7000\n" +
                "  }");
        assertTrue(explorer.isInitializeOK());
        explorerFailed.initialize("This not is correct a sentence.");
        assertFalse(explorerFailed.isInitializeOK());

        /*** DECISION ***/
        assertTrue(explorerFailed.takeDecision().equals(new Stop().getMessageString()));
        assertTrue(explorer.takeDecision().equals(new Echo(Direction.EAST).getMessageString()));
    }

}