package fr.unice.polytech.qgl.qcd.action;

import fr.unice.polytech.qgl.qcd.database.Action;
import fr.unice.polytech.qgl.qcd.database.Biome;
import fr.unice.polytech.qgl.qcd.database.Direction;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mathias Chevalier
 * @school Polytech' Nice
 * @date 11/11/2015
 */
public class Glimpse extends ActionExplorer {
    //TODO: Ajout des attributs et choix de la structure de données de récupération pour Glimpse Long Range
    protected int range;
    protected Direction direction;
    protected ArrayList<ArrayList<Biome>> biomeList;

    public Glimpse(Direction direction, int range) throws JSONException {
        super(Action.glimpse);
        JSONObject temp = new JSONObject();
        temp.put("direction", direction.getStrDirection());
        temp.put("range", range);
        this.parameters = temp;
        this.range = range;
        this.direction = direction;
        this.biomeList = new ArrayList<>();
        this.createJsonMessage();
        this.createStringMessage();

    }

    @Override
    public void analyzeExtras(){
        if (this.extras != null){
            this.range = this.extras.getInt("asked_range");

            JSONArray tempReport = this.extras.getJSONArray("report");
            ArrayList<Biome> tempList = new ArrayList<>();
            JSONArray globalBiomeArrayOnCase;
            for (int k = 0; k < 2 && k < range; k++){
                globalBiomeArrayOnCase = tempReport.getJSONArray(k);
                for (int i = 0; i < globalBiomeArrayOnCase.length(); i++){
                    tempList.add(Biome.valueOf(globalBiomeArrayOnCase.getJSONArray(i).getString(0)));
                }
                biomeList.add(tempList);
                tempList = new ArrayList<>();
            }
            if (range > 2) {
                globalBiomeArrayOnCase = tempReport.getJSONArray(2);
                for (int j = 0; j < globalBiomeArrayOnCase.length(); j++) {
                    tempList.add(Biome.valueOf(globalBiomeArrayOnCase.getString(j)));
                }
                biomeList.add(2, tempList);
                tempList = new ArrayList<>();
            }
            if (range > 3) {
                globalBiomeArrayOnCase = tempReport.getJSONArray(3);
                tempList.add(Biome.valueOf(globalBiomeArrayOnCase.getString(0)));
                biomeList.add(3, tempList);
            }



        }
    }

    @Override
    public List<ArrayList<Biome>> getBiomeListList() {
        return biomeList;
    }

    @Override
    public int getRange() {
        return range;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Glimpse glimpse = (Glimpse) o;

        if (range != glimpse.range) return false;
        if (direction != glimpse.direction) return false;
        return biomeList != null ? biomeList.equals(glimpse.biomeList) : glimpse.biomeList == null;

    }

    @Override
    public int hashCode() {
        int result = range;
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        result = 31 * result + (biomeList != null ? biomeList.hashCode() : 0);
        return result;
    }


    /**
     * Regarde si le biome recherché est dans les resultats du glimpse
     * @param biomeListList
     * @param biomeSearched
     * @return true si le biome est dans le résultat du glimpse
     */
    public static int contains(List<ArrayList<Biome>> biomeListList, Biome biomeSearched) {
        for (int i = 0 ; i < biomeListList.size() ; i++) {
            if (biomeListList.get(i).contains(biomeSearched))
                return i;
        }
        return -1;
    }

    /**
     * Renvoie la distance maximale possédant le biome recherché (3 si présent 4 cases plus loin, 2 pour 3, 1 pour 2, 0 pour 1) et le biome recherché, -1 si le biome n'est pas trouvé par le glimpse
     * @param biomeListList
     * @param biomeSearched
     * @return
     */
    public static int containsAt(List<ArrayList<Biome>> biomeListList, Biome biomeSearched) {

        int closerSetOfTile=biomeListList.size()-1;
        for (int i = closerSetOfTile; i>=0 ; i--) {
            if (!biomeListList.get(i).contains(biomeSearched))
                closerSetOfTile=i-1;
        }
        return closerSetOfTile;
    }

    /**
     * Renvoie la distance entre la position actuelle et le biome recherché, -1 si le biome n'est pas trouvé par le glimpse
     * @param biomeListList
     * @param biomeSearched
     * @return distance du biome - 1
     * @return -1 si le biome n'est pas trouvé
     */
    public static int biomeDistance(List<ArrayList<Biome>> biomeListList, Biome biomeSearched) {
        for (int i = 0 ; i < biomeListList.size() ; i++) {
            if (biomeListList.get(i).contains(biomeSearched))
                return i;
        }
        return -1;
    }
}

