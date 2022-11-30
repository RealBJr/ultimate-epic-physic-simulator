/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.ueps.simulations.functions;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
//not going to be supported
/**
 *
 * @author 2158914
 */
public class SimulationProjectileMotion{
        //canvas
        final Canvas canvas = new Canvas(1300, 500);      
        
        //initial position of rect/shape
        double initialPosX = 150;
        double initialPosY = 500;
        
        //following position after a certain time    
        double nextPositionX;
        double nextPositionY;
        
        //time passed b4 Modification("update")
        double time;
        
         //time passed b4 Modification("update")
        double timeInSeconds = Math.pow(time, -3);
        
        //norm of the velocity vector; more like speed
        double speed;
        
        //direction in radians
        double direction;
        
        GraphicsContext gc;

    public Canvas getCanvas() {
        return this.canvas;
    }

    /**
     * 
     * @param time
     * @param speed
     * @param direction 
     */
    public SimulationProjectileMotion(double time, double speed, double direction) {
        this.time = time;
        this.speed = speed*Math.pow(10,-3);
        this.direction = direction;
        this.gc = this.canvas.getGraphicsContext2D();
    }
        
    public void draw(GraphicsContext gc){
        gc.fillRect(initialPosX, initialPosY, 150, 150);
    }
    
    public void update(){
        this.initialPosX = projectileMotionX(this.time, this.initialPosX, velocityX(this.speed));
        this.initialPosY = projectileMotionY(this.time, this.initialPosY, velocityY(this.speed));
    }

    private double velocityX(double speed){
        //use the fact that cos(direction) = x/v -> x = cos(direction)*v)
        double velocityX = speed*Math.cos(this.direction);
        //System.out.println("velocity x = " + velocityX);
        return velocityX;
    }
    
    private double velocityY(double speed) {
        //use the fact that tan(direction) = y/v -> y = v*tan(direction))
        double velocityY = speed*Math.tan(this.direction);
        //System.out.println("velocity y = " + velocityY);
        return velocityY;
    }
    
    private double projectileMotionX(double time, double currentPositionX, double velocityX){
        //x position IS NOT affected by gravitational acceleration, it follows a velocity
        double derivedNextX = currentPositionX + time*velocityX;
        System.out.println("position X update = " + derivedNextX);
        return derivedNextX;
    }
    
    private double projectileMotionY(double time, double currentPositionY, double velocityY){
        //y position IS affected by gravitational constant 
        //yo + voy t ? 4.9 t^2
        //TODO: fix position Y
        //Make that it depends on the vector of the projectile; 2 timing: when it goes up, and when it goes down
        double derivedNextY = currentPositionY + time*velocityY - (4.9 * time * time);
        System.out.println("position Y update = " + derivedNextY);
        return derivedNextY;
    }
    
    public Timeline animation() {
        this.gc.setFill(Color.BLUE);
        EventHandler<ActionEvent> onFinished = this::handleAnimationUpdate;
        Timeline animation = new Timeline(new KeyFrame(
                Duration.millis(time), onFinished)
        );
        animation.setCycleCount(Animation.INDEFINITE);
        animation.setAutoReverse(true);
        return animation;
    }

    private void handleAnimationUpdate(ActionEvent event) {
        System.out.println("Drawing");
        update();
        if (initialPosY < 0) {
            initialPosY = 0;
        }
        if (initialPosX > 1300) {
            initialPosX = 0;
        }
        this.gc.clearRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
        draw(gc);
    }

}
