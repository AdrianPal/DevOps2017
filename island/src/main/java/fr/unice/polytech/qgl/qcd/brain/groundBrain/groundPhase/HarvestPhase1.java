package fr.unice.polytech.qgl.qcd.brain.groundBrain.groundPhase;

import fr.unice.polytech.qgl.qcd.action.*;
import fr.unice.polytech.qgl.qcd.database.Action;
import fr.unice.polytech.qgl.qcd.database.Biome;
import fr.unice.polytech.qgl.qcd.database.Direction;
import fr.unice.polytech.qgl.qcd.map.IslandMap;
import fr.unice.polytech.qgl.qcd.progress.Progress;

/**
 * Created by Antoine on 06/03/2016.
 */
public class HarvestPhase1 extends GroundPhase {

    private Direction currentDirection;
    private boolean toRight=true;


    public HarvestPhase1(IslandMap islandMap, Progress progress, Biome biome, Direction currentDirection, boolean toRight) {
        this.resourceWanted = progress.getResourceToExploit();
        this.islandMap = islandMap;
        this.currentBiome=biome;
        this.progress=progress;
        this.currentDirection=currentDirection;
        this.toRight=toRight;

        refillQueue(currentDirection);
    }

    public void refillQueue(Direction dir){
        actions.add(new Scout(dir));
        actions.add(new MoveTo(dir));
        actions.add(new Exploit(resourceWanted));
    }

    public ActionExplorer nextAction (ActionExplorer currentAction){
        if (currentAction.getAction().equals(Action.scout)) {
            if (currentAction.contains(resourceWanted)) {
                islandMap.getCase().visit();
                return actions.poll();
            } else {
                phaseDone();
                return new Glimpse(currentDirection,4);
            }
        } else {

            if (!isPhaseDone()) {
                if (!actions.isEmpty()) {
                    return actions.poll();
                } else {
                    refillQueue(currentDirection);
                    return actions.poll();
                }
            }
        }
    return new Scout(Direction.EAST);
    }

    @Override
    public GroundPhase nextPhase() {
        return new HarvestPhase2(islandMap, progress, currentBiome, currentDirection,toRight);
    }




}
