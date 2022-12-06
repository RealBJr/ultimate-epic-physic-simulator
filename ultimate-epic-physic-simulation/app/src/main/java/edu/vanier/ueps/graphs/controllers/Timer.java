
package edu.vanier.ueps.graphs.controllers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class Timer {
    private double time = 0;
    private Timeline timer;
    private KeyFrame kfs;
    private EventHandler<ActionEvent> onFinished = this::handleOnFinished;

    public Timer() {
        kfs = new KeyFrame(Duration.seconds(1), onFinished);
        timer = new Timeline(kfs);
        timer.setCycleCount(Animation.INDEFINITE);
        
    }

    public void startTimer(){
        this.timer.play();
    }
    public void stopTimer(){
        this.timer.stop();
    }
    
    public double getTime() {
        return this.time;
    }

    public Timeline getTimer() {
        return this.timer;
    }

    private void handleOnFinished(ActionEvent event) {
        this.time++;
    }
    
    
}
