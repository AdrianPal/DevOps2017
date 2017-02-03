package fr.unice.polytech.qgl.qcd.brain.groundBrain.groundPhase;

import fr.unice.polytech.qgl.qcd.action.ActionExplorer;
import fr.unice.polytech.qgl.qcd.action.Glimpse;
import fr.unice.polytech.qgl.qcd.action.Land;
import fr.unice.polytech.qgl.qcd.action.MoveTo;
import fr.unice.polytech.qgl.qcd.database.Biome;
import fr.unice.polytech.qgl.qcd.database.Direction;
import fr.unice.polytech.qgl.qcd.database.Position;
import fr.unice.polytech.qgl.qcd.database.Resource;
import fr.unice.polytech.qgl.qcd.map.IslandCell;
import fr.unice.polytech.qgl.qcd.map.IslandMap;
import fr.unice.polytech.qgl.qcd.progress.Contract;
import fr.unice.polytech.qgl.qcd.progress.Inventory;
import fr.unice.polytech.qgl.qcd.progress.Progress;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by monierv on 02/03/16.
 */
public class ReachBiomeTest {

    private ReachBiome phase;
    private ReachBiome phase2;
    private IslandMap islandMap;
    private Progress progress;

    @Before
    public void beforeFlyOverTest() {
        Position pos = new Position(1, 2);
        islandMap = new IslandMap(10, 10);
        islandMap.setPos(pos);
        islandMap.setCase(3, 3, new IslandCell(Biome.TEMPERATE_DECIDUOUS_FOREST));
        islandMap.setCase(1, 3, new IslandCell(Biome.OCEAN));
        islandMap.setCase(9, 9, new IslandCell(Biome.TEMPERATE_DECIDUOUS_FOREST));



        List contracts = new ArrayList<Contract>();
        contracts.add(new Contract(50, Resource.WOOD));
        contracts.add(new Contract(100, Resource.ORE));
        List<Resource> resourceList = new ArrayList<>();
        resourceList.add(Resource.WOOD);
        resourceList.add(Resource.ORE);
        progress = new Progress(new Inventory(), contracts, resourceList);

        phase = new ReachBiome(islandMap, progress);
        phase2 = new ReachBiome(islandMap, progress);
    }


    @Test
    public void testNextAction() throws Exception {
        ActionExplorer currentAction = phase.nextAction(new Land("some Id here", 42));
        assertTrue(currentAction.equals(new MoveTo(Direction.EAST)));

        /* ON SE DEPLACE JUSQU'AU BIOME INTERESSANT LE PLUS PROCHE */
        currentAction.catchResponse("{ \"cost\": 4, \"extras\": {}, \"status\": \"OK\" }");
        currentAction.analyze();
        currentAction = phase.nextAction(currentAction);
        assertTrue(currentAction.equals(new MoveTo(Direction.EAST)));
        assertFalse(phase.isPhaseDone());

        currentAction.catchResponse("{ \"cost\": 4, \"extras\": {}, \"status\": \"OK\" }");
        currentAction.analyze();
        currentAction = phase.nextAction(currentAction);
        assertTrue(currentAction.equals(new MoveTo(Direction.SOUTH)));
        assertFalse(phase.isPhaseDone());

        currentAction.catchResponse("{ \"cost\": 4, \"extras\": {}, \"status\": \"OK\" }");
        currentAction.analyze();
        currentAction = phase.nextAction(currentAction);
        assertTrue(currentAction.equals(new Glimpse(Direction.NORTH, 4)));
        assertFalse(phase.isPhaseDone());

        //biome not found
        currentAction.catchResponse("{ \n" +
                "  \"cost\": 3,\n" +
                "  \"extras\": {\n" +
                "    \"asked_range\": 4,\n" +
                "    \"report\": [\n" +
                "      [ [ \"BEACH\", 59.35 ], [ \"OCEAN\", 40.65 ] ],\n" +
                "      [ [ \"OCEAN\", 70.2  ], [ \"BEACH\", 29.8  ] ],\n" +
                "      [ \"OCEAN\", \"BEACH\" ],\n" +
                "      [ \"OCEAN\" ]\n" +
                "    ]\n" +
                "  },\n" +
                "  \"status\": \"OK\"\n" +
                "}  ");
        currentAction.analyze();
        currentAction = phase.nextAction(currentAction);
        assertTrue(currentAction.equals(new Glimpse(Direction.EAST, 4)));
        assertFalse(phase.isPhaseDone());

        //biome found
        currentAction.catchResponse("{ \n" +
                "  \"cost\": 3,\n" +
                "  \"extras\": {\n" +
                "    \"asked_range\": 4,\n" +
                "    \"report\": [\n" +
                "      [ [ \"BEACH\", 59.35 ], [ \"OCEAN\", 40.65 ] ],\n" +
                "      [ [ \"OCEAN\", 70.2  ], [ \"TEMPERATE_DECIDUOUS_FOREST\", 29.8  ] ],\n" +
                "      [ \"OCEAN\", \"BEACH\" ],\n" +
                "      [ \"OCEAN\" ]\n" +
                "    ]\n" +
                "  },\n" +
                "  \"status\": \"OK\"\n" +
                "}  ");
        currentAction.analyze();
        currentAction = phase.nextAction(currentAction);
        assertTrue(currentAction.equals(new MoveTo(Direction.EAST)));
        assertFalse(phase.isPhaseDone());

        currentAction.catchResponse("{ \"cost\": 4, \"extras\": {}, \"status\": \"OK\" }");
        currentAction.analyze();
        currentAction = phase.nextAction(currentAction);
        assertTrue(currentAction.equals(new MoveTo(Direction.EAST)));
        assertTrue(phase.isPhaseDone());

    }

