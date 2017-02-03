package fr.unice.polytech.qgl.qcd.database;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by thomas on 31/12/2015.
 */
public class BiomeTest {

    Biome unknow=Biome.UNKNOWN;
    Biome alpine=Biome.ALPINE;
    Biome forest_tropical=Biome.TROPICAL_RAIN_FOREST;

    @Before
    public void before() {

    }

    @Test
    public void testToString() throws Exception {

        assertEquals("UNKNOWN",unknow.toString());
        assertEquals("ALPINE",alpine.toString());

    }

    @Test
    public void testGetBiome(){
        assertEquals("BEACH",Biome.BEACH.getBiome());
    }

    @Test
    public void testContainsResource(){

        assertEquals(false,unknow.containsResource(Resource.WOOD));
        assertEquals(false,alpine.containsResource(Resource.WOOD));
        assertEquals(true,forest_tropical.containsResource(Resource.WOOD));
    }

}
