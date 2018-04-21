package sprites;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Clase Enemiga
 * Nave invasora.
 */
public class Invader {

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
    public static double speed;
    /**
     * Ancho nave.
     */
    private double width;
    /**
     * Alto nave.
     */
    private double height;
    /**
     * Tipo de enemigo.
     * Minion o jefe.
     */
    private String invaderType;
    /**
     * Vida del enemigo.
     */
    private int life;

    /**
     * Limite de movimientos hacia la izquierda
     */
    private double movesToLeft;
    /**
     * Limite de movimientos hacia la derecha
     */
    private double movesToRight;
    /**
     * Puntaje segun enemigo.
     */
    private int score;

    private boolean right = true;
    private boolean left;

    /**
     * Contructo Nave Enemiga.
     * @param image Imagen.
     * @param x Posicion en x.
     * @param y Posicion en y.
     * @param movesToLeft Limite de movimientos hacia la izquierda.
     * @param movesToRight Limite de movimientos hacia la derecha.
     * @param invaderType Tipo enemigo.
     */
    public Invader(Image image, double x, double y, double movesToLeft,double movesToRight, String invaderType) {
        this.image = image;
        speed = 2;
        this.width = image.getWidth();
        this.height = image.getHeight();
        setPosition(x,y, movesToRight, movesToLeft);
        this.invaderType = invaderType;
        this.score = 100;
        if (invaderType.equals("Boss")) {
            life = 7;
            this.score = 500;
        }
    }

    public void update(){
        if (80 * movesToLeft > posX) {
            setRight(true);
            posY += 40;
        }
        if (posX > 800 -  80 * movesToRight){
            setLeft(true);
            posY += 40;
        }

        if (right){
            dx = speed;
        }

        if (left){
            dx = -speed;
        }

        posX += dx;
    }

    public void bossUpdate(int limitLeft, int limitRight){
        if (80 * limitLeft > posX) {
            setRight(true);
            posY += 30;
        }
        if (posX > 800 -  80 * limitRight){
            setLeft(true);
            posY += 30;
        }

        if (right){
            dx = speed;
        }

        if (left){
            dx = -speed;
        }

        posX += dx;
    }
    public void render(GraphicsContext gc){
        gc.drawImage(image, posX, posY);

    }

    public Rectangle2D getFrontier(){
        return new Rectangle2D(posX, posY, width, height);
    }

    public void setRight(boolean right) {
        this.right = right;
        left = false;
    }

    public void setLeft(boolean left) {
        this.left = left;
        right = false;
    }

    public double getX() {
        return posX;
    }

    public double getY() {
        return posY;
    }

    public void setInvaderType(String invaderType){
        this.invaderType = invaderType;

        if (getInvaderType().equals("Boss"))
            setLife(7);
    }

    public String getInvaderType(){
        return invaderType;
    }

    public void setLife(int life){
        this.life = life;
    }

    public int getLife(){
        return life;
    }

    public void setPosition(double x, double y, double iR, double iL){
        this.posX = x;
        this.posY = y;
        this.movesToRight = iR;
        this.movesToLeft = iL;
    }


    public void setXAndY(double x, double y){
        this.posX = x;
        this.posY = y;
    }

    public double getMovesToRight() {
        return movesToRight;
    }

    public double getMovesToLeft() {
        return movesToLeft;
    }

    public int getScore(){
        return this.score;
    }

}
