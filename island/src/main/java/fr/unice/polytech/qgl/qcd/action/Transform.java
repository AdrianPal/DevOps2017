package fr.unice.polytech.qgl.qcd.action;

import fr.unice.polytech.qgl.qcd.database.Action;
import fr.unice.polytech.qgl.qcd.database.Resource;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * @author Mathias Chevalier
 * @school Polytech' Nice
 * @date 11/11/2015
 */
public class Transform extends ActionExplorer {
    protected int production;
    protected Resource kind;
    protected Resource res1;
    protected Resource res2;

    /**
     * Construit une action de transformation selon les 2 ressources de la recette et leur montant.
     * Si il faut une seul ressource pour la recette, la deuxième ressource doit être null.
     * @param resource1
     * @param amount1
     * @param resource2
     * @param amount2
     * @throws JSONException
     */
    public Transform(Resource resource1, int amount1, Resource resource2, int amount2)throws JSONException{
        super(Action.transform);
        JSONObject temp = new JSONObject();
        temp.put(resource1.getResourceName(), amount1);
        if(resource2!=null)
            temp.put(resource2.getResourceName(), amount2);
        this.res1 = resource1;
        this.res2 = resource2;
        this.parameters = temp;
        this.createJsonMessage();
        this.createStringMessage();
    }

    /**
     * Méthode analyseExtras.
     */
    @Override
    public void analyzeExtras() {
        if (this.extras != null){
            this.production = extras.getInt("production");
            this.kind = Resource.valueOf(extras.getString("kind"));
        }
    }

    //TODO: prodcution réele non testée, waiting for factory
    /**
     * Retourne le nombre d'unité créé.
     * @return
     */
    @Override
    public int getProduction() {
        return production;
    }

    /**
     * Accesseur de la ressource de retour.
     * @return
     */
    @Override
    public Resource getKind() {
        return kind;
    }

    /**
     * Méthode hashCode.
     * @return
     */
    @Override
    public int hashCode() {
        int result = production;
        result = 31 * result + (kind != null ? kind.hashCode() : 0);
        result = 31 * result + (res1 != null ? res1.hashCode() : 0);
        result = 31 * result + (res2 != null ? res2.hashCode() : 0);
        return result;
    }

    /**
     * Méthode equals.
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Transform transform = (Transform) o;

        if (production != transform.production) return false;
        if (kind != transform.kind) return false;
        if (res1 != transform.res1) return false;
        return res2 == transform.res2;

    }
}
