package fr.unice.polytech.qgl.qcd.progress;

import fr.unice.polytech.qgl.qcd.database.Resource;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * @author Mathias Chevalier
 * @school Polytech' Nice
 * @date 07/12/2015
 */
public class InventoryTest {
    Inventory inventoryClass;
    Map<Resource, Integer> inventoryMap;
    Random rdm = new Random();

    @Before
    public void createInventory(){
        this.inventoryClass = new Inventory();
        inventoryMap = inventoryClass.getInventory();
    }

    @Test
    public void inventoryIntegrity(){
        assertTrue(inventoryMap.containsKey(Resource.FISH));
        assertTrue(inventoryMap.containsKey(Resource.FLOWER));
        assertTrue(inventoryMap.containsKey(Resource.FRUITS));
        assertTrue(inventoryMap.containsKey(Resource.FUR));
        assertTrue(inventoryMap.containsKey(Resource.ORE));
        assertTrue(inventoryMap.containsKey(Resource.QUARTZ));
        assertTrue(inventoryMap.containsKey(Resource.SUGAR_CANE));
        assertTrue(inventoryMap.containsKey(Resource.WOOD));
        assertTrue(inventoryMap.containsKey(Resource.GLASS));
        assertTrue(inventoryMap.containsKey(Resource.INGOT));
        assertTrue(inventoryMap.containsKey(Resource.LEATHER));
        assertTrue(inventoryMap.containsKey(Resource.PLANK));
        assertTrue(inventoryMap.containsKey(Resource.RUM));
        assertEquals(13, inventoryMap.size());
        assertEquals(inventoryMap, inventoryClass.getInventory());
    }

    @Test
    public void inventoryEmpty(){
        Integer int0 = new Integer(0);
        assertEquals(int0, inventoryMap.get(Resource.FISH));
        assertEquals(int0, inventoryMap.get(Resource.FLOWER));
        assertEquals(int0, inventoryMap.get(Resource.FRUITS));
        assertEquals(int0, inventoryMap.get(Resource.FUR));
        assertEquals(int0, inventoryMap.get(Resource.ORE));
        assertEquals(int0, inventoryMap.get(Resource.QUARTZ));
        assertEquals(int0, inventoryMap.get(Resource.SUGAR_CANE));
        assertEquals(int0, inventoryMap.get(Resource.WOOD));
        assertEquals(int0, inventoryMap.get(Resource.GLASS));
        assertEquals(int0, inventoryMap.get(Resource.INGOT));
        assertEquals(int0, inventoryMap.get(Resource.LEATHER));
        assertEquals(int0, inventoryMap.get(Resource.PLANK));
        assertEquals(int0, inventoryMap.get(Resource.RUM));
    }

    @Test
    public void wrongResource(){
        assertFalse(inventoryMap.containsKey(Resource.UNKNOWN));
    }

    @Test
    public void addResource(){
        int randomizer, before, after;
        before = inventoryClass.get(Resource.WOOD);
        inventoryClass.add(randomizer = rdm.nextInt(1000), Resource.WOOD);
        after = inventoryClass.get(Resource.WOOD);
        assertEquals(randomizer, after-before);
    }

    @Test
    public void removeResource(){
        int randomizer, before, after;
        inventoryClass.add(1500, Resource.ORE);
        before = inventoryClass.get(Resource.ORE);
        inventoryClass.remove(randomizer = rdm.nextInt(1000), Resource.ORE);
        after = inventoryClass.get(Resource.ORE);
        assertEquals(randomizer, before-after);
    }
}