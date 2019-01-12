import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

public class InstructionScene extends Scene{

    private static BridgeToSuccess bridgeToSuccess;

    private static Group root;

    private static Image instructionSceneAsImage;
    private static ImageView instructionSceneImageViewer;

    private static Button backButton;

    public InstructionScene(BridgeToSuccess bridgeToSuccess){

        super(buildScene());

        this.bridgeToSuccess = bridgeToSuccess;

        this.getStylesheets().add(getClass().getResource("/Design.css").toExternalForm());
        this.setFill(Color.rgb(192, 192, 192));

    }

    private static Group buildScene(){

        root = new Group();

        instructionSceneAsImage = new Image(InstructionScene.class.getResourceAsStream("/InstructionScene2.png"));

        instructionSceneImageViewer = new ImageView(instructionSceneAsImage);
        instructionSceneImageViewer.setX(0);
        instructionSceneImageViewer.setY(0);

        backButton = new Button("Back");
        backButton.getStyleClass().add("backButton");
        backButton.setLayoutX(50);
        backButton.setLayoutY(680);

        backButton.setOnAction((event) -> {

            Media buttonClickedSound = new Media(MenuScene.class.getResource("/ButtonClicked.mp3").toString());
            MediaPlayer mediaPlayer = new MediaPlayer(buttonClickedSound);
            mediaPlayer.play();

            bridgeToSuccess.primaryStage.setScene(bridgeToSuccess.menuScene);

        });

        root.getChildren().add(instructionSceneImageViewer);
        root.getChildren().add(backButton);

        return root;

    }

}
