import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class GameScene extends Scene{

    private static BridgeToSuccess bridgeToSuccess;
    private static QuestionManager questionManager;

    private static Group root;

    private static Text blueQuestionText;
    private static Text[] blueAnswers;
    private static Text redQuestionText;
    private static Text[] redAnswers;
    private static Line questionDivider;

    private static Rectangle lava;
    private static Tile[][] boardArray;

    private static Boolean blueQuestionAsked;
    private static Question currentBlueQuestion;
    private static Boolean redQuestionAsked;
    private static Question currentRedQuestion;

    private static Tile redPosition;
    private static Integer[] redPositionCoords; // in form y, x
    private static Tile redPending;
    private static Integer[] redPendingCoords; // in form y, x

    private static Tile bluePosition;
    private static Integer[] bluePositionCoords; // in form y, x
    private static Tile bluePending;
    private static Integer[] bluePendingCoords; // in form y, x

    public GameScene(BridgeToSuccess bridgeToSuccess){

        super(buildScene());

        this.bridgeToSuccess = bridgeToSuccess;

        this.getStylesheets().add(getClass().getResource("/design.css").toExternalForm());
        this.setFill((Color.rgb(56, 191, 82)));

        questionManager = new QuestionManager();

        // The following code is for event handling //

        blueQuestionAsked = false;
        redQuestionAsked = false;

        bluePositionCoords = new Integer[2];
        bluePositionCoords[0] = 9;
        bluePositionCoords[1] = 0;
        bluePosition = boardArray[bluePositionCoords[0]][bluePositionCoords[1]];
        bluePosition.setId("tileCapturedByBlue");

        bluePendingCoords = new Integer[2];

        redPositionCoords = new Integer[2];
        redPositionCoords[0] = 9;
        redPositionCoords[1] = 9;
        redPosition = boardArray[redPositionCoords[0]][redPositionCoords[1]];
        redPosition.setId("tileCapturedByRed");

        redPendingCoords = new Integer[2];

        this.setOnKeyPressed(new KeyEventHandler());

    }

    private class KeyEventHandler implements EventHandler<KeyEvent>{

        @Override
        public void handle(KeyEvent event){

            if(blueQuestionAsked) {

                switch (event.getCode()) {

                    case DIGIT1: handleQuestionGuess(0, "BLUE"); break;
                    case DIGIT2: handleQuestionGuess(1, "BLUE"); break;
                    case DIGIT3: handleQuestionGuess(2, "BLUE"); break;

                }

            } if (redQuestionAsked) {

                switch (event.getCode()) {

                    case DIGIT7: handleQuestionGuess(0, "RED"); break;
                    case DIGIT8: handleQuestionGuess(1, "RED"); break;
                    case DIGIT9: handleQuestionGuess(2, "RED"); break;

                }

            } if (!blueQuestionAsked) {

                switch (event.getCode()){

                    case A: handleMovement("LEFT", "BLUE"); break;
                    case S: handleMovement("DOWN", "BLUE"); break;
                    case W: handleMovement("UP", "BLUE"); break;
                    case D: handleMovement("RIGHT", "BLUE"); break;


                }

            } if (!redQuestionAsked) {

                switch (event.getCode()){

                    case UP: handleMovement("UP", "RED"); break;
                    case LEFT: handleMovement("LEFT", "RED"); break;
                    case RIGHT: handleMovement("RIGHT", "RED"); break;
                    case DOWN: handleMovement("DOWN", "RED"); break;

                }

            }

        }

    }

    private static Group buildScene(){

        root = new Group();

        blueQuestionText = new Text();
        blueQuestionText.getStyleClass().add("questionTextBlue");
        blueQuestionText.setWrappingWidth(370);
        blueQuestionText.setX(15);
        blueQuestionText.setY(25);

        blueAnswers = new Text[3];

        redQuestionText = new Text();
        redQuestionText.getStyleClass().add("questionTextRed");
        redQuestionText.setWrappingWidth(370);
        redQuestionText.setX(415);
        redQuestionText.setY(25);

        redAnswers = new Text[3];

        for (int i = 0; i < blueAnswers.length; i++){

            blueAnswers[i] = new Text();
            blueAnswers[i].getStyleClass().add("answerTextBlue");
            blueAnswers[i].setWrappingWidth(370);
            blueAnswers[i].setX(20);
            blueAnswers[i].setY(80 + 50 * i);

            redAnswers[i] = new Text();
            redAnswers[i].getStyleClass().add("answerTextRed");
            redAnswers[i].setWrappingWidth(370);
            redAnswers[i].setX(420);
            redAnswers[i].setY(80 + 50 * i);

        }

        questionDivider = new Line(400, 25, 400, 225);

        lava = new Rectangle(0, 240, 800, 525);
        lava.getStyleClass().add("lava");

        boardArray = new Tile[10][10];
        populateBoard();

        root.getChildren().add(blueQuestionText);
        root.getChildren().add(redQuestionText);
        root.getChildren().add(questionDivider);
        root.getChildren().add(lava);

        for (int i = 0; i < boardArray.length; i++){
            for (int j = 0; j < boardArray[i].length; j++){

                root.getChildren().add(boardArray[i][j]);

            }
        }

        for (int i = 0; i < blueAnswers.length; i++){

            root.getChildren().add(blueAnswers[i]);
            root.getChildren().add(redAnswers[i]);

        }

        return root;

    }

    private static void populateBoard(){

        for (int i = 0; i < boardArray.length; ++i){
            for (int j = 0; j < boardArray[i].length; ++j){

                boardArray[i][j] = new Tile();
                boardArray[i][j].setWidth(50);
                boardArray[i][j].setHeight(50);
                boardArray[i][j].setState("CLEAR");
                boardArray[i][j].getStyleClass().add("tileClear");
                boardArray[i][j].setX(150 + 51 * j);
                boardArray[i][j].setY(250 + 51 * i);

            }
        }
    }

    private static void handleQuestionGuess(int answerIndex, String player){

        if (player == "BLUE"){

            if (currentBlueQuestion.possibleAnswers[answerIndex].equals(currentBlueQuestion.correctAnswer)){

                System.out.println("answer correct");

                bluePending.setState("CAPTURED");

                System.out.println(bluePending.getState());

                bluePositionCoords[0] = bluePendingCoords[0];
                bluePositionCoords[1] = bluePendingCoords[1];
                bluePosition = boardArray[bluePositionCoords[0]][bluePositionCoords[1]];

                bluePending.setId("tileCapturedByBlue");

                System.out.println(bluePositionCoords[0].toString() + " " + bluePositionCoords[1].toString());

                // bluePending = null;
                bluePendingCoords[0] = null;
                bluePendingCoords[1] = null;

                blueQuestionAsked = false;

                checkForVictory();

            } else {

                bluePending.setId("tileDestroyed");
                bluePending.setState("DESTROYED");

                System.out.println(bluePending.getState());

                bluePending = null;
                bluePendingCoords[0] = null;
                bluePendingCoords[1] = null;

                blueQuestionAsked = false;

                System.out.println("Answer false");

            }

        } else {

            System.out.println("handlequestionguess running");

            if (currentRedQuestion.possibleAnswers[answerIndex].equals(currentRedQuestion.correctAnswer)){

                System.out.println("answer correct");

                redPending.setState("CAPTURED");

                System.out.println(redPending.getState());

                redPositionCoords[0] = redPendingCoords[0];
                redPositionCoords[1] = redPendingCoords[1];
                redPosition = boardArray[redPositionCoords[0]][redPositionCoords[1]];

                redPosition.setId("tileCapturedByRed");

                System.out.println(redPositionCoords[0].toString() + " " + redPositionCoords[1].toString());

                redPending = null;
                redPendingCoords[0] = null;
                redPendingCoords[1] = null;

                redQuestionAsked = false;

                checkForVictory();

            } else {

                redPending.setId("tileDestroyed");
                redPending.setState("DESTROYED");

                System.out.println(redPending.getState());

                redPending = null;
                redPendingCoords[0] = null;
                redPendingCoords[1] = null;

                redQuestionAsked = false;

                System.out.println("Answer false");

            }

        }
    }

    private static void handleMovement(String direction, String player){

        if (direction == "UP"){

            try {

                if (player == "BLUE"){

                    bluePendingCoords[0] = bluePositionCoords[0] - 1;
                    bluePendingCoords[1] = bluePositionCoords[1];

                    if (boardArray[bluePendingCoords[0]][bluePendingCoords[1]].getState() == "CLEAR") {

                        bluePending = boardArray[bluePendingCoords[0]][bluePendingCoords[1]];

                        bluePending.getStyleClass().add("tilePendingByBlue");
                        bluePending.setState("PENDING");

                        setCurrentBlueQuestion();

                    } else {

                        bluePendingCoords[0] = null;
                        bluePendingCoords[1] = null;

                    }

                } else {

                    redPendingCoords[0] = redPositionCoords[0] - 1;
                    redPendingCoords[1] = redPositionCoords[1];

                    if (boardArray[redPendingCoords[0]][redPendingCoords[1]].getState() == "CLEAR") {

                        redPending = boardArray[redPendingCoords[0]][redPendingCoords[1]];

                        redPending.getStyleClass().add("tilePendingByRed");
                        redPending.setState("PENDING");

                        setCurrentRedQuestion();

                    } else {

                        redPendingCoords[0] = null;
                        redPendingCoords[1] = null;

                    }

                }

            } catch (IndexOutOfBoundsException nonExistentTile) {}

        } else if (direction == "DOWN"){

            try {

                if (player == "BLUE"){

                    bluePendingCoords[0] = bluePositionCoords[0] + 1;
                    bluePendingCoords[1] = bluePositionCoords[1];

                    if (boardArray[bluePendingCoords[0]][bluePendingCoords[1]].getState() == "CLEAR") {

                        bluePending = boardArray[bluePendingCoords[0]][bluePendingCoords[1]];

                        bluePending.getStyleClass().add("tilePendingByBlue");
                        bluePending.setState("PENDING");

                        setCurrentBlueQuestion();

                    } else {

                        bluePendingCoords[0] = null;
                        bluePendingCoords[1] = null;

                    }

                } else {

                    redPendingCoords[0] = redPositionCoords[0] + 1;
                    redPendingCoords[1] = redPositionCoords[1];

                    if (boardArray[redPendingCoords[0]][redPendingCoords[1]].getState() == "CLEAR") {

                        redPending = boardArray[redPendingCoords[0]][redPendingCoords[1]];

                        redPending.getStyleClass().add("tilePendingByRed");
                        redPending.setState("PENDING");

                        setCurrentRedQuestion();

                    } else {

                        redPendingCoords[0] = null;
                        redPendingCoords[1] = null;

                    }

                }

            } catch (IndexOutOfBoundsException nonExistentTile) {}

        } else if (direction == "LEFT"){

            try {

                if (player == "BLUE"){

                    bluePendingCoords[0] = bluePositionCoords[0];
                    bluePendingCoords[1] = bluePositionCoords[1] - 1;

                    if (boardArray[bluePendingCoords[0]][bluePendingCoords[1]].getState() == "CLEAR") {

                        bluePending = boardArray[bluePendingCoords[0]][bluePendingCoords[1]];

                        bluePending.getStyleClass().add("tilePendingByBlue");
                        bluePending.setState("PENDING");

                        setCurrentBlueQuestion();

                    } else {

                        bluePendingCoords[0] = null;
                        bluePendingCoords[1] = null;

                    }

                } else {

                    redPendingCoords[0] = redPositionCoords[0];
                    redPendingCoords[1] = redPositionCoords[1] - 1;

                    if (boardArray[redPendingCoords[0]][redPendingCoords[1]].getState() == "CLEAR") {

                        redPending = boardArray[redPendingCoords[0]][redPendingCoords[1]];

                        redPending.getStyleClass().add("tilePendingByRed");
                        redPending.setState("PENDING");

                        setCurrentRedQuestion();

                    } else {

                        redPendingCoords[0] = null;
                        redPendingCoords[1] = null;

                    }

                }

            } catch (IndexOutOfBoundsException nonExistentTile) {}

        } else if (direction == "RIGHT"){

            try {

                if (player == "BLUE"){

                    bluePendingCoords[0] = bluePositionCoords[0];
                    bluePendingCoords[1] = bluePositionCoords[1] + 1;

                    if (boardArray[bluePendingCoords[0]][bluePendingCoords[1]].getState() == "CLEAR") {

                        bluePending = boardArray[bluePendingCoords[0]][bluePendingCoords[1]];

                        bluePending.getStyleClass().add("tilePendingByBlue");
                        bluePending.setState("PENDING");

                        setCurrentBlueQuestion();

                    } else {

                        bluePendingCoords[0] = null;
                        bluePendingCoords[1] = null;

                    }

                } else {

                    redPendingCoords[0] = redPositionCoords[0];
                    redPendingCoords[1] = redPositionCoords[1] + 1;

                    if (boardArray[redPendingCoords[0]][redPendingCoords[1]].getState() == "CLEAR") {

                        redPending = boardArray[redPendingCoords[0]][redPendingCoords[1]];

                        redPending.getStyleClass().add("tilePendingByRed");
                        redPending.setState("PENDING");

                        setCurrentRedQuestion();

                    } else {

                        redPendingCoords[0] = null;
                        redPendingCoords[1] = null;

                    }

                }

            } catch (IndexOutOfBoundsException nonExistentTile) {}

        }

    }

    private static void setCurrentBlueQuestion(){

        blueQuestionAsked = true;

        currentBlueQuestion = questionManager.getQuestion();

        blueQuestionText.setText(currentBlueQuestion.question);

        for (int i = 0; i < blueAnswers.length; i++){

            blueAnswers[i].setText((i + 1) + ": " + currentBlueQuestion.possibleAnswers[i]);

        }

    }

    private static void setCurrentRedQuestion(){

        System.out.println("question being set");

        redQuestionAsked = true;

        currentRedQuestion = questionManager.getQuestion();

        redQuestionText.setText(currentRedQuestion.question);

        for (int i = 0; i < redAnswers.length; i++){

            redAnswers[i].setText((i + 7) + ": " + currentRedQuestion.possibleAnswers[i]);

        }

    }

    private static void checkForVictory(){

        if(redPositionCoords[0] == 0 && redPositionCoords[1] == 0){

            Alert victoryAlert = new Alert(Alert.AlertType.INFORMATION, "The Red Player has Won!");
            victoryAlert.showAndWait();

            bridgeToSuccess.primaryStage.setScene(bridgeToSuccess.menuScene);

        } else if (bluePositionCoords[0] == 0 && bluePositionCoords[1] == 9){

            Alert victoryAlert = new Alert(Alert.AlertType.INFORMATION, "The Blue Player has Won!");
            victoryAlert.showAndWait();

            bridgeToSuccess.primaryStage.setScene(bridgeToSuccess.menuScene);

        }

    }

}
