package edu.vanier.ueps.simulations.functions;

import edu.vanier.ueps.simulations.controller.Controller;
import java.io.File;
import java.util.ArrayList;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.Line;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public class SimulationSHM {

    private PathTransition shm;

    public SimulationSHM(Rectangle targetedShape, Line linePath, double amplitude, Duration cycleTime) {
        shm(targetedShape, linePath, amplitude, cycleTime);
    }
    /**
     * Animation of SHM, from the function x(t) = Acos(wt+phi), a final position
     * is calculated and added as a KeyFrame(KeyValue)
     *
     * @param targetedShape
     * @param amplitude //should be the place where it should end
     * @param cycleTime
     * @param phaseShift
     * @return Timeline
     */
    private PathTransition shm(Rectangle targetedShape, Line linePath, double amplitude, Duration cycleTime) {
//        Path path = new Path();
//        
//        path.getElements().add(new MoveTo(targetedShape.getWidth()/2, targetedShape.getHeight()/2));
//        path.getElements().add(new HLineTo(amplitude));
        double centerRectLayoutX = targetedShape.getLayoutX()+(targetedShape.getWidth()/2);
        linePath.setStartX(-centerRectLayoutX);
        linePath.setEndX(centerRectLayoutX + amplitude);
        
        
        shm = new PathTransition();
        shm.setDuration(cycleTime);
        
        shm.setPath(linePath);
        shm.setNode(targetedShape);
        shm.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        shm.setCycleCount(Timeline.INDEFINITE);
        shm.setAutoReverse(true);
        
        
        return shm;
    }
    public Timeline damping(){
        
        return null;
        
    }
    public ArrayList<Shape> collisionDetect(Shape targetedShape, ArrayList<Shape> shapes) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void collisionReaction(Shape collidingShape) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public File saveSim(Controller simController) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public PathTransition getShm() {
        return shm;
    }

    public void setShm(PathTransition shm) {
        this.shm = shm;
    }
}
