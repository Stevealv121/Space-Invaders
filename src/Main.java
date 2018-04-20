
import gamecore.SpaceInvadersGame;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Clase Main, aloja la ejecucion de la aplicacion.
 */
public class Main extends Application{

    /**
     * Atributo en el cual se instancia el juego.
     */
    private SpaceInvadersGame game = new SpaceInvadersGame();


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
