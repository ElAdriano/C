package game;

import javafx.scene.shape.Rectangle;

public class Paddle {

    private final Rectangle rectangle;

    public Paddle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public double getX() {
        return rectangle.getLayoutX();
    }

    public double getY() {
        return rectangle.getLayoutY();
    }

    public void setX(double x) {
        rectangle.setLayoutX(x);
    }

    public double getWidth() {
        return rectangle.getWidth();
    }

    public void setWidth(double value) {
        rectangle.setWidth(value);
    }

    public double getHeight() {
        return rectangle.getHeight();
    }
}
