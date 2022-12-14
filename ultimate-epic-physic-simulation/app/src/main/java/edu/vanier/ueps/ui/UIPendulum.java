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
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Youssif
 */
public class UIPendulum extends Stage {

    public UIPendulum() {
        this.initModality(Modality.APPLICATION_MODAL);
        this.setTitle("Pendulum Simulator");
        try {
            makeComponents();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void makeComponents() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/pendulumsimui.fxml"));

        PendulumController controller = new PendulumController();

        loader.setController(controller);

        Pane root = loader.load();

        Scene sc = new Scene(root,1000,800);
        
        Image img = new Image("/image/logo.jpg");
        
        this.getIcons().add(img);

        this.setScene(sc);
        
        this.setResizable(false);

        this.show();
    }
}
