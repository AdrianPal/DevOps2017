package fr.unice.polytech.qgl.qcd.progress;

import fr.unice.polytech.qgl.qcd.Explorer;
import fr.unice.polytech.qgl.qcd.database.Biome;
import fr.unice.polytech.qgl.qcd.database.Resource;

import java.util.*;

/**
 * Created by thomas on 29/02/2016.
 * @author : Monzein Thomas
 * @version : V1.0.0
 * @changes : V1.0.0    - Création de la classe
 *                      - Ordonner la fréquences des biomes sans coefficient
 *            V2.0.0    - Ordonne les contrats selon les scan de la phase aérienne,
 *                      les coéfficients de récolte des ressources et le montant à récolter.
 *                      - La ressource ORE est placé en dernier car aucune île n'en contient,
 *                      pour l'instant.
 */

public class Calculator {

    private Map<Biome,Integer> biomesFrequences;
    private Map<Resource,Double> resourcesStats = new HashMap<Resource, Double>();
    private Map<Resource,Integer> resourcesFrequences;

    public static final double fishCoeff = 1/1.0;
    public static final double flowerCoeff = 1/0.09;
    public static final double fruitsCoeff = 1/1.0;
    public static final double furCoeff = 1/0.83;//1/0.3;
    public static final double oreCoeff = 1/0.01;//1/0.20;
    public static final double quartzCoeff = 1/0.15;//0.33;
    public static final double sugarCaneCoeff = 1/0.8;
    public static final double woodCoeff = 1/1.9;
    public static final double glassCoeff = 10*quartzCoeff+5*woodCoeff;
    public static final double ingotCoeff = 5*oreCoeff+5*woodCoeff;
    public static final double leatherCoeff = 3*furCoeff;
    public static final double plankCoeff = woodCoeff/4;
    public static final double rumCoeff = fruitsCoeff+10*sugarCaneCoeff;

    /**
     * Construit un calculateur
     */
    public Calculator(){
        biomesFrequences = new HashMap<Biome,Integer>();
        resourcesFrequences = new HashMap<Resource,Integer>();
        completeStats();
    }

    /**
     * Ajoute les biomes au dictionnaire ou incrémente le nombre d'occurence des biomes
     * @param list
     */
    public void scanBiome(List<Biome> list){
        for(final Biome b: list){
            if(!b.equals(Biome.UNKNOWN)){
                // Si le biome est déjà présent
                if(biomesFrequences.containsKey(b)){
                    biomesFrequences.replace(b,biomesFrequences.get(b)+1);
                }
                else{
                    biomesFrequences.put(b,1);
                }
            }
        }
    }

    /**
     * Ajoute les ressources au dictionnaire ou incrémente le nombre d'occurence des ressources selon
     * la liste de biome d'une case.
     * @param list
     */
    public void scanResource(List<Biome> list){
        List<Resource> res_list = new ArrayList<Resource>();
        for(final Biome b: list){
            if(!b.equals(Biome.UNKNOWN)){
                Resource[] tab= b.getResources();
                for(int j=0;j<tab.length;j++){
                    if(!res_list.contains(tab[j]))
                        res_list.add(tab[j]);
                }
            }
        }
        for(final Resource r: res_list){
            if(resourcesFrequences.containsKey(r))
                resourcesFrequences.replace(r,resourcesFrequences.get(r)+1);
            else
                resourcesFrequences.put(r,1);
        }
    }

    /**
     * Méthode remplissant la HashMap des statistiques en fonction
     * des résultats des semaines précédentes
     */
    private void completeStats(){
        resourcesStats.put(Resource.FISH, fishCoeff);
        resourcesStats.put(Resource.FLOWER, flowerCoeff);
        resourcesStats.put(Resource.FRUITS, fruitsCoeff);
        resourcesStats.put(Resource.FUR, furCoeff);
        resourcesStats.put(Resource.ORE, oreCoeff);
        resourcesStats.put(Resource.QUARTZ, quartzCoeff);
        resourcesStats.put(Resource.SUGAR_CANE, sugarCaneCoeff);
        resourcesStats.put(Resource.WOOD, woodCoeff);
        resourcesStats.put(Resource.GLASS, glassCoeff);
        resourcesStats.put(Resource.INGOT, ingotCoeff);
        resourcesStats.put(Resource.LEATHER, leatherCoeff);
        resourcesStats.put(Resource.PLANK, plankCoeff);
        resourcesStats.put(Resource.RUM, rumCoeff);
    }

