package fr.unice.polytech.qgl.qcd.progress;

import fr.unice.polytech.qgl.qcd.database.Craft;
import fr.unice.polytech.qgl.qcd.database.Resource;

/**
 * @author Mathias Chevalier
 * @school Polytech' Nice
 * @date 20/02/2016
 */
public class Factory {

    private static final double SECURE_COEFF = 1.10;


    /**
     * Retourne une recette de craft correspondant à la ressource demandé.
     * Si la ressource n'est pas manufactué, retourne null.
     * @param res
     * @return
     */
    public static Craft getNeeds(Resource res){
        switch (res){
            case GLASS:
                return new Craft(Resource.QUARTZ, 10.0*SECURE_COEFF, Resource.WOOD, 5.0*SECURE_COEFF);
            case INGOT:
                return new Craft(Resource.ORE, 5.0*SECURE_COEFF, Resource.WOOD, 5.0*SECURE_COEFF);
            case LEATHER:
                return new Craft(Resource.FUR, 3.0*SECURE_COEFF);
            case PLANK:
                return new Craft(Resource.WOOD, 0.25*SECURE_COEFF);
            case RUM:
                return new Craft(Resource.SUGAR_CANE, 10.0*SECURE_COEFF, Resource.FRUITS, 1.0*SECURE_COEFF);
            default:
                return null;
        }
    }
}
