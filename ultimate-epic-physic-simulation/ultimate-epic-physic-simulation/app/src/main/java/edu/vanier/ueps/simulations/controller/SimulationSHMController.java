package edu.vanier.ueps.simulations.controller;

import edu.vanier.ueps.graphs.GraphGenerator;
import edu.vanier.ueps.simulations.functions.SimulationSHM;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 *
 * @author Youssif
 */
public class SimulationSHMController implements Initializable {

    @FXML
    Rectangle rect;

    @FXML
    Node spring;

    @FXML
    Button playbtn, stopbtn, pausebtn, graphbtn;

    @FXML
    Slider frictionslider, AmplitudeSlider, PeriodSlider, PhaseSlider;

    @FXML
    Line linePath;

    @FXML
    ColorPicker colorPicker;

    Duration originalDuration = Duration.seconds(2);
    Duration duration;

    double amplitude = 100;

    //boolean enableClickAndDrag = true;
    public Duration getTime() {
        SimulationSHM simulation = new SimulationSHM(rect, linePath, amplitude, originalDuration);
        Duration Time = simulation.getShm().getCurrentTime();
        return Time;
    }

    public Slider getFrictionslider() {
        return frictionslider;
    }

    public void setFrictionslider(Slider frictionslider) {
        this.frictionslider = frictionslider;
    }

    public Slider getAmplitudeSlider() {
        return AmplitudeSlider;
    }

    public void setAmplitudeSlider(Slider AmplitudeSlider) {
        this.AmplitudeSlider = AmplitudeSlider;
    }

    public Slider getPeriodSlider() {
        return PeriodSlider;
    }

    public void setPeriodSlider(Slider PeriodSlider) {
        this.PeriodSlider = PeriodSlider;
    }

    public Slider getPhaseSlider() {
        return PhaseSlider;
    }

    /**
     *
     * @param location
     * @param resources
     */
    public void setPhaseSlider(Slider PhaseSlider) {
        this.PhaseSlider = PhaseSlider;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        frictionslider.valueProperty().addListener(new ChangeListener<Number>() {
            int myfriction;

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                myfriction = (int) frictionslider.getValue();
                //TODO: impliment the logic required to the animation when there is friction
            }
        });

        /**
         * Change speed of animation
         */
        frictionslider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (frictionslider.getValue() >= 1) {
                    duration = Duration.seconds(originalDuration.toSeconds() * frictionslider.getValue());
                    System.out.println(duration);
                }
            }
        });

        //TODO: The spring extends on both sides, it should be only extending on the right hand side, to fix
        playbtn.setDisable(false);
        pausebtn.setDisable(true);
        stopbtn.setDisable(true);

        System.out.println("playbtn is " + playbtn.isDisable() + " pausebtn is " + pausebtn.isDisable() + " stopbtn is " + stopbtn.isDisable());

        //TODO: make that when you start, it has a neutral Amplitude (preset), when you drag: you cant change the amplitude with slider,
        //when you stop, you can change with slider if you want (basically, one can happen with the other)
        amplitude = AmplitudeSlider.getValue();
        linePath.setVisible(false);
        /**
         * Change color of rectangle
         */
        colorPicker.setOnAction((e) -> {
            Color newColor = colorPicker.getValue();
            rect.setFill(newColor);
        });

        /**
         * Start of animation
         */
        SimulationSHM shm = new SimulationSHM(rect, linePath, amplitude, originalDuration);

        //SO when initialized, clickanddrag should be working
        clickAndDrag();
        /**
         * Play btn action, when u play, click and drag should not be available
         */
        playbtn.setOnAction((e) -> {
            if (pausebtn.isDisable() == true) {
                shm.getShm().playFrom(Duration.ONE);
                pausebtn.setDisable(false);
            } else {
                shm.getShm().play();
            }
            playbtn.setDisable(true);
            pausebtn.setDisable(false);
            stopbtn.setDisable(false);
            frictionslider.setDisable(true);
            System.out.println("When play is clicked playbtn is " + playbtn.isDisable() + " pausebtn is " + pausebtn.isDisable() + " stopbtn is " + stopbtn.isDisable());
        });

        /**
         * Pause btn action
         */
        pausebtn.setOnAction((e) -> {
            shm.getShm().pause();
            playbtn.setDisable(false);
            pausebtn.setDisable(true);
            stopbtn.setDisable(false);
            System.out.println("When pause is clicked playbtn is " + playbtn.isDisable() + " pausebtn is " + pausebtn.isDisable() + " stopbtn is " + stopbtn.isDisable());
        });

        /**
         * Stop btn action
         */
        stopbtn.setOnAction((e) -> {
            shm.getShm().stop();
            playbtn.setDisable(false);
            pausebtn.setDisable(true);
            stopbtn.setDisable(true);
            frictionslider.setDisable(false);
            System.out.println("When stop is clicked playbtn is " + playbtn.isDisable() + " pausebtn is " + pausebtn.isDisable() + " stopbtn is " + stopbtn.isDisable());
            clickAndDrag();
        });

        /**
         * Graph btn action
         */
        graphbtn.setOnAction((e) -> {
            GraphGenerator graph = new GraphGenerator();
        });
    }

    private void clickAndDrag() {
    EventHandler<MouseEvent> eventMousePressed = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
            rect.setCursor(Cursor.CLOSED_HAND);
            rect.setLayoutX(amplitude);

        }
    };
    EventHandler<MouseEvent> eventMouseDragged = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
            amplitude = event.getSceneX() - rect.getWidth() / 2;
                rect.setLayoutX(amplitude);

        }
    };
    
        
            rect.setCursor(Cursor.OPEN_HAND);
            
            //When its closed and pressed make it drags
            //Adding 2 eventHandlers here
            rect.addEventHandler(MouseEvent.MOUSE_PRESSED, eventMousePressed);
            
            rect.addEventHandler(MouseEvent.MOUSE_DRAGGED, eventMouseDragged);
            
            //Animate the position of the rectangle to the position of the mouse when released
            rect.addEventHandler(MouseEvent.MOUSE_RELEASED, (e) -> {
                
                    rect.removeEventHandler(MouseEvent.MOUSE_PRESSED, eventMousePressed);
                    rect.removeEventHandler(MouseEvent.MOUSE_DRAGGED, eventMouseDragged);
                
                rect.setCursor(Cursor.DEFAULT);
                
                //if playbtn is still of playable tho, don't remove yet
            });
    }
} 

