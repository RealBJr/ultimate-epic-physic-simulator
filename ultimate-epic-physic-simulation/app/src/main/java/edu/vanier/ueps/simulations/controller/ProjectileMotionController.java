/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.ueps.simulations.controller;

import edu.vanier.ueps.graphs.controllers.GraphControllerProjectileMotion;
import edu.vanier.ueps.simulations.functions.SimulationProjectileMotion;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;

/**
 *
 * @author Mijan
 */
public class ProjectileMotionController implements Initializable {

    Canvas canvas;

    @FXML
    Pane paneContainer;

    @FXML
    Pane paneAnimationArea;

    @FXML
    Pane paneSettings;

    @FXML
    Button playBtn, restartBtn, saveBtn, pauseBtn, graphBtn;

    @FXML
    Slider velocitySlider, angleSlider, gravitationalSlider;

    double gravity, speed, angle, radianAngle, velocityY;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    angleSlider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldNumber, Number newNumber) {

                angle = angleSlider.getValue();

                radianAngle = Math.toRadians(angle);

            }
        });

        velocitySlider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldNumber, Number newNumber) {

                speed = velocitySlider.getValue();

            }
        });
        
        gravitationalSlider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldNumber, Number newNumber) {
                //rendering the gravity to the limit of the animation
                gravity = velocitySlider.getValue()/(10*10);

            }
        });
        
        //paneSettings.
        speed = velocitySlider.getValue();
        angle = angleSlider.getValue();

        radianAngle = Math.toRadians(angle);

        canvas = new Canvas(paneAnimationArea.getPrefWidth(), paneAnimationArea.getPrefHeight());
        paneContainer.getChildren().add(canvas);

        //Put the lines (vectors in front of the canvas: think of pane as a glass into which is graved the vectors
        //think of canvas as a paper that i want as a background
        //if my paper is in front of my glass, it wont be seen, instead, when paper is in the back, it works just fine
        paneContainer.getChildren().get(0).toFront();
        System.out.println("Height of canvas = " + canvas.getHeight() + ",width = " + canvas.getWidth());
        SimulationProjectileMotion pm = new SimulationProjectileMotion(4, Math.PI / 4, canvas);

        pm.setOnTop(paneAnimationArea);

        //here we display the canvas before the animation
        pm.displayCanvas();
        angle = angleSlider.getValue();
        speed = velocitySlider.getValue();
        
        playBtn.setOnMousePressed((e) -> {

            pm.startAnimation();
            disableButtons(true, false, false, false);
        });

        restartBtn.setOnMousePressed((e) -> {
            pm.resetAnimation();
            pm.startAnimation();
            
            disableButtons(true, false, false, false);
        });

        saveBtn.setOnMousePressed((e) -> {
            pm.setGravitationalConstant(gravity);
            pm.setDirection(radianAngle);
            pm.setSpeed(speed);

            System.out.println(pm.getGravitationalConstant());
            System.out.println(pm.getDirection());
            System.out.println(pm.getSpeed());

            disableButtons(false, true, false, true);
                     
            pm.resetAnimation();

        });

        pauseBtn.setOnMousePressed((e) -> {
            pm.pauseAnimation();

            disableButtons(false, false, false, true);
            
        });
        
        graphBtn.setOnMousePressed((e) -> {
            //Making sure the value nedded are gotten
            gravity = pm.getGravitationalConstant();
            velocityY = pm.velocityY(speed);
            radianAngle = pm.getDirection();
            
            GraphControllerProjectileMotion graph = new GraphControllerProjectileMotion(gravity*10*10, velocityY, radianAngle);
            
        });
    }

    private void disableButtons(boolean playBtn, boolean restartButton, boolean saveButton, boolean pauseBtn){
        this.playBtn.setDisable(playBtn);
            this.restartBtn.setDisable(restartButton);
            this.saveBtn.setDisable(saveButton);
            this.pauseBtn.setDisable(pauseBtn);
    }
}
