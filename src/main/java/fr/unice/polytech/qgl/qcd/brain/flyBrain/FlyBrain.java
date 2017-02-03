package fr.unice.polytech.qgl.qcd.brain.flyBrain;

import fr.unice.polytech.qgl.qcd.action.ActionExplorer;
import fr.unice.polytech.qgl.qcd.action.Land;
import fr.unice.polytech.qgl.qcd.action.Scan;
import fr.unice.polytech.qgl.qcd.action.Stop;
import fr.unice.polytech.qgl.qcd.brain.flyBrain.flyPhase.FirstHeading;
import fr.unice.polytech.qgl.qcd.brain.flyBrain.flyPhase.FlyPhase;
import fr.unice.polytech.qgl.qcd.database.Action;
import fr.unice.polytech.qgl.qcd.database.Direction;
import fr.unice.polytech.qgl.qcd.database.Phase;
import fr.unice.polytech.qgl.qcd.map.IslandCell;
import fr.unice.polytech.qgl.qcd.map.IslandMap;
import fr.unice.polytech.qgl.qcd.progress.Calculator;

/**
 * Created by Monierv on 08/12/2015.
 */
public class FlyBrain {

    //TODO: make this move
    private Phase phase = Phase.FIRST_HEADING;

    private FlyPhase flyPhase;
    private Direction currentDirection;
    private ActionExplorer currentAction;
    private boolean land;
    private IslandMap islandMap;
    private Calculator calculator;

    private final int MARGIN = 5;
    private final int CREW = 1;


    /**
     * Construit le cerveau aérien avec la direction de départ
     * @param beginDirection
     */
    public FlyBrain(Direction beginDirection) {
        this.flyPhase = new FirstHeading(beginDirection);
        this.currentDirection = beginDirection;
        this.land = false;
        this.calculator = new Calculator();
    }

    /**
     * Retourne l'action suivante à partir de l'action courante
     * @param currentAction
     * @return nextAction
     */
    public ActionExplorer nextAction(ActionExplorer currentAction) {
        if (islandMap != null) {
            optimizeDimension();
            if (currentAction.getAction().equals(Action.scan)) {
                islandMap.setCase(new IslandCell(currentAction.getBiomeList()));
                calculator.scanResource(currentAction.getBiomeList());
            }
        }
        this.currentAction = currentAction;

        if (phase.equals(Phase.END_FLY_PHASE)) {
            if (islandMap.getCreeks().isEmpty()) return new Stop();
            this.land = true;
            this.islandMap.setPos(islandMap.getCreeks().get(0).getPos());
            return new Land(islandMap.getCreeks().get(0).getID(), CREW);
        }



        this.refresh();
        this.currentAction = flyPhase.nextAction(this.currentAction);

        //security
        this.currentAction = beSafe();

        return this.currentAction;
    }

    /**
     * Regarde si la position du drone est safe et agit en conséquence
     */
    private ActionExplorer beSafe() {
        switch (safePosition()) {
            case -1:
                if (islandMap.getCreeks().isEmpty()) return new Stop();
                this.land = true;
                this.islandMap.setPos(islandMap.getCreeks().get(0).getPos());
                return new Land(islandMap.getCreeks().get(0).getID(), CREW);
            case 0:
                return this.currentAction;
            case 1:
                flyPhase.setPhaseDone();
                return new Scan();
            case 2:
                //TODO: si pas de creek parcourir l'ile dans l'autre sens
                if (islandMap.getCreeks().isEmpty()) return new Stop();
                this.land = true;
                this.islandMap.setPos(islandMap.getCreeks().get(0).getPos());
                return new Land(islandMap.getCreeks().get(0).getID(), CREW);
        }
        return new Stop();
    }


