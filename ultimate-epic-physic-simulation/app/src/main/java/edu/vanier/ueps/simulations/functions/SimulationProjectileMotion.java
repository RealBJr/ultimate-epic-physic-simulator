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
        final Canvas canvas = new Canvas(600, 700);      
        
        //initial position of rect/shape
        double initialPosX = 100;
        double initialPosY = 100;
        
        //following position after a certain time    
        double nextPositionX;
        double nextPositionY;
        
        //time passed b4 Modification("update")
        double time;
        
         //time passed b4 Modification("update")
        double timeInSeconds;
        
        //norm of the velocity vector; more like speed
        double speed;
        
        //direction in radians
        double direction;
        
        GraphicsContext gc;

    public Canvas getCanvas() {
        return this.canvas;
    }

    public SimulationProjectileMotion(double time, double speed, double direction) {
        this.time = time;
        this.speed = speed;
        this.direction = direction;
        this.gc = this.canvas.getGraphicsContext2D();

        
    }
        
    public void draw(GraphicsContext gc){
        gc.fillRect(initialPosX, initialPosY, 50, 50);
        
    }
    
    public void update(){
        this.initialPosX += projectileMotionX(this.time, this.initialPosX, velocityX(this.speed));
        this.initialPosY += projectileMotionY(this.time, this.initialPosY, velocityY(this.speed));
    }

    private double velocityX(double speed){
        //use the fact that cos(direction) = x^2/v^2 -> x = sqrt(cos(direction)/v^2)
        double velocityX = Math.sqrt(Math.cos(this.direction)/(speed*speed));
        return velocityX;
    }
    
    private double velocityY(double speed) {
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
    
    public Timeline animation() {
        this.gc.setFill(Color.BLUE);
        EventHandler<ActionEvent> onFinished = this::handleAnimationUpdate;
        Timeline animation = new Timeline(new KeyFrame(
                Duration.millis(50), onFinished)
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
        if (initialPosX > 100) {
            initialPosX = 0;
        }
        this.gc.clearRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
        draw(gc);
    }

}
