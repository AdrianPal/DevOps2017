package fr.unice.polytech.qgl.qcd.map;

import fr.unice.polytech.qgl.qcd.database.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by epu on 11/16/15.
 */
public class IslandMapTest {

    IslandMap m;
    IslandMap superMap;
    IslandMap miniMap;
    List<Biome> biomes;



    @Before
    public void before() {

        m = new IslandMap(30,30);
        superMap = new IslandMap(40,50,-1, Direction.EAST);
        miniMap = new IslandMap(3, 3);
        this.initializeMiniMap();
    }

    private void initializeMiniMap() {
        List<Biome> biomes1 = new ArrayList<>();
        biomes1.add(Biome.ALPINE);
        biomes1.add(Biome.TROPICAL_RAIN_FOREST);
        miniMap.setPos(new Position(2, 1));
        miniMap.setCase(0, 0, new IslandCell(Biome.OCEAN));
        miniMap.setCase(1, 0, new IslandCell(biomes1));
        miniMap.setCase(2, 0, new IslandCell(Biome.OCEAN));
        miniMap.setCase(0, 1, new IslandCell(Biome.OCEAN));
        miniMap.setCase(1, 1, new IslandCell(Biome.OCEAN));
        miniMap.setCase(2, 1, new IslandCell(Biome.OCEAN));
        miniMap.setCase(0, 2, new IslandCell(Biome.OCEAN));
        miniMap.setCase(1, 2, new IslandCell(Biome.OCEAN));
        miniMap.setCase(2, 2, new IslandCell(Biome.OCEAN));
    }

    @Test
    public void testFlyMap(){

        IslandMap mapTest = new IslandMap(4,5,12,Direction.EAST);
        Position expected = new Position(0,4);
        assertEquals(expected,mapTest.getPos());
        assertEquals(13,mapTest.getSpaces().length);
        assertEquals(10,mapTest.getSpaces()[0].length);

        IslandMap mapTest2 = new IslandMap(4,5,-1,Direction.EAST);
        Position expected2 = new Position(0,4);
        assertEquals(expected2,mapTest2.getPos());
        assertEquals(1000,mapTest2.getSpaces().length);
        assertEquals(10,mapTest2.getSpaces()[0].length);

        IslandMap mapTest3 = new IslandMap(4,5,12,Direction.WEST);
        Position expected3 = new Position(12,5);
        assertEquals(expected3,mapTest3.getPos());
        assertEquals(13,mapTest3.getSpaces().length);
        assertEquals(10,mapTest3.getSpaces()[0].length);

        IslandMap mapTest4 = new IslandMap(4,5,-1,Direction.WEST);
        Position expected4 = new Position(999,5);
        assertEquals(expected4,mapTest4.getPos());
        assertEquals(1000,mapTest4.getSpaces().length);
        assertEquals(10,mapTest4.getSpaces()[0].length);


        IslandMap mapTest5 = new IslandMap(6,3,12,Direction.SOUTH);
        Position expected5 = new Position(3,0);
        assertEquals(expected5,mapTest5.getPos());
        assertEquals(10,mapTest5.getSpaces().length);
        assertEquals(13,mapTest5.getSpaces()[0].length);

        IslandMap mapTest6 = new IslandMap(6,3,-1,Direction.SOUTH);
        Position expected6 = new Position(3,0);
        assertEquals(expected6,mapTest6.getPos());
        assertEquals(10,mapTest6.getSpaces().length);
        assertEquals(1000,mapTest6.getSpaces()[0].length);

        IslandMap mapTest7 = new IslandMap(4,5,12,Direction.NORTH);
        Position expected7 = new Position(4,12);
        assertEquals(expected7,mapTest7.getPos());
        assertEquals(10,mapTest7.getSpaces().length);
        assertEquals(13,mapTest7.getSpaces()[0].length);

        IslandMap mapTest8 = new IslandMap(4,5,-1,Direction.NORTH);
        Position expected8 = new Position(4,999);
        assertEquals(expected8,mapTest8.getPos());
        assertEquals(10,mapTest8.getSpaces().length);
        assertEquals(1000,mapTest8.getSpaces()[0].length);
    }

    @Test
    public void testGetCase() throws Exception {
        biomes=new ArrayList<Biome>();
        biomes.add(Biome.UNKNOWN);

        assertEquals(biomes,m.getCase(new Position(0,0)).getBiomes());
    }

    @Test
    public void testSetCase() throws Exception {
        biomes=new ArrayList<Biome>();
        biomes.add(Biome.MANGROVE);
        biomes.add(Biome.TROPICAL_RAIN_FOREST);
        biomes.add(Biome.UNKNOWN);

        m.setCase(new Position(0,0), new IslandCell(biomes));
        assertEquals(biomes,m.getCase(new Position(0,0)).getBiomes());
    }

