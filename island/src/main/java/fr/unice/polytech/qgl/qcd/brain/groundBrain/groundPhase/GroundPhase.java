package fr.unice.polytech.qgl.qcd.brain.groundBrain.groundPhase;

import fr.unice.polytech.qgl.qcd.action.ActionExplorer;
import fr.unice.polytech.qgl.qcd.action.MoveTo;
import fr.unice.polytech.qgl.qcd.action.Stop;
import fr.unice.polytech.qgl.qcd.action.Transform;
import fr.unice.polytech.qgl.qcd.database.*;
import fr.unice.polytech.qgl.qcd.map.IslandMap;
import fr.unice.polytech.qgl.qcd.progress.Factory;
import fr.unice.polytech.qgl.qcd.progress.Progress;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Monierv on 12/02/2016.
 */
public abstract class GroundPhase implements BrainPhase {

    protected Phase phase;
    protected IslandMap islandMap;
    protected Progress progress;
    protected Queue<ActionExplorer> actions = new LinkedList<>();
    protected Biome currentBiome;
    protected Resource resourceWanted;
    protected boolean phaseDone = false;

    /**
     * Renvoit l'action suivante à partir de l'action courante
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
    public GroundPhase nextPhase() {
        return new StopPhase();
    }


    /**
     * Permet de se rendre à la position indiquée en paramètre
     * @param pos
     */
    public void moveTo(Position pos) {
        if (pos == null) return;
        int howManyX = pos.getX() - islandMap.getPos().getX();
        int howManyY = pos.getY() - islandMap.getPos().getY();
        Direction directionX = (howManyX >= 0) ? Direction.EAST : Direction.WEST;
        Direction directionY = (howManyY >= 0) ? Direction.SOUTH : Direction.NORTH;
        if (howManyX < 0) howManyX = - howManyX;
        if (howManyY < 0) howManyY = - howManyY;

        for (int i = 0 ; i < howManyX ; i++) {
            actions.add(new MoveTo(directionX));
        }
        for (int i = 0 ; i < howManyY ; i++) {
            actions.add(new MoveTo(directionY));
        }
    }

    /**
     * Permet de se déplacer de X cases dans une certaine direction
     * @param direction
     * @param distance
     */
    public void moveTo(Direction direction, int distance) {
        for (int i = 0 ; i < distance ; i++) {
            actions.add(new MoveTo(direction));
        }
    }

    public void transform(Resource r, int amount){
        Craft c = Factory.getNeeds(r);
        int am1 = (int)Math.ceil(c.getAmount1()*amount);
        int am2 = -1;
        if(c.getRes2()!=null)
            am2 = (int)Math.ceil(c.getAmount2()*amount);
        actions.add(new Transform(c.getRes1(),am1,c.getRes2(),am2));
        //actions.add(new Transform(c.getRes1(),progress.getInventory().get(c.getRes1()),c.getRes2(),progress.getInventory().get(c.getRes2())));
    }

    /*** GETTERS ***/

    public void phaseDone() {
        this.phaseDone = true;
    }

    public boolean isPhaseDone() {
        return phaseDone;
    }

    public IslandMap getIslandMap() {
        return islandMap;
    }

    public Resource getResourceWanted() {
        return resourceWanted;
    }

    public Phase getPhase() {
        return phase;
    }
}