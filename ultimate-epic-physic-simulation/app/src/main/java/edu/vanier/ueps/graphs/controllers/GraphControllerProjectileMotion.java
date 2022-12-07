/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.ueps.graphs.controllers;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

/**
 *
 * @author 16162829
 */
public class GraphControllerProjectileMotion {
    final int WINDOW_SIZE = 10;
    private ScheduledExecutorService scheduledExecutorService;
    
    Timer timer = new Timer();
    String timeInS;
    double currentTime = 0;
    double gravitational, velocityY, angle;
/**
 * It will help to stop timer when window is closed
 * @return timer animation
 */
    public Timer getTimer() {
        return timer;
    }

    public GraphControllerProjectileMotion(double gravitational, double velocityY, Double angle) {
        this.angle = angle;
        this.gravitational = gravitational;
        this.velocityY = velocityY;
        initialize();
    }
     // posY = initialY + initialV*t*sin(teta) - 1/2*g*t^2
    public double takeComponents(){
        currentTime = timer.getTime();
        double positionY = velocityY * currentTime - (0.5 * gravitational * (currentTime * currentTime));
        return positionY;
    }
    
    public void initialize() {
        Stage primaryStage = new Stage();
        timer.startTimer();

    
    primaryStage.setTitle("Projectile Motion Graph");

        //defining the axes
        final CategoryAxis xAxis = new CategoryAxis(); // we are gonna plot against time
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time(s)");
        xAxis.setAnimated(false); // axis animations are removed
        yAxis.setLabel("Position Y");
        yAxis.setAnimated(false); // axis animations are removed

        //creating the line chart with two axis created above
        final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Projectile Motion Graph");
        lineChart.setAnimated(false); // disable animations

        //defining a series to display data
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Data Series");

        // add series to chart
        lineChart.getData().add(series);

        // setup scene
        Scene scene = new Scene(lineChart, 800, 600);
        primaryStage.setScene(scene);

        // show the stage
        primaryStage.show();

        // setup a scheduled executor to periodically put data into the chart
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        // put data onto graph per second
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            
            

            // Update the chart
            Platform.runLater(() -> {
                double test2 = takeComponents();
                
                System.out.println("current time with timer = " + currentTime);
                
                // get current time
                timeInS = ""+this.currentTime;
                // put test2 number with current time
                series.getData().add(new XYChart.Data<>(timeInS, test2));

                if (series.getData().size() > WINDOW_SIZE)
                    series.getData().remove(0);
            });
        }, 0, 1, TimeUnit.SECONDS);
    }
}
