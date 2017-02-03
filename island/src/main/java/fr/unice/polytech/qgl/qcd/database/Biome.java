package fr.unice.polytech.qgl.qcd.database;

/**
 * @author : Monzein Thomas
 * @version : 1.0.0
 * @changes : V1.0.0    - Création de l'énumération
 *                      - Représente les biomes
 *                      - Garde en mémoire les resources disponnibles sur un biome
 *
 */
public enum Biome {

    //TODO: vérifier toussa avec des runs (very important and very long aussi :))
    UNKNOWN("UNKNOWN", new Resource[]{Resource.UNKNOWN}),
    OCEAN("OCEAN", new Resource[]{Resource.FISH}),
    LAKE("LAKE", new Resource[]{Resource.FISH}),
    BEACH("BEACH", new Resource[]{Resource.QUARTZ}),
    GRASSLAND("GRASSLAND", new Resource[]{Resource.FUR}),
    MANGROVE("MANGROVE", new Resource[]{Resource.WOOD, Resource.FLOWER}),
    TROPICAL_RAIN_FOREST("TROPICAL_RAIN_FOREST", new Resource[]{Resource.FRUITS, Resource.SUGAR_CANE, Resource.WOOD}),
    TROPICAL_SEASONAL_FOREST("TROPICAL_SEASONAL_FOREST", new Resource[]{Resource.FRUITS, Resource.SUGAR_CANE, Resource.WOOD}),
    TEMPERATE_DECIDUOUS_FOREST("TEMPERATE_DECIDUOUS_FOREST", new Resource[]{Resource.WOOD}),
    TEMPERATE_RAIN_FOREST("TEMPERATE_RAIN_FOREST",new Resource[]{Resource.FUR, Resource.WOOD}),
    TEMPERATE_DESERT("TEMPERATE_DESERT",new Resource[]{Resource.ORE, Resource.QUARTZ}),
    TAIGA("TAIGA",new Resource[]{Resource.UNKNOWN}),
    SNOW("SNOW",new Resource[]{Resource.UNKNOWN}),
    TUNDRA("TUNDRA",new Resource[]{Resource.QUARTZ, Resource.ORE}),
    ALPINE("ALPINE",new Resource[]{Resource.FLOWER, Resource.ORE}),
    GLACIER("GLACIER",new Resource[]{Resource.FLOWER}),
    SHRUBLAND("SHRUBLAND",new Resource[]{Resource.FUR}),
    SUB_TROPICAL_DESERT("SUB_TROPICAL_DESERT",new Resource[]{Resource.ORE, Resource.QUARTZ});

    String biome;
    Resource[] resources;

    /**
     * Constructeur de Biome
     * @param b
     * @param r
     */
    Biome(String b, Resource[] r){
        biome = b;
        resources = r;
    }

    /**
     * Accesseur de biome
     * @return biome
     */

    public String getBiome(){
        return biome;
    }

    /**
     * Accesseur du tableau de ressource
     * @return resources
     */
    public Resource[] getResources(){
        return resources;
    }

    public String toString(){
        return biome;
    }

    /**
     * Retourne true si le biome contient la ressource en paramètre
     * @param r
     * @return
     */
    public boolean containsResource(Resource r){
        for(int i=0;i<resources.length;i++)
            if(resources[i].equals(r))
                return true;
        return false;
    }
}