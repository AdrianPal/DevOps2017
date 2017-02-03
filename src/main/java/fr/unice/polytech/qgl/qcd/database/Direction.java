package fr.unice.polytech.qgl.qcd.database;

/**
 * Created by Monierv on 06/12/2015.
 */
public enum Direction {
    NORTH("N"),
    EAST("E"),
    SOUTH("S"),
    WEST("W");

    public String strDirection;

    Direction(String direction){
        this.strDirection=direction;
    }
    public String getStrDirection(){
        return this.strDirection;
    }


    public boolean equals(Direction direction2) {
        return strDirection.equals(direction2.getStrDirection());
    }


    public static Direction toLeft(Direction direction) {
        switch (direction) {
            case WEST:
                return Direction.SOUTH;
            case NORTH:
                return Direction.WEST;
            case EAST:
                return Direction.NORTH;
            case SOUTH:
                return Direction.EAST;
        }
        return null;
    }

    public static Direction toRight(Direction direction) {
        switch (direction) {
            case WEST:
                return Direction.NORTH;
            case NORTH:
                return Direction.EAST;
            case EAST:
                return Direction.SOUTH;
            case SOUTH:
                return Direction.WEST;
        }
        return null;
    }

    public static Direction toBack(Direction direction) {
        switch (direction) {
            case WEST:
                return Direction.EAST;
            case NORTH:
                return Direction.SOUTH;
            case EAST:
                return Direction.WEST;
            case SOUTH:
                return Direction.NORTH;
        }
        return null;
    }
}
