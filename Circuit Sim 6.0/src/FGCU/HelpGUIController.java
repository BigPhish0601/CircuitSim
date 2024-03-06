package FGCU;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class HelpGUIController implements Initializable {

    @FXML
    private JFXListView<Label> listView;

    @FXML
    private JFXCheckBox noInternetCheckBox;

    @FXML
    private AnchorPane listPane;

    @FXML
    private WebView webView;

    Label battery = new Label("BATTERY");
    Label resistor = new Label("RESISTOR");
    ObservableList<Label> list = FXCollections.observableArrayList(battery, resistor);
    private boolean hasInternet = true;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Label lb : list) {
            lb.setFont(new Font(18));


            lb.setOnMouseClicked(e -> {

                WebEngine webEngine = webView.getEngine();

                System.out.println(lb.getText());

                switch (lb.getText()) {

                    case "BATTERY": {//HTML

                        if(!noInternetCheckBox.isSelected()) {
                            System.out.println("fire");
                            webEngine.load("https://en.wikipedia.org/wiki/Battery_(electricity)");
                        }else{
                            URL url = getClass().getResource("res/Battery (electricity) - Wikipedia.html");
                            webEngine.load(url.toExternalForm());

                        }
                    }break;

                    case "RESISTOR": {//ACTUAL WEBSITE

                        if(!noInternetCheckBox.isSelected()) {
                        webEngine.load("https://en.wikipedia.org/wiki/Resistor");
                        }else{
                           URL url = getClass().getResource("res/Resistor - Wikipedia.html");
                            webEngine.load(url.toExternalForm());
                        }
                    }break;

                    default:{
                        System.out.println("load nothing");
                    }

                }




            });


            listView.setItems(list);


        }
    }

};