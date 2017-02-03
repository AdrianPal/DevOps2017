package fr.unice.polytech.qgl.qcd.action;

import fr.unice.polytech.qgl.qcd.database.Action;
import fr.unice.polytech.qgl.qcd.database.Resource;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mathias Chevalier
 * @school Polytech' Nice
 * @date 15/11/2015
 */
public class Explore extends ActionExplorer{
    protected List<Resource> resourcesList;
    protected List<String> pois;

    public Explore() throws JSONException{
        super(Action.explore);
        this.createJsonMessage();
        this.createStringMessage();
    }

    @Override
    public void analyzeExtras(){
        if (this.extras != null){
            List<Resource> tempListRes = new ArrayList<Resource>();
            List<String> tempListCreek = new ArrayList<String>();
            JSONArray tempArray = this.extras.getJSONArray("resources");
            JSONObject tempField;
            for (int i=0; i < tempArray.length(); i++){
                tempField = tempArray.getJSONObject(i);
                tempListRes.add(Resource.valueOf(tempField.getString("resource")));
            }
            tempArray = this.extras.getJSONArray("pois");
            for (int j=0; j < tempArray.length(); j++){
                tempField = tempArray.getJSONObject(j);
                if (tempField.optString("kind").equals("Creek")){
                    tempListCreek.add(tempField.getString("id"));
                }
            }
            resourcesList = tempListRes;
            pois = tempListCreek;
        }
    }

    @Override
    public boolean contains(Resource r) {
        return resourcesList.contains(r);
    }

    @Override
    public List<Resource> getResourcesList() {
        return resourcesList;
    }
    @Override
    public List<String> getPois() {
        return pois;
    }

    @Override
    public int hashCode() {
        int result = resourcesList != null ? resourcesList.hashCode() : 0;
        result = 31 * result + (pois != null ? pois.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Explore explore = (Explore) o;

        if (resourcesList != null ? !resourcesList.equals(explore.resourcesList) : explore.resourcesList != null)
            return false;
        return pois != null ? pois.equals(explore.pois) : explore.pois == null;

    }

}
