package lindenmayer;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.awt.geom.Rectangle2D;
import java.io.FileReader;

import static java.lang.System.out;


/**
 * The entry point of the app.
 *
 *This class prints the eps drawing to the standard output by default.
 *
 * @author Josué Mongan (20290870) and David Stanescu(20314518)
 */
public class Main {

    public static void main(String[] args) {
        try {
            // Get the arguments of the function
            String file = args[0];
            int numIter = Integer.parseInt(args[1]);

            // Read JSON File
            JSONObject pars = new JSONObject(new JSONTokener(new FileReader(file)));

            // Create a LSystem instance
            LSystem lSystem = new LSystem();

            // Create a PostScriptTurtle instance
            PostScriptTurtle turtle = new PostScriptTurtle();

            // Print header of PostScript file
            out.println("%!PS-Adobe-3.0 EPSF-3.0");
            out.printf("%%%%Title: (%s)\n", file);
            out.println("%%Creator: (lindenmayer.PostScriptTurtle)");
            out.println("%%BoundingBox: (atend)"); // we will get the bounding box after the drawing
            out.println("%%EndComments");
            out.println("0.5 setlinewidth"); // decrease the line's width

            // Initialize LSystem and Turtle from JSON
            lSystem.initFromJson(pars, turtle);

            // Use tell function on PostScriptTurtle
            Rectangle2D bbox = lSystem.tell(turtle, lSystem.getAxiom(), numIter); // drawing by the turtle

            // Draw
            out.println("stroke");

            // Print footer of PostScript file with boundingBox
            out.println("%%Trailer");
            out.printf("%%%%BoundingBox: %d %d %d %d\n", (int)bbox.getMinX(), (int)bbox.getMinY()
                    , (int)bbox.getMaxX(), (int)bbox.getMaxY());
            out.println("%%EOF");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
