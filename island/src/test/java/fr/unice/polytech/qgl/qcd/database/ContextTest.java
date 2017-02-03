package fr.unice.polytech.qgl.qcd.database;

import fr.unice.polytech.qgl.qcd.progress.Contract;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Mathias Chevalier
 * @school Polytech' Nice
 * @date 12/11/2015
 */
public class ContextTest {
    Context reference;

    @Before
    public void setUp() throws Exception {
        Context temp = new Context();
        temp.setBudget(10000);
        temp.setHeading(Direction.WEST);
        temp.setMen(12);
        List tempM = new ArrayList<Contract> ();
        tempM.add(new Contract(600, Resource.WOOD));
        tempM.add(new Contract(200, Resource.SUGAR_CANE));
        temp.setContracts(tempM);
        this.reference = temp;
    }

    @Test
    public void testContext() throws Exception {
        Context context = new Context("{ \"men\": 12,  \"budget\": 10000,  \"contracts\": [  { \"amount\": 600, \"resource\": \"WOOD\" },  { \"amount\": 200, \"resource\": \"SUGAR_CANE\" }  ],  \"heading\": \"W\"}" );
        assertEquals(reference.getMen(), context.getMen());
        assertEquals(reference.getBudget(), context.getBudget());
        assertEquals(reference.getHeading(), context.getHeading());
        assertEquals(reference.getContracts().get(0).getAmount(), context.getContracts().get(0).getAmount());
        assertEquals(reference.getContracts().get(0).getResource(), context.getContracts().get(0).getResource());
        assertEquals(reference.getContracts().get(1).getResource(), context.getContracts().get(1).getResource());
        assertEquals(reference.getContracts().get(1).getResource(), context.getContracts().get(1).getResource());
    }

    //Todo: Ancien test avant MonzFactor, à virer/refaire
    @Ignore
    public void wrongResourceInContract(){
       assertNull(reference.getContracts().get(2));
       assertNull(reference.getContracts().get(50));
       assertNull(reference.getContracts().get(-1));
    }

    @Test
    public void testContextWith4Contracts() throws Exception{
        Context context = new Context("{\n" +
                "    \"heading\": \"E\",\n" +
                "    \"men\": 4,\n" +
                "    \"contracts\": [\n" +
                "      {\n" +
                "        \"amount\": 50,\n" +
                "        \"resource\": \"GLASS\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"amount\": 200,\n" +
                "        \"resource\": \"QUARTZ\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"amount\": 5000,\n" +
                "        \"resource\": \"WOOD\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"amount\": 40,\n" +
                "        \"resource\": \"FLOWER\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"budget\": 15000\n" +
                "  }");
        assertEquals(4, context.getMen());
        assertEquals(15000, context.getBudget());
        assertEquals(Direction.EAST, context.getHeading());
        assertEquals(new Contract(50,Resource.GLASS).getAmount(), context.getContracts().get(0).getAmount());
        assertEquals(new Contract(200,Resource.QUARTZ).getAmount(), context.getContracts().get(1).getAmount());
        assertEquals(new Contract(5000,Resource.WOOD).getAmount(), context.getContracts().get(2).getAmount());
        assertEquals(new Contract(40,Resource.FLOWER).getAmount(), context.getContracts().get(3).getAmount());
    }

    @Test
    public void testResList(){
        Context context = new Context("{ \"men\": 12,  \"budget\": 10000,  \"contracts\": [  { \"amount\": 600, \"resource\": \"WOOD\" },  { \"amount\": 200, \"resource\": \"SUGAR_CANE\" }  ],  \"heading\": \"W\"}" );
        assertTrue(context.getResourceList().contains(Resource.WOOD) && context.getResourceList().contains(Resource.SUGAR_CANE));
        assertFalse(context.getResourceList().contains(Resource.ORE));
        assertFalse(context.getResourceList().contains(Resource.QUARTZ));
    }

}