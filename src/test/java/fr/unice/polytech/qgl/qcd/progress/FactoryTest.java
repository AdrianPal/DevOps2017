package fr.unice.polytech.qgl.qcd.progress;

import fr.unice.polytech.qgl.qcd.database.Craft;
import fr.unice.polytech.qgl.qcd.database.Resource;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * UnVerDevInt
 * package fr.unice.polytech.qgl.qcd.progress
 * Polytech' Nice - SI3
 * Created: 28/02/16
 *
 * @author Mathias Chevalier
 */
public class FactoryTest {
    Factory factory;

    double SECURE_COEFF = 1.10;

    @Before
    public void before() {
        this.factory = new Factory();
    }

    @Test
    public void testGlass(){
        Craft craft = factory.getNeeds(Resource.GLASS);
        assertTrue(craft.getRes1().equals(Resource.QUARTZ) && craft.getAmount1()==10*SECURE_COEFF);
        assertTrue(craft.getRes2().equals(Resource.WOOD) && craft.getAmount2()==5*SECURE_COEFF);
        assertFalse(craft.getRes1().equals(Resource.INGOT));
        assertTrue(craft.isDuo());
    }

    @Test
    public void testIngot(){
        Craft craft = factory.getNeeds(Resource.INGOT);
        assertTrue(craft.getRes1().equals(Resource.ORE) && craft.getAmount1()==5*SECURE_COEFF);
        assertTrue(craft.getRes2().equals(Resource.WOOD) && craft.getAmount2()==5*SECURE_COEFF);
        assertFalse(craft.getRes1().equals(Resource.LEATHER));
        assertTrue(craft.isDuo());
    }

    @Test
    public void testLeather(){
        Craft craft = factory.getNeeds(Resource.LEATHER);
        assertTrue(craft.getRes1().equals(Resource.FUR) && craft.getAmount1()==3*SECURE_COEFF);
        assertFalse(craft.getRes1().equals(Resource.INGOT) && craft.getAmount1()==5*SECURE_COEFF);
        assertFalse(craft.isDuo());
    }

    @Test
    public void testPlank(){
        Craft craft = factory.getNeeds(Resource.PLANK);
        //TODO:alerte  = 1 alors que 0,25 mayday mayday
        assertTrue(craft.getRes1().equals(Resource.WOOD) && craft.getAmount1()==0.25*SECURE_COEFF);
        assertFalse(craft.getRes1().equals(Resource.INGOT) && craft.getAmount1()==5*SECURE_COEFF);
        assertFalse(craft.isDuo());
    }

    @Test
    public void testRum(){
        Craft craft = factory.getNeeds(Resource.RUM);
        assertTrue(craft.getRes1().equals(Resource.SUGAR_CANE) && craft.getAmount1()==10*SECURE_COEFF);
        assertTrue(craft.getRes2().equals(Resource.FRUITS) && craft.getAmount2()==1*SECURE_COEFF);
        assertFalse(craft.getRes1().equals(Resource.INGOT) && craft.getAmount1()==5*SECURE_COEFF);
        assertTrue(craft.isDuo());
    }
}