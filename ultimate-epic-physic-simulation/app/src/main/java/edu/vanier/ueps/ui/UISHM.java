/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.ueps.ui;

import edu.vanier.ueps.simulations.controller.SimulationSHMController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Youssif
 */
public class UISHM extends Stage{
    
    public UISHM(){
         this.initModality(Modality.APPLICATION_MODAL);
        this.setTitle("Simple Harmonic Motion simulator");
        try {
            makeComponenets(); //e.printStackTrace();
        } catch (Exception ex) {
            System.out.println(ex);
            System.out.println("Problem with makecomponents");
            ex.printStackTrace();
        }
        this.setResizable(false);
    }

    private void makeComponenets() throws IOException{
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/shmsim.fxml"));
       
       loader.setController(new SimulationSHMController());
       
       Pane root = loader.load();
       
       Scene sc = new Scene(root,1200,800);
       
       Image img = new Image("/image/logo.jpg");
        
       this.getIcons().add(img);
       
       this.setScene(sc);
       
       this.show();
    }
    
}
