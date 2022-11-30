/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.ueps.simulations.functions;

import edu.vanier.ueps.simulations.controller.Controller;
import java.io.File;
import java.util.ArrayList;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
//not going to be supported
/**
 *
 * @author 2158914
 */
public class SimulationProjectileMotion{
        //initial position of rect/shape
        
        int initialPosX = 0;
        int initialPosY = 0;
        
        //following position after a certain time    
        int nextPositionX;
        int nextPositionY;
        
        //time passed b4 Modification("update")
        double time;
        
        //norm of the velocity vector; more like speed
        int speed;
        
        //direction in radians
        
        int direction;
        

    public void sim(Shape targetedShape, Duration cycleTime) {
        Canvas canvas = new Canvas();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
    }
    
    public void draw(){
        
        
    }

    
    public void update(){
        nextPositionX += projectileMotionX(time, initialPosX, velocityX(speed));
        nextPositionY += projectileMotionY(time, initialPosY, velocityY(speed));
    }

    
    private double velocityX(int speed){
        //use the fact that cos(direction) = x^2/v^2 -> x = sqrt(cos(direction)/v^2)
        double velocityX = Math.sqrt(Math.cos(this.direction)/(speed*speed));
        return velocityX;
    }
    
    private double velocityY(int speed) {
        //use the fact that tan(direction) = y^2/v^2 -> x = sqrt(tan(direction)/v^2)
        double velocityY = Math.sqrt(Math.tan(this.direction)/(speed*speed));
        return velocityY;
    }
    
    private double projectileMotionX(double time, double currentPositionX, double velocityX){
        //x position is not affected by gravitational acceleration, it follows a velocity
        double derivedNextX = currentPositionX + time*velocityX;
        return derivedNextX;
                
    }
    
    private double projectileMotionY(double time, double currentPositionY, double velocityY){
        //y position is affected by gravitational constant 
        //yo + voy t ? 4.9 t^2

        double derivedNextY = currentPositionY + time*velocityY - (4.9 * time * time);
        return derivedNextY;
    }

    
}
