package fr.unice.polytech.qgl.qcd.brain.flyBrain.flyPhase;

import fr.unice.polytech.qgl.qcd.action.ActionExplorer;
import fr.unice.polytech.qgl.qcd.action.Fly;
import fr.unice.polytech.qgl.qcd.action.Heading;
import fr.unice.polytech.qgl.qcd.action.Stop;
import fr.unice.polytech.qgl.qcd.database.BrainPhase;
import fr.unice.polytech.qgl.qcd.database.Direction;
import fr.unice.polytech.qgl.qcd.database.Phase;
import fr.unice.polytech.qgl.qcd.map.IslandMap;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Monierv on 10/12/2015.
 */
public abstract class FlyPhase implements BrainPhase {

    protected Phase phase = null;
    protected IslandMap islandMap;
    protected Queue<ActionExplorer> actions = new LinkedList<>();
    protected Direction beginDirection;
    protected Direction bestSide;
    protected boolean phaseDone = false;
    protected boolean takeIslandMap = false;
    protected int indexAction = -1;

    /**
     * Renvoit l'action suivante à partir de l'action actuelle
     * @param currentAction
     * @return
     */
    public ActionExplorer nextAction(ActionExplorer currentAction) {
        return new Stop();
    }

    /**
     * Renvoit la phase suivante
     * @return
     */
    public FlyPhase nextPhase() {
        return new EndPhase(false);
    }

    /**
     * Permet de faire demi-tour par la gauche
     * @param direction
     */
    protected void goBackLeft(Direction direction, boolean quickly) {
        actions.add(new Heading(Direction.toLeft(direction)));
        if (!quickly) {
            actions.add(new Fly());
            actions.add(new Fly());
        }
        actions.add(new Heading(Direction.toBack(direction)));
    }

    /**
     * Permet de faire demi-tour par la droite
     * @param direction
     */
    protected void goBackRight(Direction direction, boolean quickly) {
        actions.add(new Heading(Direction.toRight(direction)));
        if (!quickly) {
            actions.add(new Fly());
            actions.add(new Fly());
        }
        actions.add(new Heading(Direction.toBack(direction)));
    }

    /**
     * Permet d'avancer de X cases
     * @param distance
     */
    protected void goFront(int distance) {
        for (int i = 0 ; i < distance ; i++)
            actions.add(new Fly());
    }

    /*** GETTERS ***/

    @Override
    public Phase getPhase() {
        return phase;
    }

    public boolean isPhaseDone() {
        return phaseDone;
    }

    public IslandMap getIslandMap() {
        return islandMap;
    }

    public boolean canTakeIslandMap() {
        return takeIslandMap;
    }

    public int getMissingDimension() {
        return -1;
    }

    public void setPhaseDone() {
        this.phaseDone = true;
    }

    public Direction getNextDirection() {
        return null;
    }
}
