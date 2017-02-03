package fr.unice.polytech.qgl.qcd.brain;

import fr.unice.polytech.qgl.qcd.action.*;
import fr.unice.polytech.qgl.qcd.database.Direction;
import fr.unice.polytech.qgl.qcd.database.Resource;
import fr.unice.polytech.qgl.qcd.progress.Contract;
import fr.unice.polytech.qgl.qcd.progress.Inventory;
import fr.unice.polytech.qgl.qcd.progress.Progress;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by thomas on 17/01/2016.
 */
public class BrainTest {

    Brain b;

    @Before
    public void before() {

        b = new Brain(Direction.EAST,201,10, new Progress(new Inventory(), new ArrayList<Contract>(), new ArrayList<Resource>()));
    }

    @Test
    public void testIsPossible() throws Exception {

        assertEquals(true,b.isPossible());
        assertEquals(false,new Brain(Direction.EAST,1000,0, new Progress(new Inventory(), new ArrayList<Contract>(), new ArrayList<Resource>())).isPossible());
        assertEquals(false,new Brain(Direction.EAST,1000,1, new Progress(new Inventory(), new ArrayList<Contract>(), new ArrayList<Resource>())).isPossible());

    }

    @Ignore
    public void testActualizeBudget(){
        assertEquals(true,b.actualizeBudget());

        ActionExplorer a = new Fly();
        a.catchResponse("{\n" +
                "    \"cost\": 2,\n" +
                "    \"extras\": {},\n" +
                "    \"status\": \"OK\"\n" +
                "  }");
        a.analyze();
        b.setCurrentAction(a);

        assertEquals(true,b.actualizeBudget());
    }

    @Ignore
    public void testNextAction() throws Exception{
        assertEquals(new Echo(Direction.EAST),b.nextAction());
        assertEquals(new Echo(Direction.NORTH),b.nextAction());
        assertEquals(new Echo(Direction.SOUTH),b.nextAction());
        assertEquals(new Heading(Direction.SOUTH),b.nextAction());

        b=new Brain(Direction.EAST,1000,1, new Progress(new Inventory(), new ArrayList<Contract>(), new ArrayList<Resource>()));
        assertEquals(new Stop(),b.nextAction());
        b=new Brain(Direction.EAST,1,10, new Progress(new Inventory(), new ArrayList<Contract>(), new ArrayList<Resource>()));
        assertEquals(new Stop(),b.nextAction());
    }
}