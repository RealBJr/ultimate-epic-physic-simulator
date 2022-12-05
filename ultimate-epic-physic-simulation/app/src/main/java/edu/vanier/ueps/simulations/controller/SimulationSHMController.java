package edu.vanier.ueps.simulations.controller;

import edu.vanier.ueps.graphs.controllers.GraphController;
import edu.vanier.ueps.simulations.functions.SimulationSHM;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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
    Pane animationContainer;

    @FXML
    Button playbtn, stopbtn, pausebtn, graphbtn, savebtn;

    @FXML
    Slider dampingSlider, AmplitudeSlider, SpringStiffnessSlider,MassSlider ;

    @FXML
    ColorPicker colorPicker;
    
    @FXML
    Canvas canvas;
    
    @FXML
    Rectangle rect;
    
     GraphicsContext gc;
     
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
    
    //variables needed to run the animation
    double amplitude = 5;
    double mass = 6;
    double springStiffness = 6;
    double damping = 0;
    
    SimulationSHM shm; 
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        canvas.setHeight(animationContainer.getPrefHeight());
        canvas.setWidth(animationContainer.getPrefWidth());
        gc = canvas.getGraphicsContext2D();
        drawMovingSpring(0);
        //TODO: The spring extends on both sides, it should be only extending on the right hand side, to fix
        /*playbtn.setDisable(false);
        pausebtn.setDisable(true);
        stopbtn.setDisable(true);*/
        
        shm = new SimulationSHM(rect,amplitude, mass, damping, springStiffness);
       // initializeBtns(shm);
        animate(shm.getShm());
        
        savebtn.setOnAction((e)->{
        SimulationSHM shm = new SimulationSHM(rect, AmplitudeSlider.getValue(), MassSlider.getValue(), dampingSlider.getValue(), SpringStiffnessSlider.getValue());
        //initializeBtns(shm);
        animate(shm.getShm());
        });
        
        
        rect.translateXProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                drawMovingSpring(rect.getTranslateX());
            }
            
        });
        
        /**
         * Change color of rectangle
         */
        colorPicker.setOnAction((e) -> {
            Color newColor = colorPicker.getValue();
            rect.setFill(newColor);
        });
    }
    
    
    public void animate(Animation animation){
        playbtn.setOnAction((e) -> {
           //rect.setVisible(true);
            if (pausebtn.isDisable() == true) {
                
                animation.playFrom(Duration.ONE);
                pausebtn.setDisable(false);
                Duration duration = animation.getCurrentTime();
                Double time = (Double) duration.toSeconds()/60;
                //rect.setVisible(true);
            } else {
                animation.play();
                
            }
            pausebtn.setDisable(false);
            //enabling(true,false,false);
        });

        pausebtn.setOnAction((e) -> {
            animation.pause();
            //enabling(false,true,false);
        });

        stopbtn.setOnAction((e) -> {
            animation.stop();
            pausebtn.setDisable(true);
            //enabling(false,false,true);
        });
    }
    private void enabling(boolean playbtnisClicked, boolean pausebtnisClicked, boolean stopbtnClicked){
           if(playbtnisClicked){
            playbtn.setDisable(true);
            pausebtn.setDisable(false);
            stopbtn.setDisable(false);
            savebtn.setDisable(true);
            dampingSlider.setDisable(true);
            AmplitudeSlider.setDisable(true);
            SpringStiffnessSlider.setDisable(true);
            MassSlider.setDisable(true);
           }else if(pausebtnisClicked){
            playbtn.setDisable(false);
            pausebtn.setDisable(true);
            stopbtn.setDisable(false);
           }else if(stopbtnClicked){
            playbtn.setDisable(false);
            pausebtn.setDisable(true);
            stopbtn.setDisable(true);
            savebtn.setDisable(false);
            dampingSlider.setDisable(false);
            AmplitudeSlider.setDisable(false);
            SpringStiffnessSlider.setDisable(false);
            MassSlider.setDisable(false);
           }

    }
    private void initializeBtns(final SimulationSHM shm){
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
            AmplitudeSlider.setDisable(true);
            SpringStiffnessSlider.setDisable(true);
            MassSlider.setDisable(true);
            
            rect.cursorProperty().set(Cursor.DEFAULT);
            rect.removeEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, eventMouseEnteredTarget);
            rect.removeEventHandler(MouseEvent.MOUSE_PRESSED, eventMousePressed);
            rect.removeEventHandler(MouseEvent.MOUSE_DRAGGED, eventMouseDragged);
            rect.removeEventHandler(MouseEvent.MOUSE_RELEASED, eventMouseReleased);

        });

        /**
         * Pause btn action
         */
        pausebtn.setOnAction((e) -> {
            shm.getShm().pause();
            playbtn.setDisable(false);
            pausebtn.setDisable(true);
            stopbtn.setDisable(false);
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
            AmplitudeSlider.setDisable(false);
            SpringStiffnessSlider.setDisable(false);
            MassSlider.setDisable(false);
            //System.out.println("When stop is clicked playbtn is " + playbtn.isDisable() + " pausebtn is " + pausebtn.isDisable() + " stopbtn is " + stopbtn.isDisable());
            clickAndDrag();
        });

        /**
         * Graph btn action
         */
        graphbtn.setOnAction((e) -> {
            GraphController graph = new GraphController(AmplitudeSlider.getValue(),SpringStiffnessSlider.getValue(),MassSlider.getValue(), getTime().toSeconds());
        });
        

        /**
         * Graph btn action
         */
        /*graphbtn.setOnAction((e) -> {
            GraphController graph = new GraphController(AmplitudeSlider.getValue(),SpringStiffnessSlider.getValue(),MassSlider.getValue(), getTime().toSeconds());
        });*/
    }
    
    public void drawMovingSpring(double transitionX){
        
        double x = rect.getLayoutX() + transitionX;
        double y = rect.getLayoutY();
        final int NUMBER_LINES = 30;
        final double LENGTH = 100;
        
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        
        gc.setStroke(Color.RED);
        gc.setFill(Color.BLUE);   
        
        double width = (x-20)/15.5;
        double height = Math.sqrt(LENGTH*LENGTH - width*width);
        
        double startingX = x;
        double startingY = y + rect.getWidth()/2;
        
        double LastX = x - 20;
        double LastY = startingY;        
        //First Line
        gc.strokeLine(startingX, startingY, LastX, LastY );
        
        gc.strokeLine(LastX, LastY, LastX - width/2, LastY + height/2);
        
        LastX = LastX - width/2;
        LastY = LastY + height/2;
        
        for(int i = 1; i<=NUMBER_LINES; i++){
             gc.strokeLine(LastX, LastY, LastX-width/2, LastY + Math.pow(-1, i)* height);
             LastX = LastX-width/2;
             LastY = LastY + Math.pow(-1, i)* height;
        }
    }
    
    private void clickAndDrag() {
            
            rect.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, eventMouseEnteredTarget);
            
            //When its closed and pressed make it drags
            //Adding 2 eventHandlers here
            rect.addEventHandler(MouseEvent.MOUSE_PRESSED, eventMousePressed);
            
            rect.addEventHandler(MouseEvent.MOUSE_DRAGGED, eventMouseDragged);
            
            //Animate the position of the rectangle to the position of the mouse when released
            rect.addEventHandler(MouseEvent.MOUSE_RELEASED, eventMouseReleased);
            
            
    }

    public Duration getTime() {
        SimulationSHM simulation = new SimulationSHM(rect, amplitude, mass, damping, springStiffness);
        Duration Time = simulation.getShm().getCurrentTime();
        return Time;
    }
    
    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public Button getPlaybtn() {
        return playbtn;
    }

    public void setPlaybtn(Button playbtn) {
        this.playbtn = playbtn;
    }

    public Button getStopbtn() {
        return stopbtn;
    }

    public void setStopbtn(Button stopbtn) {
        this.stopbtn = stopbtn;
    }

    public Button getPausebtn() {
        return pausebtn;
    }

    public void setPausebtn(Button pausebtn) {
        this.pausebtn = pausebtn;
    }

    public Button getGraphbtn() {
        return graphbtn;
    }

    public void setGraphbtn(Button graphbtn) {
        this.graphbtn = graphbtn;
    }

    public Button getSavebtn() {
        return savebtn;
    }

    public void setSavebtn(Button savebtn) {
        this.savebtn = savebtn;
    }

    public Slider getDampingSlider() {
        return dampingSlider;
    }

    public void setDampingSlider(Slider dampingSlider) {
        this.dampingSlider = dampingSlider;
    }

    public Slider getSpringStiffnessSlider() {
        return SpringStiffnessSlider;
    }

    public void setSpringStiffnessSlider(Slider SpringStiffnessSlider) {
        this.SpringStiffnessSlider = SpringStiffnessSlider;
    }

    public Slider getMassSlider() {
        return MassSlider;
    }

    public void setMassSlider(Slider MassSlider) {
        this.MassSlider = MassSlider;
    }

    public ColorPicker getColorPicker() {
        return colorPicker;
    }

    public void setColorPicker(ColorPicker colorPicker) {
        this.colorPicker = colorPicker;
    }
    public double getSpringStiffness() {
        return springStiffness;
    }

    public void setSpringStiffness(double springStiffness) {
        this.springStiffness = springStiffness;
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