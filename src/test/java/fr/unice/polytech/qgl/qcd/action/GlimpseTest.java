package fr.unice.polytech.qgl.qcd.action;

import fr.unice.polytech.qgl.qcd.database.Biome;
import fr.unice.polytech.qgl.qcd.database.Direction;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Mathias Chevalier
 * @school Polytech' Nice
 * @date 14/11/2015
 */
public class GlimpseTest {
    ActionExplorer referenceN4;


    @Before
    public void setUp() throws Exception {
        this.referenceN4 = new Glimpse(Direction.NORTH, 4);
    }

    @Test
    public void glimpseRange1() throws JSONException {
        ActionExplorer glimpse = new Glimpse(Direction.EAST, 1);
        glimpse.catchResponse("{ \n" +
                "  \"cost\": 3,\n" +
                "  \"extras\": {\n" +
                "    \"asked_range\": 1,\n" +
                "    \"report\": [\n" +
                "      [ [ \"MANGROVE\", 59.35 ], [ \"ALPINE\", 40.65 ] ] \n" +
                "    ]\n" +
                "  },\n" +
                "  \"status\": \"OK\"\n" +
                "}  ");
        glimpse.analyze();
        assertEquals(1, glimpse.getRange());
        assertTrue(glimpse.getBiomeListList().get(0).contains(Biome.MANGROVE));
        assertTrue(glimpse.getBiomeListList().get(0).contains(Biome.ALPINE));
    }

    @Test
    public void createGlimpseActionRange4()throws JSONException {
        ActionExplorer glimpse = new Glimpse(Direction.NORTH, 2);
        assertFalse(referenceN4.equals(glimpse));
        referenceN4.catchResponse("{ \n" +
                "  \"cost\": 3,\n" +
                "  \"extras\": {\n" +
                "    \"asked_range\": 4,\n" +
                "    \"report\": [\n" +
                "      [ [ \"BEACH\", 59.35 ], [ \"OCEAN\", 40.65 ] ],\n" +
                "      [ [ \"LAKE\", 70.2  ], [ \"GRASSLAND\", 29.8  ] ],\n" +
                "      [ \"OCEAN\", \"MANGROVE\" ],\n" +
                "      [ \"ALPINE\" ]\n" +
                "    ]\n" +
                "  },\n" +
                "  \"status\": \"OK\"\n" +
                "}  ");
        referenceN4.analyze();
        assertEquals(4,referenceN4.getRange());
        assertTrue(referenceN4.getBiomeListList().get(0).contains(Biome.BEACH));
        assertTrue(referenceN4.getBiomeListList().get(0).contains(Biome.OCEAN));
        assertTrue(referenceN4.getBiomeListList().get(1).contains(Biome.LAKE));
        assertTrue(referenceN4.getBiomeListList().get(1).contains(Biome.GRASSLAND));
        assertTrue(referenceN4.getBiomeListList().get(2).contains(Biome.MANGROVE));
        assertTrue(referenceN4.getBiomeListList().get(2).contains(Biome.OCEAN));
        assertTrue(referenceN4.getBiomeListList().get(3).contains(Biome.ALPINE));
        assertFalse(referenceN4.getBiomeListList().get(3).contains(Biome.BEACH));
        assertFalse(referenceN4.getBiomeListList().get(2).contains(Biome.TEMPERATE_DECIDUOUS_FOREST));
        assertFalse(referenceN4.getBiomeListList().get(1).contains(Biome.SUB_TROPICAL_DESERT));
        assertFalse(referenceN4.getBiomeListList().get(0).contains(Biome.LAKE));
    }

