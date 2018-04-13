package sprites;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

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

    /**
     * Constructor de la nave defensora.
     * @param image Imagen de la nave.
     * @param x Posicion de la nave en X.
     * @param y Posicion de la nave en Y.
     */
    public Defender(Image image,double x, double y) {
        this.image = image;
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

        gc.drawImage(image, posX, posY);

    }


}
