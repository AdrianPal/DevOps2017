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
public class GoBack extends FlyPhase {

    private Direction currentDirection;
    private boolean goBackLeft;
    private boolean creekFound;
    private boolean islandExplored = false;

    public GoBack(Direction currentDirection, boolean goBackLeft, boolean creekFound) {
        this.phase = Phase.GO_BACK;
        this.currentDirection = currentDirection;
        this.goBackLeft = goBackLeft;
        this.creekFound = creekFound;
        this.initializeGoBackActions();
    }

    @Override
    public ActionExplorer nextAction(ActionExplorer currentAction) {
        if (!actions.isEmpty()) return actions.poll();

        //echo?
        if (currentAction.getAction().equals(Action.echo)) {

            //out_of_range
            if (!currentAction.isGround()) {
                islandExplored = true;
                setPhaseDone();
                return new Scan();
            }
            //ground
            else {
                //ground is far
                if (currentAction.getRange() > 4) {
                    goFront(currentAction.getRange() - 1);
                    actions.add(new Echo(currentDirection));
                    return new Fly();
                }
                //ground is near
                setPhaseDone();
                return new Scan();
            }
        }

        //on vient de tourner
        return new Echo(currentDirection);
    }

    public FlyPhase nextPhase() {
        if (islandExplored) {
            return new EndPhase(creekFound);
        } else {
            return new FlyOver(currentDirection, !goBackLeft, creekFound);
        }
    }

    private void initializeGoBackActions() {
        if (goBackLeft) {
            goBackLeft(currentDirection, !creekFound);
        } else {
            goBackRight(currentDirection, !creekFound);
        }
        this.currentDirection = Direction.toBack(currentDirection);
    }
}
