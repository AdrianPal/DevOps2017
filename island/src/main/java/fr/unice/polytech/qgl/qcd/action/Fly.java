package fr.unice.polytech.qgl.qcd.action;

import fr.unice.polytech.qgl.qcd.database.Action;
import org.json.JSONException;

/**
 * @author Mathias Chevalier
 * @school Polytech' Nice
 * @date 11/11/2015
 */
public class Fly extends ActionExplorer {

    public Fly() throws JSONException{
        super(Action.fly);
        this.createJsonMessage();
        this.createStringMessage();
    }
}
