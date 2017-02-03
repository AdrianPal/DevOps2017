package fr.unice.polytech.qgl.qcd.brain.groundBrain;

import fr.unice.polytech.qgl.qcd.action.ActionExplorer;
import fr.unice.polytech.qgl.qcd.action.Stop;
import fr.unice.polytech.qgl.qcd.brain.groundBrain.groundPhase.GroundPhase;
import fr.unice.polytech.qgl.qcd.brain.groundBrain.groundPhase.ReachBiome;
import fr.unice.polytech.qgl.qcd.database.Action;
import fr.unice.polytech.qgl.qcd.map.IslandMap;
import fr.unice.polytech.qgl.qcd.progress.Progress;

/**
 * Created by Monierv on 14/01/2016.
 */

public class GroundBrain {

    //TODO: add the Phase Enum here and make it move
    private IslandMap islandMap;
    private Progress progress;
    private GroundPhase phase;
    private static int MARGIN_BORDER = 5;
    //TODO:progress

    /**
     * Constructeur du cerveau terrestre à partir de la carte et du progrès (contrats en cours etc)
     * @param islandMap
     * @param progress
     */
    public GroundBrain(IslandMap islandMap, Progress progress) {
        this.progress = progress;
        islandMap.convertToGroundMap();
        this.islandMap = islandMap;
        //TODO: change wood by /* something */
        //this.phase = new ReachBiome(this.islandMap, Resource.WOOD /*progress.getResourceToExploit()*/);
        this.phase = new ReachBiome(this.islandMap,this.progress);//new CreateSwagYoloMdr(this.islandMap, Resource.WOOD);
    }

    /**
     * Renvoit l'action suivante à partir de l'action actuelle
     * @param currentAction
     * @return
     */
    public ActionExplorer nextAction(ActionExplorer currentAction) {
        this.refresh(currentAction);

        if (!safePos()) return new Stop();

        return phase.nextAction(currentAction);
    }

    /**
     * Actualise toutes les informations, contrats, map, etc..
     */
    public void refresh(ActionExplorer currentAction) {
        refreshPosition(currentAction);
        refreshMap(currentAction);
        refreshContract(currentAction);

        if (phase.isPhaseDone())
            phase = phase.nextPhase();
    }

    /**
     * Actualise la progression du contrat
     * @param currentAction
     */
    private void refreshContract(ActionExplorer currentAction) {
        if (currentAction.getAction().equals(Action.exploit)) {
            this.progress.add(currentAction.getAmount());
        }
        if (!progress.getResourceToExploit().equals(phase.getResourceWanted())) {
            islandMap.unvisit();
            phase.phaseDone();
        }
    }

    /**
     * Actualise la map : indique si la case a été exploitée
     * @param currentAction
     */
    private void refreshMap(ActionExplorer currentAction) {
        if (!currentAction.getAction().equals(Action.exploit)) return;
        islandMap.getCase().visit();
    }

    /**
     * Vérifie que l'on est pas sur une bordure de la carte
     * @return
     */
    private boolean safePos() {
        int x = islandMap.getPos().getX();
        int y = islandMap.getPos().getY();
        return x > MARGIN_BORDER && x < islandMap.getWidth() - MARGIN_BORDER && y > MARGIN_BORDER && y < islandMap.getHeight() - MARGIN_BORDER;
    }

    /**
     * Actualise la position à partir d'une action
     * @param action
     */
    private void refreshPosition(ActionExplorer action) {
        if (!action.getAction().equals(Action.move_to)) {
            return;
        } else {
            islandMap.refreshPos(action.getDirection());
        }
    }

}
