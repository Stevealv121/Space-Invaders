package gamecore;

import javafx.scene.canvas.GraphicsContext;

/**
 * GameEngine: Motor del juego.
 */
public abstract class GameEngine {

    /**
     * Actualiza cada animacion en el juego.
     */
    public abstract void update();

    /**
     * Se encarga de dibujar los componentes necesarios.
     * @param gc Graphics Context: Dibuja dentro de un canvas mediante un buffer.
     */
    public abstract void render(GraphicsContext gc);
}
