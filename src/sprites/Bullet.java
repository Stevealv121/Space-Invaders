package sprites;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import java.io.File;

/**
 * Clase Bala
 * Creada por la nave defensora.
 */
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
     * Constructor de la bala.
     * @param image Imagen de la bala.
     * @param x Posicion de la bala en X.
     * @param y Posicion de la bala en Y.
     * @param speed velocidad bala.
     */

    Bullet(Image image, double x, double y, double speed) {
        this.image = image;
        this.speed = speed;
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.posX = x+18;
        this.posY = y-21;
        this.dy = -speed;

        playShootSound();

    }

    /**
     * Reproduce el sonido del disparo.
     */
    private void playShootSound(){

        AudioClip shootSound = new AudioClip(new File("src/sounds/shoot.wav").toURI().toString());
        shootSound.play();
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

    /**
     * Forma un rectangulo con respecto al alto y ancho del sprite.
     * @return Rectangulo Bala.
     */
    private Rectangle2D getFrontier() {
        return new Rectangle2D(posX, posY, width, height);
    }

    /**
     * Revisa la colision de la bala.
     * @param invader Enemigo.
     * @return true si la bala esta chocando con un enemigo.
     */
    public boolean isColliding(Invader invader){
        return invader.getFronter().intersects(this.getFrontier());
    }

}
