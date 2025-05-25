package lindenmayer;

import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


import org.json.JSONObject;

public class LSystem extends AbstractLSystem {
    /* constructeur pour un système avec alphabet vide et sans règles */
    protected LSystem(){  }
    /* méthodes d’initialisation de système */
    public Symbol setAction(char sym, String action){

    }
    public void setAxiom(String axiom){

    }
    public void addRule(Symbol sym, String expansion) {

    }
    /* accès aux règles et exécution */
    public Iterator<Symbol> getAxiom(){

    }
    public Iterator<Symbol> rewrite(Symbol sym) {

    }
    public void tell(Turtle turtle, Symbol sym) {

    }

    /* inférence + dessin */
    /* retourne BoundingBox pour le dessin */
    public Rectangle2D tell(Turtle T, Iterator<Symbol> seq, int n){

    }

    /* initialisation par fichier JSON */
    protected void initFromJson(JSONObject obj, Turtle turtle) {
        JSONObject actions = obj.getJSONObject("actions");
        for (String code: actions.keySet()) {
            String action = actions.getString(code);
            char c = code.charAt(0);
            this.setAction(c, action);
        }
        String axiom = obj.getString("axiom");
        this.setAxiom(axiom);
        
        /* A completer pour le numéro c */
    };
}
