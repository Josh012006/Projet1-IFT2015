package lindenmayer;

import java.awt.geom.Point2D;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * DummyTurtle : tortue « à crayon imaginaire »
 * Implémente Turtle en enregistrant la position, l’angle et la pile d’états.
 */
public class DummyTurtle implements Turtle {

    // Classe State pour conserver (position, angle).
    private static class State {
        Point2D.Double pos;
        double angleDeg;
        State(Point2D.Double p, double a) {
            this.pos = new Point2D.Double(p.x, p.y);
            this.angleDeg = a;
        }
    }

    private Point2D.Double currentPos;
    private double currentAngleDeg;
    private double unitStep;
    private double unitAngleDeg;
    // Pile d’états
    private final Deque<State> stack;

    //Initialise la tortue à (0,0) angle 0 par défaut et la pile d'état.
    public DummyTurtle() {
        currentPos = new Point2D.Double(0.0, 0.0);
        currentAngleDeg = 0.0;
        unitStep = 1.0;
        unitAngleDeg = 90.0;
        stack = new ArrayDeque<>();
    }

    @Override
    public void init(Point2D pos, double angle) {
        // on remet à zéro la pile et on positionne la tortue
        stack.clear();
        this.currentPos = new Point2D.Double(pos.getX(), pos.getY());
        this.currentAngleDeg = angle;
    }

    @Override
    public Point2D getPosition() {
        // on retourne une copie pour éviter toute modification externe
        return new Point2D.Double(currentPos.x, currentPos.y);
    }

    @Override
    public double getAngle() {
        return currentAngleDeg;
    }

    @Override
    public void setUnits(double step, double delta) {
        this.unitStep = step;
        this.unitAngleDeg = delta;
    }

    @Override
    public double getUnitStep() {
        return unitStep;
    }

    @Override
    public double getUnitAngle() {
        return unitAngleDeg;
    }

    @Override
    public void draw() {
        // même comportement que move(), avec tracé
        move();
    }

    @Override
    public void move() {
        // calcule le déplacement en fonction de currentAngleDeg
        double rad = Math.toRadians(currentAngleDeg);
        double dx = unitStep * Math.cos(rad);
        double dy = unitStep * Math.sin(rad);
        currentPos.x += dx;
        currentPos.y += dy;
    }

    @Override
    public void turnR() {
        currentAngleDeg -= unitAngleDeg;
    }

    @Override
    public void turnL() {
        currentAngleDeg += unitAngleDeg;
    }

    @Override
    public void push() {
        // empiler une copie de l’état courant
        stack.push(new State(currentPos, currentAngleDeg));
    }

    @Override
    public void pop() {
        if (!stack.isEmpty()) {
            State s = stack.pop();
            // restaurer la position et l’angle
            currentPos = new Point2D.Double(s.pos.x, s.pos.y);
            currentAngleDeg = s.angleDeg;
        } else {
            // aucun état à dépiler
            throw new IllegalStateException("Pile vide dans DummyTurtle.pop()");
        }
    }

    @Override
    public void stay() {
        // ne fait rien
    }
}