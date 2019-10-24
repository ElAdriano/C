package main;

import javafx.scene.shape.Rectangle;

public class Brick {

    private final Rectangle rectangle;
    private int healthPoints;

    public Brick(Rectangle rectangle, int initalHealthPoints) {
        this.rectangle = rectangle;
        this.healthPoints = initalHealthPoints;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void decreaseHealthPoints() {
        healthPoints--;
        rectangle.getStyleClass().clear();
        rectangle.getStyleClass().add( "hp" + healthPoints );
    }

    public void setVisible(boolean value) {
        rectangle.setVisible(value);
    }

    public boolean isVisible() {
        return rectangle.isVisible();
    }

    public double getX() {
        return rectangle.getLayoutX();
    }

    public double getY() {
        return rectangle.getLayoutY();
    }

    public double getWidth() {
        return rectangle.getWidth();
    }

    public double getHeight() {
        return rectangle.getHeight();
    }
}
