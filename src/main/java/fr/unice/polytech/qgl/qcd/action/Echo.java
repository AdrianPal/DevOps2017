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
public class Echo extends ActionExplorer {
    protected int range;
    protected String found;
    protected boolean ground;
    protected Direction direction;

    public Echo(Direction direction)throws JSONException {
        super(Action.echo);
        this.direction = direction;
        JSONObject temp = new JSONObject();
        temp.put("direction", direction.getStrDirection());
        this.parameters = temp;
        this.createJsonMessage();
        this.createStringMessage();
    }

    @Override
    public void analyzeExtras(){
        if (this.extras != null ){
            this.range = this.extras.getInt("range");
            this.found = this.extras.getString("found");
            this.ground = (found.equals("GROUND"));
        }
    }


    @Override
    public int getRange() {
        return range;
    }

    @Override
    public String getFound() {
        return found;
    }

    @Override
    public boolean isGround() {
        return ground;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public int hashCode() {
        int result = range;
        result = 31 * result + (found != null ? found.hashCode() : 0);
        result = 31 * result + (ground ? 1 : 0);
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Echo echo = (Echo) o;

        if (range != echo.range) return false;
        if (ground != echo.ground) return false;
        if (found != null ? !found.equals(echo.found) : echo.found != null) return false;
        return direction == echo.direction;

    }

}
