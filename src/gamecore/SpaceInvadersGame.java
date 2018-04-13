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

        AnchorPane introPane = new AnchorPane();
        AnchorPane firstStagePane = new AnchorPane();
        Scene menuScene = new Scene(introPane, WIDTH, HEIGHT);
        Scene firstStageScene = new Scene(firstStagePane, WIDTH, HEIGHT);
        primaryStage = new Stage();
        primaryStage.setScene(menuScene);
        canvas = new Canvas(WIDTH,HEIGHT);
        firstStagePane.getChildren().add(canvas);
        Intro intro = new Intro(introPane, primaryStage, firstStageScene);
        FirstLevel firstLevel = new FirstLevel(firstStagePane);

        levelList = new LinkedList<>();
        levelList.add(intro);
        levelList.add(firstLevel);
    }

    /**
     * Obtiene el Stage principal del juego.
     * @return primaryStage
     */
    public Stage getStage(){
        return primaryStage;
    }

    /**
     * Inicia el juego.
     */
    public void init(){

        gc = canvas.getGraphicsContext2D();
        levelList.getAtPos(levelNumber).update();
        levelList.getAtPos(levelNumber).render(gc);
    }
}
