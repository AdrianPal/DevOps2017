package fr.unice.polytech.qgl.qcd.brain.flyBrain.flyPhase;

import fr.unice.polytech.qgl.qcd.action.ActionExplorer;
import fr.unice.polytech.qgl.qcd.action.Scan;
import fr.unice.polytech.qgl.qcd.action.Stop;
import fr.unice.polytech.qgl.qcd.database.Phase;

/**
 * Created by Monierv on 10/12/2015.
 * The default phase, used to avoid problems. As Plato said, it's better to stop than to die.
 */

public class EndPhase extends FlyPhase {

    private boolean creekFound;

    /**
     * Un constructeur tout ce qu'il y a de plus constructeur
     */
    public EndPhase(boolean creekFound) {
        this.phase = Phase.END_FLY_PHASE;
        this.creekFound = creekFound;
    }

    public ActionExplorer nextAction(ActionExplorer currentAction) {
        if (creekFound) return new Scan();
        else return new Stop();
    }
}

