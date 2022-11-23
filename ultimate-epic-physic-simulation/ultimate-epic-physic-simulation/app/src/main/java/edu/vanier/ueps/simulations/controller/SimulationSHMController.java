package edu.vanier.ueps.simulations.controller;

import edu.vanier.ueps.graphs.GraphGenerator;
import edu.vanier.ueps.simulations.functions.SimulationSHM;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
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
    Slider frictionslider, AmplitudeSlider, PeriodSlider;

    @FXML
    ColorPicker colorPicker;

    Duration originalDuration = Duration.seconds(2);
    Duration duration;

    double amplitude = 100;

    /**
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO: The spring extends on both sides, it should be only extending on the right hand side, to fix

        amplitude = AmplitudeSlider.getValue();
        
        /**
         * Change color of rectangle
         */
        colorPicker.setOnAction((e) -> {
            Color newColor = colorPicker.getValue();
            rect.setFill(newColor);
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

        /**
         * Start of animation
         */
        SimulationSHM shm = new SimulationSHM(rect, amplitude, originalDuration);

        /**
         * Play btn action
         */
        playbtn.setOnAction((e) -> {
            if (pausebtn.isDisable() == true) {
                shm.getShm().playFrom(Duration.ONE);
                pausebtn.setDisable(false);
            } else {
                shm.getShm().play();
            }
            pausebtn.setDisable(false);
            frictionslider.setDisable(true);
        });

        /**
         * Pause btn action
         */
        pausebtn.setOnAction((e) -> {
            shm.getShm().pause();

        });

        /**
         * Stop btn action
         */
        stopbtn.setOnAction((e) -> {
            shm.getShm().stop();
            pausebtn.setDisable(true);
            frictionslider.setDisable(false);
        });

        /**
         * Graph btn action
         */
        graphbtn.setOnAction((e) -> {
            GraphGenerator graph = new GraphGenerator();
        });

        /**
         * When rectangle is clicked and moved, we change its position
         */
        rect.setCursor(Cursor.OPEN_HAND);

        rect.addEventHandler(MouseEvent.MOUSE_PRESSED, (eventMousePressed) -> {
            rect.setCursor(Cursor.CLOSED_HAND);
            amplitude = eventMousePressed.getSceneX() - rect.getWidth()/2;
            rect.setLayoutX(amplitude);

            //When its closed and pressed make it drags
            //It has to change the Amplitude of the path : the LineTo i believe
            rect.addEventHandler(MouseEvent.MOUSE_DRAGGED, (eventMouseDragged) -> {
                amplitude = eventMouseDragged.getSceneX() - rect.getWidth()/2;
                rect.setLayoutX(amplitude);
                AmplitudeSlider.setDisable(true);
            });
        });

        rect.addEventHandler(MouseEvent.MOUSE_RELEASED, (e) -> {
            rect.setCursor(Cursor.OPEN_HAND);
            //Animate the position of the rectangle to the position of the mouse when released
        }
        );
        frictionslider.valueProperty().addListener(new ChangeListener<Number>() {

            int myfriction;

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                myfriction = (int) frictionslider.getValue();
                //TODO: impliment the logic required to the animation when there is friction

            }
        });
    }

}
