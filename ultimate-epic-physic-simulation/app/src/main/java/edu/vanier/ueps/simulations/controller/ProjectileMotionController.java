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
import javafx.scene.layout.Pane;

/**
 *
 * @author Mijan
 */
public class ProjectileMotionController implements Initializable {
    
    @FXML
    Canvas canvas;
    
    @FXML
    Pane pane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        canvas.setLayoutX(1300);
        canvas.setLayoutY(1300);
        
        
        SimulationProjectileMotion pm = new SimulationProjectileMotion(4,0.5/*Math.PI/2*/,canvas);
        pm.setOnTop(pane);
        
        
        pm.startAnimation();
    }
    
}
