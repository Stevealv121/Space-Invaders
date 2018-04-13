package gamecore;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Pantalla inicial.
 */
public class Intro extends GameEngine{
    /**
     * Contiene la pantalla incial.
     */
    private AnchorPane pane;
    /**
     * Principal Stage.
     */
    private Stage primaryStage;
    /**
     * Escena primer nivel.
     */
    private Scene firstStageScene;

    /**
     * Contructor del intro.
     * @param introPane Almacena la pantalla incial.
     * @param primaryStage Stage principal.
     * @param firstLevelScene Scene del nivel 1.
     */
    public Intro(AnchorPane introPane, Stage primaryStage, Scene firstLevelScene){
        this.pane = introPane;
        this.primaryStage = primaryStage;
        this.firstStageScene = firstLevelScene;

        Image background = new Image("images/background6.jpg");
        BackgroundImage bgImage = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        pane.setBackground(new Background(bgImage));

        startGame();
    }

    /**
     * Incia el juego.
     * Espera a que el usuario oprima "Enter".
     */
    public void startGame(){
        new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event){
                if(event.getCode()== KeyCode.ENTER){
                    primaryStage.setScene(firstStageScene);
                    SpaceInvadersGame.levelNumber++;
                }
            }
        };
    }

    /**
     * Oprimir "Enter" para iniciar juego.
     */
    @Override
    public void update() {
        pane.getScene().setOnKeyPressed(
                (keyEvent)->{
                    if(keyEvent.getCode()==KeyCode.ENTER){
                        System.out.println("works");
                        primaryStage.setScene(firstStageScene);
                        SpaceInvadersGame.levelNumber++;

                    }
                }
        );

    }

    @Override
    public  void render(GraphicsContext gc){

    }

}