    /**
     * Accesseur du dictionnaire de la fréquence des biomes.
     * @return biomesFrequences
     */
    public Map<Biome, Integer> getBiomesFrequences() {
        return biomesFrequences;
    }

    /**
     * Accesseur de la fréquence d'un biome. Retourne 0 si le biome n'est pas présent.
     * @param b
     * @return La fréquence du biome
     */
    public int getBiomeFrequence(Biome b){
        if(biomesFrequences.containsKey(b))
            return biomesFrequences.get(b);
        else
            return 0;
    }

    /**
     * Accesseurs du dictionnaire des statistiques des ressources.
     * @return ressourcesStats
     */
    public Map<Resource, Double> getResourcesStats() {
        return resourcesStats;
    }

    /**
     * Méthode retournant le double contenant la statistique pour une ressource donnée en paramètre
     * @param res
     * @return double Stat
     */
    public double getStat(Resource res){
        return this.resourcesStats.get(res);
    }

    /**
     * Accesseur du dictionnaire de fréquence des ressources.
     * @return resourcesFrequences
     */
    public Map<Resource, Integer> getResourcesFrequences(){
        return resourcesFrequences;
    }

    /**
     * Accesseur de la fréquence d'une ressource. Retourne 0 si la ressource n'est pas présente.
     * @param r
     * @return La fréquence de la ressource
     */
    public int getResourceFrequence(Resource r){
        if(resourcesFrequences.containsKey(r))
            return resourcesFrequences.get(r);
        else
            return 0;
    }

    /**
     * Construit un dictionnaire de fréquence de ressources à partir
     * du dictionnaire de fréquence des biomes.
     */
    public void biomesFrequencesToResourcesFrequence(){
        resourcesFrequences=new HashMap<Resource,Integer>();
        Iterator i=biomesFrequences.keySet().iterator();
        while(i.hasNext()){
            Biome b=(Biome)i.next();
            Resource[] tab= b.getResources();
            for(int j=0;j<tab.length;j++){
                if(resourcesFrequences.containsKey(tab[j]))
                    resourcesFrequences.replace(tab[j],resourcesFrequences.get(tab[j])+1*biomesFrequences.get(b));
                else
                    resourcesFrequences.put(tab[j],1*biomesFrequences.get(b));
            }
        }
    }

    /**
     * Evalue l'ordre de réalisation des contrats.
     * @param contracts
     */
    public List<Contract> evalContract(List<Contract> contracts){

        int size=contracts.size();
        Double tab[] = new Double[size];
        List<Contract> result=new ArrayList<Contract>();
        for(int i=0;i<size;i++){
            Contract c = contracts.get(i);
            List<Contract> completeContractList = c.manufacturedToRawContract();
            tab[i]=0.0;
            for(int j=0;j<completeContractList.size();j++) {
                if(!completeContractList.get(j).getResource().isManufactured()) {
                    if(resourcesFrequences.get(completeContractList.get(j).getResource())<=0)
                        tab[i]=0.0;
                    else
                        tab[i] += completeContractList.get(j).getAmount() *
                            resourcesStats.get(completeContractList.get(j).getResource())
                            * 1/resourcesFrequences.get(completeContractList.get(j).getResource());
                }
            }
        }
        for(int i=0;i<size;i++){
            int index = getMinDoubleArray(tab);
            if(index!=-1) {
                Contract contractToAdd = contracts.get(index);
                List<Contract> completeContractList = contractToAdd.manufacturedToRawContract();
                for (int j = 0; j < completeContractList.size(); j++)
                    result.add(completeContractList.get(j));
                tab[index] = -1.0;
            }
        }

        for(int i=0;i<result.size();i++)
            Explorer.log.debug("Contrat n"+i+ " " + result.get(i).getResource() + " " + result.get(i).getAmount());

        return result;
    }

    /**
     * Retourne l'index de la plus petite valeur positive dans le tableau.
     * @param tab
     * @return
     */
    private static int getMinDoubleArray(Double[] tab){

        Double minValue = tab[0];
        int index=0;
        for(int i=1;i<tab.length;i++){
            if((tab[i] < minValue && tab[i] > 0 )||minValue<=0){
                minValue = tab[i];
                index=i;
            }
        }
        if(tab[index]<=0)
            return -1;
        return index;
    }
}
