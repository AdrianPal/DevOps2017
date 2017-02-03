package fr.unice.polytech.qgl.qcd;

import eu.ace_design.island.bot.IExplorerRaid;
import fr.unice.polytech.qgl.qcd.action.ActionExplorer;
import fr.unice.polytech.qgl.qcd.action.Stop;
import fr.unice.polytech.qgl.qcd.brain.Brain;
import fr.unice.polytech.qgl.qcd.database.Context;
import fr.unice.polytech.qgl.qcd.progress.Inventory;
import fr.unice.polytech.qgl.qcd.progress.Progress;
import org.apache.log4j.Logger;
import org.json.JSONException;

/**
 * @author Mathias Chevalier
 * @school Polytech' Nice
 * @date 09/11/2015
 */
public class Explorer implements IExplorerRaid {

    private ActionExplorer action;
    private Brain explorerBrain;
    private Context world;
    private boolean initializeOK;

    public final static Logger log = Logger.getLogger(Explorer.class);

    public Explorer(){}

    public void initialize(String context) throws JSONException {
        log.debug("Initialisation du programme.");
        try {
            this.world = new Context(context);
            this.explorerBrain = new Brain(this.world.getHeading(), this.world.getBudget(), this.world.getMen(), new Progress(new Inventory(), world.getContracts(), this.world.getResourceList()));
            this.initializeOK = true;
        }
        catch (Exception e) {
            this.initializeOK = false;
            action = new Stop();
        }
    }

    public String takeDecision() throws JSONException {
        if (!initializeOK) return new Stop().getMessageString();
        try {
            this.action = explorerBrain.nextAction();
            return action.getMessageString();
        }
        catch (Exception e) {
            return new Stop().getMessageString();
        }
    }

    public void acknowledgeResults(String results) {
        try {
            action.catchResponse(results);
            action.analyze();
        }
        catch (Exception e){
        }
    }


    /*** SCOPE: test ***/
    public boolean isInitializeOK() {
        return initializeOK;
    }
}
