package fr.unice.polytech.qgl.qcd.brain.groundBrain.groundPhase;

import fr.unice.polytech.qgl.qcd.action.*;
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
public class HarvestPhase1Test {

    private HarvestPhase1 harvestPhase1;


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

        harvestPhase1 = new HarvestPhase1(islandMap, progress, Biome.TROPICAL_SEASONAL_FOREST, Direction.NORTH,true);
    }

    @Test
    /*
    * Tests the execution of the two patterns of Scout MoveTo Exploit
    * Thus, we check that the action queue refills itself and it corresponds to the expected behavior
    */
    public void testNextAction1() throws Exception {

        ActionExplorer currentAction = harvestPhase1.nextAction(new Land("rfhdgfh", 25));
        assertTrue(currentAction.equals(new Scout(Direction.NORTH)));

        currentAction.catchResponse("{ \"cost\": 5, \"extras\": { \"altitude\": 1, \"resources\": [\"FUR\", \"WOOD\"] }, \"status\": \"OK\" }");
        currentAction.analyze();
        currentAction = harvestPhase1.nextAction(currentAction);
        assertTrue(currentAction.equals(new MoveTo(Direction.NORTH)));

        currentAction.catchResponse("{ \"cost\": 6, \"extras\": { }, \"status\": \"OK\" }");
        currentAction.analyze();
        currentAction = harvestPhase1.nextAction(currentAction);
        assertTrue(currentAction.equals(new Exploit(harvestPhase1.getResourceWanted())));

                /*--------------------------------Second pattern of Scout MoveTo Exploit*/
        currentAction.catchResponse("{ \"cost\": 3, \"extras\": {\"amount\": 9}, \"status\": \"OK\" }");
        currentAction.analyze();
        currentAction = harvestPhase1.nextAction(currentAction);
        assertTrue(currentAction.equals(new Scout(Direction.NORTH)));

    }

        @Test
    /*
    *
    * Checks if we go to the next phase if we don't see the desired resource
    */
    public void testNextAction2() throws Exception {

        assertTrue(harvestPhase1.isPhaseDone()==false);
        ActionExplorer currentAction = harvestPhase1.nextAction(new Land("rfhdgfh", 25));
        assertTrue(currentAction.equals(new Scout(Direction.NORTH)));

        currentAction.catchResponse("{ \"cost\": 5, \"extras\": { \"altitude\": 1, \"resources\": [] }, \"status\": \"OK\" }");
        currentAction.analyze();
        currentAction = harvestPhase1.nextAction(currentAction);
        assertTrue(currentAction.equals(new Glimpse(Direction.NORTH, 4)));
        assertTrue(harvestPhase1.isPhaseDone()==true);


    }

}
