/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.ueps.driver;

import edu.vanier.ueps.simulations.functions.SimulationProjectileMotion;
import edu.vanier.ueps.simulations.functions.SimulationSHM;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author 2158914
 * Trying out the simulations
 */

public class Driver extends Application{
        Rectangle rect = new Rectangle(200,200);
        double amplitude =0;
        Button b = new Button("reset");
        Button b1 = new Button("Play");

    @Override
    public void start(Stage stage) throws Exception {
        Pane root = new Pane();

        b.setLayoutX(220);
        b.setLayoutY(220);
//        
        b1.setLayoutX(20);
        b1.setLayoutY(220);
//        
//        clickAndDrag();
//            b.setOnMousePressed((e)->{
//            clickAndDrag();
//            System.out.println("clcik and drag = true");
//            });
            

        SimulationProjectileMotion pm = new SimulationProjectileMotion(10,Math.PI/4/*Math.PI/2*/);
        Scene scene = new Scene(root, 300, 300);
        root.getChildren().addAll(pm.getCanvas(),pm.getOnTop(),b1,b);
        
        pm.displayCanvas();
        b1.setOnMousePressed((e)->{
            pm.getAnimation().start();        
        });
        
        b.setOnMousePressed((e)->{
//            pm.getAnimation().stop();
//            pm.getAnimation().getKeyFrames().removeAll(pm.getAnimation().getKeyFrames());
//            pm.getAnimation().setRate(0);
//            stage.close();
            //
            //root.getChildren().removeAll(pm.getCanvas(),pm.getOnTop());
            //pm = new SimulationProjectileMotion(3,Math.PI/4/*Math.PI/2*/);
//            stage.show();
//            root.getChildren().addAll();
            pm.getAnimation().stop();
            
        });        
        stage.setScene(scene);
        stage.show();
         
        //playDamping(rect, Duration.INDEFINITE, 3000, Duration.seconds(2), Math.PI, 5, 5, 5);
    }
    public static void main(String[] args) {
            launch(args);

    }
    //TODO: COPY THIS CODE INTO CONTROLLER SHM; PROBABLY MAKE A RESET BUTTON TO PUT THE THING BACK IN POSITION
    //TODO: FIX THE PARAMETERS AND THE SLIDERS TO IMPACT ANIMATION --> DEPENDS ON YOUSIF IMPLEMENTATION OF SHM
//    private void clickAndDrag() {
//    EventHandler<MouseEvent> eventMousePressed = new EventHandler<MouseEvent>() {
//
//        @Override
//        public void handle(MouseEvent event) {
//            rect.setCursor(Cursor.CLOSED_HAND);
//            rect.setLayoutX(amplitude);
//
//        }
//    };
//    EventHandler<MouseEvent> eventMouseDragged = new EventHandler<MouseEvent>() {
//
//        @Override
//        public void handle(MouseEvent event) {
//            amplitude = event.getSceneX() - rect.getWidth() / 2;
//                rect.setLayoutX(amplitude);
//
//        }
//    };
//    
//        
//            rect.setCursor(Cursor.OPEN_HAND);
//            
//            //When its closed and pressed make it drags
//            //Adding 2 eventHandlers here
//            rect.addEventHandler(MouseEvent.MOUSE_PRESSED, eventMousePressed);
//            
//            rect.addEventHandler(MouseEvent.MOUSE_DRAGGED, eventMouseDragged);
//            
//            //Animate the position of the rectangle to the position of the mouse when released
//            rect.addEventHandler(MouseEvent.MOUSE_RELEASED, (e) -> {
//                
//                    rect.removeEventHandler(MouseEvent.MOUSE_PRESSED, eventMousePressed);
//                    rect.removeEventHandler(MouseEvent.MOUSE_DRAGGED, eventMouseDragged);
//                
//                rect.setCursor(Cursor.DEFAULT);
//                
//                //if playbtn is still of playable tho, don't remove yet
//            });
//
//    System.out.println ("click and drag is enabled? " + b);
//    }
    
}
