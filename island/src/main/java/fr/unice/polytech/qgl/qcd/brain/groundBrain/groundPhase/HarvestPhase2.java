package fr.unice.polytech.qgl.qcd.brain.groundBrain.groundPhase;

import fr.unice.polytech.qgl.qcd.action.ActionExplorer;
import fr.unice.polytech.qgl.qcd.action.Glimpse;
import fr.unice.polytech.qgl.qcd.action.MoveTo;
import fr.unice.polytech.qgl.qcd.database.Action;
import fr.unice.polytech.qgl.qcd.database.Biome;
import fr.unice.polytech.qgl.qcd.database.Direction;
import fr.unice.polytech.qgl.qcd.map.IslandMap;
import fr.unice.polytech.qgl.qcd.progress.Progress;

/**
 * Created by Antoine on 06/03/2016.
 */
public class HarvestPhase2 extends GroundPhase {

    private Direction currentDirection;
    private Direction nextDirection;
    private boolean goPhase1=false;
    private boolean isBorder =false;
    private boolean toRight = true;

    public HarvestPhase2(IslandMap islandMap, Progress progress, Biome biome, Direction currentDirection, boolean toRight) {
        this.resourceWanted = progress.getResourceToExploit();
        this.islandMap = islandMap;
        this.currentBiome = biome;
        this.progress =progress;
        this.currentDirection = currentDirection;
        this.toRight=toRight;
        if(toRight)
            this.nextDirection = Direction.EAST;
        else
            this.nextDirection = Direction.WEST;
    }

    public ActionExplorer nextAction(ActionExplorer currentAction){
        if(islandMap.getCase().isVisited()) {
            this.nextDirection = Direction.toBack(nextDirection);
        }


        if (currentAction.getAction().equals(Action.glimpse)){
            islandMap.getCase().visit();

            if(Glimpse.containsAt(currentAction.getBiomeListList(), currentBiome)!=-1) {
                phaseDone();
                goPhase1=true;
                return new MoveTo(nextDirection);
            }
            else{
                phaseDone();
                isBorder=true;
                currentDirection=Direction.toBack(currentDirection);
                return new MoveTo(nextDirection);
            }
        }
        return new Glimpse(currentDirection,4);
    }


    @Override
    public GroundPhase nextPhase() {
        currentDirection= Direction.toBack(currentDirection);

        if(goPhase1==true){
            return new HarvestPhase1(islandMap, progress, currentBiome, currentDirection,toRight);
        }
        else {

            if (isBorder) {
                return new ReachBiome(islandMap, progress);
            } else{
                return new HarvestPhase1(islandMap, progress, currentBiome, currentDirection,toRight);
            }
        }
    }

    public boolean isGoPhase1() {
        return goPhase1;
    }

    public boolean isBorder() {
        return isBorder;
    }
}
