/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.ueps.driver;

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
        Scene scene = new Scene(root, 30, 30);
        Rectangle rct = new Rectangle(30,31);
        SimulationSHM sim = new SimulationSHM(5);
        
        Timeline t = sim.sim(rct, Duration.seconds(45));
        
        t.setAutoReverse(true);
        t.setCycleCount(5);
        t.play();
        
        
        root.getChildren().add(rct);
        
        primaryStage.setTitle("Driver test");
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        
        primaryStage.show();
    }

    public static void main(String[] args) {
        System.out.println("hi");
    }
}
