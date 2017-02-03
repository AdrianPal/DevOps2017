package fr.unice.polytech.qgl.qcd.progress;

import fr.unice.polytech.qgl.qcd.database.Biome;
import fr.unice.polytech.qgl.qcd.database.Resource;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by thomas on 29/02/2016.
 */
public class CalculatorTest {
    Calculator calc;

    @Before
    public void setUp(){
        calc = new Calculator();
    }

    @Test
    public void TestInitialization(){
        assertTrue(calc.getResourcesStats().get(Resource.FISH).equals(Calculator.fishCoeff));
        assertTrue(calc.getResourcesStats().get(Resource.FLOWER).equals(Calculator.flowerCoeff));
        assertTrue(calc.getResourcesStats().get(Resource.FRUITS).equals(Calculator.fruitsCoeff));
        assertTrue(calc.getResourcesStats().get(Resource.ORE).equals(Calculator.oreCoeff));
        assertTrue(calc.getResourcesStats().get(Resource.WOOD).equals(Calculator.woodCoeff));
        assertTrue(calc.getResourcesStats().get(Resource.QUARTZ).equals(Calculator.quartzCoeff));
        assertTrue(calc.getResourcesStats().get(Resource.SUGAR_CANE).equals(Calculator.sugarCaneCoeff));
        assertTrue(calc.getResourcesStats().get(Resource.FUR).equals(Calculator.furCoeff));
        assertTrue(calc.getResourcesStats().get(Resource.GLASS).equals(Calculator.glassCoeff));
        assertTrue(calc.getResourcesStats().get(Resource.INGOT).equals(Calculator.ingotCoeff));
        assertTrue(calc.getResourcesStats().get(Resource.LEATHER).equals(Calculator.leatherCoeff));
        assertTrue(calc.getResourcesStats().get(Resource.PLANK).equals(Calculator.plankCoeff));
        assertTrue(calc.getResourcesStats().get(Resource.RUM).equals(Calculator.rumCoeff));
    }

    @Test
    public void testGetRes(){
        assertTrue(calc.getStat(Resource.WOOD) == Calculator.woodCoeff);
        assertTrue(calc.getStat(Resource.RUM) == Calculator.rumCoeff);
    }

    @Test
    public void testScanBiome(){

        List<Biome> l1=new ArrayList<Biome>();
        l1.add(Biome.UNKNOWN);

        List<Biome> l2=new ArrayList<Biome>();
        l2.add(Biome.UNKNOWN);
        l2.add(Biome.TROPICAL_RAIN_FOREST);
        l2.add(Biome.MANGROVE);

        List<Biome> l3=new ArrayList<Biome>();
        l3.add(Biome.TROPICAL_RAIN_FOREST);

        List<Biome> l4=new ArrayList<Biome>();
        l4.add(Biome.BEACH);
        l4.add(Biome.OCEAN);
        l4.add(Biome.GRASSLAND);

        assertEquals(0,calc.getBiomesFrequences().size());

        calc.scanBiome(l1);
        assertEquals(0,calc.getBiomesFrequences().size());

        calc.scanBiome(l2);
        assertEquals(2,calc.getBiomesFrequences().size());
        assertEquals(1,calc.getBiomeFrequence(Biome.TROPICAL_RAIN_FOREST));
        assertEquals(1,calc.getBiomeFrequence(Biome.MANGROVE));

        calc.scanBiome(l2);
        assertEquals(2,calc.getBiomesFrequences().size());
        assertEquals(2,calc.getBiomeFrequence(Biome.TROPICAL_RAIN_FOREST));
        assertEquals(2,calc.getBiomeFrequence(Biome.MANGROVE));

        calc.scanBiome(l3);
        calc.scanBiome(l4);
        assertEquals(5,calc.getBiomesFrequences().size());
        assertEquals(3,calc.getBiomeFrequence(Biome.TROPICAL_RAIN_FOREST));
        assertEquals(2,calc.getBiomeFrequence(Biome.MANGROVE));
        assertEquals(1,calc.getBiomeFrequence(Biome.BEACH));
        assertEquals(1,calc.getBiomeFrequence(Biome.OCEAN));
        assertEquals(1,calc.getBiomeFrequence(Biome.GRASSLAND));
        assertEquals(0,calc.getBiomeFrequence(Biome.ALPINE));
    }

