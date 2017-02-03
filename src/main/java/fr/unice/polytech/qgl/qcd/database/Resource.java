package fr.unice.polytech.qgl.qcd.database;

/**
 * @author : Monzein Thomas
 * @version : 1.0.0
 * @changes : V1.0.0    - Création de l'énumération
 *                      - Représente les resources primaires
 *
 */
public enum Resource {
    UNKNOWN("UNKNOWN", false),
    FISH("FISH", false),
    FLOWER("FLOWER", false),
    FRUITS("FRUITS", false),
    FUR("FUR", false),
    ORE("ORE", false),
    QUARTZ("QUARTZ", false),
    SUGAR_CANE("SUGAR_CANE", false),
    WOOD("WOOD", false),
    GLASS("GLASS", true),
    INGOT("INGOT", true),
    LEATHER("LEATHER", true),
    PLANK("PLANK", true),
    RUM("RUM", true);

    private String resourceName;
    private boolean manufactured;

    /**
     * Constructeur de biome
     * @param r
     * @param  t
     */
     Resource(String r, boolean t){
         resourceName=r;
         manufactured = t;
    }

    /**
     * Accesseur du biome
     * @return biome
     */
    public String getResourceName(){
        return resourceName;
    }

    /**
     * Accesseur de l'état de la ressource, manufacturée ou non
     * @return true if manufactured, false if basic
     */
    public boolean isManufactured() {
        return manufactured;
    }

}
