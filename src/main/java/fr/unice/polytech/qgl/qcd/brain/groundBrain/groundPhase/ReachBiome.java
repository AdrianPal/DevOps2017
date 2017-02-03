package fr.unice.polytech.qgl.qcd.brain.groundBrain.groundPhase;


import fr.unice.polytech.qgl.qcd.action.ActionExplorer;
import fr.unice.polytech.qgl.qcd.action.Glimpse;
import fr.unice.polytech.qgl.qcd.action.Stop;
import fr.unice.polytech.qgl.qcd.database.Action;
import fr.unice.polytech.qgl.qcd.database.Direction;
import fr.unice.polytech.qgl.qcd.database.Position;
import fr.unice.polytech.qgl.qcd.map.IslandMap;
import fr.unice.polytech.qgl.qcd.progress.Progress;

/**
 * Created by Monierv on 27/02/2016.
 */

public class ReachBiome extends GroundPhase {

    private Direction direction = Direction.NORTH;
    private boolean biomeAround = false;
    private boolean endPhase = false;
    private boolean toRight = true;

    public ReachBiome(IslandMap islandMap, Progress progress) {
        this.islandMap = islandMap;
        this.progress = progress;
        this.resourceWanted = progress.getResourceToExploit();
        if(resourceWanted.isManufactured()){
            transform(resourceWanted,progress.getCurrentContractAmount());
            progress.add(progress.getCurrentContractAmount());
        }
        else {
            Position p = islandMap.found(resourceWanted);
            if(islandMap.getPos().getX()-p.getX()>0)
                toRight=true;
            else
                toRight=false;
            moveTo(p);
        }
        this.currentBiome = islandMap.getObjBiome();
    }

    @Override
    public ActionExplorer nextAction(ActionExplorer currentAction) {

        //TODO: edit, null -> unknown
        if (resourceWanted == null) return new Stop();

        if (actions.size() == 1 && endPhase) {
            phaseDone();
        }

        if (!actions.isEmpty())
            return actions.poll();

        if (currentAction.getAction().equals(Action.glimpse)) {
            int distance = Glimpse.contains(currentAction.getBiomeListList(), currentBiome);
            if (distance >= 0) {
                moveTo(direction, distance + 1);
                biomeAround = true;
                endPhase = true;
                if (actions.size() > 1) {
                    return actions.poll();
                }
            } else {
                direction = Direction.toRight(direction);
                //éviter boucle infinie
                if (direction.equals(Direction.NORTH)) {
                    phaseDone();
                }
                return new Glimpse(direction, 4);
            }
        }

        return new Glimpse(direction, 4);
    }

    @Override
    public GroundPhase nextPhase() {
        if (biomeAround)
            return new HarvestPhase1(islandMap, progress, currentBiome, direction,toRight);
        else
            return new ReachBiome(islandMap, progress);
    }

}
