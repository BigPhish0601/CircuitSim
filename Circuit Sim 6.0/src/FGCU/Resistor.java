package FGCU;

import com.jfoenix.controls.JFXTextField;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;


public class Resistor extends Component {

    public double resistance = 100; //in ohms

    Pane firstBand = new Pane();
    Pane secondBand = new Pane();
    Pane thirdBand = new Pane();
    Pane multiplierPane = new Pane();
    Pane tolerancePane = new Pane();

    Label componentNameLabel;
    Label currentLabel;
    JFXTextField currentTextField;
    Label voltageLabel;
    JFXTextField voltageTextField;
    Label resistanceLabel;
    JFXTextField resistanceTextField;
    Label powerLabel;
    JFXTextField powerTextField;

    private List<Node> elements = new ArrayList<>();



    /*
    Band Color CSS Hex Reference
    Black #000000
    Brown #a6692b
    Red #ff0000
    Orange #ffa500
    Yellow #ffff00
    Green #00ff00
    Blue #0066ff
    Violet #cc0099
    Grey #808080
    White #ffffff
    Gold #e6c300
    Silver #f2f2f2

     */

    //private Text


    public Resistor() {
        super("Resistor");
        firstBand.setStyle("-fx-background-color: #a6692b");
        secondBand.setStyle("-fx-background-color: #000000");
        thirdBand.setStyle("-fx-background-color: #000000");
        multiplierPane.setStyle("-fx-background-color: #000000");
        tolerancePane.setStyle("-fx-background-color: #a6692b");

        componentNameLabel = new Label("RESISTOR");
        componentNameLabel.setFont(new Font(40));
        componentNameLabel.setAlignment(Pos.CENTER);
        AnchorPane.setLeftAnchor(componentNameLabel, 5.0);
        AnchorPane.setRightAnchor(componentNameLabel, 5.0);
        AnchorPane.setTopAnchor(componentNameLabel, 20.0);

        voltageLabel = new Label("VOLTAGE");
        voltageLabel.setFont(new Font(30));
        voltageLabel.setAlignment(Pos.CENTER);;
        AnchorPane.setLeftAnchor(voltageLabel, 34.0);
        AnchorPane.setRightAnchor(voltageLabel, 34.0);
        AnchorPane.setTopAnchor(voltageLabel, 95.0);

        voltageTextField = new JFXTextField();
        voltageTextField.setFont(new Font(18));
        voltageTextField.setAlignment(Pos.CENTER);
        voltageTextField.setUnFocusColor(Color.rgb(64, 134, 234));
        voltageTextField.setFocusColor(Color.rgb(64, 134, 234));
        voltageTextField.setStyle("-fx-background-color:#e7e7e7");
        voltageTextField.setDisable(true);
        AnchorPane.setLeftAnchor(voltageTextField, 5.0);
        AnchorPane.setRightAnchor(voltageTextField, 5.0);
        AnchorPane.setTopAnchor(voltageTextField, 135.0);

        currentLabel = new Label("CURRENT");
        currentLabel.setFont(new Font(30));
        currentLabel.setAlignment(Pos.CENTER);
        AnchorPane.setLeftAnchor(currentLabel, 34.0);
        AnchorPane.setRightAnchor(currentLabel, 34.0);
        AnchorPane.setTopAnchor(currentLabel, 200.0);

        currentTextField = new JFXTextField();
        currentTextField.setFont(new Font(20));
        currentTextField.setAlignment(Pos.CENTER);
        currentTextField.setUnFocusColor(Color.rgb(64, 134, 234));
        currentTextField.setFocusColor(Color.rgb(64, 134, 234));
        currentTextField.setStyle("-fx-background-color:#e7e7e7");
        currentTextField.setDisable(true);
        AnchorPane.setLeftAnchor(currentTextField, 5.0);
        AnchorPane.setRightAnchor(currentTextField, 5.0);
        AnchorPane.setTopAnchor(currentTextField, 240.0);

        resistanceLabel = new Label("RESISTANCE");
        resistanceLabel.setFont(new Font(23));
        resistanceLabel.setAlignment(Pos.CENTER);
        AnchorPane.setLeftAnchor(resistanceLabel, 34.0);
        AnchorPane.setRightAnchor(resistanceLabel, 34.0);
        AnchorPane.setTopAnchor(resistanceLabel, 305.0);

        resistanceTextField = new JFXTextField();
        resistanceTextField.setFont(new Font(18));
        resistanceTextField.setAlignment(Pos.CENTER);
        resistanceTextField.setUnFocusColor(Color.rgb(64, 134, 234));
        resistanceTextField.setFocusColor(Color.rgb(64, 134, 234));
        resistanceTextField.setDisable(false);
        AnchorPane.setLeftAnchor(resistanceTextField, 5.0);
        AnchorPane.setRightAnchor(resistanceTextField, 5.0);
        AnchorPane.setTopAnchor(resistanceTextField, 345.0);
        resistanceTextField.setPromptText("ENTER RESISTANCE");



        elements.add(componentNameLabel);
        elements.add(voltageLabel);
        elements.add(voltageTextField);
        elements.add(currentLabel);
        elements.add(currentTextField);
        elements.add(resistanceLabel);
        elements.add(resistanceTextField);

        super.getComponentPane().getChildren().add(getComponentImageView());

    }

    public List<Node> getElements(){
        return elements;
    }
}
