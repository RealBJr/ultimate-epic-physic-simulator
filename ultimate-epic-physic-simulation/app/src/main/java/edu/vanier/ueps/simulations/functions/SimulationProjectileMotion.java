package edu.vanier.ueps.simulations.functions;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class SimulationProjectileMotion {
    
    //canvas
    private Canvas canvas = new Canvas(1300, 1300);

    //kind of scene onTop the canvas (overlay) where i can add lines and stuff
    private  Pane onTop = new Pane();

    //initial position of rect/shape
    private double initialPosX = 0;
    private double initialPosY;
    
    //progressive position of rectangle
    private double posX;
    private double posY;

    //following position after a certain time    
    private double nextPositionX;
    private double nextPositionY;

    //time passed b4 Modification("update")
    private final double time;

    //norm of the initial velocity vector; more like initialSpeed
    private final double initialSpeed;

    //Velocity in y direction is a variable; it will change
    private double velocityY;
    private final double initialVelocityY;

    //Velocity in x direction won't change; it is just to access it easily
    private double velocityX;

    //Velocity(initialSpeed) vector
    private Line vectorSpeed;
    private Line vectorY;
    private Line vectorX;

    private double rate;

    //direction in radians
    private double direction;

    //Gravitational acceleration
    private double gravitationalConstant = 0.01;

    private GraphicsContext gc;
    //Height and Width
    private double rectHeight = 150;
    private double rectWidth = 150;

    //Center coordinates
    private double rectCenterX = posX + rectWidth / 2;
    private double rectCenterY = posY + rectHeight / 2;

    
    private AnimationTimer animation;

    /**
     *
     * @param speed
     * @param direction
     * @param cv
     */
    public SimulationProjectileMotion(double speed, double direction, Canvas cv) {
        setCanvas(cv);
        //by default best fps
        this.time = 1;
        this.initialSpeed = speed;
        this.direction = direction;
        this.gc = this.canvas.getGraphicsContext2D();
        this.rate = speed;
        this.velocityX = velocityX(this.initialSpeed);
        this.velocityY = velocityY(this.initialSpeed);
        this.initialVelocityY = velocityY(this.initialSpeed);
        this.initialPosY = this.canvas.getWidth() - 2*this.rectHeight;

        this.animation = new AnimationTimer() {
            @Override
            public void handle(long now) {
                handleAnimation();
            }
        };
    }

    private double getEndVectorX() {
        //Changes the time to make the vector be more visible; it displays the next position that is/will be as tall as the rate vector
        this.nextPositionX = projectileMotionX(rate * time, rectCenterX, velocityX);
        //this is indeed the next position within the next given time
        return this.nextPositionX;
    }

    /**
     * TODO: Check logic here, Every time we create a new vector, we change the
     * velocity of y how to draw line now
     *
     * @return
     */
    private double getEndVectorY() {
        double endY;
        //Changes the time to make the vector be more visible; it displays the next position that is/will be as tall as the rate vector
        this.nextPositionY = projectileMotionY(rate * time, rectCenterY, velocityY);
        //To offset velocity change made by projectileMotionY()
        this.velocityY += gravitationalConstant;
        //this is indeed the next position within the next given time
        endY = this.nextPositionY;
        //Vy = Voy - gt, unless its already at 0
        return endY;
    }

    private double velocityX(double speed) {
        //use the fact that cos(direction) = x/v -> x = cos(direction)*v) in pixel/ms
        this.velocityX = speed * Math.cos(this.direction);
        //System.out.println("velocity x = " + velocityX);
        return this.velocityX;
    }

    private double velocityY(double speed) {
        //use the fact that sin(direction) = y/v -> y = v*sin(direction) in pixel/ms
        this.velocityY = speed * Math.sin(this.direction);
        //System.out.println("velocity Y = " + velocityY);

        //System.out.println("velocity y = " + velocityY);
        return this.velocityY;
    }

    private double projectileMotionX(double time, double currentPositionX, double velocityX) {
        //x position IS NOT affected by gravitational acceleration, it follows a velocity
        double derivedNextX = currentPositionX + time * velocityX;
        //System.out.println("position X update = " + derivedNextX);
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
            //System.out.println("position Y update = " + derivedNextY);
            return derivedNextY;
        } else {
            //here im kinda trying to implement acceleration, just change rate.
            //I just changed this and its constant -> implement acceleration
            derivedNextY = currentPositionY - time * velocityY;
            //acceleration changes velocity
            //CHANGING THIS VALUE KINDA CHANGES THE GRAVITY
            this.velocityY -= gravitationalConstant;
        }

        //System.out.println("position Y update = " + derivedNextY);
        return derivedNextY;
    }

    public void displayCanvas() {
        this.posX = this.initialPosX;
        this.posY = this.initialPosY;
        draw(this.gc);
    }

    public void draw(GraphicsContext gc) {
        
        this.gc.setFill(Color.RED);
        //System.out.println("nxtPositionY = " + nextPositionY);

        System.out.println("posX = " + this.posX);
        System.out.println("posY = " + this.posY);
        gc.fillRect(this.posX, this.posY, this.rectHeight, this.rectWidth);

        //TODO: Check line drawing after the initial call
        this.vectorY = new Line(this.rectCenterX, this.rectCenterY, this.rectCenterX, getEndVectorY());
        this.vectorY.setStrokeWidth(4);
        this.vectorY.setStroke(Color.RED);

        this.vectorX = new Line(this.rectCenterX, this.rectCenterY, getEndVectorX(), this.rectCenterY);
        this.vectorX.setStrokeWidth(4);
        this.vectorX.setStroke(Color.GREEN);

        this.vectorSpeed = new Line(this.rectCenterX, this.rectCenterY, getEndVectorX(), getEndVectorY());
        this.vectorSpeed.setStrokeWidth(3);
        this.vectorSpeed.setStroke(Color.BLACK);

        this.onTop.getChildren().addAll(this.vectorY, this.vectorX, this.vectorSpeed);
    }

