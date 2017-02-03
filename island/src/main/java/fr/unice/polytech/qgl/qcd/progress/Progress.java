package fr.unice.polytech.qgl.qcd.progress;

import fr.unice.polytech.qgl.qcd.database.Resource;

import java.util.List;

/**
 * @author Mathias Chevalier
 * @school Polytech' Nice
 * @date 07/12/2015
 */
public class Progress {

    private Inventory inventory;
    //private Map<Resource, Contract> contracts;
    private List<Contract> contracts;
    private Factory factory;
    private List<Resource> resourceList;
    private int currentContract;
    private int currentAmount;

    /**
     * Construit un Progress à partir d'un inventaire, une liste de contrats (non ordonné)
     * et une liste de ressource.
     * @param inventory
     * @param contracts
     * @param resList
     */
    public Progress(Inventory inventory, List<Contract> contracts, List<Resource> resList){
        this.inventory = inventory;
        this.contracts = contracts;
        this.resourceList = resList;
        this.factory = new Factory();
        this.currentContract=0;
        this.currentAmount=0;
    }

    /**
     * Evalue les contrats pour découper et ordonner les contrats.
     * @param calculator
     */
    public void evaluateContracts(Calculator calculator){
        contracts=calculator.evalContract(contracts);
    }

    /**
     * Retourne si le contrat à la position i est remplit.
     * @param i
     * @return
     */
    public boolean isContractSucces(int i){
        if(i<0 || i>=contracts.size())
            return false;
        Contract ctr = contracts.get(i);
        int amount = inventory.get(ctr.getResource());
        if (amount >= ctr.getAmount())
            ctr.successful();
        else
            ctr.fail();
        return ctr.isSuccess();
    }

    /**
     * Retourne l'index du contrat courant.
     * @return
     */
    public int getCurrentContract() {
        return currentContract;
    }

    /**
     * Retourne le nombre de ressource necessaire pour accomplir le
     * contrat courant.
     * @return
     */
    public int getCurrentContractAmount(){
        return contracts.get(currentContract).getAmount();
    }

    /**
     * Retourne le montant de ressource récolté pour le contrat courant.
     * @return currentAmount
     */
    public int getCurrentAmount() {
        return currentAmount;
    }

    /**
     * Permet de mettre à jour le contrat en court suivant le montant de ressource
     * récolté pour le contrat. Si le contrat est terminé, passe au suivant,
     * ajoute les ressource à l'inventaire et passe les ressources actuel à 0.
     */
    public void testSuccess(){
        Contract ctr = contracts.get(currentContract);
        if (currentAmount >= ctr.getAmount()) {
            ctr.successful();
            this.inventory.add(currentAmount, ctr.getResource());
            currentAmount=0;
            currentContract++;
        }
        else
            ctr.fail();
    }

    /**
     * Ajoute le montant de ressource au montant de ressource récolté pour
     * le contrat en court.
     * @param amount
     */
    public void add(int amount){
        this.currentAmount+=amount;
        this.testSuccess();
    }

    //TODO: Plus besoin car on ne remove pas de ressource pour la transformation
    /*public void remove(int amount){
        this.currentAmount-=amount;
        this.testSuccess();
    }*/

    /**
     * Retourne la ressource à éxploiter pour le contrat en court.
     * @return resource
     */
    public Resource getResourceToExploit() {
        testSuccess();
        if(currentContract>=contracts.size())
            return Resource.UNKNOWN;
        return contracts.get(currentContract).getResource();
    }

    /**
     * Accesseurs de l'inventaire
     * @return
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Méthode retournant les contrats (scope test princnipalement)
     * @return map des contrats associés à leur ressource respective
     */
    public List<Contract> getContracts() {
        return contracts;
    }

    /**
     * Modificateur de la liste de contrats.
     * @param contracts
     */
    public void setContracts(List<Contract> contracts) {this.contracts=contracts;}

    /**
     * Accesseur pour la liste des ressources, scope plutôt test
     * @return listes des ressources à exploiter
     */
    public List<Resource> getResourceList() {
        return resourceList;
    }
}
