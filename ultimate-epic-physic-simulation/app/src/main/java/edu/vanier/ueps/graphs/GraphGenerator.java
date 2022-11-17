
package edu.vanier.ueps.graphs;

import edu.vanier.ueps.graphs.controllers.GraphController;
import edu.vanier.ueps.simulations.controller.SimulationSHMController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GraphGenerator extends Stage{

    public GraphGenerator() {
        this.initModality(Modality.APPLICATION_MODAL);
        this.setTitle("Graph");
        try {
            makeComponents(); //e.printStackTrace();
        } catch (Exception ex) {
            System.out.println(ex);
            System.out.println("Problem with makecomponents");
        }
        //this.setResizable(false);
    
    }
    
    private void makeComponents() throws IOException{
        
       FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/graphGenerator.fxml"));
       
       loader.setController(new GraphController());
       
       Pane root = loader.load();
       System.out.println("makecomponents works");
       
       Scene sc = new Scene(root);
       
       this.setScene(sc);
       
       this.show();
    }
    
    
}
