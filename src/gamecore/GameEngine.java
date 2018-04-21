package gamecore;

import adt.CircularNode;
import adt.IList;
import adt.INode;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import sprites.Invader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * GameEngine: Motor del juego.
 */
public abstract class GameEngine {
    /**
     * Row Label
     */
    Label rowLabel;
    /**
     * Next row label
     */
    Label nextRowLabel;
    /**
     * Level Label
     */
    Label levelLabel;
    /**
     * Score label
     */
    Label scoreLabel;

    /**
     * Score
     */
    static int score;

    /**
     * Actualiza cada animacion en el juego.
     */
    public abstract void update();

    /**
     * Se encarga de dibujar los componentes necesarios.
     *
     * @param gc Graphics Context: Dibuja dentro de un canvas mediante un buffer.
     */
    public abstract void render(GraphicsContext gc);

    void makeLabels(AnchorPane anchorPane){

        rowLabel = new Label();
        try {
            rowLabel.setFont(Font.loadFont(new FileInputStream("src/font/space_invaders.ttf"),13));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        rowLabel.setTranslateX(800-200);
        rowLabel.setTranslateY(0);
        rowLabel.setTextFill(Color.valueOf("FFFFFF"));
        anchorPane.getChildren().add(rowLabel);

        nextRowLabel = new Label();
        try {
            nextRowLabel.setFont(Font.loadFont(new FileInputStream("src/font/space_invaders.ttf"),13));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        nextRowLabel.setTranslateX(800-200);
        nextRowLabel.setTranslateY(20);
        nextRowLabel.setTextFill(Color.valueOf("FFFFFF"));
        anchorPane.getChildren().add(nextRowLabel);

        scoreLabel = new Label();
        try {
            scoreLabel.setFont(Font.loadFont(new FileInputStream("src/font/space_invaders.ttf"),13));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        scoreLabel.setTranslateX(10);
        scoreLabel.setTranslateY(0);
        scoreLabel.setTextFill(Color.valueOf("FFFFFF"));
        anchorPane.getChildren().add(scoreLabel);

        levelLabel = new Label();
        try {
            levelLabel.setFont(Font.loadFont(new FileInputStream("src/font/space_invaders.ttf"),13));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        levelLabel.setTranslateX(10);
        levelLabel.setTranslateY(600-30);
        levelLabel.setTextFill(Color.valueOf("FFFFFF"));
        anchorPane.getChildren().add(levelLabel);
    }

    /**
     * Crea el background del juego.
     * Fondo del juego.
     * @param anchorPane anchorPane actual.
     */
    void makeBackground(AnchorPane anchorPane){

        Image background = new Image("images/background6.jpg");
        BackgroundImage bgImage = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        anchorPane.setBackground(new Background(bgImage));
    }

    /**
     * BubbleSort: Ordena lista de mayor a menor resistencia.
     * @param invaderList Lista de enemigos
     */
    void bubbleSort(IList<Invader> invaderList){
        for (int i = 0; i < invaderList.size() - 1; i++) {
            for (int j = 0; j < invaderList.size() - 1 - i; j++) {
                if (invaderList.getAtPos(j).getLife() < invaderList.getAtPos(j + 1).getLife()){
                    INode<Invader> node1 = invaderList.getNode(j);
                    INode<Invader> node2 = invaderList.getNode(j + 1);
                    CircularNode<Invader> temp = new CircularNode<>(node1.getData());

                    node1.setData(node2.getData());
                    node2.setData(temp.getData());
                }
            }
        }
    }

    /**
     * Cambia de posicion al jefe de manera aleatoria.
     * @param invaderList lista de enemigos.
     * @param type Tipo de enemigo.
     */
    void doSwitch(IList<Invader> invaderList, String type){
        int randomChange = (int) (Math.random() * invaderList.size()), bossPos = 0;

        for (int i = 0; i < invaderList.size(); i++) {
            if (invaderList.getAtPos(i).getInvaderType().equals("Boss"))
                bossPos = i;
        }
        if (invaderList.size() != 0){
            while (randomChange == bossPos){
                randomChange = (int) (Math.random() * invaderList.size());
            }
        }
        double changeX = invaderList.getAtPos(randomChange).getX(),
                changeY = invaderList.getAtPos(randomChange).getY(),
                changeRight = invaderList.getAtPos(randomChange).getMovesToRight(),
                changeLeft = invaderList.getAtPos(randomChange).getMovesToLeft();

        double bossX = invaderList.getAtPos(bossPos).getX()
                , bossY = invaderList.getAtPos(bossPos).getY()
                , bossRight = invaderList.getAtPos(bossPos).getMovesToRight()
                , bossLeft = invaderList.getAtPos(bossPos).getMovesToLeft();

        if (type.equals("ClaseC")){
            double lastX = invaderList.getAtPos(0).getX();

            if (lastX >= 800 - 80 * invaderList.size())
                lastX = (800 - 80 * invaderList.size()) - 1;

            if (invaderList.size() == 1)
                invaderList.removeAtPos(bossPos);

            else{
                score =+ invaderList.getAtPos(bossPos).getScore();
                invaderList.removeAtPos(bossPos);

                invaderList.getAtPos(randomChange).setInvaderType("Boss");

                int count = invaderList.size();

                for (int k = 0; k < invaderList.size(); k++) {
                    invaderList.getAtPos(k).setPosition(
                            lastX + 80 * k,
                            invaderList.getAtPos(k).getY(),
                            count, k);
                    count--;
                }
            }
        }
        else {
            invaderList.getAtPos(bossPos).setPosition(changeX
                    ,changeY
                    ,changeRight
                    ,changeLeft);

            invaderList.getAtPos(randomChange).setPosition(bossX
                    , bossY
                    , bossRight
                    ,bossLeft);
        }
    }
}
