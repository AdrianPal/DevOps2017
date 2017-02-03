package fr.unice.polytech.qgl.qcd.brain.groundBrain.groundPhase;

import fr.unice.polytech.qgl.qcd.action.ActionExplorer;
import fr.unice.polytech.qgl.qcd.action.Stop;

/**
 * Created by Monierv on 28/02/2016.
 */
public class StopPhase extends GroundPhase {

    public StopPhase() {
    }

    @Override
    public ActionExplorer nextAction(ActionExplorer currentAction) {
        return new Stop();
    }
}
