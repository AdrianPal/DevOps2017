package fr.unice.polytech.qgl.qcd.brain;

import fr.unice.polytech.qgl.qcd.action.ActionExplorer;
import fr.unice.polytech.qgl.qcd.action.Stop;
import fr.unice.polytech.qgl.qcd.brain.flyBrain.FlyBrain;
import fr.unice.polytech.qgl.qcd.brain.groundBrain.GroundBrain;
import fr.unice.polytech.qgl.qcd.database.Direction;
import fr.unice.polytech.qgl.qcd.map.IslandMap;
import fr.unice.polytech.qgl.qcd.progress.Calculator;
import fr.unice.polytech.qgl.qcd.progress.Progress;

/**
 * @author Monierv Patman
 * @school Polytech' Nice
 * @date 14/11/2015
**/

public class Brain {

    private Direction beginDirection;
    private int budget;
    //TODO:gérer le nombre de gens qu'on Land (actuellement 1)
    private final int crew;
    private final int BUDGET_MIN = 300;

    private boolean flyPhase;
    private ActionExplorer currentAction;
    private FlyBrain flyBrain;
    private GroundBrain groundBrain;
    private Progress progress;

    /**
     * Constructeur du cerveau à partir des informations de départ
     * @param beginDirection
     * @param budget
     * @param crew
     * @param progress
     */
    public Brain(Direction beginDirection, int budget, int crew, Progress progress) {
        this.beginDirection = beginDirection;
        this.budget = budget;
        this.crew = crew;
        this.progress = progress;

        this.flyPhase = true;
        this.flyBrain = new FlyBrain(beginDirection);
    }

    /**
     * Renvoit la prochaine action
     * @return
     */
    public ActionExplorer nextAction() {
        //mission possible ?
        if (!isPossible())
        return new Stop();

        //actualize&checkBudget
        if (!actualizeBudget())
            return new Stop();

        //isLand
        if (flyPhase && flyBrain.isLand()) {
            IslandMap islandMap = flyBrain.getIslandMap();
            Calculator calculator = flyBrain.getCalculator();
            progress.evaluateContracts(calculator);
            this.groundBrain = new GroundBrain(islandMap, progress);
            flyPhase = false;
        }

        if (flyPhase) {
            currentAction = flyBrain.nextAction(currentAction);
        } else {
            currentAction = groundBrain.nextAction(currentAction);
        }
        return currentAction;
    }

    /**
     * Actualise le budget restant, on arrête tout en-dessous de 200
     * @return
     */
    protected boolean actualizeBudget() {
        if (currentAction != null)
            budget -= currentAction.getCost();
        return (budget > BUDGET_MIN);
    }

    protected boolean isPossible() {
        return (crew > 1);
    }



    /***
     * SETTERS
     */
    public void setCurrentAction(ActionExplorer currentAction) {
        this.currentAction = currentAction;
    }
}