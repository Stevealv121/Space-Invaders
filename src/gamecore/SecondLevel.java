package gamecore;

import adt.CircularIList;
import adt.DoubleLinkedIList;
import adt.LinkedList;
import adt.IList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import sprites.Bullet;
import sprites.Defender;
import sprites.Invader;


/**
 * Nivel 2.
 */
public class SecondLevel extends GameEngine {
    /**
     * Contiene el nivel 2.
     */
    private AnchorPane anchorPane;

    /**
     * Atributo jugador.
     */
    private Defender player;

    /**
     * Balas del juego.
     */
    private LinkedList<Bullet> bullets;

    private LinkedList<IList<Invader>> invadersMatrix;

    /**
     * Principal Stage.
     */
    private Stage primaryStage;
    /**
     * Escena tercer nivel.
     */
    private Scene thirdStageScene;
    /**
     * Escena fin del juego.
     */
    private Scene endStageScene;
    /**
     * Hileras actuales.
     */
    private int current;

    /**
     * Contructor del primer nivel.
     * @param anchorPane Nivel 2.
     * @param primaryStage Stage base.
     * @param thirdStageScene Escena nivel 3.
     * @param endStageScene Escena fin.
     */
    SecondLevel(AnchorPane anchorPane, Stage primaryStage, Scene thirdStageScene,Scene endStageScene){
        this.anchorPane = anchorPane;
        this.primaryStage = primaryStage;
        this.thirdStageScene = thirdStageScene;
        this.endStageScene = endStageScene;

        player = new Defender(new Image("images/ship1.png"),new Image("images/ship1-x1.png"),new Image("images/ship1+x.png"),800/2-30, 600-60);
        bullets = new LinkedList<>();

        invadersMatrix = new LinkedList<>();
        makeBackground(anchorPane);
        generateRows();
        makeLabels(anchorPane);

    }

    @Override
    public void update() {

        if (current == 6){
            primaryStage.setScene(thirdStageScene);
            SpaceInvadersGame.levelNumber++;
            Invader.speed = 3.6;
        }

        player.controllingDefender(anchorPane,player,bullets);
        player.update();

        checkCollisionsAtLevel2(invadersMatrix.getAtPos(current));
        player.updateBullets(bullets);
        updateInvaderAtLevel2();

        String currentRow = invadersMatrix.getAtPos(current).getType();
        rowLabel.setText("Hilera actual: "+currentRow);

        String nextRow = "Siguiente: ";

        if (current + 1 >= invadersMatrix.size())
            nextRow += "";
        else
            nextRow += invadersMatrix.getAtPos(current + 1).getType();

        nextRowLabel.setText(nextRow);

        String currentLevel = "Nivel 2";
        levelLabel.setText(currentLevel);
        scoreLabel.setText("Puntaje: "+score);
    }


    @Override
    public void render(GraphicsContext gc) {
        gc.clearRect(0 , 0, 800, 600);

        player.render(gc);

        for (int i = 0; i < invadersMatrix.getAtPos(current).size(); i++) {
            invadersMatrix.getAtPos(current).getAtPos(i).render(gc);
        }

        for (int i = 0; i < bullets.size(); i++) {
            bullets.getAtPos(i).render(gc);
        }
    }

    private void updateInvaderAtLevel2(){

        if (current != invadersMatrix.size() - 1){
            for (int i = 0; i < invadersMatrix.getAtPos(current).size(); i++) {
                invadersMatrix.getAtPos(current).getAtPos(i).update();
                if (invadersMatrix.getAtPos(current).getAtPos(i).getY() > 600-80){
                    End.condition = "Defeat";
                    SpaceInvadersGame.levelNumber = 4;
                    primaryStage.setScene(endStageScene);
                }
            }
        }

        if (invadersMatrix.getAtPos(current).size() == 0) {
            current++;
            Invader.speed += 0.25;
        }
    }