    /**
     * Vérifie que la position du drone est safe - sécurité primitive pour les out of range
     * @return -1 si la position n'est pas rattrapable
     * @return  0 si la position est safe
     * @return 1 si le drone risque de mourir sur les côtés de la direction de départ
     * @return 2 si le drone risque de mourir dans la direction de départ
     */
    private int safePosition() {
        if (islandMap == null) return 0;

        int x = islandMap.getPos().getX();
        int y = islandMap.getPos().getY();
        int width = islandMap.getWidth();
        int height = islandMap.getHeight();

        //retour rapide si la position n'est pas sujette à discussion (wow)
        if ((x >= MARGIN) && (x < width - 1 - MARGIN) && (y >= MARGIN) && (y < height - 1 - MARGIN)) return 0;


        //mort inévitable
        if (x == 0 && currentDirection.equals(Direction.WEST)) {
            return -1;
        }
        if (x == width - 1 && currentDirection.equals(Direction.EAST)) {
            return -1;
        }
        if (y == 0 && currentDirection.equals(Direction.NORTH)) {
            return -1;
        }
        if (y == height - 1 && currentDirection.equals(Direction.SOUTH)) {
            return -1;
        }


        //parcours de l'île
        if (phase.equals(Phase.FLY_OVER)) {
            //mort sur les côtés
            switch (currentDirection) {
                case NORTH:
                    if (y == 2) return 1;
                    break;
                case EAST:
                    if (x == width - 1 - 2) return 1;
                    break;
                case SOUTH:
                    if (y == height - 1 - 2) return 1;
                    break;
                case WEST:
                    if (x == 2) return 1;
                    break;
            }

            //fin du ponçage de la carte
            switch (flyPhase.getNextDirection()) {
                case NORTH:
                    if (y < MARGIN) return 2;
                    break;
                case EAST:
                    if (x > width - 1 - MARGIN) return 2;
                    break;
                case SOUTH:
                    if (y > height - 1 - MARGIN) return 2;
                    break;
                case WEST:
                    if (x < MARGIN) return 2;
                    break;
            }
        }

        //position safe
        return 0;
    }

    /**
     * Optimise les dimensions de la carte, dans le cas ou une des dimensions a été choisie par défaut
     */
    private void optimizeDimension() {
        if (islandMap.isDimensionOptimized()) return;
        if (flyPhase.getMissingDimension() == -1) return;
        islandMap.optimizeDimensions(flyPhase.getMissingDimension());

    }


    /**
     * Rafraichit la phase, la carte, et la direction
     */
    private void refresh(){
        this.refreshPhase();
        if (currentAction != null) {
            this.refreshMap();
            this.refreshDirection(currentAction);
        }
    }

    /**
     * Rafraichit la phase actuelle
     */
    private void refreshPhase() {
        if (flyPhase.isPhaseDone()) flyPhase = flyPhase.nextPhase();
        this.phase = flyPhase.getPhase();
    }

    /**
     * Rafraichit la carte, la position, les biomes, etc...
     */
    private void refreshMap() {
        if (islandMap == null) {
            if (flyPhase.canTakeIslandMap()) {
                this.islandMap = this.flyPhase.getIslandMap();
            }

        }
        else {
            if (currentAction.getAction().equals(Action.scan)) {
                islandMap.scanCell(currentAction.getBiomeList(), currentAction.getCreeks());
            }

            if (currentAction.getAction().equals(Action.fly))
                islandMap.actualizePosition(currentDirection);

            if (currentAction.getAction().equals(Action.heading))
                islandMap.actualizePosition(currentAction.getDirection(), currentDirection);
        }
    }

    /**
     * Rafraichit la direction avec l'action actuelle
     * @param currentAction
     */
    private void refreshDirection(ActionExplorer currentAction) {
        if (currentAction.getAction().equals(Action.heading))
            currentDirection = currentAction.getDirection();
    }



    /*** GETTERS ***/
    public boolean isLand() {
        return land;
    }

    public void setLand(boolean land) {
        this.land = land;
    }

    public IslandMap getIslandMap() {
        return islandMap;
    }

    public FlyPhase getFlyPhase() {
        return flyPhase;
    }

    public Calculator getCalculator() {
        return calculator;
    }
}
