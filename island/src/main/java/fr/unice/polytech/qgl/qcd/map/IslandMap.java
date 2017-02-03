package fr.unice.polytech.qgl.qcd.map;


import fr.unice.polytech.qgl.qcd.database.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Monzein Thomas
 * @version : MUMRAU&MONIERV
 * @changes : V1.0.0    - CrÃ©ation de la classe
 *                      - ReprÃ©sente la terre et la mer
 *                      - utilisation d'un type Ã©numÃ©rÃ© Terrain
 *                      - Une case peut avoir comme valeur "unknown", "land", "sea"
 *                      - Carte Ã  taille dynamique : on peut augmenter
 *                      la carte dans une direction
 *            V1.1.0    - Ajout de la classe InvalidIslandMapParameterException pour la
 *                      gestion des exceptions de la classe.
 *            V2.0.0    - Utilisation du matrice de IslandCell Ã  la place de la matrice
 *                      de Terrain.
 *            V3.0.0    - Ajout de la position du robot.
 *            VMUMRAU&MONIERV - ajout de trucs qui servent vraiment
 */

public class IslandMap {
    private final int DEFAULT_SIZE = 1000;
    private IslandCell spaces[][];
    private Position pos;
    private List<Creek> creeks = new ArrayList<>();
    private boolean dimensionOptimized = true;
    private final int DISTANCE_FOUND = 200;

    //TODO: propre?
    private Biome objBiome;

    /**
     * A method that return the width of the map
     * @return int, width of the map
     */
    public int getWidth() {
        return spaces.length;
    }

    /**
     * A method that return the height of the map
     * @return int, height of the map
     */
    public int getHeight() {
        return spaces[0].length;
    }


    /**
     * Constructeur d'une carte Ã  partir des dimension gauche, droite, en face et de la direction du drone
     * @param g dimension Ã  gauche
     * @param d dimension Ã  droite
     * @param front dimension en face, -1 si obstacle
     * @param dir direction du drone
     */
    public IslandMap(int g, int d, int front, Direction dir){
        int dimension = g + d + 1;
        if (front < 0) {
            dimensionOptimized = false;
            front = DEFAULT_SIZE;
        }
        else front++;

        switch(dir){
            case EAST:
                spaces = new IslandCell[front][dimension];
                pos = new Position(0, g);
                break;
            case WEST:
                spaces = new IslandCell[front][dimension];
                pos = new Position(front - 1, d);
                break;
            case NORTH:
                spaces = new IslandCell[dimension][front];
                pos = new Position(g, front - 1);
                break;
            case SOUTH:
                spaces = new IslandCell[dimension][front];
                pos = new Position(d, 0);
                break;
        }
        initMap();
    }

    /**
     * Construction d'une carte de dimension l * h
     * @param l longueur
     * @param h hauteur
     */
    public IslandMap(int l, int h){
        spaces = new IslandCell[l][h];
        initMap();
    }

    /**
     * MÃ©thode privÃ©e qui met toute les cases Ã  unknown
     */
    private void initMap(){
        for(int i = 0 ; i < spaces.length ; i++)
            for (int j = 0; j < spaces[i].length; j++)
                spaces[i][j] = new IslandCell();
    }

    /**
     * Actualise la position sur la carte Ã  partir d'une direction
     * @param dir une direction
     */
    public void actualizePosition(Direction dir){
        this.pos.actualizePosition(dir);
    }

    /**
     * Actualise la position sur la carte Ã  partir de l'action en cours et suivante
     * @param currentDirection action en cours
     * @param previousDirection action suivante
     */
    public void actualizePosition(Direction currentDirection, Direction previousDirection){
        this.pos.actualizePosition(currentDirection, previousDirection);
    }

    /**
     * Accesseurs de la matrice reprÃ©sentant la carte
     * @return spaces
     */
    public IslandCell[][] getSpaces(){
        return spaces;
    }

    /**
     * Retourne le terrain aux coordonnées x et y
     * @param p
     * @return
     */
    public IslandCell getCase(Position p){
        int x = p.getX();
        int y = p.getY();
        if(x < 0 || x >= spaces.length || y < 0 || y >= spaces[0].length)
            return null;
        return spaces[x][y];
    }

