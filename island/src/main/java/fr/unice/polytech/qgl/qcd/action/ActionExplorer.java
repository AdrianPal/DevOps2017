package fr.unice.polytech.qgl.qcd.action;

import fr.unice.polytech.qgl.qcd.database.Action;
import fr.unice.polytech.qgl.qcd.database.Biome;
import fr.unice.polytech.qgl.qcd.database.Direction;
import fr.unice.polytech.qgl.qcd.database.Resource;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mathias Chevalier
 * @school Polytech' Nice
 * @date 10/11/2015
 */

public abstract class ActionExplorer {

    protected Action action;
    protected JSONObject parameters;
    protected JSONObject response;
    protected JSONObject messageJSON;
    protected String messageString;

    protected int cost;
    protected String status;
    protected JSONObject extras;

    public ActionExplorer(Action action) {
        this.action = action;
    }

    public void createJsonMessage() throws JSONException {
        //on prendra en paramètre une action et on ajustera la création du message
        JSONObject message = new JSONObject();
        message.put("action", this.action.getToken());
        if (!(parameters == null)) {
            message.put("parameters", this.parameters);
        }
        this.messageJSON = message;
    }

    public void createStringMessage() throws JSONException {
        if (!(this.messageJSON == null)) {
            this.messageString = this.messageJSON.toString();
        }
    }

    public void analyze(){
        analyzeResponse();
        analyzeExtras();
    }

    protected void analyzeResponse() {
        this.cost = this.response.getInt("cost");
        this.status = this.response.optString("status");
        this.extras = this.response.getJSONObject("extras");
    }
    //some kind of polymorphism
    protected void analyzeExtras() {
    }

    public void catchResponse(String ack) {
        this.response = new JSONObject(ack);
    }

    public JSONObject getMessageJSON() {
        return messageJSON;
    }

    public String getMessageString() {
        return messageString;
    }

    public int getCost() {
        return cost;
    }

//    public String getStatus() {
//        return status;
//    }

    public Action getAction() {
        return action;
    }

    //trying polymorpshim v2 23/12 Mumrau
    //Echo
    public int getRange() {
        return 0;
    }


    public String getFound() {
        return "";
    }

    public boolean isGround() {
        return false;
    }

    //Exploit
    public Resource getRes() {return null;}

    //Scan
    public String[] getCreeks() {
        return null;
    }

    public boolean isThereLand() {
        return false;
    }

    public List getBiomeList() {
        return null;
    }

    //Land
    public String getCreek() {
        return "";
    }

    public int getPeople(){
        return 0;
    }

    //Scout
    public int getAltitude() {
        return 0;
    }

    //Glimpse/Heading/MoveTo
    public Direction getDirection() {
        return null;
    }

    //Exploit
    public int getAmount() {
        return 0;
    }

    //Transform
    public int getProduction() {
        return 0;
    }

    public Resource getKind() {
        return Resource.UNKNOWN;
    }

    //Explore
    public List<Resource> getResourcesList() {
        return null;
    }
    public boolean contains(Resource r) {return false; }
    public List<String> getPois() {
        return null;
    }

    //Glimpse
    public List<ArrayList<Biome>> getBiomeListList(){
        return null;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActionExplorer that = (ActionExplorer) o;

        if (cost != that.cost) return false;
        if (action != that.action) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        return extras != null ? extras.equals(that.extras) : that.extras == null;

    }

}