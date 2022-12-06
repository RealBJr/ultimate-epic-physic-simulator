/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.ueps.simulations.controller;

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
    Button playBtn, restartBtn, resetBtn, testBtn;

    @FXML
    Slider velocitySlider, angleSlider;

    double gravity, velocity, angle, radianAngle;

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

                velocity = velocitySlider.getValue();

            }
        });
        //paneSettings.
        velocity = velocitySlider.getValue();
        angle = angleSlider.getValue();

        radianAngle = Math.toRadians(angle);

        canvas = new Canvas(paneAnimationArea.getPrefWidth(), paneAnimationArea.getPrefHeight());
        paneContainer.getChildren().add(canvas);

        //Put the lines (vectors in front of the canvas: think of pane as a glass into which is graved the vectors
        //think of canvas as a paper that i want as a background
        //if my paper is in front of my glass, it wont be seen, instead, when paper is in the back, it works just fine
        paneContainer.getChildren().get(0).toFront();
        System.out.println("Height of canvas = " + canvas.getHeight() + ",width = " + canvas.getWidth());
        SimulationProjectileMotion pm = new SimulationProjectileMotion(4, Math.PI / 2, canvas);

        pm.setOnTop(paneAnimationArea);

        //pm.setInitialPosY(300);
        pm.displayCanvas();
        angle = angleSlider.getValue();
        velocity = velocitySlider.getValue();
        playBtn.setOnMousePressed((e) -> {

            pm.startAnimation();

        });

        restartBtn.setOnMousePressed((e) -> {

            pm.resetAnimation();

        });

        resetBtn.setOnMousePressed((e) -> {
            pm.setGravitationalConstant(0.01);
            pm.setDirection((Math.PI) / 2);
            pm.setSpeed(4);

            System.out.println(pm.getGravitationalConstant());
            System.out.println(pm.getDirection());
            System.out.println(pm.getSpeed());

            pm.resetAnimation();

        });

        testBtn.setOnMousePressed((e) -> {
            pm.setDirection(radianAngle);
            pm.setSpeed(velocity);
            
            System.out.println(pm.getDirection());
            System.out.println(pm.getSpeed());

        });
    }

}
