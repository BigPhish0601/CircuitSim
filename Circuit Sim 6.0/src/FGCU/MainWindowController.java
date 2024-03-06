package FGCU;

import com.jfoenix.controls.*;
import com.jfoenix.effects.JFXDepthManager;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import com.jfoenix.controls.JFXButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;

public class MainWindowController implements Initializable {


    /////////own shit
    JFXDepthManager depthManager = new JFXDepthManager(); //Used to manage drop shadow of some components
    Map<Integer, Component> componentHashMap = new HashMap<>(); //Holds the components and there keys
    ObservableList<String> lightChoice = FXCollections.observableArrayList( //List of illumination settings
            "Moonless; Overcast Night Sky",
            "Public Areas with Dark Surroundigns", "Bathroom",
            "Office Lighting", "Direct Sunlight");
    private int currentId = -1; //holds the id of the currently selected component. If no component is selected, is -1
    private Map<Integer, Component> deletedEntries = new HashMap<>();
    private Map<Integer, Component> componentsCopy = new HashMap<>();

    private ImageView leftArrow = new ImageView(new Image("/FGCU/res/leftArrow.png"));
    private ImageView rightArrow = new ImageView(new Image("/FGCU/res/rightArrow.png"));


    //Main Window Stuff
    @FXML
    private AnchorPane topBarAnchorPane;
    @FXML
    private JFXHamburger mainMenuHamburger;
    @FXML
    private JFXButton runButton;
    @FXML
    private JFXButton deleteButton;
    @FXML
    private JFXButton rotateButton;
    @FXML
    private JFXButton clearButton;
    @FXML
    private AnchorPane canvasPane;
    @FXML
    AnchorPane basePane;
    @FXML
    private JFXSlider temperatureSlider;
    @FXML
    private Label temperatureLabel;
    @FXML
    ChoiceBox lightChoiceBox;
    @FXML
    JFXButton showInspector;


    //Inspector
    AnchorPane inspectorPane;
    private boolean showing = false;
    private boolean inspectorMouseIsOver = false;

