package fr.unice.polytech.qgl.qcd.map;

import fr.unice.polytech.qgl.qcd.database.Biome;
import fr.unice.polytech.qgl.qcd.database.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Monzein Thomas
 * @version : 1.0.0
 * @changes : V1.0.0    - Création de la classe
 *                      - Représente la terre et la mer sur une case
 *                      - Représente la liste de biome
 *           V2.0.0     - Ajout d'un attribut visited (modif marquée en TODO)
 */

public class IslandCell {

    private List<Biome> biomes = new ArrayList<>();
    private boolean visited = false;


    /**
     * Constructeur par défaut d'une cellule. La cellule est inconnue.
     */
    public IslandCell(){
        biomes.add(Biome.UNKNOWN);
    }

    /**
     * Constructeur d'une cellule à partir d'une liste de biomes.
     * @param b
     */
    public IslandCell(List<Biome> b){
        biomes = b;
    }

    /**
     * Constructeur d'une cellule à partir d'un biome.
     * @param b
     */
    public IslandCell(Biome b){
        biomes.add(b);
    }

    /**
     * Set la case à 'découverte' (à pattes)
     */
    public void visit(){
        this.visited = true;
    }

    public void unvisit() { this.visited = false; }

    /**
     * Accesseur de la liste des biomes.
     * @return biomes
     */
    public List<Biome> getBiomes(){
        return biomes;
    }

    /**
     * Accesseur du biome i dans la liste de biomes.
     * @param i la position du biome
     * @return biome à la position i
     */
    public Biome getBiome(int i) {
        if(i>=biomes.size() || i<0)
            return null;
        return biomes.get(i);
    }

    /**
     * Modificateur de la liste des biomes.
     * @param b biomes
     */
    public void setBiomes(List<Biome> b){
        biomes = b;
    }

    /**
     * Ajoute le biome à la liste des biomes. La liste est créé si elle n'éxiste pas.
     * @param b biome
     */
    public void addBiome(Biome b){
        if(biomes==null)
            biomes=new ArrayList<Biome>();
        biomes.add(b);
    }

    /**
     * Méthode toString
     * @return s
     */
    @Override
    public String toString() {
        String s="{";
        for(int i=0;i<biomes.size();i++)
            s+=biomes.get(i).getBiome()+";";
        s+="}";
        return "IslandCell{" +
                "biomes=" + s +
                '}';
    }

    /**
     * Retourne true si la ressource en paramètre est disponnible sur cette cellule
     * @param r
     * @return
     */
    public boolean containsResource(Resource r){

        for(int i=0;i<biomes.size();i++){
            if(biomes.get(i).containsResource(r))
                return true;
        }
        return false;
    }

    public boolean isVisited() {
        return visited;
    }
}
