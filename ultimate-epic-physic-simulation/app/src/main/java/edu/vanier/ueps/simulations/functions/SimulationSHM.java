/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

/**
 *
 * @author 2158914
 */
public class SimulationSHM {
    /**
     * Amplitude of movement
     */
    private double a;

    public SimulationSHM(double a) {
        this.a = a;
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }
    
    public ArrayList<Shape> collisionDetect(Shape targetedShape, ArrayList<Shape> shapes) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void collisionReaction(Shape collidingShape) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Timeline sim(Shape targetedShape, Duration cycleTime) {
        //initial position of node in pixel
        double x = targetedShape.getLayoutX();
        
        //Amplitude of movement in pixel
        double a = getA();
        
        //Angular frequency (kind of the speed of the node) in radians
        double w = 2*Math.PI/cycleTime.toSeconds();
        
        
        //Phase shift
        double phi;
        
        double finalPosition = a*Math.cos(w*cycleTime.toSeconds());
        
        Timeline SHM = new Timeline();
        KeyFrame kf1 = new KeyFrame(cycleTime);
        KeyValue kv1 = new KeyValue(targetedShape.layoutXProperty(), finalPosition, Interpolator.LINEAR);
        kf1.getValues().add(kv1);
        
        SHM.getKeyFrames().add(kf1);
        
        return SHM;
    }

    public File saveSim(Controller simController) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Timeline stopSim(Shape targetedShape) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