    @Test
    public void setPos(){
        m.setPos(new Position(10,10));
        assertEquals(new Position(10,10),m.getPos());
    }

    @Test
    public void testInitSuper(){
        Position p = superMap.getPos();
        assertEquals(0, p.getX());
        assertEquals(40, p.getY());
    }
    @Test
    public void testCase(){
        IslandCell[][] temp = superMap.getSpaces();
        for (int i = 0; i < temp.length; i++){
            for (int j = 0; j < temp[i].length; j++){
                assertEquals(new IslandCell().getBiomes(),superMap.getCase(new Position(i,j)).getBiomes());
                //assertEquals(new IslandCell().getTerrain(),superMap.getCase(new Position(i,j)).getTerrain());
            }
        }
    }

    @Test
    public void testScanCell(){
        biomes=new ArrayList<Biome>();
        biomes.add(Biome.UNKNOWN);
        assertEquals(biomes,superMap.getCase(new Position(0,0)).getBiomes());
        assertEquals(new ArrayList<Creek>(),superMap.getCreeks());

        biomes=new ArrayList<Biome>();
        biomes.add(Biome.MANGROVE);
        biomes.add(Biome.TROPICAL_RAIN_FOREST);
        biomes.add(Biome.UNKNOWN);
        List<Creek> l = new ArrayList<Creek>();
        String[] creeks = {"Crique test"};
        l.add(new Creek(superMap.getPos().getX(),superMap.getPos().getY(),creeks[0]));
        superMap.scanCell(biomes,creeks);
        assertEquals(biomes,superMap.getCase(superMap.getPos()).getBiomes());
        assertEquals(1,superMap.getCreeks().size());
        assertEquals(l.get(0),superMap.getCreeks().get(0));

    }

    @Test
    public void testComplete() throws Exception{

        List<Biome> l1=new ArrayList<>();
        List<Biome> l2=new LinkedList<>();
        List<Biome> l3=new LinkedList<>();
        l1.add(Biome.ALPINE);
        l1.add(Biome.GLACIER);
        l2.add(Biome.GLACIER);
        l2.add(Biome.GRASSLAND);
        l3.add(Biome.BEACH);
        l3.add(Biome.LAKE);


        List<Biome> lexpected0=new ArrayList<>();
        lexpected0.add(Biome.UNKNOWN);
        List<Biome> lexpected1=new ArrayList<>();
        lexpected1.add(Biome.ALPINE);
        lexpected1.add(Biome.GLACIER);
        lexpected1.add(Biome.GRASSLAND);
        List<Biome> lexpected2=new ArrayList<>();
        lexpected2.add(Biome.ALPINE);
        lexpected2.add(Biome.GLACIER);
        lexpected2.add(Biome.BEACH);
        lexpected2.add(Biome.LAKE);

        Position p1 = new Position (2,2);
        Position p2 = new Position (3,2);
        Position p3 = new Position (4,2);
        Position p4 = new Position (2,3);
        Position p5 = new Position (2,4);

        m.setCase(p1,new IslandCell(l1));
        m.setCase(p3,new IslandCell(l2));
        m.setCase(p5,new IslandCell(l3));

        assertEquals(lexpected0,m.getCase(p2).getBiomes());
        assertEquals(lexpected0,m.getCase(p4).getBiomes());

        m.complete(true);
        assertEquals(lexpected1,m.getCase(p2).getBiomes());
        assertEquals(lexpected0,m.getCase(p4).getBiomes());

        m.complete(false);
        //assertEquals(lexpected1,m.getCase(p2).getBiomes());
        //assertEquals(lexpected2,m.getCase(p4).getBiomes());
    }

    @Test
    public void testFusion(){
        List<Biome> l1=new ArrayList<>();
        List<Biome> l2=new LinkedList<>();
        l1.add(Biome.ALPINE);
        l1.add(Biome.GLACIER);
        l2.add(Biome.GLACIER);
        l2.add(Biome.GRASSLAND);
        List<Biome> lexpected=new ArrayList<>();
        lexpected.add(Biome.ALPINE);
        lexpected.add(Biome.GLACIER);
        lexpected.add(Biome.GRASSLAND);

        assertEquals(lexpected, IslandMap.fusion(l1,l2));
    }

