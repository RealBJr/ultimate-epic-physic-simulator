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
    Pane animationContainer;

    @FXML
    Button playbtn, pausebtn, graphbtn, savebtn, resetbtn;

    @FXML
    Slider dampingSlider, AmplitudeSlider, SpringStiffnessSlider,MassSlider ;

    @FXML
    ColorPicker colorPicker;
    
    @FXML
    Canvas canvas;
    
    @FXML
    Rectangle rect;
    
    GraphicsContext gc;
    
    Line centralLine = new Line(0,0,0,50);
     
    private final EventHandler<MouseEvent> eventMousePressed = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
            rect.setCursor(Cursor.CLOSED_HAND);
            //rect.setLayoutX(amplitude);
            //rect.setLayoutX(rect.translateXProperty().getValue());
            //rect.setTranslateX(rect.translateXProperty().getValue());
        }
    };
    
    private final EventHandler<MouseEvent> eventMouseDragged = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
            rect.setLayoutX(event.getSceneX() - rect.getWidth() / 2);
            /*
            drawMovingSpring(0);
            System.out.println(rect.getLayoutX());
            amplitude = (rect.getLayoutX() - centralLine.getLayoutX())/50;
            SimulationSHM shm = new SimulationSHM();
            Animation shmHandled = shm.bringToCenter(rect, amplitude, mass, damping, springStiffness, centralLine.getLayoutX());
            animate(shmHandled);*/
        }
    };
    
    private final EventHandler<MouseEvent> eventMouseReleased = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
           rect.setCursor(Cursor.OPEN_HAND);
        }
    };
    private final EventHandler<MouseEvent> eventMouseEnteredTarget = new EventHandler<MouseEvent>() {

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
        centralLine.setLayoutX(rect.getLayoutX()  + rect.getWidth()/2);
        /**
         * Setting up the graphics context for the canvas and drawing the initial spring
         */
        canvas.setHeight(animationContainer.getPrefHeight());
        canvas.setWidth(animationContainer.getPrefWidth());
        gc = canvas.getGraphicsContext2D();
        
        drawMovingSpring(0);
       
        playbtn.setDisable(false);
        pausebtn.setDisable(true);
        
        /**
         * Allow user to click on the rectangle and place it where ever he wants on the x axis by dragging it
         */
        clickAndDrag();
        
        shm = new SimulationSHM(rect,amplitude, mass, damping, springStiffness);
        animate(shm.getShm());
        
        /*
        *save the settings
        */
        savebtn.setOnAction((e)->{
        SimulationSHM shm = new SimulationSHM(rect, AmplitudeSlider.getValue(), MassSlider.getValue(), dampingSlider.getValue(), SpringStiffnessSlider.getValue());
        animate(shm.getShm());
        });
        
        /**
         * Change color of rectangle
         */
        colorPicker.setOnAction((e) -> {
            Color newColor = colorPicker.getValue();
            rect.setFill(newColor);
        });
        
        /**
         * During animation the spring has to be adapted to the position of the rectangle, therefore we have added a listener to the rectangle's 
         * translateXProperty to draw the spring every time it changes position
         */
        rect.translateXProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                drawMovingSpring(rect.getTranslateX());
            }
        });
        
        rect.layoutXProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                drawMovingSpring(0);
                amplitude = (rect.getLayoutX() - centralLine.getLayoutX())/50;
            }
        });
    }
    
    /**
     * Animation of spring and mass
     * @param animation 
     */
    public void animate(Animation animation){
        playbtn.setOnAction((e) -> {
           //rect.setVisible(true);
            if (pausebtn.isDisable() == true) {
                
                animation.playFrom(Duration.ONE);
                pausebtn.setDisable(false);
                disableSliders(true);
            } else {
                animation.play();
                
            }
            pausebtn.setDisable(false);
            disableClickAndDrag();
            disableSliders(true);
            //enabling(true,false,false);
        });

        pausebtn.setOnAction((e) -> {
            animation.pause();
            //enabling(false,true,false);
            disableSliders(true);
        });

        
        graphbtn.setOnAction((e) -> {
            GraphController graph = new GraphController(AmplitudeSlider.getValue(),SpringStiffnessSlider.getValue(),MassSlider.getValue(), getTime().toSeconds());
            
        });
        
        resetbtn.setOnAction((e)->{
            AmplitudeSlider.setValue(5);
            MassSlider.setValue(6);
            dampingSlider.setValue(0);
            SpringStiffnessSlider.setValue(6);
            
            playbtn.setDisable(false);
            pausebtn.setDisable(true);
            
            animation.stop();
            
            //setting it back to its original value and then changing translateX to 0
            rect.setLayoutX(460);
            rect.setTranslateX(0);
            drawMovingSpring(0);
            
            SimulationSHM shm = new SimulationSHM(rect, 5, 6, 0, 6);
            animate(shm.getShm());
            clickAndDrag();
            disableSliders(false);
        });
    }
    
    /**
     * Disable or enable sliders
     * @param disable 
     */
    public void disableSliders(boolean disable){
        dampingSlider.setDisable(disable);
        AmplitudeSlider.setDisable(disable);
        SpringStiffnessSlider.setDisable(disable);
        MassSlider.setDisable(disable);
    }
    
    /**
     * Animation for spring
     * @param transitionX 
     */
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
    
    /**
     * Enabling click and Drag
     */
    private void clickAndDrag() {
            rect.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, eventMouseEnteredTarget);
            
            //When its closed and pressed make it drags
            //Adding 2 eventHandlers here
            rect.addEventHandler(MouseEvent.MOUSE_PRESSED, eventMousePressed);
            
            rect.addEventHandler(MouseEvent.MOUSE_DRAGGED, eventMouseDragged);
            
            //Animate the position of the rectangle to the position of the mouse when released
            rect.addEventHandler(MouseEvent.MOUSE_RELEASED, eventMouseReleased);       
    }
    
    /**
     * Disabling click and drag
     */
    private void disableClickAndDrag() {
        rect.cursorProperty().set(Cursor.DEFAULT);
        rect.removeEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, eventMouseEnteredTarget);
        rect.removeEventHandler(MouseEvent.MOUSE_PRESSED, eventMousePressed);
        rect.removeEventHandler(MouseEvent.MOUSE_DRAGGED, eventMouseDragged);
        rect.removeEventHandler(MouseEvent.MOUSE_RELEASED, eventMouseReleased);   
    }

    /**
     * 
     * @return time: time passed since beginning of animation, to graph
     */
    public Duration getTime() {
        SimulationSHM simulation = new SimulationSHM(rect, amplitude, mass, damping, springStiffness);
        Duration Time = simulation.getShm().getCurrentTime();
        return Time;
    }
    
    //Getters and setters
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