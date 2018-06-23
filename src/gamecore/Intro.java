package gamecore;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Pantalla inicial.
 */
public class Intro extends GameEngine{
    /**
     * Contiene la pantalla incial.
     */
    private AnchorPane anchorPane;
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
        this.anchorPane = introPane;
        this.primaryStage = primaryStage;
        this.firstStageScene = firstLevelScene;

        makeBackground(anchorPane);

        Image logoImage = new Image("images/logo1.png",600,350,true,false);
        ImageView logo = new ImageView(logoImage);
        logo.setX(100);
        logo.setY(40);

        anchorPane.getChildren().add(logo);

        Label introLabel = new Label();
        introLabel.setText("Presiona ENTER para iniciar");
        try {
            introLabel.setFont(Font.loadFont(new FileInputStream("src/font/space_invaders.ttf"),20));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        introLabel.setTranslateX(220);
        introLabel.setTranslateY(450);
        introLabel.setTextFill(Color.valueOf("F8FFFD"));
        anchorPane.getChildren().add(introLabel);

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
        anchorPane.getScene().setOnKeyPressed(
                (keyEvent)->{
                    if(keyEvent.getCode()==KeyCode.ENTER){
                        //System.out.println("works");
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
