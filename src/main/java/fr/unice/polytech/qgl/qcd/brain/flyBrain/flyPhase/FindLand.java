package fr.unice.polytech.qgl.qcd.brain.flyBrain.flyPhase;

import fr.unice.polytech.qgl.qcd.action.ActionExplorer;
import fr.unice.polytech.qgl.qcd.action.Echo;
import fr.unice.polytech.qgl.qcd.action.Fly;
import fr.unice.polytech.qgl.qcd.action.Heading;
import fr.unice.polytech.qgl.qcd.database.Direction;
import fr.unice.polytech.qgl.qcd.database.Phase;

/**
 * Created by Monierv on 08/12/2015.
 * A class that find the Island
 */
public class FindLand extends FlyPhase {

    /**
     * Constructeur de la phase à partir de la direction initiale et du côté où a tourné FirstHeading
     * @param beginDirection
     * @param bestSide
     */
    public FindLand(Direction beginDirection, Direction bestSide) {
        this.phase = Phase.FIND_LAND;
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
        if (indexAction % 2 == 0) {
            return new Echo(beginDirection);
        } else {
            if (currentAction.isGround()) {
                phaseDone = true;
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
        return new ReachLand(beginDirection, bestSide);
    }
}
