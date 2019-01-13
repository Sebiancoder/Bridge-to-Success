import javafx.application.Application;
import javafx.stage.Stage;

public class BridgeToSuccess extends Application{

    public Stage primaryStage;

    public MenuScene menuScene;
    public GameScene gameScene;
    public InstructionScene instructionScene;

    public int windowWidth;
    public int windowHeight;

    public static void main(String args[]){

        System.out.println("6.02 \u2715 10\u00B2\u00B2");

        launch(args);

    }

    @Override
    public void start(Stage primaryStage){

        this.primaryStage = primaryStage;

        this.windowWidth = 800;
        this.windowHeight = 800;

        menuScene = new MenuScene(this);

        primaryStage.setTitle("Bridge To Success");
        primaryStage.setWidth(windowWidth);
        primaryStage.setHeight(windowHeight);
        primaryStage.setResizable(false);
        primaryStage.setScene(menuScene);

        primaryStage.show();

    }

}
