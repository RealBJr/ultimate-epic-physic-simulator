package edu.vanier.ueps.simulations.controller;

import edu.vanier.ueps.graphs.controllers.GraphControllerPendulum;
import java.net.URL;
import java.util.EventListener;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Path;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

/**
 *
 * @author Youssif
 */
public class PendulumController implements Initializable {

    
    private GraphicsContext gc;
    public final static double g = 9.8; 
    private double length = 200;
    private double angularFrequency = Math.sqrt(length/g);
    private double maxAngle = Math.PI/4;
    private double angle = 0;
    double currentRate = 1;
    
    
    EventHandler<ActionEvent> onFinished = this::handleAnimationUpdate;
    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2), onFinished));
    
    @FXML
    Canvas canvas;
    @FXML
    ColorPicker colorPicker;

    @FXML
    Button playbtn, pausebtn, stopbtn, graphbtn;

    @FXML
    Slider lenghtSlider, dampingSlider;
    

    double lenght, damping;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gc  = canvas.getGraphicsContext2D();
        draw(gc);
        
        playbtn.setDisable(false);
        pausebtn.setDisable(true);
        stopbtn.setDisable(true);
        
       intitializeBtns(animate()); 
        
    
        lenghtSlider.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                setLength(lenghtSlider.getValue());
            }
        });
       
       
    
    }
    public void draw(GraphicsContext gc){
        final double startX = canvas.getWidth()/2;
        final double startY = 50;
        
        
        double lastX = startX + length * Math.sin(angle);
        double lastY = startY + length * Math.cos(angle);
        
        
        gc.strokeLine(startX, startY, lastX, lastY);
        
        gc.fillOval(lastX-25, lastY-25, 50,50);
        
    }
    
    public Animation animate(){
        timeline.setAutoReverse(true);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setRate(2);
        return timeline;
    }
    
    
    private void handleAnimationUpdate(ActionEvent event) {
        
        gc.clearRect(0, 0, 1200, 800);
        if(maxAngle > 0 && angle <maxAngle){
            angle += 0.01;
            if(angle >= maxAngle){
                maxAngle = -1 * maxAngle;
            }
    
        }else if(maxAngle < 0 & angle > maxAngle){
            angle -= 0.01;
            if(angle <= maxAngle){
                maxAngle = -1 * maxAngle;
            }
        }
        //System.out.println(Math.sqrt(Math.pow((angle/maxAngle), 2)));
        //timeline.setRate(5 - Math.sqrt(Math.pow((angle/maxAngle), 2)));
       // System.out.println(5 - Math.sqrt(Math.pow((angle/maxAngle), 2)));
        draw(gc);
    }
    
    
     
    
    private void intitializeBtns(Animation animation){
         playbtn.setOnAction((e) -> {
            if (pausebtn.isDisable() == true) {
                
                animation.playFrom(Duration.ONE);
                pausebtn.setDisable(false);
            } else {
                animation.play();
                
            }
            pausebtn.setDisable(false);
            stopbtn.setDisable(false);
        });

        pausebtn.setOnAction((e) -> {
            animation.pause();
        });

        stopbtn.setOnAction((e) -> {
            animation.stop();
            pausebtn.setDisable(true);
            setAngle(0);
        });
        colorPicker.setOnAction((e) -> {
            Color newColor = colorPicker.getValue();
            gc.setFill(newColor);
        });
        graphbtn.setOnAction((e) -> {
            GraphControllerPendulum graph = new GraphControllerPendulum(getLength()); //a
        });
    }

    public GraphicsContext getGc() {
        return gc;
    }

    public void setGc(GraphicsContext gc) {
        this.gc = gc;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getAngularFrequency() {
        return angularFrequency;
    }

    public void setAngularFrequency(double angularFrequency) {
        this.angularFrequency = angularFrequency;
    }

    public double getMaxAngle() {
        return maxAngle;
    }

    public void setMaxAngle(double maxAngle) {
        this.maxAngle = maxAngle;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getCurrentRate() {
        return currentRate;
    }

    public void setCurrentRate(double currentRate) {
        this.currentRate = currentRate;
    }

    public EventHandler<ActionEvent> getOnFinished() {
        return onFinished;
    }

    public void setOnFinished(EventHandler<ActionEvent> onFinished) {
        this.onFinished = onFinished;
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public ColorPicker getColorPicker() {
        return colorPicker;
    }

    public void setColorPicker(ColorPicker colorPicker) {
        this.colorPicker = colorPicker;
    }

    public Button getBtnPlay() {
        return playbtn;
    }

    public void setBtnPlay(Button playbtn) {
        this.playbtn = playbtn;
    }

    public Button getPauseBtn() {
        return pausebtn;
    }

    public void setPauseBtn(Button pausebtn) {
        this.pausebtn = pausebtn;
    }

    public Button getStopBtn() {
        return stopbtn;
    }

    public void setStopBtn(Button stopbtn) {
        this.stopbtn = stopbtn;
    }

    public Button getGraphBtn() {
        return graphbtn;
    }

    public void setGraphBtn(Button graphbtn) {
        this.graphbtn = graphbtn;
    }

    public Slider getLenghtSlider() {
        return lenghtSlider;
    }

    public void setLenghtSlider(Slider lenghtSlider) {
        this.lenghtSlider = lenghtSlider;
    }

    public Slider getDampingSlider() {
        return dampingSlider;
    }

    public void setDampingSlider(Slider dampingSlider) {
        this.dampingSlider = dampingSlider;
    }

    public double getLenght() {
        return lenght;
    }

    public void setLenght(double lenght) {
        this.lenght = lenght;
    }

    public double getDamping() {
        return damping;
    }

    public void setDamping(double damping) {
        this.damping = damping;
    }
    
}
