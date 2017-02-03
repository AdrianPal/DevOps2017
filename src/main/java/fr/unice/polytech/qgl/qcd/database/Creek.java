package fr.unice.polytech.qgl.qcd.database;

/**
 * @author Mathias Chevalier
 * @school Polytech' Nice
 * @date 13/12/2015
 */

public class Creek {
    private final Position pos;
    private final String ID;

    public Creek(Position pos, String ID) {
        this.pos = pos;
        this.ID = ID;
    }

    public Creek(int x, int y, String ID) {
        this.pos = new Position(x, y);
        this.ID = ID;
    }


    public Position getPos() {
        return pos;
    }

    public String getID() {
        return ID;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Creek creek = (Creek) o;

        if (pos != null ? !pos.equals(creek.pos) : creek.pos != null) return false;
        return !(ID != null ? !ID.equals(creek.ID) : creek.ID != null);

    }

    @Override
    public int hashCode() {
        int result = pos != null ? pos.hashCode() : 0;
        result = 31 * result + (ID != null ? ID.hashCode() : 0);
        return result;
    }
}
