package sprites;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Bullet {

    /**
     * Posicion en X.
     */
    private double posX;

    /**
     * Posicion en Y.
     */
    private double posY;
    /**
     * Imagen de la bala.
     */
    private Image image;
    /**
     * Direccion de la bala en y.
     */
    private double dy;
    /**
     * Velocidad bala.
     */
    private double speed;
    /**
     * Ancho bala.
     */
    private double width;
    /**
     * Alto bala.
     */
    private double height;

    /**
     * Constructor de la nave defensora.
     * @param image Imagen de la nave.
     * @param x Posicion de la nave en X.
     * @param y Posicion de la nave en Y.
     */
    public Bullet(Image image,double x, double y, double speed) {
        this.image = image;
        this.speed = speed;
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.posX = x+18;
        this.posY = y-21;
        this.dy = -speed;

    }

    public double getPosY() {
        return posY;
    }

    /**
     * Movimiento de la bala.
     */
    public void update() {
        posY += dy;
    }

    /**
     * Dibuja la imagen de la bala.
     * @param gc Herramienta para dibujar.
     */
    public void render(GraphicsContext gc) {

        gc.drawImage(image, posX, posY);

    }
}
