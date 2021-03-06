package gamecore;

import adt.LinkedList;
import server.Server;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import sprites.Bullet;
import sprites.Defender;
import sprites.Invader;

import java.io.IOException;

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
     * Escena segundo nivel.
     */
    private Scene secondStageScene;
    /**
     * Escena fin del juego.
     */
    private Scene endStageScene;

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

    private Server server;

    /**
     * Contructor del primer nivel.
     * @param anchorPane Nivel 1.
     * @param primaryStage Stage base.
     * @param secondStageScene Escena nivel 2.
     * @param endStageScene Escena fin.
     */
    FirstLevel(AnchorPane anchorPane, Stage primaryStage, Scene secondStageScene, Scene endStageScene) {
        this.anchorPane = anchorPane;
        this.primaryStage = primaryStage;
        this.secondStageScene = secondStageScene;
        this.endStageScene = endStageScene;

        player = new Defender(new Image("images/ship1.png"),new Image("images/ship1-x1.png"),
                new Image("images/ship1+x.png"),800/2-30, 600-60);
        bullets = new LinkedList<>();

        invadersMatrix = new LinkedList<>();

        server = Server.getServer();

        makeBackground(anchorPane);
        generateRows();
        makeLabels(anchorPane);
        connectSever();
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

        String currentLevel = "Nivel 1";
        levelLabel.setText(currentLevel);

        scoreLabel.setText("Puntaje: "+score);

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

                if (invader.getY() > 600-80){
                    primaryStage.setScene(endStageScene);
                    End.condition = "Defeat";
                    SpaceInvadersGame.levelNumber = 4;
                }
            }
        }

        if (invadersMatrix.getAtPos(current).size() == 0) {
            current++;
            Invader.speed += 2;
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
                        score += invadersMatrix.getAtPos(current).getAtPos(k).getScore();
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
                score += invaderList.getAtPos(i).getScore();
                Invader.speed += 2;
                current++;
            }
        }
    }

    private void connectSever() {
        server.setPlayer(player);
        server.setBullets(bullets);

        //Permite correr el servidor sin que el thread principal deje de correr la interfaz del juego
        Thread thread = new Thread(() -> {
            try {
                server.run();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}
