package fr.unice.polytech.qgl.qcd.database;

import fr.unice.polytech.qgl.qcd.action.ActionExplorer;
import fr.unice.polytech.qgl.qcd.map.IslandMap;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by monierv on 01/03/16.
 */
public interface BrainPhase {

    Phase phase = null;
    IslandMap islandMap = null;
    Queue<ActionExplorer> actions = new LinkedList<>();
    boolean phaseDone = false;

    ActionExplorer nextAction(ActionExplorer currentAction);
    BrainPhase nextPhase();
    Phase getPhase();
}
