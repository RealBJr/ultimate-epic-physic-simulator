/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.ueps.ui;

import edu.vanier.ueps.simulations.controller.PendulumController;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Youssif
 */
public class UIPendulum extends Stage{
    public UIPendulum() {
        this.initModality(Modality.APPLICATION_MODAL);
        this.setTitle("Simple Harmonic Motion simulator");
        try{
        makeComponents();
        }catch(IOException e){
            System.out.println(e);
        }
    }

    private void makeComponents() throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/pendulumdemo.fxml"));
       
    PendulumController controller = new PendulumController();
       
    loader.setController(controller);
       
    Pane root = loader.load();
    
    Scene sc = new Scene(root);
    
    this.setScene(sc);
    
    this.show();
    }
}
