package fr.unice.polytech.qgl.qcd.brain.flyBrain.flyPhase;

import fr.unice.polytech.qgl.qcd.action.ActionExplorer;
import fr.unice.polytech.qgl.qcd.action.Echo;
import fr.unice.polytech.qgl.qcd.action.Fly;
import fr.unice.polytech.qgl.qcd.database.Direction;
import fr.unice.polytech.qgl.qcd.database.Phase;

/**
 * Created by Monierv on 10/12/2015.
 * A class that reach the land we just found
 */
public class ReachLand extends FlyPhase {

    private int distanceFront;

    /**
     * Constructeur de la phase à partir de la direction initiale et du côté où a tourné FirstHeading
     * @param beginDirection
     * @param bestSide
     */
    public ReachLand(Direction beginDirection, Direction bestSide) {
        this.phase = Phase.REACH_LAND;
        this.beginDirection = beginDirection;
        this.bestSide = bestSide;
    }

    /**
     * Renvoit la prochaine action à partir de l'action actuelle
     * @param currentAction
     * @return nextAction
     */
    @Override
    public ActionExplorer nextAction(ActionExplorer currentAction) {
        indexAction++;
        if (indexAction == 0) {
            return new Echo(beginDirection);
        }
        if (indexAction == 1) {
            distanceFront = currentAction.getRange();
        }
        if (indexAction - 1 < distanceFront) {
            return new Fly();
        }
        //terre atteinte
        phaseDone = true;
        return new Fly();
    }

    /**
     * Renvoit la prochaine phase
     * @return
     */
    @Override
    public FlyPhase nextPhase() {
        boolean goBackLeft = (bestSide.equals(Direction.toLeft(beginDirection)));
        return new FlyOver(beginDirection, goBackLeft, false);
    }
}