    //Components Window Stuff
    AnchorPane pane;
    int x = 1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainWindowButtons();
        canvasDragBullshit();
        componentsWindowActions();
        inspectorWindow();
    }

    public void mainWindowButtons() {
        //Setup

        showInspector.setText("");
        showInspector.setGraphic(leftArrow);

        //Light Stuff (Illumination)
        lightChoiceBox.setItems(lightChoice);
        lightChoiceBox.setValue(lightChoice.get(3));

        //Temperature Stuff
        temperatureSlider.setValue(20);
        temperatureLabel.setText("Temperature: " + (int) temperatureSlider.getValue() + "°C");
        temperatureSlider.valueProperty().addListener(e -> {
            //Needed to make it a little complicated. The JFeonix shown value is an int, but the slider returns double and
            //casting did some odd things. It works correctly now, but the user cannot set a temperature of 1 degree
            if (temperatureSlider.getValue() >= 0) {
                temperatureLabel.textProperty().setValue("Temperature: " + String.valueOf((int) (temperatureSlider.getValue() + .5)) + "ºC");
            } else {
                temperatureLabel.textProperty().setValue("Temperature: " + String.valueOf((int) (temperatureSlider.getValue() - .5)) + "ºC");
            }
        });

        /***************************************************
         Below are handlers for the button presses of the
         Run, Delete, Clear, and Rotate buttons located in the top bar
         of the main window
         ***************************************************/

        //Run Button
        runButton.setOnMouseClicked(e -> {
            System.out.println("Run Button Clicked");
        });

        //Delete Button
        deleteButton.setOnMouseClicked(e -> {
            int selectedID = -1;
            System.out.println("Delete Button Clicked");
            Component temp;
            for (Integer key1 : componentHashMap.keySet()) {
                deletedEntries.put(key1, componentHashMap.get(key1));
                temp = componentHashMap.get(key1);
                if (temp.isSelected()) {
                    selectedID = temp.getMyId();
                    canvasPane.getChildren().remove(temp.getComponentPane());
                    selectedID = -1;
                    emptyInspector();
                }
            }
            componentHashMap.clear();
            for (Integer key2 : deletedEntries.keySet()) {
                if (key2 != selectedID) {
                    componentHashMap.put(key2, deletedEntries.get(key2));
                }
            }
            deletedEntries.clear();
            System.out.println(componentHashMap);
        });

        //Rotate Button
        rotateButton.setOnMouseClicked(e -> {
            System.out.println("Rotate Button Clicked");
        });

        //Clear Button
        clearButton.setOnMouseClicked(e -> {
            System.out.println("Clear Button Clicked");
            for (Integer key : componentHashMap.keySet()) {
                canvasPane.getChildren().remove(componentHashMap.get(key).getComponentPane());
            }
            componentHashMap.clear();
            System.out.println(componentHashMap);
            emptyInspector();
        });

        /**********************************
         * Handles pertaining to the Canvas Pane
         ***********************************/
        canvasPane.setOnMouseClicked(e -> {
            //Note that the below method is shot when the canvasPane is clicked
            //Clicks for the canvasPane are still shot even if there's a component
            //between it and the canvas
            selectedComponentDecider();


        });

        showInspector.setOnMouseClicked(e -> {

            if (showing) {
                canvasPane.getChildren().remove(inspectorPane);
                canvasPane.getChildren().add(inspectorPane);
                TranslateTransition transition = new TranslateTransition();
                transition.setDuration(Duration.seconds(.2));
                transition.setNode(inspectorPane);
                transition.setToX(200);
                transition.setInterpolator(Interpolator.EASE_BOTH);
                transition.play();
                showing = false;
                showInspector.setGraphic(leftArrow);
            } else {
                canvasPane.getChildren().remove(inspectorPane);
                canvasPane.getChildren().add(inspectorPane);
                TranslateTransition transition = new TranslateTransition();
                transition.setDuration(Duration.seconds(.2));
                transition.setNode(inspectorPane);
                transition.setToX(0);
                transition.setInterpolator(Interpolator.EASE_BOTH);
                transition.play();
                showing = true;
                showInspector.setGraphic(rightArrow);
            }

        });
    }

    /**************************************************************
     * A whole bunch of stuff about the components window
     * It's a a little convoluted, but I'll try and explain
     * The only component of the fxml file that can be accessed
     * is the AnchorPane that holds everything in it. The child
     * components cannot be directly accessed. Further, they do not hold
     * their specific types (Button, Pane, etc.). Luckily, all JFX objects
     * extend Node and you can set an "accessible text" for each GUI component
     * in SceneBuilder. So, we're basically looping through all the child components
     * of the base AnchorPane pane and determing what exactly they are by accessing
     * their accessible text.
     * */
    public void componentsWindowActions() {
        try {
            pane = FXMLLoader.load(getClass().getResource("fxml/componentsMenu.fxml")); //Load FXML file into AnchorPane
            AnchorPane.setBottomAnchor(pane, 0.0);
            AnchorPane.setTopAnchor(pane, 0.0);
            for (Node node : pane.getChildren()) { //Loop through all the children of the anchorpane
                depthManager.setDepth(node, 5); //Adds depth to all the components (advanced drop shadow)

                if (node.getAccessibleText() != null) {

                    //Tooltips for components
                    switch (node.getAccessibleText()) {
                        case "battery":
                            Tooltip.install(node, new Tooltip("Drag to add a battery to the canvas"));
                            break;
                        case "resistor":
                            Tooltip.install(node, new Tooltip("Drag to add a resistor to the canvas"));
                            break;
                        case "switch":
                            Tooltip.install(node, new Tooltip("Drag to add a switch to the canvas"));
                            break;
                        case "button":
                            Tooltip.install(node, new Tooltip("Drag to add a button to the canvas"));
                            break;
                        case "potentiometer":
                            Tooltip.install(node, new Tooltip("Drag to add a potentiometer to the canvas"));
                            break;
                        case "led":
                            Tooltip.install(node, new Tooltip("Drag to add a led to the canvas"));
                            break;
                        case "photoresistor":
                            Tooltip.install(node, new Tooltip("Drag to add a photoresistor to the canvas"));
                            break;
                        case "thermistor":
                            Tooltip.install(node, new Tooltip("Drag to add a thermistor to the canvas"));
                            break;
                    }

                    //Handles for About and Help buttons
                    node.setOnMouseClicked(e -> {
                        switch (node.getAccessibleText()) {
                            case "about": {
                                try {
                                    Parent root = FXMLLoader.load(getClass().getResource("fxml/AboutWindow.fxml"));

                                    Stage stage = new Stage();
                                    Scene sceneHelpPane = new Scene(root);
                                    stage.setScene(sceneHelpPane);
                                    stage.setTitle("About");
                                    stage.getIcons().add(new Image("FGCU/res/fgcuLogo.png"));
                                    stage.show();
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                            break;

                            case "help": {
                                try {
                                    Parent root = FXMLLoader.load(getClass().getResource("fxml/helpGUI.fxml"));
                                    Stage stage = new Stage();
                                    stage.setTitle("Help");
                                    stage.getIcons().add(new Image("FGCU/res/fgcuLogo.png"));
                                    Scene sceneHelpPane = new Scene(root);
                                    stage.setScene(sceneHelpPane);
                                    stage.show();
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                            break;
                            default:
                        }
                    });

                    //Handles for the dragging of various electrical components
                    node.setOnDragDetected(e -> {
                        switch (node.getAccessibleText()) {
                            case "battery": {
                                Image i = new Image((getClass().getResourceAsStream("res/batteryPaneComponent.png")));
                                Dragboard db = node.startDragAndDrop(TransferMode.ANY);
                                ClipboardContent content = new ClipboardContent();
                                content.putString("battery");
                                db.setDragView(i);
                                db.setContent(content);
                                System.out.println("BATTERY DRAG");
                                e.consume();
                            }
                            break;

                            case "resistor": {
                                Image i = new Image((getClass().getResourceAsStream("res/100OhmResistor.png")));
                                Dragboard db = node.startDragAndDrop(TransferMode.ANY);
                                ClipboardContent content = new ClipboardContent();
                                content.putString("resistor");
                                db.setDragView(i);
                                db.setContent(content);
                                System.out.println("RESISTOR DRAG");
                                e.consume();
                            }
                            break;

                            case "switch": {
                                Image i = new Image((getClass().getResourceAsStream("res/switchIconOff.png")));
                                Dragboard db = node.startDragAndDrop(TransferMode.ANY);
                                ClipboardContent content = new ClipboardContent();
                                content.putString("switch");
                                db.setDragView(i);
                                db.setContent(content);
                                e.consume();
                            }
                            break;

                            case "button": {
                                Image i = new Image((getClass().getResourceAsStream("res/nonPressedButton.png")));
                                Dragboard db = node.startDragAndDrop(TransferMode.ANY);
                                ClipboardContent content = new ClipboardContent();
                                content.putString("button");
                                db.setDragView(i);
                                db.setContent(content);
                                e.consume();
                            }
                            break;

                            case "potentiometer": {
                                Image i = new Image((getClass().getResourceAsStream("res/potentiometerIcon.png")));
                                Dragboard db = node.startDragAndDrop(TransferMode.ANY);
                                ClipboardContent content = new ClipboardContent();
                                content.putString("potentiometer");
                                db.setDragView(i);
                                db.setContent(content);
                                e.consume();
                            }
                        }
                    });

                    // ¯\_(ツ)_/¯
                    node.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {

                        }
                    });

                }
            }
            HamburgerSlideCloseTransition h = new HamburgerSlideCloseTransition(mainMenuHamburger);
            h.setRate(-1);
            mainMenuHamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                h.setRate(h.getRate() * -1);
                h.play();

                if (x == 0) {//Dissapear
                    TranslateTransition transition = new TranslateTransition();
                    transition.setDuration(Duration.seconds(.2));
                    transition.setNode(pane);
                    transition.setToX(-200);
                    transition.setInterpolator(Interpolator.EASE_BOTH);
                    transition.play();
                    x = 1;
                } else {//appear
                    if (!canvasPane.getChildren().contains(pane)) {
                        canvasPane.getChildren().add(pane);
                        pane.setTranslateX(-200);
                    }
                    TranslateTransition transition = new TranslateTransition();
                    transition.setDuration(Duration.seconds(.2));
                    transition.setNode(pane);
                    transition.setToX(0);
                    transition.setInterpolator(Interpolator.EASE_BOTH);
                    transition.play();
                    x = 0;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /********************************
     *Handles for canvas drags
     *
     * How it works:
     * If the content dragged over the canvas has a dragboard (dragboards hold transferrable content) that
     * contains a string that .equals "battery", "resistor", "switch", etc., it just accepts the content
     * without any additional processing. However, if what is dragged over is ELSE (meaning that the content
     * wasn't dragged from the component pane and was instead already on the canvas and is being moved it is processed
     * as follows. Its dragboard contains the string value of its id. This id is parsed to an Integer and is used to
     * determine which component is being dragged over. Its position is then updated depending on the position of the mouse
     * during the drag operation. Because the canvasPane is being continuously updated during these procedures, the drawer
     * needs to be continuously removed and re-added so that it is always on top of any components being moved.
     * ********************************/
    public void canvasDragBullshit() {

        canvasPane.setOnDragOver(e -> {
            Dragboard db = e.getDragboard();
            e.acceptTransferModes(TransferMode.ANY);

            if (db.getString().equals("battery")) {
            } else if (db.getString().equals("resistor")) {
            } else if (db.getString().equals("switch")) {
            } else if (db.getString().equals("button")) {
            } else if (db.getString().equals("potentiometer")) {
            } else if (db.getString().equals("led")) {
            } else if (db.getString().equals("photoresistor")) {
            } else if (db.getString().equals("thermistor")) {
            } else { //This only activates if the dragboard doesn't have the prior strings
                //hence, is already on the canvas
                int id = Integer.valueOf(db.getString());
                Component temp = componentHashMap.get(id);
                canvasPane.getChildren().remove(temp.getComponentPane());
                canvasPane.getChildren().remove(pane);
                canvasPane.getChildren().remove(inspectorPane);
                temp.getComponentPane().setLayoutX(e.getX() - temp.posx);
                temp.getComponentPane().setLayoutY(e.getY() - temp.posy);
                canvasPane.getChildren().add(temp.getComponentPane());
                canvasPane.getChildren().add(pane);
                canvasPane.getChildren().add(inspectorPane);
            }

            e.consume();

        });

        /***********************************************
         *Handles for canvas droppped
         *
         * How it works:
         * This describes battery, but is is the same for all the rest of the components
         * If the string of the dragboard .equals "battery" than a new component is created
         * of type battery (Component temp = new Battery()), is given an id, the selected items
         * are reset to false,
         *
         * Similar things happen when a component on the pane is being dragged and dropped
         * */
        canvasPane.setOnDragDropped(e -> {
            Dragboard db = e.getDragboard();
            boolean success = false;
            Component temp = null;

            if (db.getString().equals("battery")) {
                canvasPane.getChildren().remove(pane);
                canvasPane.getChildren().remove(inspectorPane);
                temp = new Battery();
                setCurrentId(temp.getMyId());
                for (Integer id : componentHashMap.keySet()) {
                    componentHashMap.get(id).setSelected(false);
                }
                componentHashMap.put(temp.getMyId(), temp);
                temp.getComponentPane().setLayoutX(e.getX());
                temp.getComponentPane().setLayoutY(e.getY());
                canvasPane.getChildren().add(temp.getComponentPane());
                canvasPane.getChildren().add(pane);
                canvasPane.getChildren().add(inspectorPane);
                success = true;
            }

            if (db.getString().equals("resistor")) {
                canvasPane.getChildren().remove(pane);
                canvasPane.getChildren().remove(inspectorPane);
                temp = new Resistor();
                setCurrentId(temp.getMyId());
                for (Integer id : componentHashMap.keySet()) {
                    componentHashMap.get(id).setSelected(false);
                }
                componentHashMap.put(temp.getMyId(), temp);
                temp.getComponentPane().setLayoutX(e.getX());
                temp.getComponentPane().setLayoutY(e.getY());
                canvasPane.getChildren().add(temp.getComponentPane());
                canvasPane.getChildren().add(pane);
                canvasPane.getChildren().add(inspectorPane);
                success = true;
            }

            if (db.getString().equals("switch")) {
                canvasPane.getChildren().remove(pane);
                canvasPane.getChildren().remove(inspectorPane);
                temp = new Switch();
                setCurrentId(temp.getMyId());
                for (Integer id : componentHashMap.keySet()) {
                    componentHashMap.get(id).setSelected(false);
                }
                componentHashMap.put(temp.getMyId(), temp);
                temp.getComponentPane().setLayoutX(e.getX());
                temp.getComponentPane().setLayoutY(e.getY());
                canvasPane.getChildren().add(temp.getComponentPane());
                canvasPane.getChildren().add(pane);
                canvasPane.getChildren().add(inspectorPane);
                success = true;
            }

            if (db.getString().equals("button")) {
                canvasPane.getChildren().remove(pane);
                canvasPane.getChildren().remove(inspectorPane);
                temp = new Button();
                setCurrentId(temp.getMyId());
                for (Integer id : componentHashMap.keySet()) {
                    componentHashMap.get(id).setSelected(false);
                }
                componentHashMap.put(temp.getMyId(), temp);
                temp.getComponentPane().setLayoutX(e.getX());
                temp.getComponentPane().setLayoutY(e.getY());
                canvasPane.getChildren().add(temp.getComponentPane());
                canvasPane.getChildren().add(pane);
                canvasPane.getChildren().add(inspectorPane);
                success = true;
            }

            if (db.getString().equals("potentiometer")) {
                canvasPane.getChildren().remove(pane);
                canvasPane.getChildren().remove(inspectorPane);
                temp = new Potentiometer();
                setCurrentId(temp.getMyId());
                for (Integer id : componentHashMap.keySet()) {
                    componentHashMap.get(id).setSelected(false);
                }
                componentHashMap.put(temp.getMyId(), temp);
                temp.getComponentPane().setLayoutX(e.getX());
                temp.getComponentPane().setLayoutY(e.getY());
                canvasPane.getChildren().add(temp.getComponentPane());
                canvasPane.getChildren().add(pane);
                canvasPane.getChildren().add(inspectorPane);
                success = true;
            }

            //Components from canvas being dropped
            if (e.getGestureSource() == componentHashMap.get(Integer.valueOf(db.getString())).getComponentPane()) {

                System.out.println("fired");
                temp = componentHashMap.get(Integer.valueOf(db.getString()));
                setCurrentId(temp.getMyId());
                for (Integer id : componentHashMap.keySet()) {
                    componentHashMap.get(id).setSelected(false);
                }
                temp.getComponentPane().setLayoutX(e.getX() - temp.posx);
                temp.getComponentPane().setLayoutY(e.getY() - temp.posy);

                if (canvasPane.getChildren().contains(pane)) {
                    canvasPane.getChildren().remove(pane);
                    canvasPane.getChildren().add(pane);
                }

                if (canvasPane.getChildren().contains(inspectorPane)) {
                    canvasPane.getChildren().remove(inspectorPane);
                    canvasPane.getChildren().add(inspectorPane);
                }

                success = true;
            }

            e.setDropCompleted(success);
            e.consume();

        });

    }

    public void inspectorWindow() {
        try {
            inspectorPane = FXMLLoader.load(getClass().getResource("fxml/inspectorWindow.fxml")); //Load FXML file into AnchorPane
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Sets location of inspector window
        AnchorPane.setBottomAnchor(inspectorPane, 0.0);
        AnchorPane.setTopAnchor(inspectorPane, 0.0);
        AnchorPane.setRightAnchor(inspectorPane, 0.0);
        inspectorPane.setTranslateX(200);
        canvasPane.getChildren().add(inspectorPane);

        emptyInspector();

        inspectorPane.setOnMouseEntered(e -> {
            System.out.println("inspector entered");
            inspectorMouseIsOver = true;
        });

        inspectorPane.setOnMouseExited(e -> {
            System.out.println("inspector exited");
            inspectorMouseIsOver = false;
        });


    }

    /*
    * This method is used to determine what component is currently selected
    * If a component is selected the currentId instance variable is set
    * to the selected component's id (key value) in the componentHashMap
    * If a component isn't selected, the currentId is set to -1
    *
    * How it works:
    * This method is only shot when the canvasPane is clicked. If the mouse is over
    * a component when the canvasPane is clicked, the component is set as selected (true)
    * and all other components are set as not selected (false). If the mouse is over no
    * components when the mouse is clicked on the canvas, all the components are set selected
    * to false.
    *
    * It then loops through our components and if one is selected (true), the currentId instance
    * variable is set to the id (key) of that component. If none are true (all false) the currentId
    * is set to -1
    */
    public void selectedComponentDecider() {
        Component temp;
        setCurrentId(-1);
        //canvasPane.getChildren().remove(inspectorDrawer);

        //If selected
        for (Integer id : componentHashMap.keySet()) {
            temp = componentHashMap.get(id);
            if (temp.getMouseOver()) {
                temp.setSelected(true);
                temp.getComponentPane().setEffect(new DropShadow());
            } else if (inspectorMouseIsOver) {


            } else {
                temp.setSelected(false);
                temp.getComponentPane().setEffect(null);
            }
        }
        for (Integer id : componentHashMap.keySet()) {
            temp = componentHashMap.get(id);
            if (temp.isSelected()) {
                setCurrentId(id);
            }
        }

        if (getCurrentId() != -1) {

            if (componentHashMap.get(getCurrentId()).getType().equals(("Battery"))) {
                showInspector.setDisable(false);

                Battery bat = (Battery) componentHashMap.get(getCurrentId());
                inspectorPane.getChildren().clear();
                for (Node node : bat.getElements()) {
                    inspectorPane.getChildren().add(node);
                }
            } else if (componentHashMap.get(getCurrentId()).getType().equals(("Resistor"))) {
                showInspector.setDisable(false);

                Resistor res = (Resistor) componentHashMap.get(getCurrentId());
                inspectorPane.getChildren().clear();
                for (Node node : res.getElements()) {
                    inspectorPane.getChildren().add(node);
                }
            } else if (componentHashMap.get(getCurrentId()).getType().equals(("Switch"))) {
                showInspector.setDisable(false);

                Switch swi = (Switch) componentHashMap.get(getCurrentId());
                inspectorPane.getChildren().clear();
                for (Node node : swi.getElements()) {
                    inspectorPane.getChildren().add(node);
                }

                swi.getSwitchToggle().setOnMouseClicked(e -> {
                    System.out.println("fired");
                    if(swi.getIsOn()){
                        System.out.println("Switch Off");
                        canvasPane.getChildren().remove(inspectorPane);
                        canvasPane.getChildren().remove(pane);
                        canvasPane.getChildren().remove(swi.getComponentPane());
                        swi.setAlternativeImageView("FGCU/res/switchIconOff.png");
                        swi.getComponentPane().getChildren().remove(swi.getComponentImageView());
                        swi.getComponentPane().getChildren().add(swi.getComponentImageView());
                        canvasPane.getChildren().add(swi.getComponentPane());
                        canvasPane.getChildren().add(pane);
                        canvasPane.getChildren().add(inspectorPane);
                       swi.setIsOn(false);
                    }else{
                        System.out.println("Switch On");
                        canvasPane.getChildren().remove(inspectorPane);
                        canvasPane.getChildren().remove(pane);
                        canvasPane.getChildren().remove(swi.getComponentPane());
                        swi.setAlternativeImageView("FGCU/res/switchIconOn.png");
                        swi.getComponentPane().getChildren().remove(swi.getComponentImageView());
                        swi.getComponentPane().getChildren().add(swi.getComponentImageView());
                        canvasPane.getChildren().add(swi.getComponentPane());
                        canvasPane.getChildren().add(pane);
                        canvasPane.getChildren().add(inspectorPane);
                        swi.setIsOn(true);
                    }
                });



            } else if (componentHashMap.get(getCurrentId()).getType().equals(("Button"))) {
                showInspector.setDisable(true);

                if (showing) {
                    canvasPane.getChildren().remove(inspectorPane);
                    canvasPane.getChildren().add(inspectorPane);
                    TranslateTransition transition = new TranslateTransition();
                    transition.setDuration(Duration.seconds(.2));
                    transition.setNode(inspectorPane);
                    transition.setToX(200);
                    transition.setInterpolator(Interpolator.EASE_BOTH);
                    transition.play();
                    showing = false;
                    showInspector.setGraphic(leftArrow);
                }
            } else if (componentHashMap.get(getCurrentId()).getType().equals(("Potentiometer"))) {
                showInspector.setDisable(false);
                Potentiometer pot = (Potentiometer) componentHashMap.get(getCurrentId());
                inspectorPane.getChildren().clear();
//                for (Node node : pot.getElements()){
//                    inspectorPane.getChildren().add(node);
//                }
            }

//            if(componentHashMap.get(getCurrentId()).getType().equals(("Resistor"))) {
//                Resistor res = (Resistor) componentHashMap.get(getCurrentId());
//                inspectorPane.getChildren().clear();
//                for (Node node : res.getElements()){
//                    inspectorPane.getChildren().add(node);
//                }
//            }

        } else {
            showInspector.setDisable(false);
            emptyInspector();
        }

        System.out.println("current id is: " + getCurrentId());

        for (Integer id : componentHashMap.keySet()) {
            System.out.println(componentHashMap.get(id).getName() + " " + componentHashMap.get(id).getMyId() + " " + componentHashMap.get(id).isSelected());
        }
    }

    public void emptyInspector() {
        inspectorPane.getChildren().clear();
        Label unselectedInspector = new Label("SELECT A\nCOMPONENT\n ON THE CANVAS\n TO CHANGE\nAND VIEW\nITS VALUE(S)");
        unselectedInspector.setFont(new Font(25));
        unselectedInspector.setTextAlignment(TextAlignment.CENTER);
        unselectedInspector.setTextFill(Color.BLACK);
        inspectorPane.getChildren().add(unselectedInspector);
    }

    public void setCurrentId(int currentId) {
        this.currentId = currentId;
    }

    public int getCurrentId() {
        return this.currentId;
    }


}