/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.ueps.ui;

import edu.vanier.ueps.simulations.controller.ProjectileMotionController;
import edu.vanier.ueps.simulations.controller.SimulationSHMController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Mijan
 */
public class UIProjectileMotion extends Stage {

    public UIProjectileMotion() {
        this.initModality(Modality.APPLICATION_MODAL);
        this.setTitle("Projectile Motion Simulator");
        try {
            makeComponenets();
        } catch (IOException e) {
            System.out.println(e);
        }
        
        
        
    }
    
    private void makeComponenets() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/uiProjectileMotion.fxml"));

        loader.setController(new ProjectileMotionController());

        Pane root = loader.load();

        Scene sc = new Scene(root,900,600);
        
        Image img = new Image("/image/logo.jpg");
        
        this.getIcons().add(img);

        this.setScene(sc);
        this.setResizable(false);

        this.show();
    }
    
    
    
}
