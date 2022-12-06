
package edu.vanier.ueps.graphs.controllers;

import edu.vanier.ueps.simulations.controller.SimulationSHMController;
import edu.vanier.ueps.simulations.functions.SimulationSHM;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GraphController{
    
    
    final int WINDOW_SIZE = 10;
    private ScheduledExecutorService scheduledExecutorService;
    
    double amplitude;
    double SpringStiffness;
    double mass;
    double time;
    
    ArrayList values = new ArrayList();
    double xPos;
    
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
    
    public GraphController(double amplitude,double SpringStiffness,double mass, double time){
        initialize();
        this.amplitude = amplitude;
        this.SpringStiffness = SpringStiffness;
        this.mass = mass; 
        this.time = time;
    }
    
    
    public void initialize() {
        Stage primaryStage = new Stage();

    
    primaryStage.setTitle("SHM Graph");

        //defining the axes
        final CategoryAxis xAxis = new CategoryAxis(); // we are gonna plot against time
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time(s)");
        xAxis.setAnimated(false); // axis animations are removed
        yAxis.setLabel("X position");
        yAxis.setAnimated(false); // axis animations are removed

        //creating the line chart with two axis created above
        final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("SHM Graph");
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

        // this is used to display time in HH:mm:ss format
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

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
                series.getData().add(new XYChart.Data<>(simpleDateFormat.format(timeInS), test2));

                if (series.getData().size() > WINDOW_SIZE)
                    series.getData().remove(0);
            });
        }, 0, 1, TimeUnit.SECONDS);
    }

    //SHM formula x=Acos(wt+phi)
    public double takeComponents(){
        double angularVelocity = Math.sqrt(this.SpringStiffness/this.mass);
        currentTime = timer.getTime();
        double xPosition = this.amplitude*Math.cos(angularVelocity*currentTime);
        return xPosition;
    }
            
    }