    /**
     * Retourne le terrain aux coordonnées x et y
     * @param x
     * @param y
     * @return
     */
    public IslandCell getCase(int x, int y){
        if(x < 0 || x >= spaces.length || y < 0 || y >= spaces[0].length)
            return null;
        return spaces[x][y];
    }

    /**
     * Retourne le terrain aux coordonnées courantes
     * @return
     */
    public IslandCell getCase(){
        return getCase(pos);
    }

    /**
     * Assigne le terrain Ã  la position courante
     * @param cell
     */
    public void setCase(IslandCell cell){
        setCase(pos,cell);
    }

    /**
     * Assigne le terrain au coordonnées x et y
     * @param p
     * @param cell
     */
    public void setCase(Position p, IslandCell cell){
        int x = p.getX();
        int y = p.getY();
        if(x < 0 || x > spaces.length || y < 0 || y > spaces[0].length)
            return;
        spaces[x][y]=cell;
    }

    /**
     * Assigne le terrain au coordonnées x et y
     * @param x
     * @param y
     * @param cell
     */
    public void setCase(int x, int y, IslandCell cell){
        if(x < 0 || x > spaces.length || y < 0 || y > spaces[0].length)
            return;
        spaces[x][y]=cell;
    }

    /**
     * Accesseur de modification de position
     * @param p
     */
    public void setPos(Position p){
        this.pos=p;
    }

    /**
     * Accesseurs de la liste de crique
     * @return creeks
     */
    public List<Creek> getCreeks() {
        return creeks;
    }

    /**
     * Retourne un String representant la matrice de terrain. Les terrains
     * sont ecrits par leurs premieres lettres.
     */
    public String toString(){
        return MatrixToString(spaces);
    }

    /**
     * Retourne un String representant la matrice de terrain. Les terrains
     * sont ecrits par leurs premieres lettres.
     */
    public String MatrixToString(IslandCell[][] map){
        String s = "The map is sized: " + map.length + " x " + map[0].length + ". Its representation is : \n\n";
        for(int i = 0 ; i < map[0].length ; i++){
            s += "\n|";
            for(int j = 0 ; j < map.length ; j++){
                try{
                    s += " " + map[j][i].toString() + " |";
                }
                catch(Exception E){
                    s +=" " + new IslandCell().toString() +  " |";
                }
            }
        }
        return s;
    }

    /**
     * Permet de remplir les informations de la cellule sur laquelle on se trouve
     * @param biomeList une liste de biome
     * @param creeks un tableau contenant les criques de la case. Le tableau est de taille 0 si aucune crique
     */
    public void scanCell(List<Biome> biomeList, String[] creeks) {
        spaces[pos.getX()][pos.getY()].setBiomes(biomeList);
        for(int i=0; i< creeks.length; i++){
            this.creeks.add(new Creek(pos.getX(),pos.getY(),creeks[i]));
        }

    }

    /**
     * Accesseur de consultation de position
     * @return pos
     */
    public Position getPos() {
        return pos;
    }

    /**
     * Complete une ligne, ou colonne si vertically est Ã  true, selon les informations des lignes/colonnes adjacentes.
     * @param vertically
     */
    public void complete(boolean vertically) {
        int width = getWidth();
        int height = getHeight();
        for (int i = 1 ; i < width - 1 ; i++) {
            for (int j = 1 ; j < height - 1 ; j++) {
                if (spaces[i][j].getBiome(0).equals(Biome.UNKNOWN)) {
                    List<Biome> biomes;
                    if (vertically)
                        biomes = fusion(spaces[i - 1][j].getBiomes(), spaces[i + 1][j].getBiomes());
                    else
                        biomes = fusion(spaces[i][j - 1].getBiomes(), spaces[i][j + 1].getBiomes());
                    spaces[i][j].setBiomes(biomes);
                }
            }
        }
    }

