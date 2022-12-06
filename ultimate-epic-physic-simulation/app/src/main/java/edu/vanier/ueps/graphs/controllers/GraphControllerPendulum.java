/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.ueps.graphs.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
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
public class GraphControllerPendulum {
    
    final int WINDOW_SIZE = 10;
    private ScheduledExecutorService scheduledExecutorService;
    
    double length;
    double gravity= 9.8;
    double maxAngle = 180;
    
    Timer timer = new Timer();
    String timeInS;
    double currentTime = 0;
/**
 * It will help to stop timer when window is closed
 * @return timer animation
 */
    public Timer getTimer() {
        return timer;
    }

    public GraphControllerPendulum(double length) {
        initialize();
        this.length = length;
    }
    //? = ?????(?? + ?) 
    public double takeComponents(){
        double angularVelocity = Math.sqrt(gravity/this.length);
        currentTime = timer.getTime();
        double angle = maxAngle*Math.cos(angularVelocity*currentTime);
        return angle;
    }
    
    public void initialize() {
        Stage primaryStage = new Stage();
        timer.startTimer();

    
    primaryStage.setTitle("Pendulum Graph");

        //defining the axes
        final CategoryAxis xAxis = new CategoryAxis(); // we are gonna plot against time
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time(s)");
        xAxis.setAnimated(false); // axis animations are removed
        yAxis.setLabel("Angle(rad)");
        yAxis.setAnimated(false); // axis animations are removed

        //creating the line chart with two axis created above
        final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Pendulum Graph");
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

        // put dummy data onto graph per second
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
