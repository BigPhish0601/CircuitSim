package FGCU;

import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.effects.JFXDepthManager;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;


public class Component{

    private static int NEXT_ID = 0;
    private int id = 0;

    //Gives the component a unique identification
    //number. I know, it's a little basic using
    //an int, but the name of the component will
    //be set by the user, so all that really matters
    //is that each component has its own unique id.

    //Component ID
    private String type; //Component type
    private boolean selected;//Component selected
    private boolean mouseOver;
    private String name;// Name of component;
    private ImageView componentImageView;
    private Pane componentPane = new Pane();
    public double posx;
    public double posy;
    public DropShadow dropShadow = new DropShadow();



    JFXSlider redSlider;
    JFXSlider greenSlider;
    JFXSlider blueSlider;



    public Component(String type) {

        //Sets the Unique ID of the Component
        setType(type); //Sets the type of the component (used for casting)
        setId();
        setComponentImageView(type); //Set the image o
        setName(type);
        listeners();
        System.out.println(isSelected());





    }

    public void listeners(){

        componentPane.setOnDragDetected(e -> {
            Dragboard db = getComponentPane().startDragAndDrop(TransferMode.ANY);
            posx = e.getX();
            posy = e.getY();
            ClipboardContent content = new ClipboardContent();
            content.putString(String.valueOf(getMyId()));
            db.setContent(content);
            e.consume();
        });

        componentPane.setOnMouseEntered(e -> {
            System.out.println("Mouse Over " + name);
            setMouseOver(true);
        });

        componentPane.setOnMouseExited(e -> {
            setMouseOver(false);
        });

        componentPane.setOnMouseClicked(e -> {
            System.out.println("not firing");
            selected = true;
            getComponentPane().setEffect(dropShadow);
            System.out.println("Selected" + id + " " + name);
        });

        componentPane.setOnMouseMoved(e -> {
            //Mouse Moved
        });

        componentPane.setOnMouseClicked(e -> {
            //System.out.println(getMyId());
        });



    }



    public void setId(){
        this.id = NEXT_ID;
        NEXT_ID++;
    }
    public int getMyId(){
        return id;
    }

    public void setType(String type){
        this.type = type;
    }
    public String getType(){
        return type;
    }

    public void setComponentImageView(String type){

        switch(type){
            case "Battery" :  componentImageView = new ImageView(new Image("/FGCU/res/batteryPaneComponent.png"));
                break;
            case "Resistor" : componentImageView = new ImageView(new Image("FGCU/res/100OhmResistor.png"));
                break;
            case "LED" : componentImageView = new ImageView(new Image("FGCU/res/ledIcon.png"));
                break;
            case "Potentiometer" : componentImageView = new ImageView(new Image("FGCU/res/potentiometerIcon.png"));
                break;
            case "Switch" : componentImageView = new ImageView(new Image("FGCU/res/switchIconOff.png"));
                break;
            case "Button" : componentImageView = new ImageView(new Image("FGCU/res/nonPressedButton.png"));
                break;
            case "Photoresistor" : componentImageView = new ImageView(new Image("FGCU/res/lightSensor.png"));
                break;
            case "Thermistor" : componentImageView = new ImageView(new Image("FGCU/res/heatSensor.png"));
        }
    }

    public void setAlternativeImageView(String path){
        componentImageView = new ImageView(new Image(path));
    }

    public ImageView getComponentImageView(){
        return componentImageView;
    }

    public void setName(String name){this.name = name;}
    public String getName(){return this.name;}

    public void setSelected(boolean selected){this.selected = selected;}
    public boolean isSelected(){return selected;}

    public Pane getComponentPane(){return componentPane;}

    public void setMouseOver(boolean mouseOver){
        this.mouseOver = mouseOver;
    }

    public boolean getMouseOver(){
        return mouseOver;
    }

}