    @Test
    public void containsAtTest(){
        ActionExplorer currentAction =new Glimpse(Direction.NORTH, 4);
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
        assertTrue(Glimpse.containsAt(currentAction.getBiomeListList(), Biome.TROPICAL_SEASONAL_FOREST)==3);

        currentAction =new Glimpse(Direction.NORTH, 4);
        currentAction.catchResponse("{ \"cost\": 3,\n" +
                "  \"extras\": {\n" +
                "    \"asked_range\": 4,\n" +
                "    \"report\": [\n" +
                "      [ [ \"TROPICAL_SEASONAL_FOREST\", 59.35 ], [ \"ALPINE\", 40.65 ] ],\n" +
                "      [ [ \"TROPICAL_SEASONAL_FOREST\", 59.35 ], [ \"ALPINE\", 40.65 ] ],\n" +
                "      [ \"OCEAN\", \"ALPINE\" ],\n" +
                "      [ \"TROPICAL_SEASONAL_FOREST\" ]\n" +
                "    ]\n" +
                "   },\n" +
                "  \"status\": \"OK\"}");
        currentAction.analyze();
        assertTrue(Glimpse.containsAt(currentAction.getBiomeListList(), Biome.TROPICAL_SEASONAL_FOREST)==1);

        currentAction =new Glimpse(Direction.NORTH, 4);
        currentAction.catchResponse("{ \"cost\": 3,\n" +
                "  \"extras\": {\n" +
                "    \"asked_range\": 4,\n" +
                "    \"report\": [\n" +
                "      [ [ \"TROPICAL_SEASONAL_FOREST\", 59.35 ], [ \"ALPINE\", 40.65 ] ],\n" +
                "      [ [ \"TROPICAL_SEASONAL_FOREST\", 59.35 ], [ \"ALPINE\", 40.65 ] ],\n" +
                "      [ \"TROPICAL_SEASONAL_FOREST\", \"ALPINE\" ],\n" +
                "      [ \"OCEAN\" ]\n" +
                "    ]\n" +
                "   },\n" +
                "  \"status\": \"OK\"}");
        currentAction.analyze();
        assertTrue(Glimpse.containsAt(currentAction.getBiomeListList(), Biome.TROPICAL_SEASONAL_FOREST)==2);

        currentAction =new Glimpse(Direction.NORTH, 4);
        currentAction.catchResponse("{ \"cost\": 3,\n" +
                "  \"extras\": {\n" +
                "    \"asked_range\": 4,\n" +
                "    \"report\": [\n" +
                "      [ [ \"TROPICAL_SEASONAL_FOREST\", 59.35 ], [ \"ALPINE\", 40.65 ] ],\n" +
                "      [ [ \"OCEAN\", 59.35 ], [ \"ALPINE\", 40.65 ] ],\n" +
                "      [ \"TROPICAL_SEASONAL_FOREST\", \"ALPINE\" ],\n" +
                "      [ \"OCEAN\" ]\n" +
                "    ]\n" +
                "   },\n" +
                "  \"status\": \"OK\"}");
        currentAction.analyze();
        assertTrue(Glimpse.containsAt(currentAction.getBiomeListList(), Biome.TROPICAL_SEASONAL_FOREST)==0);


        currentAction =new Glimpse(Direction.NORTH, 4);
        currentAction.catchResponse("{ \"cost\": 3,\n" +
                "  \"extras\": {\n" +
                "    \"asked_range\": 4,\n" +
                "    \"report\": [\n" +
                "      [ [ \"OCEAN\", 59.35 ], [ \"ALPINE\", 40.65 ] ],\n" +
                "      [ [ \"OCEAN\", 59.35 ], [ \"ALPINE\", 40.65 ] ],\n" +
                "      [ \"TROPICAL_SEASONAL_FOREST\", \"ALPINE\" ],\n" +
                "      [ \"OCEAN\" ]\n" +
                "    ]\n" +
                "   },\n" +
                "  \"status\": \"OK\"}");
        currentAction.analyze();
        assertTrue(Glimpse.containsAt(currentAction.getBiomeListList(), Biome.TROPICAL_SEASONAL_FOREST)==-1);

/*-------------------------------------------------------------------------------------------------------------------*/
        currentAction =new Glimpse(Direction.NORTH, 3);
        currentAction.catchResponse("{ \"cost\": 3,\n" +
                "  \"extras\": {\n" +
                "    \"asked_range\": 3,\n" +
                "    \"report\": [\n" +
                "      [ [ \"TROPICAL_SEASONAL_FOREST\", 59.35 ], [ \"ALPINE\", 40.65 ] ],\n" +
                "      [ [ \"TROPICAL_SEASONAL_FOREST\", 59.35 ], [ \"ALPINE\", 40.65 ] ],\n" +
                "      [ \"TROPICAL_SEASONAL_FOREST\", \"ALPINE\" ],\n" +
                "    ]\n" +
                "   },\n" +
                "  \"status\": \"OK\"}");
        currentAction.analyze();
        assertTrue(Glimpse.containsAt(currentAction.getBiomeListList(), Biome.TROPICAL_SEASONAL_FOREST)==2);
/*-------------------------------------------------------------------------------------------------------------------*/

        currentAction =new Glimpse(Direction.NORTH, 2);
        currentAction.catchResponse("{ \"cost\": 3,\n" +
                "  \"extras\": {\n" +
                "    \"asked_range\": 2,\n" +
                "    \"report\": [\n" +
                "      [ [ \"TROPICAL_SEASONAL_FOREST\", 59.35 ], [ \"ALPINE\", 40.65 ] ],\n" +
                "      [ [ \"TROPICAL_SEASONAL_FOREST\", 59.35 ], [ \"ALPINE\", 40.65 ] ],\n" +
                "    ]\n" +
                "   },\n" +
                "  \"status\": \"OK\"}");
        currentAction.analyze();
        assertTrue(Glimpse.containsAt(currentAction.getBiomeListList(), Biome.TROPICAL_SEASONAL_FOREST)==1);
/*-------------------------------------------------------------------------------------------------------------------*/

        currentAction =new Glimpse(Direction.NORTH, 1);
        currentAction.catchResponse("{ \"cost\": 3,\n" +
                "  \"extras\": {\n" +
                "    \"asked_range\": 1,\n" +
                "    \"report\": [\n" +
                "      [ [ \"TROPICAL_SEASONAL_FOREST\", 59.35 ], [ \"ALPINE\", 40.65 ] ],\n" +
                "    ]\n" +
                "   },\n" +
                "  \"status\": \"OK\"}");
        currentAction.analyze();
        assertTrue(Glimpse.containsAt(currentAction.getBiomeListList(), Biome.TROPICAL_SEASONAL_FOREST)==0);
    }
}