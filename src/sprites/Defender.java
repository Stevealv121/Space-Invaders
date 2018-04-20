package sprites;

import adt.LinkedList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

/**
 * Clase Jugador
 * Nave defensora.
 */
public class Defender{

    /**
     * Posicion en X.
     */
    private double posX;

    /**
     * Posicion en Y.
     */
    private double posY;
    /**
     * Imagen de la nave.
     */
    private Image image;
    /**
     * Direccion de la nave.
     */
    private double dx;
    /**
     * Velocidad nave.
     */
    private int speed;
    /**
     * Ancho nave.
     */
    private double width;

    private boolean left;

    private boolean right;

    private boolean fire;

    private Image imageLeft;
    private Image imageRight;

    /**
     * Constructor de la nave defensora.
     * @param image Imagen de la nave.
     * @param x Posicion de la nave en X.
     * @param y Posicion de la nave en Y.
     */
    public Defender(Image image, Image imageLeft, Image imageRight, double x, double y) {
        this.image = image;
        this.imageLeft = imageLeft;
        this.imageRight = imageRight;
        this.speed = 5;
        this.width = image.getWidth();
        this.posX = x;
        this.posY = y;

    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isFire() {
        return fire;
    }

    public void setFire(boolean fire) {
        this.fire = fire;
    }

    /**
     * Espera el input del usuario.
     * Mueve la nave de acuerdo al input.
     */
    public void update() {

        if (left&&posX>0){
            dx = -speed;
        }
        if (right&&posX<800-width) {
            dx = speed;
        }
        posX += dx;
        dx = 0;
    }

    /**
     * Dibuja la imagen de la nave.
     * @param gc Herramienta para dibujar.
     */
    public void render(GraphicsContext gc) {

        if(left){
            gc.drawImage(imageLeft, posX,posY);
        }
        if(right){
            gc.drawImage(imageRight, posX,posY);
        }else{
            gc.drawImage(image, posX, posY);
        }


    }
    /**
     * Movimiento jugador:
     * Controles:
     * LEFT - Mover hacia izquierda
     * RIGHT - Mover hacia derecha
     * SPACE - Disparar
     */
    public void controllingDefender(AnchorPane anchorPane, Defender player, LinkedList<Bullet> bullets){

        anchorPane.getScene().setOnKeyPressed(key -> {
            if (key.getCode() == KeyCode.LEFT) {
                player.setLeft(true);
            }
            if (key.getCode() == KeyCode.RIGHT) {
                player.setRight(true);
            }
        });

        anchorPane.getScene().setOnKeyReleased(key -> {
            if (key.getCode() == KeyCode.RIGHT) {
                player.setRight(false);
            }
            if (key.getCode() == KeyCode.LEFT) {
                player.setLeft(false);
            }
            if (key.getCode() == KeyCode.SPACE)
                bullets.add(new Bullet( new Image("images/laser.png"),player.getPosX(), player.getPosY(), 15));
        });

        if(player.isFire()) {
            bullets.add(new Bullet( new Image("images/laser.png"),player.getPosX(), player.getPosY(), 15));
            player.setFire(false);
        }
    }

    /**
     * Actualiza balas
     */
    public void updateBullets(LinkedList<Bullet> bullets){
        for (int i = 0; i < bullets.size(); i++) {
            Bullet shoot = bullets.getAtPos(i);

            if (shoot.getPosY() < -15) {
                bullets.removeAtPos(i);
            }
            shoot.update();
        }
    }


}
