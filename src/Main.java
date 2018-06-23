
import gamecore.SpaceInvadersGame;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Clase Main, aloja la ejecucion de la aplicacion.
 */
public class Main extends Application{

    /**
     * Atributo en el cual se instancia el juego.
     */
    private SpaceInvadersGame game = new SpaceInvadersGame();
    private final static int PORT = 5000;


    public static void main(String[] args) {

        launch(args);
    }

    /**
     * Inicia la aplicacion.
     * @param primaryStage Lugar donde se desarrolla el juego.
     */
    @Override
    public void start(Stage primaryStage){

        primaryStage = game.getPrimaryStage();

        primaryStage.setTitle("Space Invaders");

        Image icon = new Image(getClass().getResourceAsStream("images/icon.png"));
        primaryStage.getIcons().add(icon);

        primaryStage.show();

        new AnimationTimer(){
            @Override
            public void handle(long now) {
                game.init();
            }
        }.start();

    }



}
