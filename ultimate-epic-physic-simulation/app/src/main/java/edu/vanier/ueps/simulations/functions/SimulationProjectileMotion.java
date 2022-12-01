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
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;
//not going to be supported

/**
 *
 * @author 2158914
 */
public class SimulationProjectileMotion {
    //canvas
    final Canvas canvas = new Canvas(1300, 700);

    //kind of scene onTop the canvas (overlay) where i can add lines and stuff
    final Pane onTop = new Pane();

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

    //norm of the initial velocity vector; more like initialSpeed
    double initialSpeed;
    
    //Velocity in y direction is a variable; it will change
    double velocityY;
    
    //time tracker to update y velocity everytime
    double timer;
    
    //Velocity in x direction won't change; it is just to access it easily
    double velocityX;
    
    //Velocity(initialSpeed) vector
    Line vector;
    
    //direction in radians
    double direction;

    GraphicsContext gc;
    //Height and Width
    double rectHeight = 150;
    double rectWidth = 150;

    //Center coordinates
    double rectCenterX = initialPosX + rectWidth / 2;
    double rectCenterY = initialPosY + rectHeight / 2;

    

    
    public Canvas getCanvas() {
        return this.canvas;
    }

    public Pane getOnTop() {
        return onTop;
    }

    /**
     *
     * @param time
     * @param speed
     * @param direction
     */
    public SimulationProjectileMotion(double time, double speed, double direction) {
        this.time = time;
        this.initialSpeed = speed * Math.pow(10, -3);
        this.direction = direction;
        this.gc = this.canvas.getGraphicsContext2D();
    }

    public void draw(GraphicsContext gc) {
        this.gc.setFill(Color.BLUE);

        gc.fillRect(this.initialPosX, this.initialPosY, this.rectHeight, this.rectWidth);

        this.vector = new Line(this.rectCenterX, this.rectCenterY, getEndVectorX(), getEndVectorY());
        this.vector.setStrokeWidth(3);
        this.onTop.getChildren().add(this.vector);
    }

    private double getEndVectorX(){
        double endX = this.nextPositionX + this.rectCenterX;
        return endX;
    }
    
    /**
     * Every time we create a new vector, we change the velocity of y
     * @return 
     */
    private double getEndVectorY(){
        timer+=this.time;
        double endY = this.nextPositionY + this.rectCenterY;
        //Vy = Voy - gt, unless its already at 0
        if (velocityY(initialSpeed) == 0) {
            
        } else{
            this.velocityY -= 9.8 * this.timer * Math.pow(10, -3);
            this.initialSpeed = Math.sqrt(velocityY*velocityY + velocityX*velocityX)* Math.pow(10, -3);
            System.out.println("At time "+ timer + " Velocity in Y = " + velocityY);
        }
        return endY;
    }
    public void update() {
        this.initialPosX = projectileMotionX(this.time, this.initialPosX, velocityX(this.initialSpeed));
        this.nextPositionX = this.initialPosX;
        this.rectCenterX = this.initialPosX + this.rectWidth / 2;
        

        this.initialPosY = projectileMotionY(this.time, this.initialPosY, velocityY(this.initialSpeed));
        this.nextPositionY = this.initialPosY;
        this.rectCenterY = this.initialPosY + this.rectHeight / 2;
    }

    private void updateVector() {
        this.onTop.getChildren().remove(0);
    }

    private double velocityX(double speed) {
        //use the fact that cos(direction) = x/v -> x = cos(direction)*v)
        this.velocityX = speed * Math.cos(this.direction);
        //System.out.println("velocity x = " + velocityX);
        return this.velocityX;
    }

    private double velocityY(double speed) {
        //use the fact that sin(direction) = y/v -> y = v*sin(direction)
        this.velocityY = speed * Math.sin(this.direction);
        
        //System.out.println("velocity y = " + velocityY);
        return this.velocityY;
    }

    private double projectileMotionX(double time, double currentPositionX, double velocityX) {
        //x position IS NOT affected by gravitational acceleration, it follows a velocity
        double derivedNextX = currentPositionX + time * velocityX;
        System.out.println("position X update = " + derivedNextX);
        return derivedNextX;
    }

    private double projectileMotionY(double time, double currentPositionY, double velocityY) {
        //y position IS affected by gravitational constant 
        //yo + voy t ? 4.9 t^2
        //TODO: fix position Y
        //Make that it depends on the vector of the projectile; 2 timing: when it goes up, and when it goes down
        double derivedNextY = 0;
        //Calculate next Y if its non-zero velocity in Y initially; if its zero
        if (velocityY(initialSpeed) == 0) {
            System.out.println("position Y update = " + derivedNextY);
            return derivedNextY;
        } else {
            derivedNextY = currentPositionY + time * velocityY - (4.9 * time * time);
        }
        
        System.out.println("position Y update = " + derivedNextY);
        return derivedNextY;
    }

    public void displayCanvas() {
        draw(this.gc);
    }

    public Timeline animation() {
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
        updateVector();
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
