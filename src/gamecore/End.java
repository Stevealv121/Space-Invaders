package gamecore;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Fin del Juego.
 * Por victoria o derrota.
 */
public class End extends GameEngine{

    /**
     * Contiene primer nivel.
     */
    private AnchorPane anchorPane;

    /**
     * Principal Stage.
     */
    private Stage primaryStage;
    /**
     * Condicion:
     * Victoria o Derrota.
     */
    static String condition;
    /**
     * Label Victory or Defeat
     */
    private Label conditionLabel;

    /**
     * Contructor End
     * @param anchorPane AnchorPane endGame.
     * @param primaryStage Base Stage.
     */
    End(AnchorPane anchorPane, Stage primaryStage){

        this.anchorPane = anchorPane;
        this.primaryStage = primaryStage;

        makeBackground(anchorPane);

        conditionLabel = new Label();
        try {
            conditionLabel.setFont(Font.loadFont(new FileInputStream("src/font/space_invaders.ttf"),40));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        conditionLabel.setTranslateX(250);
        conditionLabel.setTranslateY(250);
        conditionLabel.setTextFill(Color.valueOf("FFFFFF"));
        anchorPane.getChildren().add(conditionLabel);

    }

    @Override
    public void update() {

        if(condition.equals("Victory")){
            conditionLabel.setText("Victoria!!");
        }else if(condition.equals("Defeat")){
            conditionLabel.setText("Has Perdido");
        }

    }

    @Override
    public void render(GraphicsContext gc) {

    }
}
