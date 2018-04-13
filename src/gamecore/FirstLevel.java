package gamecore;

import adt.LinkedList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import sprites.Bullet;
import sprites.Defender;

/**
 * Nivel 1.
 */
public class FirstLevel extends GameEngine{
    /**
     * Contiene primer nivel.
     */
    private AnchorPane pane;

    /**
     * Atributo jugador.
     */
    private Defender player;

    /**
     * Balas del juego.
     */
    private LinkedList<Bullet> bullets;

    /**
     * Contructor del primer nivel.
     * @param firstLevelPane Almacena el Nivel 1.
     */
    FirstLevel(AnchorPane firstLevelPane) {
        this.pane = firstLevelPane;

        player = new Defender(new Image("images/ship1.png"),800/2-30, 600-60);

        Image background = new Image("images/background6.jpg");
        BackgroundImage bgImage = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        pane.setBackground(new Background(bgImage));

        bullets = new LinkedList<>();
    }

    /**
     * Control NaveJugador
     * Oprimir tecla "IZQUIERDA".
     * Oprimir tecla "DERECHA".
     * Oprimir tecla "ESPACIO".
     */
    @Override
    public void update() {
        pane.getScene().setOnKeyPressed(key -> {
            if (key.getCode() == KeyCode.LEFT) {
                System.out.println("izq");
                player.setLeft(true);
            }
            if (key.getCode() == KeyCode.RIGHT) {
                System.out.println("der");
                player.setRight(true);
            }
        });

        pane.getScene().setOnKeyReleased(key ->{
            if (key.getCode() == KeyCode.RIGHT)
                player.setRight(false);
            if (key.getCode() == KeyCode.LEFT)
                player.setLeft(false);
            if (key.getCode() == KeyCode.SPACE)
                bullets.add(new Bullet(new Image("images/laser.png"),player.getPosX(),
                        player.getPosY(),15));
        });

        if(player.isFire()) {
            bullets.add(new Bullet(new Image("images/laser.png"),player.getPosX(),
                    player.getPosY(),15));
            player.setFire(false);
        }
        updateBullets();
        player.update();
    }

    /**
     * Dibuja la nave del jugador.
     * @param gc Graphics Context: Dibuja dentro de un canvas mediante un buffer.
     */
    @Override
    public void render(GraphicsContext gc){
        gc.clearRect(0 , 0, 800, 600);

        player.render(gc);

        for (int i = 0; i < bullets.size(); i++) {
            bullets.getAtPos(i).render(gc);
        }
    }

    public void updateBullets(){
        for (int i = 0; i < bullets.size(); i++) {
            Bullet fire = bullets.getAtPos(i);

            if (fire.getPosY() < -15) {
                bullets.removeAtPos(i);
            }
            fire.update();
        }
    }
}
