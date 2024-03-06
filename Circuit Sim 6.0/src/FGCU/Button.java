package FGCU;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

/**
 * Created by Brannon on 11/21/2016.
 */
public class Button extends Component {

    Label componentNameLabel;
    Label explanation;

    public Button(){
        super("Button");

        componentNameLabel = new Label("BUTTON");
        componentNameLabel.setFont(new Font(45));
        componentNameLabel.setAlignment(Pos.CENTER);
        AnchorPane.setLeftAnchor(componentNameLabel, 5.0);
        AnchorPane.setRightAnchor(componentNameLabel, 5.0);
        AnchorPane.setTopAnchor(componentNameLabel, 20.0);

        explanation = new Label("VOLTAGE");
        explanation.setFont(new Font(30));
        explanation.setAlignment(Pos.CENTER);;
        AnchorPane.setLeftAnchor(explanation, 34.0);
        AnchorPane.setRightAnchor(explanation, 34.0);
        AnchorPane.setTopAnchor(explanation, 95.0);

        getComponentPane().getChildren().add(getComponentImageView());
    }

}