    @Test
    public void testFound(){
        biomes=new ArrayList<Biome>();
        Position posInit = superMap.getPos();
        assertEquals(null,superMap.found(Resource.WOOD,posInit));

        biomes.add(Biome.MANGROVE);
        biomes.add(Biome.TROPICAL_RAIN_FOREST);
        biomes.add(Biome.UNKNOWN);
        IslandCell cell=new IslandCell(biomes);
        IslandCell cell2=new IslandCell(biomes);
        IslandCell cell3=new IslandCell(biomes);

        Position pos = new Position(10,10);
        Position pos2 = new Position(9,9);
        Position pos3 = new Position(8,8);
        Position pos4 = new Position(8,7);
        Position pos5 = new Position(10,7);
        Position pos6 = new Position(8,11);
        Position pos7 = new Position(10,11);
        Position pos8 = new Position(7,8);
        Position pos9 = new Position(11,8);
        Position pos10 = new Position(7,10);
        Position pos11 = new Position(11,10);
        Position origin = new Position(0,0);
        superMap.setCase(pos,cell);
        assertEquals(cell,superMap.getCase(pos));
        assertEquals(true,superMap.getCase(pos).containsResource(Resource.WOOD));

        superMap.setPos(pos);
        Position sp = superMap.getPos();
        assertEquals(pos,superMap.found(Resource.WOOD,sp));
        superMap.setPos(origin);
        assertEquals(null,superMap.found(Resource.WOOD,origin));

        superMap.setCase(pos2,new IslandCell(biomes));
        assertEquals(false,superMap.getCase(pos2).isVisited());
        assertEquals(pos2,superMap.found(Resource.WOOD,pos4));

        superMap.setCase(pos2,new IslandCell(biomes));
        assertEquals(false,superMap.getCase(pos2).isVisited());
        assertEquals(pos2,superMap.found(Resource.WOOD,pos5));

        superMap.setCase(pos2,new IslandCell(biomes));
        assertEquals(false,superMap.getCase(pos2).isVisited());
        assertEquals(pos2,superMap.found(Resource.WOOD,pos6));

        superMap.setCase(pos2,new IslandCell(biomes));
        assertEquals(false,superMap.getCase(pos2).isVisited());
        assertEquals(pos2,superMap.found(Resource.WOOD,pos7));

        superMap.setCase(pos2,new IslandCell(biomes));
        assertEquals(false,superMap.getCase(pos2).isVisited());
        assertEquals(pos2,superMap.found(Resource.WOOD,pos8));

        superMap.setCase(pos2,new IslandCell(biomes));
        assertEquals(false,superMap.getCase(pos2).isVisited());
        assertEquals(pos2,superMap.found(Resource.WOOD,pos9));

        superMap.setCase(pos2,new IslandCell(biomes));
        assertEquals(false,superMap.getCase(pos2).isVisited());
        assertEquals(pos2,superMap.found(Resource.WOOD,pos10));

        superMap.setCase(pos2,new IslandCell(biomes));
        assertEquals(false,superMap.getCase(pos2).isVisited());
        assertEquals(pos2,superMap.found(Resource.WOOD,pos11));

        superMap.setCase(pos3,cell3);
        assertEquals(false,superMap.getCase(pos3).isVisited());
        assertEquals(pos3,superMap.found(Resource.WOOD,origin));

    }

    @Test
    public void testFound2() {

        Position posInit = superMap.getPos();
        assertEquals(null, superMap.found(Resource.WOOD));

        biomes = new ArrayList<Biome>();
        biomes.add(Biome.MANGROVE);
        biomes.add(Biome.TROPICAL_RAIN_FOREST);
        biomes.add(Biome.UNKNOWN);
        IslandCell cell1=new IslandCell(biomes);
        IslandCell cell2=new IslandCell(biomes);
        IslandCell cell3=new IslandCell(biomes);
        Position pos1= new Position(7,3);
        Position pos2= new Position(7,2);
        Position pos3= new Position(8,3);

        List<Biome> biomes2 = new ArrayList<Biome>();
        biomes2.add(Biome.TEMPERATE_RAIN_FOREST);
        biomes2.add(Biome.TEMPERATE_DECIDUOUS_FOREST);
        biomes2.add(Biome.UNKNOWN);
        IslandCell cell4=new IslandCell(biomes2);
        IslandCell cell5=new IslandCell(biomes2);
        IslandCell cell6=new IslandCell(biomes2);
        Position pos4= new Position(5,8);
        Position pos5= new Position(5,9);
        Position pos6= new Position(6,9);

        superMap.setCase(pos1,cell1);
        superMap.setCase(pos2,cell2);
        superMap.setCase(pos3,cell3);
        superMap.setCase(pos4,cell4);
        superMap.setCase(pos5,cell5);
        superMap.setCase(pos6,cell6);

        Position posJ= new Position(3,4);

        assertEquals(pos1,superMap.found(Resource.WOOD,posJ));;

        assertEquals(pos2,superMap.found(Resource.WOOD,posJ));
        assertEquals(pos3,superMap.found(Resource.WOOD,posJ));
        assertEquals(pos4,superMap.found(Resource.WOOD,posJ));

        assertEquals(pos5,superMap.found(Resource.WOOD,posJ));
        assertEquals(pos6,superMap.found(Resource.WOOD,posJ));
        assertEquals(null,superMap.found(Resource.WOOD,posJ));

        /* Scope testing attribute objBiome */
        assertEquals(Biome.TEMPERATE_RAIN_FOREST, superMap.getObjBiome());
    }

