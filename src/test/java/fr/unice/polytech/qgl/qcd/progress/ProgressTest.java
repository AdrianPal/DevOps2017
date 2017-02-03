package fr.unice.polytech.qgl.qcd.progress;

import fr.unice.polytech.qgl.qcd.database.Resource;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * @author Mathias Chevalier
 * @school Polytech' Nice
 * @date 08/12/2015
 */
public class ProgressTest {
    Progress reference;
    Random rdm = new Random();

    @Before
    public void setUp(){
        List contracts = new ArrayList<Contract>();
        contracts.add(new Contract(50, Resource.WOOD));
        contracts.add(new Contract(100, Resource.ORE));
        List<Resource> resourceList = new ArrayList<>();
        resourceList.add(Resource.WOOD);
        resourceList.add(Resource.ORE);
        reference = new Progress(new Inventory(), contracts, resourceList);
    }

    @Test
    public void initialize(){
        List<Resource> resourceList = new ArrayList<>();
        resourceList.add(Resource.WOOD);
        resourceList.add(Resource.ORE);
        assertEquals(reference.getResourceList(), resourceList);
        assertEquals(reference.getInventory(), new Inventory());
    }

    @Test
    public void woodContract(){
        reference.testSuccess();
        assertFalse(reference.isContractSucces(0));
        reference.add(40);
        assertFalse(reference.isContractSucces(0));
        reference.add(20);
        assertTrue(reference.isContractSucces(0));
        //Todo: Plus de retour arrière.
        //reference.remove(20);
        //assertFalse(reference.isContractSucces(0));
    }

    @Ignore
    public void addTest(){
        int randomizer, before, after;
        before = reference.getCurrentAmount();
        reference.add(randomizer = rdm.nextInt());
        after = reference.getCurrentAmount();
        assertEquals(randomizer, after-before);
    }

    //TODO: méthode remove(int) enlevé car plus utile.
    /*@Ignore
    public void removeTest(){
        int randomizer, before, after;
        before = reference.getCurrentAmount();
        reference.remove(randomizer = rdm.nextInt());
        after = reference.getCurrentAmount();
        assertEquals(randomizer, before-after);
    }*/

    @Ignore
    public void worthyness(){
        List map = new ArrayList<Contract>();
        map.add(new Contract(1, Resource.PLANK));
        map.add(new Contract(1000, Resource.RUM));
        Progress pro = new Progress(new Inventory(), map, new ArrayList<Resource>());
        System.out.println(pro.getResourceToExploit());
    }

    @Test
    public void testBasicReturn(){
        List map = new ArrayList<Contract>();
        map.add(new Contract(1000, Resource.FUR));
        map.add( new Contract(1, Resource.SUGAR_CANE));
        map.add(new Contract(500, Resource.RUM));
        List<Resource> resList = new ArrayList<Resource>();
        resList.add(Resource.SUGAR_CANE);
        resList.add(Resource.FUR);
        Progress pro = new Progress(new Inventory(), map, resList);
        assertTrue(pro.getResourceToExploit().equals(Resource.FUR));
        assertEquals(0,pro.getCurrentContract());
        assertEquals(0,pro.getCurrentAmount());
        assertEquals(1000,pro.getCurrentContractAmount());
        pro.add(500);
        assertEquals(0,pro.getCurrentContract());
        assertEquals(500,pro.getCurrentAmount());
        pro.add(505);
        assertEquals(Resource.SUGAR_CANE,pro.getResourceToExploit());
        assertEquals(1,pro.getCurrentContract());
        assertEquals(1,pro.getCurrentContractAmount());
    }

    @Test
    public void testSetContract(){
        List l = new ArrayList<Contract>();
        List map = new ArrayList<Contract>();
        map.add(new Contract(1000, Resource.FUR));
        map.add( new Contract(1, Resource.SUGAR_CANE));
        map.add(new Contract(500, Resource.RUM));
        List<Resource> resList = new ArrayList<Resource>();
        resList.add(Resource.SUGAR_CANE);
        resList.add(Resource.FUR);
        Progress pro = new Progress(new Inventory(), l, resList);
        assertEquals(0,pro.getContracts().size());
        pro.setContracts(map);
        assertEquals(3,pro.getContracts().size());
    }

}