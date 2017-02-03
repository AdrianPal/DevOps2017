package fr.unice.polytech.qgl.qcd.action;

import fr.unice.polytech.qgl.qcd.database.Action;
import fr.unice.polytech.qgl.qcd.database.Direction;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Mathias Chevalier
 * @school Polytech' Nice
 * @date 11/11/2015
 */
public class MoveTo extends ActionExplorer {
    protected Direction direction;

    public MoveTo(Direction direction) throws JSONException {
        super(Action.move_to);
        JSONObject temp = new JSONObject();
        this.direction = direction;
        temp.put("direction", direction.getStrDirection());
        this.parameters = temp;
        this.createJsonMessage();
        this.createStringMessage();
    }

    @Override
    public Direction getDirection() {return direction;}

    @Override
    public int hashCode() {
        return direction != null ? direction.hashCode() : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        MoveTo moveTo = (MoveTo) o;

        return direction == moveTo.direction;

    }
}
