package fr.unice.polytech.qgl.qcd.database;

import fr.unice.polytech.qgl.qcd.progress.Contract;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Mathias Chevalier
 * @school Polytech' Nice
 * @date 12/11/2015
 */
public class Context {
    private int men;
    private int budget;
    private List<Contract> contracts;
    private Direction heading;
    private List<Resource> resourceList;

    public Context(){}

    public Context(String context) throws JSONException {
        JSONObject json = new JSONObject(context);

        this.men = json.getInt("men");
        this.budget = json.getInt("budget");

        JSONArray contractsJson = json.getJSONArray(("contracts"));
        List<Contract> contractsTemp = new ArrayList<Contract>();
        resourceList = new ArrayList<Resource>();
        int amount;
        for (int i=0; i<contractsJson.length(); i++){
            JSONObject tempObj = contractsJson.getJSONObject(i);
            Resource resource = Resource.valueOf(tempObj.getString("resource"));
            resourceList.add(resource);
            amount = tempObj.getInt("amount");
            contractsTemp.add(new Contract(amount, resource));
        }
        this.contracts=contractsTemp;

        String headingTmp = json.getString("heading");

        switch (headingTmp){
            case "W":
                this.heading = Direction.WEST;
                break;
            case "N":
                this.heading = Direction.NORTH;
                break;
            case "E":
                this.heading = Direction.EAST;
                break;
            case "S":
                this.heading = Direction.SOUTH;
                break;
        }
    }

    public int getMen() {
        return men;
    }

    public int getBudget() {
        return budget;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public Direction getHeading() {
        return heading;
    }

    public List<Resource> getResourceList() {
        return resourceList;
    }

    public void setMen(int men) {
        this.men = men;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }

    public void setHeading(Direction heading) {
        this.heading = heading;
    }
}
