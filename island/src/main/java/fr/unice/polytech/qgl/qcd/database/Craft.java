package fr.unice.polytech.qgl.qcd.database;

/**
 * UnVerDevInt
 * package fr.unice.polytech.qgl.qcd.database
 * Polytech' Nice - SI3
 * Created: 28/02/16
 *
 * @author Mathias Chevalier
 *
 * Classe gérant les objects de transformation
 */
public class Craft {
    private Resource res1;
    private Resource res2;
    private double amount1;
    private double amount2;
    private boolean duo;
    //TODO:bien penser à checker à chaque fois si duo avant d'invoker getXXX_2

    /**
     * Constructeur d'un craft
     * @param res1
     * @param amount1
     * @param res2
     * @param amount2
     */
    public Craft(Resource res1, double amount1, Resource res2, double amount2) {
        this.res1 = res1;
        this.res2 = res2;
        this.amount1 = amount1;
        this.amount2 = amount2;
        duo = true;
    }

    public Craft(Resource res1, double amount1) {
        this.res1 = res1;
        this.amount1 = amount1;
        this.res2 = null;
        this.amount2 = -1;

    }

    public Resource getRes1() {
        return res1;
    }

    public Resource getRes2() {
        return res2;
    }

    public double getAmount1() {
        return amount1;
    }

    public double getAmount2() {
        return amount2;
    }

    public boolean isDuo() {
        return duo;
    }
}
