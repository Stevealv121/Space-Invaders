import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class Main extends Application{

    private static final double WIDTH = 800;
    private static final double HEIGHT = 600;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage){

        stage.setTitle("Space Invaders");
        AnchorPane root = new AnchorPane();
        Image background = new Image("images/background6.jpg");
        root.setBackground(new Background(new BackgroundImage(background,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,null)));
        Scene scene = new Scene(root,WIDTH,HEIGHT);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }
}
