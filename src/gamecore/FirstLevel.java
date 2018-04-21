package gamecore;

import adt.LinkedList;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sprites.Bullet;
import sprites.Defender;
import sprites.Invader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Nivel 1.
 */
public class FirstLevel extends GameEngine{
    /**
     * Contiene primer nivel.
     */
    private AnchorPane anchorPane;

    /**
     * Principal Stage.
     */
    private Stage primaryStage;
    /**
     * Escena primer nivel.
     */
    private Scene secondStageScene;

    /**
     * Atributo jugador.
     */
    private Defender player;

    /**
     * Balas del juego.
     */
    private LinkedList<Bullet> bullets;

    private LinkedList<LinkedList<Invader>> invadersMatrix;
    /**
     * Hilera actual.
     */
    private int current;

    /**
     * Contructor del primer nivel.
     * @param anchorPane Nivel 1.
     * @param primaryStage Stage base.
     * @param secondStageScene Escena nivel 2.
     */
    FirstLevel(AnchorPane anchorPane, Stage primaryStage, Scene secondStageScene) {
        this.anchorPane = anchorPane;
        this.primaryStage = primaryStage;
        this.secondStageScene = secondStageScene;

        player = new Defender(new Image("images/ship1.png"),new Image("images/ship1-x1.png"),
                new Image("images/ship1+x.png"),800/2-30, 600-60);
        bullets = new LinkedList<>();

        invadersMatrix = new LinkedList<>();

        makeBackground(anchorPane);
        generateRows();

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
    }

    /**
     * Genera las hileras enemigas.
     */
    private void generateRows(){
        for (int i = 0; i < 5; i++) {
            invadersMatrix.add(new LinkedList<>());
        }

        for (int i = 0; i < 4; i++) {

            /*
              Elige un numero entre 0 y 1
             */
            int randomRow = (int) (Math.random() * 2);

            if(randomRow == 0)
                makeBasicClass(invadersMatrix.getAtPos(i), 30);

            if (randomRow == 1){
                int randomPos = (int) (Math.random() * 7);
                makeClassA(invadersMatrix.getAtPos(i), randomPos);
            }
        }

        makeBasicClass(invadersMatrix.getAtPos(4), -150);
    }

    /**
     * Crea la hilera Clase A.
     * @param invaderList Lista de enemigos.
     * @param pos Posicion del Jefe.
     */
    private void makeClassA(LinkedList<Invader> invaderList, int pos){

        String invaderImage = "images/mship1.png";
        String bossImage = "images/invader.png";

        int xPos = 7;
        invaderList.setType("ClassA");

        for (int i = 0; i < 7; i++) {
            if (i == pos) {
                invaderList.add(new Invader(new Image(bossImage,80,77,true,false)
                        ,80 * i, 30, i, xPos, "Boss"));
            }
            else{
                invaderList.add(new Invader(new Image(invaderImage,67,67,true,false),
                        80 * i, 30, i, xPos, "Minion"));
            }
            xPos--;
        }
    }

    /**
     * Crea la hilera Basic.
     * @param invaderList Lista de enemigos.
     * @param posY Posicion en Y.
     */
    private void makeBasicClass(LinkedList<Invader> invaderList, int posY){

        String invaderImage = "images/mship1.png";

        int xPos = 7;
        if (posY != -150)
            invaderList.setType("Basic");
        else invaderList.setType("");

        for (int i = 0; i < 7; i++) {
            invaderList.add(new Invader(new Image(invaderImage,67,67,true,false),
                    80 * i, posY, i, xPos,"Minion"));
            xPos--;
        }
    }
    @Override
    public void update() {

        if (current == 4){
            primaryStage.setScene(secondStageScene);
            SpaceInvadersGame.levelNumber++;
            Invader.speed = 2.5;
        }

        player.controllingDefender(anchorPane,player,bullets);

        player.updateBullets(bullets);

        checkCollisionAtLevel1();

        updateInvaderAtLevel1();

        player.update();

        String currentRow = invadersMatrix.getAtPos(current).getType();
        rowLabel.setText("Hilera actual: "+currentRow);

        String nextRow = "Siguiente: ";

        if (current + 1 >= invadersMatrix.size())
            nextRow += "";
        else
            nextRow += invadersMatrix.getAtPos(current + 1).getType();

        nextRowLabel.setText(nextRow);
    }

    /**
     * Dibuja la nave del jugador.
     * @param gc Graphics Context: Dibuja dentro de un canvas mediante un buffer.
     */
    @Override
    public void render(GraphicsContext gc){
        gc.clearRect(0 , 0, 800, 600);

        player.render(gc);

        for (int i = 0; i < invadersMatrix.getAtPos(current).size(); i++) {
            invadersMatrix.getAtPos(current).getAtPos(i).render(gc);
        }

        for (int i = 0; i < bullets.size(); i++) {
            bullets.getAtPos(i).render(gc);
        }
    }

    /**
     * Actualiza enemigos.
     */
    private void updateInvaderAtLevel1() {

        if (current != invadersMatrix.size() - 1) {

            for (int i = 0; i < invadersMatrix.getAtPos(current).size(); i++) {
                Invader invader = invadersMatrix.getAtPos(current).getAtPos(i);
                invader.update();
            }
        }

        if (invadersMatrix.getAtPos(current).size() == 0) {
            current++;
            Invader.speed += 0.25;
        }
    }


    /**
     * Verifica colisiones.
     */
    private void checkCollisionAtLevel1() {
        for (int j = 0; j < bullets.size(); j++) {
            Bullet bullet = bullets.getAtPos(j);

            for (int k = 0; k < invadersMatrix.getAtPos(current).size(); k++) {

                if (bullet.isColliding(invadersMatrix.getAtPos(current).getAtPos(k))) {
                    double lastX = 0;

                    if (invadersMatrix.getAtPos(current).getAtPos(k).getInvaderType().equals("Boss")) {

                        if (invadersMatrix.getAtPos(current).getType().equals("ClassA"))
                            classACollide(invadersMatrix.getAtPos(current));

                        bullets.removeAtPos(j);

                    } else {
                        if (invadersMatrix.getAtPos(current).size() > 0)
                            lastX = invadersMatrix.getAtPos(current).getAtPos(0).getX();
                        invadersMatrix.getAtPos(current).removeAtPos(k);
                        bullets.removeAtPos(j);

                        int count = invadersMatrix.getAtPos(current).size();

                        for (int l = 0; l < invadersMatrix.getAtPos(current).size(); l++) {
                            invadersMatrix.getAtPos(current).getAtPos(l).setPosition(
                                    lastX + 80 * l,
                                    invadersMatrix.getAtPos(current).getAtPos(l).getY(),
                                    count, l);
                            count--;
                        }
                    }
                }
            }
        }
    }

    /**
     * Colision clase A.
     * Destruye hilera cuando el jefe se queda sin vidas.
     * @param invaderList Lista de enemigos.
     */
    private void classACollide(LinkedList<Invader> invaderList){
        for (int i = 0; i < invaderList.size(); i++) {
            if (invaderList.getAtPos(i).getLife() != 1)
                invaderList.getAtPos(i).setLife(invaderList.getAtPos(i).getLife() - 1);
            else {
                Invader.speed += 0.25;
                current++;
            }
        }
    }
}
