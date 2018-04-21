package gamecore;

import adt.LinkedList;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Base del juego, Space Invaders.
 */
public class SpaceInvadersGame{

    /**
     * Ancho de la pantalla.
     */
    private static final int WIDTH = 800;

    /**
     * Alto de la pantalla.
     */
    private static final int HEIGHT = 600;

    private Stage primaryStage;

    /**
     * Canvas: Campo para dibujar.
     */
    private Canvas canvas;

    /**
     * Canvas2: Campo para dibujar.
     */
    private Canvas canvas2;
    /**
     * Canvas3: Campo para dibujar.
     */
    private Canvas canvas3;

    /**
     * Lista para almacenar cada nivel.
     */
    private LinkedList<GameEngine> levelList;

    /**
     * Numero de nivel.
     */
    static int levelNumber;

    private GraphicsContext gc;

    /**
     * Constructor del Juego.
     */
    public SpaceInvadersGame() {

        primaryStage = new Stage();

        //Intro
        AnchorPane introPane = new AnchorPane();
        Scene introScene = new Scene(introPane, WIDTH, HEIGHT);

        //Nivel1
        AnchorPane firstStagePane = new AnchorPane();
        Scene firstStageScene = new Scene(firstStagePane, WIDTH, HEIGHT);
        canvas = new Canvas(WIDTH, HEIGHT);
        firstStagePane.getChildren().add(canvas);

        //Nivel2
        AnchorPane secondStagePane = new AnchorPane();
        Scene secondStageScene = new Scene(secondStagePane, WIDTH, HEIGHT);
        canvas2 = new Canvas(WIDTH, HEIGHT);
        secondStagePane.getChildren().add(canvas2);

        //Nivel3
        AnchorPane thirdStagePane = new AnchorPane();
        Scene thirdStageScene = new Scene(thirdStagePane, WIDTH, HEIGHT);
        canvas3 = new Canvas(WIDTH, HEIGHT);
        thirdStagePane.getChildren().add(canvas3);

        //EndGame
        AnchorPane endStagePane = new AnchorPane();
        Scene endStageScene = new Scene(endStagePane,WIDTH,HEIGHT);


        Intro intro = new Intro(introPane, primaryStage, firstStageScene);
        FirstLevel firstLevelState = new FirstLevel(firstStagePane, primaryStage, secondStageScene, endStageScene);
        SecondLevel secondLevelState = new SecondLevel(secondStagePane, primaryStage, thirdStageScene, endStageScene);
        ThirdLevel thirdLevelState = new ThirdLevel(thirdStagePane,primaryStage, endStageScene);
        End endGame = new End(endStagePane,primaryStage);

        levelList = new LinkedList<>();
        levelList.add(intro);
        levelList.add(firstLevelState);
        levelList.add(secondLevelState);
        levelList.add(thirdLevelState);
        levelList.add(endGame);

        primaryStage.setScene(introScene);
    }

    public Stage getPrimaryStage(){
        return primaryStage;
    }

    /**
     * Empieza el Juego.
     */
    public void init(){

        chooseCanvas();
        levelList.getAtPos(levelNumber).update();
        levelList.getAtPos(levelNumber).render(gc);
    }

    /**
     * Elige el canvas segun el nivel actual.
     */
    private void chooseCanvas(){

        if (levelNumber == 1)
            gc = canvas.getGraphicsContext2D();
        if (levelNumber == 2)
            gc = canvas2.getGraphicsContext2D();
        if (levelNumber == 3)
            gc = canvas3.getGraphicsContext2D();
    }
}
