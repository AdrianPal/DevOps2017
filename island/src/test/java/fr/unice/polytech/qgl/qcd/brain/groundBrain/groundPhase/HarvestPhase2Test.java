package fr.unice.polytech.qgl.qcd.brain.groundBrain.groundPhase;

import fr.unice.polytech.qgl.qcd.action.ActionExplorer;
import fr.unice.polytech.qgl.qcd.action.Glimpse;
import fr.unice.polytech.qgl.qcd.action.Land;
import fr.unice.polytech.qgl.qcd.action.MoveTo;
import fr.unice.polytech.qgl.qcd.database.Biome;
import fr.unice.polytech.qgl.qcd.database.Direction;
import fr.unice.polytech.qgl.qcd.database.Resource;
import fr.unice.polytech.qgl.qcd.map.IslandMap;
import fr.unice.polytech.qgl.qcd.progress.Contract;
import fr.unice.polytech.qgl.qcd.progress.Inventory;
import fr.unice.polytech.qgl.qcd.progress.Progress;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;


/**
 * Created by Antoine on 07/03/2016.
 */
public class HarvestPhase2Test {

    private HarvestPhase2 harvestPhase2;


    @Before
    public void beforeHarvestBiomeTest() {
        IslandMap islandMap = new IslandMap(1, 1,-1, Direction.EAST);
        List contracts = new ArrayList<Contract>();
        contracts.add(new Contract(50, Resource.WOOD));
        contracts.add(new Contract(100, Resource.ORE));
        List<Resource> resourceList = new ArrayList<>();
        resourceList.add(Resource.WOOD);
        resourceList.add(Resource.ORE);
        Progress progress = new Progress(new Inventory(), contracts, resourceList);

        harvestPhase2 = new HarvestPhase2(islandMap,progress,Biome.TROPICAL_SEASONAL_FOREST, Direction.NORTH,true);
    }




    @Test
    /*
    * Checks if we go back to phase1 if we detect the desired biome
    */
    public void testNextAction1() {

        Direction currentDirection = Direction.NORTH;
        Direction nextDirection = Direction.toRight(currentDirection);
        ActionExplorer currentAction = harvestPhase2.nextAction(new Land("rfhdgfh", 25));
        assertTrue(currentAction.equals(new Glimpse(Direction.NORTH, 4)));

        currentAction.catchResponse("{ \"cost\": 3,\n" +
                "  \"extras\": {\n" +
                "    \"asked_range\": 4,\n" +
                "    \"report\": [\n" +
                "      [ [ \"TROPICAL_SEASONAL_FOREST\", 59.35 ], [ \"ALPINE\", 40.65 ] ],\n" +
                "      [ [ \"TROPICAL_SEASONAL_FOREST\", 59.35 ], [ \"ALPINE\", 40.65 ] ],\n" +
                "      [ \"TROPICAL_SEASONAL_FOREST\", \"ALPINE\" ],\n" +
                "      [ \"TROPICAL_SEASONAL_FOREST\" ]\n" +
                "    ]\n" +
                "   },\n" +
                "  \"status\": \"OK\"}");
        currentAction.analyze();
        currentAction = harvestPhase2.nextAction(currentAction);
        assertTrue(currentAction.equals(new MoveTo(nextDirection)));
        assertTrue(harvestPhase2.isPhaseDone() == true);
        assertTrue(harvestPhase2.isGoPhase1() == true);
        assertTrue(harvestPhase2.isBorder() == false);

    }


    @Test
    /*
    * Checks if we go back to reachbiome phase if we do not detect the desired biome
    */
    public void testNextAction2() {

        Direction currentDirection = Direction.NORTH;
        Direction nextDirection = Direction.toRight(currentDirection);
        ActionExplorer currentAction = harvestPhase2.nextAction(new Land("rfhdgfh", 25));
        assertTrue(currentAction.equals(new Glimpse(Direction.NORTH, 4)));

        currentAction.catchResponse("{ \"cost\": 3,\n" +
                "  \"extras\": {\n" +
                "    \"asked_range\": 4,\n" +
                "    \"report\": [\n" +
                "      [ [ \"GRASSLAND\", 59.35 ], [ \"ALPINE\", 40.65 ] ],\n" +
                "      [ [ \"GRASSLAND\", 59.35 ], [ \"ALPINE\", 40.65 ] ],\n" +
                "      [ \"GRASSLAND\", \"ALPINE\" ],\n" +
                "      [ \"GRASSLAND\" ]\n" +
                "    ]\n" +
                "   },\n" +
                "  \"status\": \"OK\"}");
        currentAction.analyze();
        currentAction = harvestPhase2.nextAction(currentAction);
        assertTrue(currentAction.equals(new MoveTo(nextDirection)));
        assertTrue(harvestPhase2.isPhaseDone() == true);
        assertTrue(harvestPhase2.isGoPhase1() == false);
        assertTrue(harvestPhase2.isBorder() == true);

    }

}