//    private AnimationTimer getAnimation() {
//        return this.animation;
//    }

    public void pauseAnimation(){
        this.animation.stop();
    }
    
    public void resetAnimation(){
        this.animation.stop();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        getOnTop().getChildren().clear();
            posX = initialPosX;
            posY = initialPosY;
            this.velocityX = velocityX(this.initialSpeed);
            this.velocityY = velocityY(this.initialSpeed);
            rectCenterX = posX + rectWidth / 2;
            rectCenterY = posY + rectHeight / 2;
        draw(gc);
        System.out.println("I got to the end here");
    }
    
    public void startAnimation(){
        this.animation.start();
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
        this.onTop.getChildren().remove(0, 3);
    }
    
    
    public void handleAnimation(){
                System.out.println("Drawing");
                update();
                updateVector();
                if (posY > this.canvas.getHeight()) {
                    posY = initialPosY;
                    pauseAnimation();
                }
                if (posY < 0) {
                    posY = 0;
                }
                //TODO: figure out how to effectively stop this
//                if (posY > this.canvas.getHeight()) {
//                    velocityX = 0;
//                    posX = 0;
//                }
                if (posX > this.canvas.getWidth() - rectWidth) {
                    System.out.println("TRUEE !!! HERE");
                    posX = this.canvas.getWidth() - rectWidth;
                }
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                draw(gc);
    }
    
    //======GETTERS AND SETTERS=========
    public Canvas getCanvas() {
        return this.canvas;
    }

    public Pane getOnTop() {
        return this.onTop;
    }
    
    public void setOnTop(Pane pane) {
        this.onTop=pane;
    }

    public double getInitialPosX() {
        return this.initialPosX;
    }

    public void setInitialPosX(double initialPosX) {
        this.initialPosX = initialPosX;
    }

    public double getInitialPosY() {
        return this.initialPosY;
    }

    public void setInitialPosY(double initialPosY) {
        this.initialPosY = initialPosY;
    }

    public double getPosX() {
        return this.posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return this.posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public Line getVectorY() {
        return this.vectorY;
    }

    public void setVectorY(Line vectorY) {
        this.vectorY = vectorY;
    }

    public Line getVectorX() {
        return this.vectorX;
    }

    public void setVectorX(Line vectorX) {
        this.vectorX = vectorX;
    }

    public double getRate() {
        return this.rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getDirection() {
        return this.direction;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public double getGravitationalConstant() {
        return this.gravitationalConstant;
    }

    public void setGravitationalConstant(double gravitationalConstant) {
        this.gravitationalConstant = gravitationalConstant;
    }

    public double getRectHeight() {
        return this.rectHeight;
    }

    public void setRectHeight(double rectHeight) {
        this.rectHeight = rectHeight;
    }

    public double getRectWidth() {
        return this.rectWidth;
    }

    public void setRectWidth(double rectWidth) {
        this.rectWidth = rectWidth;
    }
    
    private void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }
    
    
    //=============================================
}
