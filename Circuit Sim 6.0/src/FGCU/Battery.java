package FGCU;


import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Battery extends Component {

    private int voltage = 0;

    //Labels and TextFields
    Label componentNameLabel;
    Label currentLabel;
    JFXTextField currentTextField;
    Label voltageLabel;
    JFXTextField voltageTextField;

    private List<Node> elements = new ArrayList<>();

    public Battery(){
        super("Battery");

        componentNameLabel = new Label("BATTERY");
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
        AnchorPane.setLeftAnchor(voltageTextField, 5.0);
        AnchorPane.setRightAnchor(voltageTextField, 5.0);
        AnchorPane.setTopAnchor(voltageTextField, 135.0);
        voltageTextField.setPromptText("ENTER VOLTAGE");

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





        elements.add(componentNameLabel);
        elements.add(voltageLabel);
        elements.add(voltageTextField);
        elements.add(currentLabel);
        elements.add(currentTextField);
        super.getComponentPane().getChildren().add(getComponentImageView());

        actions();



    }

    public void actions(){

//        voltageTextField.textProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//                if (!newValue.matches("\\d*")) {
//                    voltageTextField.setText(newValue.replaceAll("[^\\d]", ""));
//                }
//
//                if(!voltageTextField.getText().equals("")){
//                    voltage = Integer.valueOf(voltageTextField.getText());
//                }else{
//                    voltageTextField.setText(String.valueOf(voltage));
//                }
//            }
//        });



    }


    public double getVoltage() {
        return voltage;
    }

    public void setVoltage(int voltage) {
        this.voltage = voltage;
    }


    public Label getVoltageLabel() {
        return voltageLabel;
    }

    public void setVoltageLabel(Label voltageLabel) {
        this.voltageLabel = voltageLabel;
    }

    public TextField getVoltageTextField() {
        return voltageTextField;
    }

    public void setVoltageTextField(JFXTextField voltageTextField) {
        this.voltageTextField = voltageTextField;
    }

    public List<Node> getElements(){
        return elements;
    }

}
