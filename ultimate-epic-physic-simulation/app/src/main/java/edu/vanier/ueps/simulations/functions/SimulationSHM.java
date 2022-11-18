package edu.vanier.ueps.simulations.functions;

import edu.vanier.ueps.simulations.controller.Controller;
import java.io.File;
import java.util.ArrayList;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public class SimulationSHM {

    private Timeline shm;

    public SimulationSHM(Shape targetedShape, double amplitude, Duration cycleTime, double phaseShift) {
        shm(targetedShape, amplitude, cycleTime, phaseShift);
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
    private Timeline shm(Shape targetedShape, double amplitude, Duration cycleTime, double phaseShift) {
        //initial position of node in pixel
        double x = targetedShape.getLayoutX();

        //Amplitude of movement in pixel
        double a = amplitude;

        //Angular frequency (kind of the speed of the node) in radians
        double w = 2 * Math.PI / cycleTime.toSeconds();

        //Phase shift
        double phi = phaseShift;

        double finalPosition = a * Math.cos(w * cycleTime.toSeconds());

        Timeline SHM = new Timeline();
        KeyFrame kf1 = new KeyFrame(cycleTime);
        KeyValue kv1 = new KeyValue(targetedShape.layoutXProperty(), finalPosition, Interpolator.LINEAR);
        kf1.getValues().add(kv1);

        SHM.getKeyFrames().add(kf1);

        shm = SHM;
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

    public Timeline getShm() {
        return shm;
    }

    public void setShm(Timeline shm) {
        this.shm = shm;
    }
}
