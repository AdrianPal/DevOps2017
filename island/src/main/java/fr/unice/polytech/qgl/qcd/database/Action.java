package fr.unice.polytech.qgl.qcd.database;

/**
 * @author Mathias Chevalier
 * @school Polytech' Nice
 * @date 12/12/2015
 */
public enum Action {
    echo("echo"),
    exploit("exploit"),
    explore("explore"),
    fly("fly"),
    glimpse("glimpse"),
    heading("heading"),
    land("land"),
    move_to("move_to"),
    scan("scan"),
    scout("scout"),
    stop("stop"),
    transform("transform");

    String token;

    Action(String t) {
        token = t;
    }

    public String getToken(){
        return token;
    }


}
