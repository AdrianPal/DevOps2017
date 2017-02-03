package fr.unice.polytech.qgl.qcd.database;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Monierv on 16/12/2015.
 */
public class PositionTest {

    @Test
    public void testActualizePosition() throws Exception {
        Position pos = new Position(-10, 10);
        assertEquals(-10, pos.getX());
        assertEquals(10, pos.getY());

        //fly
        pos.actualizePosition(Direction.EAST);
        pos.actualizePosition(Direction.EAST);
        pos.actualizePosition(Direction.WEST);
        assertEquals(-9, pos.getX());
        assertEquals(10, pos.getY());
        pos.actualizePosition(Direction.NORTH);
        pos.actualizePosition(Direction.NORTH);
        pos.actualizePosition(Direction.NORTH);
        pos.actualizePosition(Direction.SOUTH);
        assertEquals(-9, pos.getX());
        assertEquals(8, pos.getY());
        pos.actualizePosition(Direction.EAST, 10);
        assertEquals(1, pos.getX());
        pos.actualizePosition(Direction.WEST, 10);
        pos.actualizePosition(Direction.SOUTH, 5);
        assertEquals(13, pos.getY());
        pos.actualizePosition(Direction.NORTH, 5);

        //heading
        pos.actualizePosition(Direction.EAST, Direction.NORTH);
        assertEquals(-8, pos.getX());
        assertEquals(7, pos.getY());
        pos.actualizePosition(Direction.SOUTH, Direction.WEST);
        pos.actualizePosition(Direction.SOUTH, Direction.WEST);
        assertEquals(-10, pos.getX());
        assertEquals(9, pos.getY());

        //equals?
        Position posEqual = new Position(-10, 9);
        Position posNotEqual = new Position(18, 9);
        Position posNotEqual2 = new Position(-10, 18);
        assertTrue(posEqual.equals(pos));
        assertFalse(posNotEqual.equals(pos));
        assertFalse(posNotEqual2.equals(pos));
    }

    @Test
    public void testDistance(){

        Position p0=new Position(0,0);
        Position p1=new Position(0,1);
        Position p2=new Position(1,1);
        Position p3=new Position(2,3);

        assertEquals(0,Position.distance(p0,p0));
        assertEquals(1,Position.distance(p0,p1));
        assertEquals(2,Position.distance(p0,p2));
        assertEquals(1,Position.distance(p1,p2));
        assertEquals(5,Position.distance(p0,p3));
    }
}