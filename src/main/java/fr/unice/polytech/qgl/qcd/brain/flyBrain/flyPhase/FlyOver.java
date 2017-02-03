package fr.unice.polytech.qgl.qcd.brain.flyBrain.flyPhase;

import fr.unice.polytech.qgl.qcd.action.ActionExplorer;
import fr.unice.polytech.qgl.qcd.action.Echo;
import fr.unice.polytech.qgl.qcd.action.Fly;
import fr.unice.polytech.qgl.qcd.action.Scan;
import fr.unice.polytech.qgl.qcd.database.Action;
import fr.unice.polytech.qgl.qcd.database.Direction;
import fr.unice.polytech.qgl.qcd.database.Phase;

/**
 * Created by monierv on 01/03/16.
 */
public class FlyOver extends FlyPhase {

    private Direction currentDirection;
    private Direction nextDirection;
    private boolean goBackLeft;
    private boolean creekFound;
    private int canScan = -1;
    private int nbFly ; //le nombre de fly entre 2 scans

    private static int MARGIN = 2;



    public FlyOver(Direction currentDirection, boolean goBackLeft, boolean creekFound) {
        this.phase = Phase.FLY_OVER;
        this.currentDirection = currentDirection;
        this.nextDirection = goBackLeft?Direction.toLeft(currentDirection):Direction.toRight(currentDirection);
        this.goBackLeft = goBackLeft;
        this.creekFound = creekFound;
        nbFly = (creekFound) ? 3 : 0;
    }


    public ActionExplorer nextAction(ActionExplorer currentAction) {
        //Creek found?
        if (currentAction.getAction().equals(Action.scan) && !creekFound) {
            if (currentAction.getCreeks().length != 0) {
                creekFound = true;
                nbFly = 3;
                return new Scan();
            }
        }

        //action prioritaire?
        if (!actions.isEmpty()) return actions.poll();

        //actPrec = echo
        if (currentAction.getAction().equals(Action.echo)) {

            //echo tout droit
            if (currentAction.getDirection().equals(currentDirection)) {
                if (currentAction.isGround()) {
                    if (!creekFound) {
                        this.goFront(currentAction.getRange());
                    }
                    return new Fly();
                } else {
                    return new Echo(nextDirection);
                }
            }

            //echo nextDirection
            if (currentAction.getDirection().equals(nextDirection)) {
                if (currentAction.isGround() && currentAction.getRange() < nbFly + 3) {
                    actions.add(new Echo(nextDirection));
                    return new Fly();
                }
                setPhaseDone();
                return new Scan();
            }
        }

        //actPrec = scan
        if (currentAction.getAction().equals(Action.scan)) {
            if (currentAction.isThereLand()) {
                return new Fly();
            } else {
                return new Echo(currentDirection);
            }
        }

        canScan++;
        if (canScan == nbFly) {
            canScan = -1;
            return new Scan();
        }

        return new Fly();
    }


    public FlyPhase nextPhase() {
        return new GoBack(currentDirection, goBackLeft, creekFound);
    }

    public Direction getNextDirection() {
        return nextDirection;
    }
}
