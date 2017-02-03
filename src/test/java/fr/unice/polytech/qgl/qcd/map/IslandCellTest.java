package fr.unice.polytech.qgl.qcd.map;

import fr.unice.polytech.qgl.qcd.database.Biome;
import fr.unice.polytech.qgl.qcd.database.Resource;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by thomas on 14/12/2015.
 */
public class IslandCellTest {

    IslandCell cell1;
    IslandCell cell2;

    List<Biome> biomes;

    @Before
    public void before() {

        cell1=new IslandCell();
    }

    @Test
    public void testToString() throws Exception {
        biomes=new ArrayList<Biome>();
        biomes.add(Biome.MANGROVE);
        biomes.add(Biome.TROPICAL_RAIN_FOREST);
        biomes.add(Biome.UNKNOWN);
        cell2=new IslandCell(biomes);

        assertEquals("IslandCell{" + "biomes={" + Biome.UNKNOWN.getBiome() + ";}}",cell1.toString());
        assertEquals("IslandCell{" + "biomes={" + Biome.MANGROVE.getBiome() + ';' + Biome.TROPICAL_RAIN_FOREST.getBiome() + ';'
                + Biome.UNKNOWN.getBiome() + ";}}",cell2.toString());
    }

    @Test
    public void testAddBiome() throws Exception {
        biomes=new ArrayList<Biome>();
        biomes.add(Biome.UNKNOWN);

        assertEquals(biomes,cell1.getBiomes());

        cell1.addBiome(Biome.MANGROVE);
        biomes.add(Biome.MANGROVE);

        assertEquals(biomes,cell1.getBiomes());
    }

    @Test
    public void testGetBiome() throws Exception{

        assertEquals(Biome.UNKNOWN,cell1.getBiome(0));

    }


    @Test
    public void testContainsResource(){
        biomes=new ArrayList<Biome>();
        biomes.add(Biome.MANGROVE);
        biomes.add(Biome.TROPICAL_RAIN_FOREST);
        biomes.add(Biome.UNKNOWN);
        cell2=new IslandCell(biomes);

        assertEquals(false,cell2.containsResource(Resource.QUARTZ));
        assertEquals(true,cell2.containsResource(Resource.WOOD));
    }

}
