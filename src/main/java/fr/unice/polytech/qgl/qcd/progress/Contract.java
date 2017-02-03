package fr.unice.polytech.qgl.qcd.progress;

import fr.unice.polytech.qgl.qcd.database.Craft;
import fr.unice.polytech.qgl.qcd.database.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mathias Chevalier
 * @school Polytech' Nice
 * @date 07/12/2015
 */
public class Contract {
    private boolean isSuccess = false;
    private Resource resource;
    private int amount;

    /**
     * Construit un contrat à partir non remplit à partir d'une ressource et d'un
     * montant.
     * @param amount
     * @param resource
     */
    public Contract(int amount, Resource resource){
        this.amount = amount;
        this.resource = resource;
    }

    /**
     * Accesseur du succès du contrat.
     * @return isSuccess
     */
    public boolean isSuccess() {
        return isSuccess;
    }

    /**
     * Accesseur de la ressource du contrat.
     * @return ressource.
     */
    public Resource getResource() {
        return resource;
    }

    /**
     * Le contrat devient "terminé".
     */
    public void successful() {
        isSuccess = true;
    }

    /**
     * Le contrat devient "pas terminé".
     */
    public void fail() {
        isSuccess = false;
    }

    /**
     * Accesseur du montant de la ressource à exploiter du contrat.
     * @return amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Retourne une liste de contrat des ressource nécessaire à la recette
     * et le contrat de ressource manufacturé.
     * Si la ressource n'est pas manufacturé, retourne une liste de lui seul.
     * @return
     */
    public List<Contract> manufacturedToRawContract(){
        List<Contract> list = new ArrayList<Contract>();
        Resource contractR=getResource();
        int nb=getAmount();

        if(contractR.isManufactured()){
            Craft c = Factory.getNeeds(contractR);
            list.add(new Contract((int)Math.ceil(c.getAmount1()*(nb)),c.getRes1()));
            if(c.getRes2()!=null)
                list.add(new Contract((int)Math.ceil(c.getAmount2()*(nb)),c.getRes2()));
        }
        list.add(this);
        return list;
    }

}