    @Test
    public void testNextAction2() throws Exception {
        ActionExplorer currentAction = phase.nextAction(new Land("some Id here", 42));
        assertTrue(currentAction.equals(new MoveTo(Direction.EAST)));

        /* ON SE DEPLACE JUSQU'AU BIOME INTERESSANT LE PLUS PROCHE */
        currentAction.catchResponse("{ \"cost\": 4, \"extras\": {}, \"status\": \"OK\" }");
        currentAction.analyze();
        currentAction = phase.nextAction(currentAction);
        assertTrue(currentAction.equals(new MoveTo(Direction.EAST)));
        assertFalse(phase.isPhaseDone());

        currentAction.catchResponse("{ \"cost\": 4, \"extras\": {}, \"status\": \"OK\" }");
        currentAction.analyze();
        currentAction = phase.nextAction(currentAction);
        assertTrue(currentAction.equals(new MoveTo(Direction.SOUTH)));
        assertFalse(phase.isPhaseDone());

        currentAction.catchResponse("{ \"cost\": 4, \"extras\": {}, \"status\": \"OK\" }");
        currentAction.analyze();
        currentAction = phase.nextAction(currentAction);
        assertTrue(currentAction.equals(new Glimpse(Direction.NORTH, 4)));
        assertFalse(phase.isPhaseDone());

        //biome not found
        currentAction.catchResponse("{ \n" +
                "  \"cost\": 3,\n" +
                "  \"extras\": {\n" +
                "    \"asked_range\": 4,\n" +
                "    \"report\": [\n" +
                "      [ [ \"BEACH\", 59.35 ], [ \"OCEAN\", 40.65 ] ],\n" +
                "      [ [ \"OCEAN\", 70.2  ], [ \"BEACH\", 29.8  ] ],\n" +
                "      [ \"OCEAN\", \"BEACH\" ],\n" +
                "      [ \"OCEAN\" ]\n" +
                "    ]\n" +
                "  },\n" +
                "  \"status\": \"OK\"\n" +
                "}  ");
        currentAction.analyze();
        currentAction = phase.nextAction(currentAction);
        assertTrue(currentAction.equals(new Glimpse(Direction.EAST, 4)));
        assertFalse(phase.isPhaseDone());

        //biome not found
        currentAction.catchResponse("{ \n" +
                "  \"cost\": 3,\n" +
                "  \"extras\": {\n" +
                "    \"asked_range\": 4,\n" +
                "    \"report\": [\n" +
                "      [ [ \"BEACH\", 59.35 ], [ \"OCEAN\", 40.65 ] ],\n" +
                "      [ [ \"OCEAN\", 70.2  ], [ \"BEACH\", 29.8  ] ],\n" +
                "      [ \"OCEAN\", \"BEACH\" ],\n" +
                "      [ \"OCEAN\" ]\n" +
                "    ]\n" +
                "  },\n" +
                "  \"status\": \"OK\"\n" +
                "}  ");
        currentAction.analyze();
        currentAction = phase.nextAction(currentAction);
        assertTrue(currentAction.equals(new Glimpse(Direction.SOUTH, 4)));
        assertFalse(phase.isPhaseDone());

        //biome not found
        currentAction.catchResponse("{ \n" +
                "  \"cost\": 3,\n" +
                "  \"extras\": {\n" +
                "    \"asked_range\": 4,\n" +
                "    \"report\": [\n" +
                "      [ [ \"BEACH\", 59.35 ], [ \"OCEAN\", 40.65 ] ],\n" +
                "      [ [ \"OCEAN\", 70.2  ], [ \"BEACH\", 29.8  ] ],\n" +
                "      [ \"OCEAN\", \"BEACH\" ],\n" +
                "      [ \"OCEAN\" ]\n" +
                "    ]\n" +
                "  },\n" +
                "  \"status\": \"OK\"\n" +
                "}  ");
        currentAction.analyze();
        currentAction = phase.nextAction(currentAction);
        assertTrue(currentAction.equals(new Glimpse(Direction.WEST, 4)));
        assertFalse(phase.isPhaseDone());

        //biome not found
        currentAction.catchResponse("{ \n" +
                "  \"cost\": 3,\n" +
                "  \"extras\": {\n" +
                "    \"asked_range\": 4,\n" +
                "    \"report\": [\n" +
                "      [ [ \"BEACH\", 59.35 ], [ \"OCEAN\", 40.65 ] ],\n" +
                "      [ [ \"OCEAN\", 70.2  ], [ \"BEACH\", 29.8  ] ],\n" +
                "      [ \"OCEAN\", \"BEACH\" ],\n" +
                "      [ \"OCEAN\" ]\n" +
                "    ]\n" +
                "  },\n" +
                "  \"status\": \"OK\"\n" +
                "}  ");
        currentAction.analyze();
        currentAction = phase.nextAction(currentAction);
        assertTrue(currentAction.equals(new Glimpse(Direction.NORTH, 4)));
        assertTrue(phase.isPhaseDone());

    }
}