package fr.unice.polytech.qgl.qcd.database;


/**

 * @author Mathias Chevalier
 * @school Polytech' Nice
 * @date 13/12/2015
 */
public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    /**
     * Actualise la position dans le cas d'un MoveTo/Fly
     * @param dir
     */
    public void actualizePosition(Direction dir){
        switch (dir) {
            case NORTH:
                y--;
                break;
            case SOUTH:
                y++;
                break;
            case EAST:
                x++;
                break;
            case WEST:
                x--;
                break;
        }
    }

    /**
     * Meme méthode que précédemment mais permet de se déplacer de plusieurs cases
     * @param dir
     * @param n
     */
    public void actualizePosition(Direction dir, int n){
        for (int i = 0 ; i < n ; i++) {
            actualizePosition(dir);
        }
    }

    /**
     * Actualise la position dans le cas d'un Heading
     * @param currentDirection
     * @param previousDirection
     */
    public void actualizePosition(Direction currentDirection, Direction previousDirection){

        if (previousDirection.equals(Direction.EAST)) {
            x++;
            if (currentDirection.equals(Direction.NORTH)) y--;
            else if (currentDirection.equals(Direction.SOUTH)) y++;
        }

        else if (previousDirection.equals(Direction.WEST)) {
            x--;
            if (currentDirection.equals(Direction.NORTH)) y--;
            else if (currentDirection.equals(Direction.SOUTH)) y++;
        }

        else if (previousDirection.equals(Direction.NORTH)) {
            y--;
            if (currentDirection.equals(Direction.EAST)) x++;
            else if (currentDirection.equals(Direction.WEST)) x--;
        }

        else {
            y++;
            if (currentDirection.equals(Direction.EAST)) x++;
            else if (currentDirection.equals(Direction.WEST)) x--;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (x != position.x) return false;
        return y == position.y;
    }

    public String toString() {
        return "[" + x + ", " + y + "]";
    }

    /**
     * Retourne la distance entre deux positions
     * Retourne -1 si une des positions est null
     * @param a
     * @param b
     * @return
     */
    public static int distance(Position a,Position b){
        if(a==null || b==null)
            return -1;
        int x=Math.abs(a.getX()-b.getX());
        int y=Math.abs(a.getY()-b.getY());
        return x+y;
    }


}