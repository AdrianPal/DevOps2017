package fr.unice.polytech.qgl.qcd.progress;

import fr.unice.polytech.qgl.qcd.database.Resource;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Mathias Chevalier
 * @school Polytech' Nice
 * @date 08/12/2015
 */
public class ContractTest {
    Contract one, two, oneBis;

    @Before
    public void setUp(){
        one = new Contract(200, Resource.WOOD);
        two = new Contract(123, Resource.FISH);
        oneBis = new Contract(200, Resource.WOOD);
    }

    @Test
    public void successTest(){
        one.successful();
        assertTrue(one.isSuccess());
        assertFalse(two.isSuccess());
    }

    @Test
    public void failTest(){
        one.successful();
        assertTrue(one.isSuccess());
        one.fail();
        assertFalse(one.isSuccess());
    }
    @Test
    public void equals(){
        assertTrue(one.getAmount() == oneBis.getAmount());
        assertTrue(one.getResource() == oneBis.getResource());
        assertTrue(one.isSuccess() == oneBis.isSuccess());
    }

    @Test
    public void testManufacturedToRawContract(){
        Contract c1 = new Contract(10,Resource.WOOD);
        Contract c2 = new Contract(9,Resource.PLANK);
        Contract c3 = new Contract(5,Resource.GLASS);

        List<Contract> l1 = c1.manufacturedToRawContract();
        List<Contract> l2 = c2.manufacturedToRawContract();
        List<Contract> l3 = c3.manufacturedToRawContract();

        assertEquals(1,l1.size());
        assertEquals(Resource.WOOD,l1.get(0).getResource());
        assertEquals(10,l1.get(0).getAmount());

        assertEquals(2,l2.size());
        assertEquals(Resource.WOOD,l2.get(0).getResource());
        assertEquals(3,l2.get(0).getAmount());
        assertEquals(Resource.PLANK,l2.get(1).getResource());
        assertEquals(9,l2.get(1).getAmount());
        //TODO: Monzein à changer le progress, à voir si c'est toujours ok
/*
        assertEquals(3,l3.size());
        assertEquals(Resource.QUARTZ,l3.get(0).getResource());
        assertEquals(50,l3.get(0).getAmount());
        assertEquals(Resource.WOOD,l3.get(1).getResource());
        assertEquals(25,l3.get(1).getAmount());
        assertEquals(Resource.GLASS,l3.get(2).getResource());
        assertEquals(5,l3.get(2).getAmount());
        */
    }

}