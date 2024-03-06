package FGCU;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import sun.plugin.javascript.navig.Anchor;

public class Main extends Application {

    Image logo = new Image(getClass().getResourceAsStream("res/fgcuLogo.png"));

    Battery b = new Battery();

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("fxml/SplashScreen.fxml")); //Loads the splash screen
        primaryStage.initStyle(StageStyle.UNDECORATED); //takes away minimize, close, etc.
        primaryStage.getIcons().add(logo);//Makes the taskbar icon our logo
        primaryStage.setScene(new Scene(root, 400, 300)); //sets the scene to the splashscreen
        primaryStage.show(); //shows the splash screen

        PauseTransition delay = new PauseTransition(Duration.seconds(0)); //Duration of splashscreen
        delay.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.close(); //after the delay, closes the splashscreen
                try {
                    Stage primaryStage2 = new Stage();
                    primaryStage2.getIcons().add(logo); //makes the taskbar icon our logo again because the original window was closed
                    AnchorPane root2 = FXMLLoader.load(getClass().getResource("fxml/mainWindow.fxml")); //sets window to main window
                    primaryStage2.setTitle("Circuit Simulator"); //set window title
                    Scene scene = new Scene(root2, 1200, 800); //set default window size
                    scene.getStylesheets().add("FGCU/css/myCss.css"); //Sets css theme of window. Mainly used to make slider black
                    primaryStage2.setScene(scene);
                    primaryStage2.show();

                }catch(Exception e){
                    e.printStackTrace();
                }

            }
        });
        delay.play();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
