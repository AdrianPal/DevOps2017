package fr.unice.polytech.qgl.qcd.database;

import org.junit.Test;

import static fr.unice.polytech.qgl.qcd.database.Direction.*;
import static org.junit.Assert.assertEquals;

/**
 * @author Mathias Chevalier
 * @school Polytech' Nice
 * @date 17/01/2016
 */
public class DirectionTest {

    @Test
    public void leftDir(){
        assertEquals(Direction.SOUTH, toLeft(Direction.WEST));
        assertEquals(Direction.WEST, toLeft(Direction.NORTH));
        assertEquals(Direction.NORTH, toLeft(Direction.EAST));
        assertEquals(Direction.EAST, toLeft(Direction.SOUTH));
    }

    @Test
    public void rightDir(){
        assertEquals(Direction.WEST, toRight(Direction.SOUTH));
        assertEquals(Direction.NORTH, toRight(Direction.WEST));
        assertEquals(Direction.EAST, toRight(Direction.NORTH));
        assertEquals(Direction.SOUTH, toRight(Direction.EAST));
    }

    @Test
    public void backDir(){
        assertEquals(Direction.WEST, toBack(Direction.EAST));
        assertEquals(Direction.NORTH, toBack(Direction.SOUTH));
        assertEquals(Direction.EAST, toBack(Direction.WEST));
        assertEquals(Direction.SOUTH, toBack(Direction.NORTH));
    }

}