    /**
     * Retourne la fusion de deux listes de biomes ensembles.
     * @param l1
     * @param l2
     * @return
     */
    public static List<Biome> fusion(List<Biome> l1, List<Biome> l2) {
        List<Biome> l = l1;
        int s = l.size();
        int s2 = l2.size();
        for (int i = 0 ; i < s2 ; i++) {
            boolean add = true;
            for (int j = 0 ; j < s ; j++) {
                if (l2.get(i).equals(l.get(j)))
                    add = false;
            }
            if (add)
                l.add(l2.get(i));
        }
        return l;
    }

    //TODO: why founder quand on peut finder ?
    public Position find(Resource r) {
        int step = 1;
        boolean inc = false;
        Direction direction = Direction.NORTH;
        Position pos = new Position(this.pos.getX(), this.pos.getY());

        //TODO:Math floor on double
        int i;
        while(true) {
            if (step > 300) return null;
            for (i = 0 ; i < step ; i++) {
                pos = move(pos, direction);
                if (safePos(pos)) {
                    if (getCase(pos).containsResource(r) && !getCase(pos).isVisited()) {
                        this.objBiome = takeBiome(getCase(pos).getBiomes(), r);
                        getCase(pos).visit();
                        return pos;
                    }
                }
            }
            direction = Direction.toRight(direction);
            if (!inc)
                inc = true;
            else {
                step++;
                inc = false;
            }
        }
    }

    public boolean safePos(Position pos) {
        return pos.getX() > 0 && pos.getX() < getWidth() && pos.getY() > 0 && pos.getY() < getHeight();
    }

    public Position move(Position position, Direction direction) {
        position.actualizePosition(direction);
        return position;
    }
    /**
     * Retourne la position de la case la plus proche contenant la ressource demandée.
     * Attention : Le coût du déplacement n'est pas pris en compte ni la disponnibilitée.
     * Si rien n'est trouvé sur un carré de 50, retourne la position actuelle.
     * Pattern du rayon grandissant.
     * @param r
     * @return
     */
    public Position found(Resource r, Position p){
        int x = p.getX(), y = p.getY();
        // Si la position actuel contient la ressource demandÃ©.
        if(spaces[x][y].containsResource(r) && !spaces[x][y].isVisited()) {
            spaces[x][y].visit();
            objBiome=takeBiome(getCase(p).getBiomes(), r);
            return p;
        }
        // On commence

        int smallest,cur;
        Position result;

        Position north = foundRecu(r,new Position(x,y-1),Direction.NORTH,1);
        smallest=Position.distance(north,p)+1;
        result=north;
        Position east = foundRecu(r,new Position(x+1,y),Direction.EAST,1);
        cur=Position.distance(east,p)+1;
        if((cur>0 && cur<smallest && east!=null) || result == null) {
            smallest = cur;
            result=east;
        }
        Position south = foundRecu(r,new Position(x,y+1),Direction.SOUTH,1);
        cur=Position.distance(south,p)+1;
        if((cur>0 && cur<smallest && south!=null) || result == null) {
            smallest = cur;
            result=south;
        }
        Position west = foundRecu(r,new Position(x-1,y),Direction.WEST,1);
        cur=Position.distance(west,p)+1;
        if((cur>0 && cur<smallest && west!=null) || result == null) {
            result=west;
        }
        //if(smallest>30)
        //    return p;
        if(result!=null) {
            getCase(result).visit();
            objBiome=takeBiome(getCase(result).getBiomes(), r);
        }
        return result;
    }

