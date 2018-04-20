package gamecore;

import adt.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;
import sprites.Bullet;
import sprites.Defender;
import sprites.Invader;
/**
 * Nivel 3.
 */
public class ThirdLevel extends GameEngine{
    /**
     * Contiene primer nivel.
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
    private int current;
    private boolean eRun = true;

    /**
     * Contructor del primer nivel.
     * @param anchorPane Almacena el Nivel 3.
     */
    ThirdLevel(AnchorPane anchorPane){
        this.anchorPane = anchorPane;

        player = new Defender(new Image("images/ship1.png"),new Image("images/ship1-x1.png"),new Image("images/ship1+x.png"),800/2-30, 600-60);
        bullets = new LinkedList<>();

        invadersMatrix = new LinkedList<>();
        generateRows();
        makeBackground(anchorPane);
    }

    @Override
    public void update() {

        player.controllingDefender(anchorPane,player,bullets);
        player.updateBullets(bullets);
        player.update();

        updateInvaderAtLevel3();
        checkCollisionAtLevel3(invadersMatrix.getAtPos(current));

        if (invadersMatrix.getAtPos(current).size() == 0) {
            current++;
            Invader.speed += 0.15;
            eRun = true;
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.clearRect(0 , 0, 800, 600);

        player.render(gc);

        for (int i = 0; i < bullets.size(); i++) {
            bullets.getAtPos(i).render(gc);
        }

        for (int i = 0; i < invadersMatrix.getAtPos(current).size(); i++) {
            invadersMatrix.getAtPos(current).getAtPos(i).render(gc);
        }
    }

    private void updateInvaderAtLevel3(){

        if (current != invadersMatrix.size() - 1) {
            if (!invadersMatrix.getAtPos(current).getType().equals("ClassE")){
                for (int i = 0; i < invadersMatrix.getAtPos(current).size(); i++) {
                    invadersMatrix.getAtPos(current).getAtPos(i).update();
                }
            } else{
                if (eRun){
                    rotate(invadersMatrix.getAtPos(current));
                    eRun = false;
                }
            }
        }
    }

    private void makeEnemies(IList<Invader> invaderList) {

        String[] minionsImages = {"images/inv2.jpg","images/mship1.png","images/roundysh.png"
        ,"images/Spaceship.png","images/ELxFZ.png","images/ospaceship-main.png"
        ,"images/1.png"};

        String bossImages = "images/invader.png";

        int boss = 0;
        if (!invaderList.getType().equals("ClassE"))
            boss = (int) (Math.random() * (7));

        int xPos = 7;
        int yPos = 1;
        if (invaderList.getType().equals(""))
            yPos = -150;


        if (invaderList.getType().equals("ClassE")) {
            for (int i = 0; i < 7; i++) {
                int life = (int) (Math.random() * 5) + 1;

                if (i == 3)
                    invaderList.add(new Invader(new Image(bossImages,80,75,true,false)
                            ,80 * i + 110, 1, i, xPos, "Boss"));
                else {
                    invaderList.add(new Invader(new Image(minionsImages[i],67,67,true,false)
                            ,80 * i + 110, 1, i, xPos, "Minion"));
                    invaderList.getAtPos(i).setLife(life);
                }
                xPos--;
            }
        } else {
            for (int i = 0; i < 7; i++) {
                int life = (int) (Math.random() * 6) + 1;
                if (i == boss)
                    invaderList.add(new Invader(new Image(bossImages,75,75,true,false)
                            ,80 * i, yPos, i, xPos, "Boss"));
                else {
                    invaderList.add(new Invader(new Image(minionsImages[i],70,70,true,false)
                            ,80 * i, yPos, i, xPos, "Minion"));
                    invaderList.getAtPos(i).setLife(life);
                }
                xPos--;
            }
        }
        if (!invaderList.getType().equals("ClassE")) {
            if (invaderList.size() != 1)
                keepSorted(invaderList);
        }
    }

    private void checkCollisionAtLevel3(IList<Invader> EnemyList){
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.getAtPos(i);

            for (int j = 0; j < EnemyList.size(); j++) {
                if (bullet.isColliding(EnemyList.getAtPos(j))){
                    if (EnemyList.getAtPos(j).getLife() != 1)
                        EnemyList.getAtPos(j).setLife(EnemyList.getAtPos(j).getLife() - 1);
                    else {
                        if (EnemyList.getType().equals("ClassD") || EnemyList.getType().equals("ClassE")){
                            boolean isBoss = EnemyList.getAtPos(j).getInvaderType().equals("Boss");
                            int toReplace = 0;

                            EnemyList.removeAtPos(j);
                            if(EnemyList.getType().equals("ClassE"))
                                toReplace = (EnemyList.size() - (EnemyList.size() + 1) / 2);

                            if (isBoss) {
                                //String[] bossImages = {"resources/AlienBoss1.png","resources/AlienBoss2.png"};
                                EnemyList.getAtPos(toReplace).setInvaderType("Boss");
                                //EnemyList.get(toReplace).setImages(bossImages);
                            }
                        }
                        else if (EnemyList.getType().equals("ClassC")){
                            boolean isBoss = EnemyList.getAtPos(j).getInvaderType().equals("Boss");
                            EnemyList.removeAtPos(j);
                            if(isBoss)
                                doSwitch(EnemyList, "ClassC");
                        }
                        else if (EnemyList.getType().equals("ClassB") && EnemyList.getAtPos(j).getInvaderType().equals("Boss")){
                            current++;
                        }
                        else{
                            EnemyList.removeAtPos(j);
                        }

                    }
                    bullets.removeAtPos(i);

                    if (!EnemyList.getType().equals("ClassE"))
                        keepSorted(EnemyList);
                }
            }
        }
    }

