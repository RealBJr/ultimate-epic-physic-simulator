package edu.vanier.ueps.simulations.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Path;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

/**
 *
 * @author Youssif
 */
public class PendulumController implements Initializable {

    @FXML
    Circle circle;

    @FXML
    Line string;

    @FXML
    Path arcPath;

    @FXML
    ColorPicker colorPicker;

    @FXML
    Button playBtn, pauseBtn, stopBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colorPicker.setOnAction((e) -> {
            Color newColor = colorPicker.getValue();
            circle.setFill(newColor);
        });

        final Rotate rotate = new Rotate(90);

        string.getTransforms().add(rotate);

        final Timeline secondTime = new Timeline(
                new KeyFrame(Duration.seconds(1.0),
                        new KeyValue(rotate.angleProperty(), -90, Interpolator.EASE_BOTH))
        );

        secondTime.setAutoReverse(true);
        secondTime.setCycleCount(Timeline.INDEFINITE);

        final PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.seconds(1.0));
        pathTransition.setPath(arcPath);
        pathTransition.setNode(circle);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(Timeline.INDEFINITE);
        pathTransition.setAutoReverse(true);

        

    }
}