    @Test
    public void testMatrixToString(){
        IslandMap shortMap = new IslandMap(4, 4);
        String s="The map is sized: 4 x 4. Its representation is : \n\n\n" +
                "| IslandCell{biomes={UNKNOWN;}} | IslandCell{biomes={UNKNOWN;}} | IslandCell{biomes={UNKNOWN;}} | IslandCell{biomes={UNKNOWN;}} |\n" +
                "| IslandCell{biomes={UNKNOWN;}} | IslandCell{biomes={UNKNOWN;}} | IslandCell{biomes={UNKNOWN;}} | IslandCell{biomes={UNKNOWN;}} |\n" +
                "| IslandCell{biomes={UNKNOWN;}} | IslandCell{biomes={UNKNOWN;}} | IslandCell{biomes={UNKNOWN;}} | IslandCell{biomes={UNKNOWN;}} |\n" +
                "| IslandCell{biomes={UNKNOWN;}} | IslandCell{biomes={UNKNOWN;}} | IslandCell{biomes={UNKNOWN;}} | IslandCell{biomes={UNKNOWN;}} |";
        assertEquals(s,shortMap.MatrixToString(shortMap.getSpaces()));
        assertEquals(s,shortMap.toString());

        IslandMap shortMap2 = new IslandMap(2, 2);

        //Set alpine on (0, 0)
        shortMap2.setPos(new Position(0, 0));
        List<Biome> biomes = new ArrayList<>();
        biomes.add(Biome.ALPINE);
        shortMap2.setCase(new IslandCell(biomes));

        //Set tundra and ocean on (1, 0)
        shortMap2.setPos(new Position(1, 0));
        List<Biome> biomes2 = new ArrayList<>();
        biomes2.add(Biome.TUNDRA);
        biomes2.add(Biome.OCEAN);
        shortMap2.setCase(new IslandCell(biomes2));

        String s2="The map is sized: 2 x 2. Its representation is : \n\n\n" +
                "| IslandCell{biomes={ALPINE;}} | IslandCell{biomes={TUNDRA;OCEAN;}} |\n" +
                "| IslandCell{biomes={UNKNOWN;}} | IslandCell{biomes={UNKNOWN;}} |";
        assertEquals(s2, shortMap2.toString());
    }


    @Test
    public void testConvertToGroundMap() throws Exception {
        assertTrue(miniMap.getWidth() == 3 && miniMap.getHeight() == 3);
        assertTrue(miniMap.getCase(new Position(1, 0)).containsResource(Resource.WOOD));
        assertFalse(miniMap.getCase(new Position(1, 0)).containsResource(Resource.GLASS));
        assertFalse(miniMap.getCase(new Position(2, 2)).containsResource(Resource.WOOD));
        assertTrue(miniMap.getPos().equals(new Position(2, 1)));

        miniMap.convertToGroundMap();

        assertTrue(miniMap.getWidth() == 9 && miniMap.getHeight() == 9);
        assertTrue(miniMap.getPos().equals(new Position(7, 4)));
        assertTrue(miniMap.getCase(new Position(2, 2)).getBiome(0).equals(Biome.OCEAN));
        assertFalse(miniMap.getCase(new Position(2, 2)).getBiome(0).equals(Biome.TROPICAL_RAIN_FOREST));
        assertTrue(miniMap.getCase(new Position(5, 2)).getBiome(1).equals(Biome.TROPICAL_RAIN_FOREST));
    }

    @Test
    public void testFind() throws Exception {
        IslandMap map = new IslandMap(10, 10);
        map.setPos(new Position(3, 7));
        map.setCase(4, 6, new IslandCell(Biome.TEMPERATE_DECIDUOUS_FOREST));

        assertTrue(map.getCase(4, 6).containsResource(Resource.WOOD));
        assertFalse(map.getCase(4, 6).containsResource(Resource.GLASS));
        assertTrue(map.find(Resource.WOOD).equals(new Position(4, 6)));
        assertTrue(map.find(Resource.FISH) == null);

    }

    @Test
    public void testUnvisit(){
        IslandMap map = new IslandMap(10, 10);
        assertFalse(map.getCase(0,0).isVisited());
        assertFalse(map.getCase(1,1).isVisited());
        assertFalse(map.getCase(2,2).isVisited());
        map.getCase(0,0).visit();
        assertTrue(map.getCase(0,0).isVisited());
        map.getCase(1,1).visit();
        assertTrue(map.getCase(1,1).isVisited());
        map.unvisit();
        assertFalse(map.getCase(0,0).isVisited());
        assertFalse(map.getCase(1,1).isVisited());
        assertFalse(map.getCase(2,2).isVisited());

    }
}