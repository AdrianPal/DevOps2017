package fr.unice.polytech.qgl.qcd.progress;

import fr.unice.polytech.qgl.qcd.database.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mathias Chevalier
 * @school Polytech' Nice
 * @date 07/12/2015
 */
public class Inventory {
    private Map<Resource, Integer> inventory;

    /**
     * Construit un inventairede toute les ressources vide.
     */
    public Inventory() {
        Map tempHash = new HashMap<Resource, Integer>();
        Resource[] eNum = Resource.values();
        //on commence à un pour ne pas instancier une case de l'inventaire pour une ressource UNKNOWN
        for (int i=1; i<Resource.values().length; i++){
            tempHash.put(eNum[i], 0);
        }
        this.inventory = tempHash;

    }

    /**
     * Retourne le dictionnaire de l'inventaire.
     * @return inventory
     */
    public Map<Resource, Integer> getInventory() {
        return inventory;
    }

    /**
     * Retourne le nombre d'unité de la ressource donnée en paramètre.
     * @param res
     * @return
     */
    public int get(Resource res){
        return inventory.get(res);
    }

    /**
     * Ajoute le montant à la ressource associé.
     * @param amount
     * @param res
     */
    public void add(int amount, Resource res){
        int temp = get(res);
        inventory.put(res, temp+amount);
    }

    /*
    Inutile ici de gérer les ressources inférieures à 0
    puisque l'on transformera (et donc on utilisera remove) uniquement
    si c'est possible de le faire, chose que l'on testera
    avec get avant de Transform
     */

    /**
     * Retire le montant à la ressource associé.
     * @param amount
     * @param res
     */
    public void remove(int amount, Resource res){
        int temp = get(res);
        inventory.put(res, temp-amount);
    }

    /**
     * Méthode équals.
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Inventory inventory1 = (Inventory) o;

        return inventory != null ? inventory.equals(inventory1.inventory) : inventory1.inventory == null;

    }

    /**
     * Méthode hashCode.
     * @return
     */
    @Override
    public int hashCode() {
        return inventory != null ? inventory.hashCode() : 0;
    }
}
