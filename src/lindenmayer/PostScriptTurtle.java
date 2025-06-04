package lindenmayer;

import java.awt.geom.Point2D;


/**
 * Class implementing the EPS version of the Turtle.
 *
 *This class adds to the DummyTurtle the PostScript implementation
 *
 * @author Josué Mongan (20290870) and David Stanescu(20314518)
 */
public class PostScriptTurtle extends DummyTurtle{

    @Override
    public void init(Point2D pos, double angle) {
        super.init(pos, angle);
        double x = pos.getX();
        double y = pos.getY();
        System.out.println("newpath " + x + " " + y + " moveto");
    }

    @Override
    public void draw() {
        super.draw();
        double x = this.getPosition().getX();
        double y = this.getPosition().getY();
        System.out.println(x + " " + y + " lineto");
    }

    @Override
    public void move() {
        super.move();
        double x = this.getPosition().getX();
        double y = this.getPosition().getY();
        System.out.println(x + " " + y + " moveto");
    }

    @Override
    public void push() {
        super.push();
        System.out.println("currentpoint stroke newpath moveto");
    }

    @Override
    public void pop() {
        super.pop();
        double x = this.getPosition().getX();
        double y = this.getPosition().getY();
        System.out.println("stroke " + x + " " + y + " newpath moveto");
    }
}
