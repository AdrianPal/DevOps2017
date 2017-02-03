package fr.unice.polytech.qgl.qcd.action;

import fr.unice.polytech.qgl.qcd.database.Action;
import fr.unice.polytech.qgl.qcd.database.Direction;
import fr.unice.polytech.qgl.qcd.database.Resource;
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
public class Scout extends ActionExplorer {
    protected int altitude;
    protected List<Resource> resources;
    protected Direction direction;

    public Scout(Direction direction) throws JSONException {
        super(Action.scout);
        JSONObject temp = new JSONObject();
        temp.put("direction", direction.getStrDirection());
        this.direction = direction;
        this.parameters = temp;
        this.createJsonMessage();
        this.createStringMessage();
    }

    @Override
    public void analyzeExtras(){
        if (this.extras != null){
            this.altitude = this.extras.getInt("altitude");
            JSONArray temp = this.extras.getJSONArray("resources");
            resources = new ArrayList<Resource>();
            for (int i=0; i < temp.length(); i++){
                resources.add(Resource.valueOf(temp.getString(i)));
            }
        }
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public int getAltitude() {
        return altitude;
    }

    @Override
    public List<Resource> getResourcesList() {
        return resources;
    }

    @Override
    public int hashCode() {
        int result = altitude;
        result = 31 * result + (resources != null ? resources.hashCode() : 0);
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Scout scout = (Scout) o;

        if (altitude != scout.altitude) return false;
        if (resources != null ? !resources.equals(scout.resources) : scout.resources != null) return false;
        return direction == scout.direction;

    }

    @Override
    public boolean contains(Resource r) {
        return resources.contains(r);
    }
}
