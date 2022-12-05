/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.ueps.simulations.controller;

import edu.vanier.ueps.simulations.functions.SimulationProjectileMotion;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
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
    Button playBtn , restartBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //paneSettings.
        canvas = new Canvas(paneAnimationArea.getPrefWidth(), paneAnimationArea.getPrefHeight());
        paneContainer.getChildren().add(canvas);
        
        //Put the lines (vectors in front of the canvas: think of pane as a glass into which is graved the vectors
        //think of canvas as a paper that i want as a background
        //if my paper is in front of my glass, it wont be seen, instead, when paper is in the back, it works just fine
        
        paneContainer.getChildren().get(0).toFront();
        System.out.println("Height of canvas = "+canvas.getHeight() + ",width = " + canvas.getWidth());
        SimulationProjectileMotion pm = new SimulationProjectileMotion(4,33/*Math.PI/2*/,canvas);
        pm.setOnTop(paneAnimationArea);
        
        //pm.setInitialPosY(300);
        
        pm.displayCanvas();
        
        
        playBtn.setOnMousePressed((e)->{
            pm.startAnimation();
        });
        
        restartBtn.setOnMousePressed((e)->{

            pm.resetAnimation();
            
        }); 
    }
    
}
