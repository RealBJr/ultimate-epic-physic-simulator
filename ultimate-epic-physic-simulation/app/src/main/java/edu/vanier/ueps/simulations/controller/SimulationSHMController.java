package edu.vanier.ueps.simulations.controller;

import edu.vanier.ueps.graphs.GraphGenerator;
import edu.vanier.ueps.simulations.functions.SimulationSHM;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
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

    /**
     *
     * @param location
     * @param resources
     */
    @FXML
    Rectangle rect;

    @FXML
    ImageView spring;

    @FXML
    Button playbtn, stopbtn, pausebtn, graphbtn, savebtn;

    @FXML
    Slider dampingSlider, AmplitudeSlider, SpringStiffnessSlider,MassSlider ;

    @FXML
    ColorPicker colorPicker;

    Duration originalDuration = Duration.seconds(2);
    Duration duration;

    //variables needed to run the animation
    double amplitude = 1;
    double mass = 0.1;
    double springStiffness = 1;
    double damping;

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
    EventHandler<MouseEvent> eventMouseReleased = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
           rect.setCursor(Cursor.OPEN_HAND);
        }
    };
    EventHandler<MouseEvent> eventMouseEnteredTarget = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
           rect.setCursor(Cursor.OPEN_HAND);
        }
    };
    
    /*public SimulationSHM amplitudeDetecter(SimulationSHM shm){
        SimulationSHM shm2;
        AmplitudeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(shm.getShm().getStatus() == Animation.Status.RUNNING){
                    shm.getShm().pause();
                    shm2= new SimulationSHM(rect, AmplitudeSlider.getValue(), mass, damping, springStiffness);
                    shm.getShm().play();
                }else{
                     shm2 = new SimulationSHM(rect, AmplitudeSlider.getValue(), mass, damping, springStiffness);
                }
                
            }
        });
        return shm2;
    }*/
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
     
        //TODO: The spring extends on both sides, it should be only extending on the right hand side, to fix
        playbtn.setDisable(false);
        pausebtn.setDisable(true);
        stopbtn.setDisable(true);
        
        //TODO: make that when you start, it has a neutral Amplitude (preset), when you drag: you cant change the amplitude with slider,
        //when you stop, you can change with slider if you want (basically, one can happen with the other)
        //amplitude = AmplitudeSlider.getValue();
      
        //SO when initialized, clickanddrag should be working
        clickAndDrag();
        
        /*
        If the user has made changes to the settings the save button will allow him to run the simulation with all the information 
        that he put
        */
        savebtn.setOnAction((e)->{
        SimulationSHM shm = new SimulationSHM(rect, AmplitudeSlider.getValue(), MassSlider.getValue(), dampingSlider.getValue(), SpringStiffnessSlider.getValue(), spring);
        animating(shm);
        });
        
        /*
        If the user didn't decide to press the savebtn the animation will start with its default settings
        */
        SimulationSHM shm = new SimulationSHM(rect, amplitude, mass, damping, springStiffness, spring);
        animating(shm);
        
         
        
        /**
         * Change color of rectangle
         */
        colorPicker.setOnAction((e) -> {
            Color newColor = colorPicker.getValue();
            rect.setFill(newColor);
        });
    }
    
    public void animating(SimulationSHM shm){
         /**
         * Play btn action, when u play, click and drag should not be available
         */
        playbtn.setOnAction((e) -> {
           //System.out.println(amplitude);
            if (pausebtn.isDisable() == true) {
                shm.getShm().playFrom(Duration.ONE);
                pausebtn.setDisable(false);
            } else {
                shm.getShm().play();
            }
            playbtn.setDisable(true);
            pausebtn.setDisable(false);
            stopbtn.setDisable(false);
            savebtn.setDisable(true);
            dampingSlider.setDisable(true);
            //AmplitudeSlider.setDisable(true);
            SpringStiffnessSlider.setDisable(true);
            MassSlider.setDisable(true);
            //System.out.println("When play is clicked playbtn is " + playbtn.isDisable() + " pausebtn is " + pausebtn.isDisable() + " stopbtn is " + stopbtn.isDisable());
        });

        /**
         * Pause btn action
         */
        pausebtn.setOnAction((e) -> {
            shm.getShm().pause();
            playbtn.setDisable(false);
            pausebtn.setDisable(true);
            stopbtn.setDisable(false);
            //System.out.println("When pause is clicked playbtn is " + playbtn.isDisable() + " pausebtn is " + pausebtn.isDisable() + " stopbtn is " + stopbtn.isDisable());
        });

        /**
         * Stop btn action
         */
        stopbtn.setOnAction((e) -> {
            shm.getShm().stop();
            playbtn.setDisable(false);
            pausebtn.setDisable(true);
            stopbtn.setDisable(true);
            savebtn.setDisable(false);
            dampingSlider.setDisable(false);
           // AmplitudeSlider.setDisable(false);
            SpringStiffnessSlider.setDisable(false);
            MassSlider.setDisable(false);
            //System.out.println("When stop is clicked playbtn is " + playbtn.isDisable() + " pausebtn is " + pausebtn.isDisable() + " stopbtn is " + stopbtn.isDisable());
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
     //boolean enableClickAndDrag = true;
    public Duration getTime() {
        SimulationSHM simulation = new SimulationSHM(rect, amplitude, mass, damping, springStiffness, spring);
        Duration Time = simulation.getShm().getCurrentTime();
        return Time;
    }

    public Slider getFrictionslider() {
        return dampingSlider;
    }

    public void setFrictionslider(Slider dampingSlider) {
        this.dampingSlider = dampingSlider;
    }

    public Slider getAmplitudeSlider() {
        return AmplitudeSlider;
    }

    public void setAmplitudeSlider(Slider AmplitudeSlider) {
        this.AmplitudeSlider = AmplitudeSlider;
    }

    public double getAmplitude() {
        return amplitude;
    }

    public void setAmplitude(double amplitude) {
        this.amplitude = amplitude;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getDamping() {
        return damping;
    }

    public void setDamping(double damping) {
        this.damping = damping;
    }
    
    
} 



/* 
        AmplitudeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                SimulationSHMController.this.setAmplitude(50 * AmplitudeSlider.getValue());
                
            }
        });
        
        
        dampingSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (dampingSlider.getValue() >= 1) {
                   // duration = Duration.seconds(originalDuration.toSeconds() * frictionslider.getValue());
                    //System.out.println(duration);
                }
            }
        });*/