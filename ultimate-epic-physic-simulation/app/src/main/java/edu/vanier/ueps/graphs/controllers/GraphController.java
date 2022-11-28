
package edu.vanier.ueps.graphs.controllers;

import edu.vanier.ueps.simulations.controller.SimulationSHMController;
import edu.vanier.ueps.simulations.functions.SimulationSHM;
import java.net.URL;
import java.text.SimpleDateFormat;
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
    
    public GraphController(){
        initialize();
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

        // put dummy data onto graph per second
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            // get a random integer between 0-10
            Integer random = ThreadLocalRandom.current().nextInt(10);
            int test = 5;
            

            // Update the chart
            Platform.runLater(() -> {
                // get current time
                Date now = new Date();
                // put random number with current time
                series.getData().add(new XYChart.Data<>(simpleDateFormat.format(now), test));

                if (series.getData().size() > WINDOW_SIZE)
                    series.getData().remove(0);
            });
        }, 0, 1, TimeUnit.SECONDS);
    }

    //SHM formula x=Acos(wt+phi)
    public void takeComponents(){
        SimulationSHMController controller = new SimulationSHMController();
        
        double Amplitude = controller.getAmplitudeSlider().getValue();
        //double Period = controller.getPeriodSlider().getValue();
       // double Phi = controller.getPhaseSlider().getValue();
        Duration duration = controller.getTime();
        Double Time = (double)duration.toSeconds();
        //double Angular = 2 * Math.PI / Period;
        //double Xposition = Amplitude*Math.cos(Angular*Time+Phi);
    }
            
    }


