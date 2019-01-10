import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class InstructionScene extends Scene{

    private static BridgeToSuccess bridgeToSuccess;

    private static Group root;

    private static Image instructionSceneAsImage;
    private static ImageView instructionSceneImageViewer;

    private static Button backButton;

    public InstructionScene(BridgeToSuccess bridgeToSuccess){

        super(buildScene());

        this.bridgeToSuccess = bridgeToSuccess;

        this.getStylesheets().add(getClass().getResource("/design.css").toExternalForm());
        this.setFill(Color.rgb(31, 191, 82));

    }

    private static Group buildScene(){

        root = new Group();

        instructionSceneAsImage = new Image(InstructionScene.class.getResourceAsStream("/instructionScene.png"));

        instructionSceneImageViewer = new ImageView(instructionSceneAsImage);
        instructionSceneImageViewer.setX(0);
        instructionSceneImageViewer.setY(0);

        backButton = new Button("Back");
        backButton.getStyleClass().add("backButton");
        backButton.setLayoutX(50);
        backButton.setLayoutY(680);

        backButton.setOnAction((event) -> {

            bridgeToSuccess.primaryStage.setScene(bridgeToSuccess.menuScene);

        });

        root.getChildren().add(instructionSceneImageViewer);
        root.getChildren().add(backButton);

        return root;

    }

}
