
package edu.vanier.ueps.simulations.controller;

import edu.vanier.ueps.ui.UISHM;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class Controller implements Initializable{
    
    @FXML
    Button parachutebtn, shmbtn, pendulumbtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       shmbtn.setOnAction((e)->{
           UISHM shmStage = new UISHM();
       });
    }
    
    
    
}
