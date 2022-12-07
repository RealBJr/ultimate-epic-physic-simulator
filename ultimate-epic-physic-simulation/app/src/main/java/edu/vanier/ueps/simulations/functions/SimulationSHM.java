package edu.vanier.ueps.simulations.functions;

import edu.vanier.ueps.simulations.controller.Controller;
import java.io.File;
import java.util.ArrayList;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.Line;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public class SimulationSHM {

    private Animation shm;

    public SimulationSHM(){}
    
    public SimulationSHM(Rectangle targetedShape, double amplitude, double mass, double damping, double springStiffness) {
        shm(targetedShape, amplitude, mass, damping, springStiffness);
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
    private Animation shm(Rectangle targetedShape, double amplitude, double mass, double damping, double springStiffness) {
        amplitude = amplitude *50;
        mass = mass * 0.1;
        
        double period =  2 * Math.PI*Math.sqrt((mass/springStiffness));
        Duration cycleTime = Duration.seconds(period/4);
        
        Line path1 = new Line();
        path1.setStartX(0);
        path1.setEndX(amplitude);
        path1.setLayoutX(targetedShape.getWidth()/2);
        path1.setLayoutY(targetedShape.getHeight()/2);
        
        Line path2 = new Line();
        path2.setStartX(0);
        path2.setEndX(-amplitude);
        path2.setLayoutX(targetedShape.getWidth()/2);
        path2.setLayoutY(targetedShape.getHeight()/2);
        
        PathTransition pathTransition1 = new PathTransition();
        pathTransition1.setDuration(cycleTime);
        pathTransition1.setPath(path1);
        pathTransition1.setNode(targetedShape);
        pathTransition1.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition1.setCycleCount(2);
        pathTransition1.setAutoReverse(true);
        pathTransition1.setInterpolator(Interpolator.EASE_OUT);
        
        PathTransition pathTransition2 = new PathTransition();
        pathTransition2.setDuration(cycleTime);
        pathTransition2.setPath(path2);
        pathTransition2.setNode(targetedShape);
        pathTransition2.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition2.setCycleCount(2);
        pathTransition2.setAutoReverse(true);
        pathTransition2.setInterpolator(Interpolator.EASE_OUT);

        
        SequentialTransition seqT = new SequentialTransition(pathTransition1,pathTransition2);       
        seqT.setCycleCount(Animation.INDEFINITE);
                
        shm = seqT;
        return shm;
    
    }
    
    public Animation bringToCenter(Rectangle targetedShape, double amplitude, double mass, double damping, double springStiffness, double initialX){
        amplitude = amplitude *50;
        mass = mass * 0.1;
        
        double period =  2 * Math.PI*Math.sqrt((mass/springStiffness));
        Duration cycleTime = Duration.seconds(period/4);
        
        
        if(amplitude < 0){
            Line path = new Line(); 
            path.setStartX(targetedShape.getLayoutX());
            path.setEndX(path.getStartX() + Math.abs(amplitude));
            path.setLayoutX(targetedShape.getWidth()/2);
            path.setLayoutY(targetedShape.getHeight()/2);
            PathTransition pathTransition = new PathTransition();
            pathTransition.setDuration(cycleTime);
            pathTransition.setPath(path);
            pathTransition.setNode(targetedShape);
            pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
            pathTransition.setCycleCount(1);
            pathTransition.setAutoReverse(true);
            pathTransition.setInterpolator(Interpolator.EASE_IN);
            
            //SequentialTransition seqT = new SequentialTransition(pathTransition,shm(targetedShape, Math.abs(amplitude) , mass, damping, springStiffness));       
            //seqT.setCycleCount(Animation.INDEFINITE);
            
            return pathTransition;
        }else{
            
        }
        return shm(targetedShape, amplitude, mass, damping, springStiffness);
    }
    
    public Animation getShm() {
        return shm;
    }

    public void setShm(Animation shm) {
        this.shm = shm;
    }
}

