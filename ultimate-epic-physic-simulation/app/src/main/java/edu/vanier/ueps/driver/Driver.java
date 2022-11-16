/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.ueps.ui;

import edu.vanier.ueps.simulations.functions.SimulationSHM;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author 2158914
 * Trying out the simulations
 */
public class Driver extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
       
        Pane root = new Pane();
        Scene scene = new Scene(root);
        Rectangle rct = new Rectangle(30,31);
        SimulationSHM sim = new SimulationSHM(5);
        
        Timeline t = sim.sim(rct, Duration.seconds(45));
        
        t.setAutoReverse(true);
        t.setCycleCount(5);
        t.play();
        
        
        root.getChildren().add(rct);
        
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main2(String[] args) {
        launch(args);
    }
}
