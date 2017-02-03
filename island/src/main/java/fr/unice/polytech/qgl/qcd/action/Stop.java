package fr.unice.polytech.qgl.qcd.action;

import fr.unice.polytech.qgl.qcd.database.Action;
import org.json.JSONException;

/**
 * @author Mathias Chevalier
 * @school Polytech' Nice
 * @date 11/11/2015
 */
public class Stop extends ActionExplorer {

    public Stop() throws JSONException{
        super(Action.stop);
        this.createJsonMessage();
        this.createStringMessage();
    }
}