    @Test
    public void testBiomesFrequencesToResourcesFrequence(){
        List<Biome> l1=new ArrayList<Biome>();
        l1.add(Biome.UNKNOWN);

        List<Biome> l2=new ArrayList<Biome>();
        l2.add(Biome.UNKNOWN);
        l2.add(Biome.TROPICAL_RAIN_FOREST);
        l2.add(Biome.MANGROVE);

        List<Biome> l3=new ArrayList<Biome>();
        l3.add(Biome.TROPICAL_RAIN_FOREST);

        List<Biome> l4=new ArrayList<Biome>();
        l4.add(Biome.BEACH);
        l4.add(Biome.OCEAN);
        l4.add(Biome.GRASSLAND);

        assertEquals(0,calc.getBiomesFrequences().size());

        calc.scanBiome(l1);
        assertEquals(0,calc.getBiomesFrequences().size());

        calc.scanBiome(l2);
        assertEquals(2,calc.getBiomesFrequences().size());
        assertEquals(1,calc.getBiomeFrequence(Biome.TROPICAL_RAIN_FOREST));
        assertEquals(1,calc.getBiomeFrequence(Biome.MANGROVE));

        calc.scanBiome(l2);
        assertEquals(2,calc.getBiomesFrequences().size());
        assertEquals(2,calc.getBiomeFrequence(Biome.TROPICAL_RAIN_FOREST));
        assertEquals(2,calc.getBiomeFrequence(Biome.MANGROVE));

        calc.scanBiome(l3);
        calc.scanBiome(l4);
        assertEquals(5,calc.getBiomesFrequences().size());
        assertEquals(3,calc.getBiomeFrequence(Biome.TROPICAL_RAIN_FOREST));
        assertEquals(2,calc.getBiomeFrequence(Biome.MANGROVE));
        assertEquals(1,calc.getBiomeFrequence(Biome.BEACH));
        assertEquals(1,calc.getBiomeFrequence(Biome.OCEAN));
        assertEquals(1,calc.getBiomeFrequence(Biome.GRASSLAND));
        assertEquals(0,calc.getBiomeFrequence(Biome.ALPINE));

        assertEquals(new HashMap<Resource,Integer>(),calc.getResourcesFrequences());

        calc.biomesFrequencesToResourcesFrequence();

        assertEquals(7,calc.getResourcesFrequences().size());

        assertEquals(1,calc.getResourceFrequence(Resource.FISH));
        assertEquals(1,calc.getResourceFrequence(Resource.QUARTZ));
        assertEquals(1,calc.getResourceFrequence(Resource.FUR));
        assertEquals(5,calc.getResourceFrequence(Resource.WOOD));
        assertEquals(2,calc.getResourceFrequence(Resource.FLOWER));
        assertEquals(3,calc.getResourceFrequence(Resource.FRUITS));
        assertEquals(3,calc.getResourceFrequence(Resource.SUGAR_CANE));

    }

