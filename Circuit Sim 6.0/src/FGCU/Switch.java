package FGCU;


import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

public class Switch extends Component {

    private boolean isOn = false;


    //Labels and TextFields
    Label componentNameLabel;
    Label currentLabel;
    JFXTextField currentTextField;
    Label voltageLabel;
    JFXTextField voltageTextField;
    Label switchStateOn;
    Label switchStateOff;
    JFXToggleButton switchToggle;

    private List<Node> elements = new ArrayList<>();


    public Switch() {
        super("Switch");

        componentNameLabel = new Label("SWITCH");
        componentNameLabel.setFont(new Font(45));
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
        currentTextField.setFont(new Font(18));
        currentTextField.setAlignment(Pos.CENTER);
        currentTextField.setUnFocusColor(Color.rgb(64, 134, 234));
        currentTextField.setFocusColor(Color.rgb(64, 134, 234));
        currentTextField.setStyle("-fx-background-color:#e7e7e7");
        currentTextField.setDisable(true);
        AnchorPane.setLeftAnchor(currentTextField, 5.0);
        AnchorPane.setRightAnchor(currentTextField, 5.0);
        AnchorPane.setTopAnchor(currentTextField, 240.0);


        switchToggle = new JFXToggleButton();
        AnchorPane.setLeftAnchor(switchToggle, 68.0);
        AnchorPane.setRightAnchor(switchToggle, 60.0);
        AnchorPane.setTopAnchor(switchToggle, 305.0);

        switchStateOff = new Label("OFF");
        switchStateOff.setFont(new Font(18));
        switchStateOff.setAlignment(Pos.CENTER);
        AnchorPane.setLeftAnchor(switchStateOff, 32.0);
        AnchorPane.setRightAnchor(switchStateOff, 136.0);
        AnchorPane.setTopAnchor(switchStateOff, 320.0);

        switchStateOn = new Label("ON");
        switchStateOn.setFont(new Font(18));
        switchStateOn.setAlignment(Pos.CENTER);
        //AnchorPane.setLeftAnchor(switchStateOn, 32.0);
        AnchorPane.setRightAnchor(switchStateOn, 32.0);
        AnchorPane.setTopAnchor(switchStateOn, 320.0);

        switchToggle.setAccessibleText("Switch");


        elements.add(componentNameLabel);
        elements.add(voltageLabel);
        elements.add(voltageTextField);
        elements.add(currentLabel);
        elements.add(currentTextField);
        elements.add(switchToggle);
        elements.add(switchStateOff);
        elements.add(switchStateOn);

        super.getComponentPane().getChildren().add(getComponentImageView());
    }

    public List<Node> getElements() {
        return elements;
    }


    public boolean getIsOn(){
        return isOn;
    }

    public void setIsOn(boolean x){
        isOn = x;
    }

    public JFXToggleButton getSwitchToggle(){
        return switchToggle;
    }

}
