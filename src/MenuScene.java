import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class MenuScene extends Scene{

    private static BridgeToSuccess bridgeToSuccess;

    private static Group root;

    private static Image menuBgAsImage;
    private static ImageView menuBGAsImageView;

    private static Text titleText;
    private static Button startButton;
    private static Button instructionButton;

    public MenuScene(BridgeToSuccess bridgeToSuccess){

        super(buildScene());

        this.bridgeToSuccess = bridgeToSuccess;

        this.getStylesheets().add(getClass().getResource("/Design.css").toExternalForm());
        this.setFill(Color.rgb(31, 191, 82));

    }

    private static Group buildScene(){

        root = new Group();

        menuBgAsImage = new Image(MenuScene.class.getResourceAsStream("/MenuBG.jpg")); // Image Source: https://dumielauxepices.net/drawn-jungle/drawn-jungle-back-ground
        menuBGAsImageView = new ImageView(menuBgAsImage);
        menuBGAsImageView.setX(0);
        menuBGAsImageView.setY(0);

        titleText = new Text("Bridge to Success");
        titleText.getStyleClass().add("titleText");
        titleText.setX(150);
        titleText.setY(120);

        startButton = new Button("Start");
        startButton.getStyleClass().add("startButton");
        startButton.setLayoutX(300);
        startButton.setLayoutY(300);

        startButton.setOnAction((event) -> {

            Media buttonClickedSound = new Media(MenuScene.class.getResource("/ButtonClicked.mp3").toString());
            MediaPlayer mediaPlayer = new MediaPlayer(buttonClickedSound);
            mediaPlayer.play();

            bridgeToSuccess.gameScene = new GameScene(bridgeToSuccess);
            bridgeToSuccess.primaryStage.setScene(bridgeToSuccess.gameScene);

        });

        instructionButton = new Button("Instructions");
        instructionButton.getStyleClass().add("instructionButton");
        instructionButton.setLayoutX(300);
        instructionButton.setLayoutY(425);

        instructionButton.setOnAction((event) -> {

            Media buttonClickedSound = new Media(MenuScene.class.getResource("/ButtonClicked.mp3").toString());
            MediaPlayer mediaPlayer = new MediaPlayer(buttonClickedSound);
            mediaPlayer.play();

            bridgeToSuccess.instructionScene = new InstructionScene(bridgeToSuccess);
            bridgeToSuccess.primaryStage.setScene(bridgeToSuccess.instructionScene);

        });

        root.getChildren().add(menuBGAsImageView);
        root.getChildren().add(titleText);
        root.getChildren().add(startButton);
        root.getChildren().add(instructionButton);

        return root;

    }

}
