package fr.unice.polytech.qgl.qcd.action;

import fr.unice.polytech.qgl.qcd.database.Action;
import fr.unice.polytech.qgl.qcd.database.Biome;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mathias Chevalier
 * @school Polytech' Nice
 * @date 11/11/2015
 */
public class Scan extends ActionExplorer {
    protected String[] creeks;
    protected List<Biome> biomeList;

    public Scan() throws JSONException{
        super(Action.scan);
        this.createJsonMessage();
        this.createStringMessage();
    }

    @Override
    public void analyzeExtras(){
        if (this.extras != null){
            JSONArray temp = this.extras.getJSONArray("creeks");
            int longueur = temp.length();
            this.creeks = new String[longueur];
            for (int i=0; i < longueur; i++){
                this.creeks[i] = temp.getString(i);
            }
            temp = this.extras.getJSONArray("biomes");
            List tempList = new ArrayList<Biome>();
            for (int j=0; j< temp.length(); j++) {
                tempList.add(j, Biome.valueOf(temp.getString(j)));
            }
            biomeList = tempList;
        }
    }


    @Override
    public String[] getCreeks() {
        return creeks;
    }

    @Override
    public List getBiomeList() {
        return biomeList;
    }

    @Override
    public boolean isThereLand() {
        return (!(biomeList.size() == 1 && biomeList.get(0).equals(Biome.OCEAN)));
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(creeks);
        result = 31 * result + (biomeList != null ? biomeList.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Scan scan = (Scan) o;

        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(creeks, scan.creeks)) return false;
        return biomeList != null ? biomeList.equals(scan.biomeList) : scan.biomeList == null;

    }

}
