/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.ueps.simulations.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Path;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

/**
 *
 * @author Youssif
 */
public class PendulumController implements Initializable{
    @FXML
    Circle circle;
    
    @FXML
    Line string;
    
    @FXML
    Path arcPath;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
        final Rotate rotate = new Rotate(90);
        
        string.getTransforms().add(rotate);
        
        final Timeline secondTime = new Timeline(
                  new KeyFrame(Duration.seconds(1.0), new KeyValue(rotate.angleProperty(),-90, Interpolator.EASE_BOTH))
        );

        secondTime.setAutoReverse(true);
        secondTime.setCycleCount(Timeline.INDEFINITE);
        
        
        final PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.seconds(1.0));
        pathTransition.setPath(arcPath);
        pathTransition.setNode(circle);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(Timeline.INDEFINITE);
        pathTransition.setAutoReverse(true);
        pathTransition.play();
        
        secondTime.play();
    }
}