    /**
     * Rota la hilera clase E.
     * @param invaderList Lista de enemigos.
     */
    private void rotate(IList<Invader> invaderList){
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        final long timeStart = System.currentTimeMillis();

        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0.017), ae -> {
            double t = (System.currentTimeMillis() - timeStart) / 1000.0;
            double x, y, pos = 1.75;
            int bossPos = invaderList.size() / 2, leftSide = 0, rightSide = 0, xPos = invaderList.size();

            for (int i = 0; i < invaderList.size(); i++) {
                if (invaderList.getAtPos(i).getInvaderType().equals("Boss")){
                    bossPos = i;
                    break;
                }
                xPos--;
            }

            invaderList.getAtPos(bossPos).bossUpdate(bossPos, xPos);
            x = invaderList.getAtPos(bossPos).getX();
            y = invaderList.getAtPos(bossPos).getY();

            if (invaderList.size() < 6)
                pos = 0.60;
            if(invaderList.size() < 4)
                pos = 0;
            if(invaderList.size() < 3)
                pos = -1;

            for (int i = 0; i < invaderList.size(); i++) {
                if (i < bossPos) {
                    invaderList.getAtPos(i).setXAndY(
                            x + (80 + 80 * i) * Math.cos(t),
                            y + (80 + 80 * i) * Math.sin(t));
                    leftSide++;
                } if (i > bossPos) {
                    if (invaderList.size() < 5 && leftSide == 1)
                        pos = 0;
                    if (invaderList.size() < 7 && leftSide == 2)
                        pos = 1.15;

                    invaderList.getAtPos(i).setXAndY(
                            x + (80 - (80 * (i - pos))) * Math.cos(t),
                            y + (80 - (80 * (i - pos))) * Math.sin(t));
                    rightSide++;
                }
            }
            if((leftSide == 1 && rightSide == 3) || (rightSide == 1 && leftSide == 3) ||
                    (leftSide == 0 && rightSide == 2) || (rightSide == 0 && leftSide == 2)){
                placeBossAtMiddle(invaderList);
            }
        }));
        timeline.playFromStart();
    }

    /**
     * Jefe siempre en el centro.
     * @param invaderList Lista de enemigos.
     */
    private void placeBossAtMiddle(IList<Invader> invaderList){
        int tempBoss = 0, change = (invaderList.size() - (invaderList.size() + 1) / 2);

        for (int i = 0; i < invaderList.size(); i++) {
            if (invaderList.getAtPos(i).getInvaderType().equals("Boss"))
                tempBoss = i;
        }

        double changeX = invaderList.getAtPos(change).getX(),
                changeY = invaderList.getAtPos(change).getY(),
                changeRight = invaderList.getAtPos(change).getMovesToRight(),
                changeLeft = invaderList.getAtPos(change).getMovesToLeft();

        double bossX = invaderList.getAtPos(tempBoss).getX()
                , bossY = invaderList.getAtPos(tempBoss).getY()
                , bossRight = invaderList.getAtPos(tempBoss).getMovesToRight()
                , bossLeft = invaderList.getAtPos(tempBoss).getMovesToLeft();

        invaderList.getAtPos(tempBoss).setPosition(
                changeX, changeY,
                changeRight, changeLeft
        );

        invaderList.getAtPos(change).setPosition(
                bossX, bossY,
                bossRight, bossLeft
        );

        INode<Invader> node1 = invaderList.getNode(change);
        INode<Invader> node2 = invaderList.getNode(tempBoss);
        CircularNode<Invader> temp = new CircularNode<>(node1.getData());

        node1.setData(node2.getData());
        node2.setData(temp.getData());

        int xPos = invaderList.size();
        for (int i = 0; i < invaderList.size(); i++) {
            if(invaderList.getAtPos(i).getInvaderType().equals("Boss")){
                if (invaderList.getAtPos(i).getX() < 80 * i){
                    invaderList.getAtPos(i).setXAndY(80 * i + 1, changeY);
                }
                if (invaderList.getAtPos(i).getX() > 800 - 80 * xPos){
                    invaderList.getAtPos(i).setXAndY((800 - 80 * xPos) - 1, changeY);
                }
            }
            xPos--;
        }
    }

    /**
     * Mantiene ordenada la lista de mayor a menor resistencia.
     * @param invaderList Lista de enemigos.
     */
    private void keepSorted(IList<Invader> invaderList){
        double lastX = 0;

        if (invaderList.size() > 0)
            lastX = invaderList.getAtPos(0).getX();

        if (lastX >= 800 - 80 * invaderList.size())
            lastX = (800 - 80 * invaderList.size() - 1);

        if (invaderList.getType().equals("ClassD"))
            bubbleSort(invaderList);

        int xPos = invaderList.size();
        for (int i = 0; i < invaderList.size(); i++) {
            invaderList.getAtPos(i).setPosition(lastX + 80 * i, invaderList.getAtPos(i).getY(), xPos, i);
            xPos--;
        }
    }

    /**
     * Cambio de posicion jefe cada 3s.
     * @param invaderList Lista de enemigos.
     */
    private void bossSwitch(IList<Invader> invaderList){
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(3),
                        e -> {
                            if (invaderList.size() != 1)
                                doSwitch(invaderList, invaderList.getType());
                        }));
        timeline.playFromStart();
    }

    private void generateRows(){
        for (int i = 0; i < 6; i++) {
            int randomRow = (int) (Math.random() * 4);

            if(randomRow == 0){
                invadersMatrix.add(new DoubleLinkedIList<>());
                invadersMatrix.getAtPos(i).setType("ClassB");
                makeEnemies(invadersMatrix.getAtPos(i));
                bossSwitch(invadersMatrix.getAtPos(i));
            }
            if (randomRow == 1){
                invadersMatrix.add(new CircularIList<>());
                invadersMatrix.getAtPos(i).setType("ClassC");
                makeEnemies(invadersMatrix.getAtPos(i));
            }
            if (randomRow == 2){
                invadersMatrix.add(new CircularIList<>());
                invadersMatrix.getAtPos(i).setType("ClassD");
                makeEnemies(invadersMatrix.getAtPos(i));
            }
            if (randomRow == 3){
                invadersMatrix.add(new DoubleCircularIList<>());
                invadersMatrix.getAtPos(i).setType("ClassE");
                makeEnemies(invadersMatrix.getAtPos(i));
            }
        }

        invadersMatrix.add(new LinkedList<>());
        invadersMatrix.getAtPos(6).setType("");
        makeEnemies(invadersMatrix.getAtPos(6));
    }
}
