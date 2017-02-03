package fr.unice.polytech.qgl.qcd.brain.flyBrain.flyPhase;

import fr.unice.polytech.qgl.qcd.action.*;
import fr.unice.polytech.qgl.qcd.database.Direction;
import fr.unice.polytech.qgl.qcd.database.Phase;
import fr.unice.polytech.qgl.qcd.map.IslandMap;

/**
 * Created by Monierv on 09/12/2015.
 * The first flyPhase, just turn to the best side.
 */
public class FirstHeading extends FlyPhase {

    private int distanceFront = -1;
    private int distanceLeft;
    private int distanceRight;

    /**
     * Constructeur de la phase à l'aide de la direction initiale
     * @param beginDirection
     */
    public FirstHeading(Direction beginDirection) {
        this.phase = Phase.FIRST_HEADING;
        this.beginDirection = beginDirection;
    }

    /**
     * Renvoit la prochaine action à partir de l'action actuelle
     * @param currentAction
     * @return nextAction
     */
    @Override
    public ActionExplorer nextAction(ActionExplorer currentAction) {
        indexAction++;
        switch (indexAction) {
            case 0:
                return new Echo(beginDirection);
            case 1:
                if (currentAction.isGround())
                    distanceFront = -1;
                else
                    distanceFront = currentAction.getRange();
                return new Echo(Direction.toLeft(beginDirection));
            case 2:
                distanceLeft = currentAction.getRange();
                return new Echo(Direction.toRight(beginDirection));
            case 3:
                distanceRight = currentAction.getRange();
                bestSide = (distanceLeft > distanceRight)?Direction.toLeft(beginDirection):Direction.toRight(beginDirection);
                // CREATION DE LA MAP
                islandMap = new IslandMap(distanceLeft, distanceRight, distanceFront, beginDirection);
                takeIslandMap = true;
                return new Heading(bestSide);
            case 4:
                phaseDone = true;
                return new Fly();
            default:
                return new Stop();
        }
    }

    /**
     * Renvoie la phase suivante
     * @return
     */
    @Override
    public FlyPhase nextPhase() {
        if (distanceFront == -1) {
            return new FindDimension(beginDirection, bestSide);
        } else {
            return new FindLand(beginDirection, bestSide);
        }
    }
}