    private Position foundRecu(Resource r,Position curPos,Direction dir, int cpt){

        if(cpt > DISTANCE_FOUND)
            return null;
        int x=curPos.getX(),y=curPos.getY();
        if(x<0 || x>=spaces.length || y<0 || y>=spaces[0].length)
            return null;

        // Si la position actuel contient la ressource demandÃ©.
        if(spaces[x][y].containsResource(r) && !spaces[x][y].isVisited()) {
            this.objBiome = takeBiome(spaces[x][y].getBiomes(), r);
            return curPos;
        }

        if(dir.equals(Direction.NORTH) || dir.equals(Direction.SOUTH)){
            int smallest=cpt;
            Position front,east,west,result=null;
            if(dir.equals(Direction.NORTH) && y>0) {
                front = foundRecu(r, new Position(x, y - 1), Direction.NORTH, cpt + 1);
                smallest=Position.distance(front,curPos)+cpt;
                result=front;
            }
            else {
                if (dir.equals(Direction.SOUTH) && y < spaces[0].length - 1) {
                    front = foundRecu(r, new Position(x, y + 1), Direction.SOUTH, cpt + 1);
                    smallest=Position.distance(front,curPos)+cpt;
                    result=front;
                }
            }
            if(x<spaces.length-1) {
                east = foundRecu(r, new Position(x + 1, y), Direction.EAST, cpt + 1);
                int cur = Position.distance(east, curPos) + cpt;
                if ((cur>0 && cur<smallest && east!=null) || result == null) {
                    smallest = cur;
                    result = east;
                }
            }
            if(x>0) {
                west = foundRecu(r, new Position(x - 1, y), Direction.WEST, cpt + 1);
                int cur = Position.distance(west, curPos) + cpt;
                if ((cur>0 && cur<smallest && west!=null) || result == null) {
                    result = west;
                }
            }
            return result;
        }
        else {
            if (dir.equals(Direction.EAST))
                return foundRecu(r,new Position(x+1,y),Direction.EAST,cpt+1);
            else if(dir.equals(Direction.WEST))
                return foundRecu(r,new Position(x-1,y),Direction.WEST,cpt+1);
        }
        return null;
    }

    public Position found(Resource r){
        return found(r,pos);
    }


    public void optimizeDimensions(int newDimension) {
        if (dimensionOptimized) {
            return;
        }
        if (getWidth() != 1000 && getHeight() != 1000) {
            dimensionOptimized = true;
            return;
        }
        IslandCell[][] tmp;
        if (getWidth() == 1000) {
            int h = getHeight();
            tmp = new IslandCell[newDimension][h];
            for (int i = 0 ; i < newDimension ; i++) {
                for (int j = 0 ; j < h ; j++) {
                    tmp[i][j] = spaces[i][j];
                }
            }
        } else {
            int w = getWidth();
            tmp = new IslandCell[w][newDimension];
            for (int i = 0 ; i < w ; i++) {
                for (int j = 0 ; j < newDimension ; j++) {
                    tmp[i][j] = spaces[i][j];
                }
            }
        }
        spaces = tmp;
        dimensionOptimized = true;
    }

    public boolean isDimensionOptimized() {
        return dimensionOptimized;
    }

    public void refreshPos(Direction direction) {
        pos.actualizePosition(direction);
    }

    public void convertToGroundMap() {
        int flyWidth = getWidth();
        int flyHeight = getHeight();

        IslandMap groundMap = new IslandMap(flyWidth * 3, flyHeight * 3);
        Position groundPos = new Position(pos.getX() * 3 + 1, pos.getY() * 3 + 1);
        groundMap.setPos(groundPos);

        int x;
        int y;
        for (int i = 0 ; i < flyWidth * 3 ; i++) {
            for (int j = 0 ; j < flyHeight * 3 ; j++) {
                x = i / 3;
                y = j / 3;
                groundMap.setCase(i, j, new IslandCell(spaces[x][y].getBiomes()));
            }
        }
        spaces = groundMap.getSpaces();
        pos = groundMap.getPos();
    }

    /**
     * Dans le cas ou il y a plusieurs biome, renvoit celui qui nous intéresse, càd celui qui contient la ressource désirée.
     * @param biomes
     * @param resource
     * @return
     */
    private Biome takeBiome(List<Biome> biomes, Resource resource) {
        int size = biomes.size();
        for (int i = 0 ; i < size ; i++) {
            if (biomes.get(i).containsResource(resource)) {
                return biomes.get(i);
            }
        }
        return null;
    }

    public void unvisit() {
        for (int i = 0 ; i < getWidth() ; i++) {
            for (int j = 0 ; j < getHeight() ; j++) {
                spaces[i][j].unvisit();
            }
        }
    }

    public Biome getObjBiome() {
        return objBiome;
    }
}