    @Test
    public void testScanResource(){
        List<Biome> l1=new ArrayList<Biome>();
        l1.add(Biome.UNKNOWN);

        List<Biome> l2=new ArrayList<Biome>();
        l2.add(Biome.UNKNOWN);
        l2.add(Biome.TROPICAL_RAIN_FOREST);
        l2.add(Biome.MANGROVE);

        List<Biome> l3=new ArrayList<Biome>();
        l3.add(Biome.TROPICAL_RAIN_FOREST);

        List<Biome> l4=new ArrayList<Biome>();
        l4.add(Biome.BEACH);
        l4.add(Biome.OCEAN);
        l4.add(Biome.GRASSLAND);

        assertEquals(0,calc.getResourcesFrequences().size());

        calc.scanResource(l1);
        assertEquals(0,calc.getResourcesFrequences().size());

        calc.scanResource(l2);
        assertEquals(4,calc.getResourcesFrequences().size());
        assertEquals(1,calc.getResourceFrequence(Resource.WOOD));
        assertEquals(1,calc.getResourceFrequence(Resource.SUGAR_CANE));
        assertEquals(1,calc.getResourceFrequence(Resource.FRUITS));
        assertEquals(1,calc.getResourceFrequence(Resource.FLOWER));

        calc.scanResource(l2);
        assertEquals(4,calc.getResourcesFrequences().size());
        assertEquals(2,calc.getResourceFrequence(Resource.WOOD));
        assertEquals(2,calc.getResourceFrequence(Resource.SUGAR_CANE));
        assertEquals(2,calc.getResourceFrequence(Resource.FRUITS));
        assertEquals(2,calc.getResourceFrequence(Resource.FLOWER));

        calc.scanResource(l3);
        calc.scanResource(l4);
        assertEquals(3,calc.getResourceFrequence(Resource.WOOD));
        assertEquals(3,calc.getResourceFrequence(Resource.SUGAR_CANE));
        assertEquals(3,calc.getResourceFrequence(Resource.FRUITS));
        assertEquals(2,calc.getResourceFrequence(Resource.FLOWER));
        assertEquals(1,calc.getResourceFrequence(Resource.QUARTZ));
        assertEquals(1,calc.getResourceFrequence(Resource.FISH));
        assertEquals(1,calc.getResourceFrequence(Resource.FUR));
    }

    @Test
    public void testEvalContract(){

        List<Contract> list = new ArrayList<Contract>();
        list.add(new Contract(10000,Resource.WOOD));
        list.add(new Contract(500,Resource.QUARTZ));
        list.add(new Contract(900,Resource.FUR));

        List<Biome> l1=new ArrayList<Biome>();
        l1.add(Biome.TROPICAL_RAIN_FOREST);
        List<Biome> l2=new ArrayList<Biome>();
        l2.add(Biome.SUB_TROPICAL_DESERT);
        List<Biome> l3=new ArrayList<Biome>();
        l3.add(Biome.GRASSLAND);
        List<Biome> l4=new ArrayList<Biome>();
        l4.add(Biome.GRASSLAND);
        l4.add(Biome.BEACH);

        for(int i=0;i<24;i++)
            calc.scanResource(l1);
        for(int i=0;i<43;i++)
            calc.scanResource(l2);
        for(int i=0;i<25;i++)
            calc.scanResource(l3);
        for(int i=0;i<31;i++)
            calc.scanResource(l4);

        List<Contract> result = calc.evalContract(list);
        assertEquals(Resource.FUR,result.get(0).getResource());
        assertEquals(Resource.QUARTZ,result.get(1).getResource());
        assertEquals(Resource.WOOD,result.get(2).getResource());

    }

    @Test
    public void testEvalContract2(){

        List<Contract> list = new ArrayList<Contract>();
        list.add(new Contract(10000,Resource.WOOD));
        list.add(new Contract(500,Resource.QUARTZ));
        list.add(new Contract(900,Resource.FUR));

        List<Biome> l1=new ArrayList<Biome>();
        l1.add(Biome.TROPICAL_RAIN_FOREST);
        List<Biome> l2=new ArrayList<Biome>();
        l2.add(Biome.SUB_TROPICAL_DESERT);
        List<Biome> l3=new ArrayList<Biome>();
        l3.add(Biome.GRASSLAND);
        List<Biome> l4=new ArrayList<Biome>();
        l4.add(Biome.GRASSLAND);
        l4.add(Biome.BEACH);

        for(int i=0;i<40;i++)
            calc.scanResource(l1);
        for(int i=0;i<20;i++)
            calc.scanResource(l2);
        for(int i=0;i<20;i++)
            calc.scanResource(l3);
        for(int i=0;i<15;i++)
            calc.scanResource(l4);

        List<Contract> result = calc.evalContract(list);
        assertEquals(Resource.FUR,result.get(0).getResource());
        assertEquals(Resource.QUARTZ,result.get(1).getResource());
        assertEquals(Resource.WOOD,result.get(2).getResource());

    }
}
