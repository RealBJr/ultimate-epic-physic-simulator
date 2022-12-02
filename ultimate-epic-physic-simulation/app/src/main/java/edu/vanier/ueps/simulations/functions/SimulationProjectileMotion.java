/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.ueps.simulations.functions;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 *
 * @author 2158914
 */
public class SimulationProjectileMotion {
    //canvas
    final Canvas canvas = new Canvas(1300, 1300);

    //kind of scene onTop the canvas (overlay) where i can add lines and stuff
    final Pane onTop = new Pane();

    //initial position of rect/shape
    double posX = 0;
    double posY = 700;

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
    final double initialVelocityY;

    //Velocity in x direction won't change; it is just to access it easily
    double velocityX;

    //Velocity(initialSpeed) vector
    Line vectorSpeed;
    Line vectorY;
    Line vectorX;

    double rate;

    //direction in radians
    double direction;
    
    //Gravitational acceleration
    double gravitationalConstant = 0.01;

    GraphicsContext gc;
    //Height and Width
    double rectHeight = 150;
    double rectWidth = 150;

    //Center coordinates
    double rectCenterX = posX + rectWidth / 2;
    double rectCenterY = posY + rectHeight / 2;

    AnimationTimer animation;

    public Canvas getCanvas() {
        return this.canvas;
    }

    public Pane getOnTop() {
        return onTop;
    }

    /**
     *
     * @param speed
     * @param direction
     */
    public SimulationProjectileMotion(double speed, double direction) {
        //by default best fps
        this.time = 1;
        this.initialSpeed = speed;
        this.direction = direction;
        this.gc = this.canvas.getGraphicsContext2D();
        this.rate = speed;
        this.velocityX = velocityX(this.initialSpeed);
        this.velocityY = velocityY(this.initialSpeed);
        this.initialVelocityY = velocityY(this.initialSpeed);

        this.animation = new AnimationTimer() {
            @Override
            public void handle(long now) {
                System.out.println("Drawing");
                update();
                updateVector();
                if (posY > 700) {
                    posY = 700;
                }
                if (posY < 0) {
                    posY = 0;
                }
                //TODO: figure out how to effectively stop this
                if (posY > 700) {
                    velocityX = 0;
                    posX = 0;
                }
                if (posX > 1300) {
                    posX = 0;
                }
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                draw(gc);
            }
        };
    }

    private double getEndVectorX() {
        double endX = this.nextPositionX + this.rectCenterX;
        return endX;
    }

    /**
     * TODO: Check logic here, Every time we create a new vector, we change the
     * velocity of y how to draw line now
     *
     * @return
     */
    private double getEndVectorY() {
        double endY;
        this.nextPositionY = projectileMotionY(time, nextPositionY, velocityY);
        this.velocityY += gravitationalConstant;
        endY = this.nextPositionY + this.rectCenterY;
        //Vy = Voy - gt, unless its already at 0
        //if (velocityY(initialSpeed) == 0) {

//        } else{
//            this.velocityY -= 9.8 * this.time * Math.pow(10, -3);
//            this.initialSpeed = Math.sqrt(velocityY*velocityY + velocityX*velocityX)* Math.pow(10, -3);
//            System.out.println("At time "+ timer + " Velocity in Y = " + velocityY*timer);
//        }
        return endY;
    }

    private double velocityX(double speed) {
        //use the fact that cos(direction) = x/v -> x = cos(direction)*v) in pixel/ms
        this.velocityX = speed * Math.cos(this.direction);
        System.out.println("velocity x = " + velocityX);
        return this.velocityX;
    }

    private double velocityY(double speed) {
        
        //use the fact that sin(direction) = y/v -> y = v*sin(direction) in pixel/ms
        this.velocityY = speed * Math.sin(this.direction);
        System.out.println("velocity Y = " + velocityY);

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
        double derivedNextY = posY;
        //Calculate next Y if its non-zero velocity in Y initially; if its zero
        if (this.initialVelocityY == 0) {
            System.out.println("position Y update = " + derivedNextY);
            return derivedNextY;
        } else {
            //here im kinda trying to implement acceleration, just change rate.
            //I just changed this and its constant -> implement acceleration
            derivedNextY = currentPositionY - time * velocityY;
            //acceleration changes velocity
            //CHANGING THIS VALUE KINDA CHANGES THE GRAVITY
            this.velocityY -= gravitationalConstant;
        }

        System.out.println("position Y update = " + derivedNextY);
        return derivedNextY;
    }

    public void displayCanvas() {
        draw(this.gc);
    }

    public void draw(GraphicsContext gc) {
        this.gc.setFill(Color.BLUE);
        System.out.println("nxtPositionY = " + nextPositionY);

        gc.fillRect(this.posX, this.posY, this.rectHeight, this.rectWidth);

        //TODO: Check line drawing after the initial call
        this.vectorY = new Line(this.rectCenterX, this.rectCenterY, this.rectCenterX, getEndVectorY());
        this.vectorY.setStrokeWidth(3);
        this.vectorY.setStroke(Color.RED);

//        this.vectorX = new Line(this.rectCenterX, this.rectCenterY, getEndVectorX(), this.rectCenterY);
//        this.vectorX.setStrokeWidth(3);
//        this.vectorX.setStroke(Color.GREEN);
//
//        this.vectorSpeed = new Line(this.rectCenterX, this.rectCenterY, getEndVectorX(), getEndVectorY());
//        this.vectorSpeed.setStrokeWidth(3);
//        this.vectorSpeed.setStroke(Color.BLACK);

        this.onTop.getChildren().addAll(this.vectorY/*, this.vectorX, this.vectorSpeed*/);
    }

    public AnimationTimer getAnimation() {
        return this.animation;
    }

    public void update() {
        this.posX = projectileMotionX(this.time, this.posX, this.velocityX);
        this.nextPositionX = this.posX;
        this.rectCenterX = this.posX + this.rectWidth / 2;

        this.posY = projectileMotionY(this.time, this.posY, this.velocityY);
        //this.nextPositionY = this.initialPosY;
        this.rectCenterY = this.posY + this.rectHeight / 2;
    }

    private void updateVector() {
        this.onTop.getChildren().remove(0/*, 3*/);
    }
}
