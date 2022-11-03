/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.ueps.simulations.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 *
 * @author Youssif
 */
public class SimulationSHMController implements Initializable{
    @FXML
    Rectangle rect;
    
    @FXML
    Node spring;
    
    @FXML
    Button playbtn, stopbtn, pausebtn;
    
    @FXML
    Slider frictionslider;
    
    @FXML
    ColorPicker colorPicker;        
           
    
    
    
    Duration duration = Duration.seconds(2);
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO: The spring extends on both sides, it should be only extending on the right hand side, to fix
        
        colorPicker.setOnAction((e)->{
           Color newColor = colorPicker.getValue();
           rect.setFill(newColor);
       });
        
         frictionslider.valueProperty().addListener(new ChangeListener<Number>() {
         public void changed(ObservableValue <?extends Number>observable, Number oldValue, Number newValue){
             duration = duration * frictionslider.getValue();
         }
      });
        
        
        
        
        
        
        TranslateTransition tran = new TranslateTransition(duration, rect);
        tran.setByX(300f);
        tran.setCycleCount(Animation.INDEFINITE);
        tran.setAutoReverse(true);
        tran.setInterpolator(Interpolator.LINEAR);
        
        ScaleTransition tran2 = new ScaleTransition(duration, spring);
        tran2.setByX(5.5);
        
        tran2.setCycleCount(Animation.INDEFINITE);
        tran2.setAutoReverse(true);
        tran2.setInterpolator(Interpolator.LINEAR);
        
        System.out.println(tran.getDuration()+"here we are in initialize of controller");
        
        playbtn.setOnAction((e)->{
            if(pausebtn.isDisable() == true){
            tran.playFrom(Duration.ONE);
            tran2.playFrom(Duration.ONE);
            pausebtn.setDisable(false);
            }else{
            tran.play();
            tran2.play();
            }
            pausebtn.setDisable(false);
            frictionslider.setDisable(true);
        });
        
        pausebtn.setOnAction((e)->{
            tran.pause();
            tran2.pause();
        });
        
        stopbtn.setOnAction((e)->{
            tran.pause();
            tran2.pause();
            pausebtn.setDisable(true);
            frictionslider.setDisable(false);
        });
        
        frictionslider.valueProperty().addListener(new ChangeListener<Number>(){
            
            int myfriction;
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                myfriction = (int) frictionslider.getValue();
                //TODO: impliment the logic required to the animation when there is friction
                
            }
        });
    }
   

   
    
    
}