    private void checkCollisionsAtLevel2(IList<Invader> invaderList){
        for (int i = 0; i < bullets.size(); i++) {

            Bullet bullet = bullets.getAtPos(i);

            for (int j = 0; j < invaderList.size(); j++) {

                if (bullet.isColliding(invaderList.getAtPos(j))){

                    if (invaderList.getAtPos(j).getInvaderType().equals("Boss")){
                        if (!invaderList.getType().equals("Basic"))
                            bossCollide(invaderList, invaderList.getType());

                        bullets.removeAtPos(i);

                    } else{
                        double lastX = 0;

                        if (invaderList.size() > 0)
                            lastX = invaderList.getAtPos(0).getX();

                        if (lastX >= 800 - 80 * invaderList.size())
                            lastX = (800 - 80 * invaderList.size()) - 1;

                        score += invaderList.getAtPos(j).getScore();
                        invaderList.removeAtPos(j);
                        bullets.removeAtPos(i);

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
            }
        }
    }

    /**
     * Choque jefe
     * @param invaderList Lista de enemigos.
     * @param type Tipo de enemigo.
     */
    private void bossCollide(IList<Invader> invaderList, String type){
        for (int i = 0; i < invaderList.size(); i++) {
            if (invaderList.getAtPos(i).getLife() != 1)
                invaderList.getAtPos(i).setLife(invaderList.getAtPos(i).getLife() - 1);
            else
            if (type.equals("ClassB") || type.equals("ClassA") || (type.equals("ClassC") && invaderList.size() == 1)){
                score += invaderList.getAtPos(i).getScore();
                current++;
            }
            else if(type.equals("ClassC"))
                doSwitch(invaderList, type);
        }
    }

    private void makeBasicClass(IList<Invader> invaderList, int basicY){
        String images = "images/mship1.png";

        int xPos = 7;

        if (basicY != -150)
            invaderList.setType("Basic");
        else invaderList.setType("");

        for (int i = 0; i < 7; i++) {
            invaderList.add(new Invader(new Image(images,67,67,true,false),
                    80 * i, 30, i, xPos, "Minion"));
            xPos--;
        }
    }


    private void makeBoss(IList<Invader> invaderList){
        String images = "images/Spaceship.png";
        String bossImages = "images/invader.png";

        int boss = (int) (Math.random() * (7));

        int xPos = 7;

        for (int i = 0; i < 7; i++) {
            if (i == boss)
                invaderList.add(new Invader(new Image(bossImages,80,77,true,false),
                        80 * i, 30, i, xPos, "Boss"));
            else
                invaderList.add(new Invader(new Image(images,67,67,true,false),
                        80 * i, 30, i, xPos, "Minion"));
            xPos--;
        }
    }

    private void bossSwitch(IList<Invader> invaderList){
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(2),
                        key -> {
                            if (invaderList.size() != 1)
                                doSwitch(invaderList, invaderList.getType());
                        }));
        timeline.playFromStart();
    }

    /**
     * Crea las hileras de aliens aleatoriamente
     */
    private void generateRows(){
        for (int i = 0; i < 6; i++) {
            int randomRow = (int) (Math.random() * 4);

            if (randomRow == 0){
                invadersMatrix.add(new LinkedList<>());
                makeBasicClass(invadersMatrix.getAtPos(i), 0);
            }
            if (randomRow == 1){
                invadersMatrix.add(new LinkedList<>());
                makeBoss(invadersMatrix.getAtPos(i));
                invadersMatrix.getAtPos(i).setType("ClassA");
            }
            if (randomRow == 2){
                invadersMatrix.add(new DoubleLinkedIList<>());
                makeBoss(invadersMatrix.getAtPos(i));
                invadersMatrix.getAtPos(i).setType("ClassB");
                bossSwitch(invadersMatrix.getAtPos(i));
            }
            if (randomRow == 3){
                invadersMatrix.add(new CircularIList<>());
                makeBoss(invadersMatrix.getAtPos(i));
                invadersMatrix.getAtPos(i).setType("ClassC");
            }
        }
        invadersMatrix.add(new LinkedList<>());
        makeBasicClass(invadersMatrix.getAtPos(6), -150);
    }
}



