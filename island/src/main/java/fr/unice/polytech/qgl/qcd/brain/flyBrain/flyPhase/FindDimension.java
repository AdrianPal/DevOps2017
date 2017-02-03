package fr.unice.polytech.qgl.qcd.brain.flyBrain.flyPhase;

import fr.unice.polytech.qgl.qcd.action.ActionExplorer;
import fr.unice.polytech.qgl.qcd.action.Echo;
import fr.unice.polytech.qgl.qcd.action.Fly;
import fr.unice.polytech.qgl.qcd.action.Heading;
import fr.unice.polytech.qgl.qcd.database.Action;
import fr.unice.polytech.qgl.qcd.database.Direction;
import fr.unice.polytech.qgl.qcd.database.Phase;

/**
 * Created by monierv on 2/9/16.
 * A class that find the missing dimension of the map in the case we don't know it
 */
public class FindDimension extends FlyPhase {

    private int missingDimension = -1;

    /**
     * Constructeur de la phase à partir de la direction initiale et du côté où a tourné FirstHeading
     * @param beginDirection
     * @param bestSide
     */
    public FindDimension(Direction beginDirection, Direction bestSide) {
        this.phase = Phase.FIND_DIMENSION;
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
        if (currentAction.getAction().equals(Action.heading)) {
            phaseDone = true;
            return new Heading(Direction.toBack(bestSide));
        }
        if (indexAction % 2 == 0) {
            return new Echo(beginDirection);
        } else {
            if (!currentAction.isGround()) {
                missingDimension = 2 + currentAction.getRange();
                return new Heading(beginDirection);
            } else {
                return new Fly();
            }
        }
    }

    /**
     * Renvoit la prochaine phase
     * @return
     */
    @Override
    public FlyPhase nextPhase() {
        return new FindLand(beginDirection, Direction.toBack(bestSide));
    }

    @Override
    public int getMissingDimension() {
        return missingDimension;
    }
}
