package edu.vanier.ueps.simulations;

import edu.vanier.ueps.simulations.controller.Controller;
import java.io.File;
import java.util.ArrayList;
import javafx.scene.shape.Shape;

public abstract class Simulation {

    //All shapes in the simulation's scene
    private ArrayList<Shape> shapes;

    /*
    All simulations instances have their unique controller that allow them to 
    interact with the simulation (retrieve a simulation's particular infos, i.e
    shapes' properties
     */
    private Controller simController;

    //---Getters and setters
    public ArrayList<Shape> getShapes() {
        return shapes;
    }

    public void setShapes(ArrayList<Shape> shapes) {
        this.shapes = shapes;
    }

    public Controller getSimController() {
        return simController;
    }

    public void setSimController(Controller simController) {
        this.simController = simController;
    }

    /*
    Detect if another or multiple other shapes collided with the targeted shape
    (shape observed). To collide means that it touched the width or the height
    of the targeted shape. Still thinking about how the collision should work
    feel free to change and explain how
    @param Shape targetedShape,ArrayList<Shape> shapes
    @return ArrayList<Shape>
    @see 
     */
    public abstract ArrayList<Shape> collisionDetect(Shape targetedShape, ArrayList<Shape> shapes);

    /*
    Triggers a new timeline(animation) of the shape who collided depending on 
    certain factors (momentum)
    @param Shape collidingShape
    @return none
    @see 
     */
    public abstract void collisionReaction(Shape collidingShape);

    /*
    Triggers the initial timeline(animation) of the shapes within the scene
    @param ArrayList<Shape> shapes
    @return none
    @see 
     */
    public abstract void playSim(ArrayList<Shape> shapes);

    /*
    Stops the initial timeline(animation) of the shapes within the scene
    @param ArrayList<Shape> shapes
    @return none
    @see 
     */
    public abstract void stopSim(ArrayList<Shape> shapes);
    
    /*
    Saves the parameters of the simulation (controller) of the shapes within 
    the scene in a file
    @param ArrayList<Shape> shapes
    @return File
    @see 
     */
    public abstract File saveSim(Controller simController);

}
