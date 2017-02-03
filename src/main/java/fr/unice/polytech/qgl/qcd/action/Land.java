package fr.unice.polytech.qgl.qcd.action;

import fr.unice.polytech.qgl.qcd.database.Action;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Mathias Chevalier
 * @school Polytech' Nice
 * @date 11/11/2015
 */
public class Land extends ActionExplorer {
    protected int people;
    protected String creek;

    public Land(String creekId, int people) throws JSONException{
        super(Action.land);
        JSONObject temp = new JSONObject();
        temp.put("creek", creekId);
        temp.put("people", people);
        this.creek = creekId;
        this.people = people;
        this.parameters = temp;
        this.createJsonMessage();
        this.createStringMessage();
    }

    public int getPeople() {
        return people;
    }

    @Override
    public String getCreek() {
        return creek;
    }

    @Override
    public int hashCode() {
        int result = people;
        result = 31 * result + (creek != null ? creek.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Land land = (Land) o;

        if (people != land.people) return false;
        return creek != null ? creek.equals(land.creek) : land.creek == null;

    }
}
