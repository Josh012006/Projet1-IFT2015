package lindenmayer;

import java.awt.geom.Rectangle2D;
import java.util.*;


import org.json.JSONObject;


/**
 * Class implementing the L-system logic.
 *
 *This class implements the methods of the AstractLSystem class
 *
 * @author Josué Mongan (20290870) and David Stanescu(20314518)
 */
public class LSystem extends AbstractLSystem {

    // The axiom
    private List<Symbol> axiom = new ArrayList<>();

    // A table of the possible actions
    private final Set<String> actions = new HashSet<>(Arrays.asList("draw", "move", "turnL", "turnR", "stay", "pop", "push"));

    // The system's alphabet
    private Map<Character, Symbol> alphabet;

    // The map between the symbols and their corresponding actions
    private Map<Symbol, String> symbAct;

    // The set of rules
    private Map<Symbol, List<Iterable<Symbol>>> rules;






    /**
     * The class constructor. It initializes an empty alphabet and
     * an empty set of rules
     */
    protected LSystem(){
        this.alphabet = new HashMap<>();
        this.rules = new HashMap<>();
        this.symbAct = new HashMap<>();
    }

    @Override
    /* méthodes d’initialisation de système */
    public Symbol setAction(char sym, String action) throws IllegalArgumentException {
        if (!actions.contains(action)) {
            throw new IllegalArgumentException("Invalid action: " + action);
        }

        if(this.alphabet.containsKey(sym) || this.symbAct.containsValue(action)) {
            throw new IllegalArgumentException("Symbol or action was already associated");
        }

        final Symbol symbol = new Symbol(sym);
        this.alphabet.put(sym, symbol);
        this.symbAct.put(symbol, action);
        this.rules.put(symbol, new ArrayList<>());

        return symbol;
    }


    /**
     * This method is to help me produce an Iterable Symbol collection
     * from a String.
     *
     * @param s the string to convert
     * @return List&lt;Symbol&gt; containing all th symbols associated
     * to the characters in the string
     */
    private List<Symbol> fromString(String s){
        ArrayList<Symbol> list = new ArrayList<>();
        for(char c : s.toCharArray()) {
            list.add(this.alphabet.get(c));
        }

        return list;
    }

    @Override
    public void setAxiom(String axiom){
        this.axiom = fromString(axiom);
    }

    @Override
    public void addRule(Symbol sym, String expansion) {
        this.rules.get(sym).add(fromString(expansion));
    }


    /* accès aux règles et exécution */
    @Override
    public Iterator<Symbol> getAxiom(){
        return this.axiom.iterator();
    }

    @Override
    public Iterator<Symbol> rewrite(Symbol sym) {
        List<Iterable<Symbol>> list = this.rules.get(sym);
        if(list.isEmpty()) {
            return null;
        }
        return super.rndElement(list).iterator();
    }

    @Override
    public void tell(Turtle turtle, Symbol sym) {
        Runnable action = turtle.action(this.symbAct.get(sym));
        if(action != null) {
            action.run();
        }
    }


    /**
     * This method takes a list of iterators and chains them using the innate
     * order of the list. The code was generated using CHATGPT(@chatgpt.com)
     *
     * @param list the list of iterators to chain together
     * @return the final iterator
     */
    private Iterator<Symbol> chain (List<Iterator<Symbol>> list){
        return new Iterator<Symbol>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                while (index < list.size()) {
                    if (list.get(index).hasNext()) {
                        return true;
                    }
                    index++; // Passe à l’iterator suivant
                }
                return false;
            }

            @Override
            public Symbol next() {
                if (!hasNext()) throw new NoSuchElementException();
                return list.get(index).next();
            }
        };
    }



    /* inférence + dessin */
    /* retourne BoundingBox pour le dessin */
    @Override
    public Rectangle2D tell(Turtle T, Iterator<Symbol> seq, int n){

        if(n == 0) {
            double xmin = Double.POSITIVE_INFINITY;
            double xmax = Double.NEGATIVE_INFINITY;
            double ymin = Double.POSITIVE_INFINITY;
            double ymax = Double.NEGATIVE_INFINITY;

            while(seq.hasNext()) {
                Symbol sym = seq.next();
                tell(T, sym);
                double newX = T.getPosition().getX();
                double newY = T.getPosition().getY();
                if(newX < xmin) {
                    xmin = newX;
                }
                if(newY < ymin) {
                    ymin = newY;
                }
                if(newX > xmax) {
                    xmax = newX;
                }
                if(newY > ymax) {
                    ymax = newY;
                }
            }

            return new Rectangle2D.Double(xmin, ymin, xmax - xmin, ymax - ymin);

        }
        else {

            List<Iterator<Symbol>> iteratorList = new ArrayList<>();

            while(seq.hasNext()) {
                Symbol sym = seq.next();
                Iterator<Symbol> rewritten = rewrite(sym);

                if(rewritten != null) {
                    iteratorList.add(rewritten);
                }
                else {
                    List<Symbol> dummyList = new ArrayList<>();
                    dummyList.add(sym);
                    iteratorList.add(dummyList.iterator());
                }
            }

            return tell(T, chain(iteratorList), n - 1);
        }

    }

    /* initialisation par fichier JSON */
    @Override
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
