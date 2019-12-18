package game;

import javafx.scene.shape.Circle;

public class Ball {
    private final Circle circle;
    private double velocityX;
    private double velocityY;

    public Ball(Circle circle, double velocityX, double velocityY) {
        this.circle = circle;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    public void setX(double x) {
        circle.setLayoutX(x);
    }

    public void setY(double y) {
        circle.setLayoutY(y);
    }

    public double getX() {
        return circle.getLayoutX();
    }

    public double getY() {
        return circle.getLayoutY();
    }

    public double getRadius() {
        return circle.getRadius();
    }

    public void setRadius( double value ){
        circle.setRadius(value);
    }

    public double getVelocityX() {
        return velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityX(double value) {
        velocityX = value;
    }

    public void setVelocityY(double value) {
        velocityY = value;
    }

    public double getNextX() {
        return getX() + velocityX;
    }

    public double getNextY() {
        return getY() + velocityY;
    }

    public Circle getCircle() {
        return circle;
    }

    public Circle getNextCircle() {
        Circle circle = new Circle(getRadius());
        circle.setLayoutX(getNextX());
        circle.setLayoutY(getNextY());
        return circle;
    }

    public void makeBounceFromVertical() {
        velocityX = -velocityX;
    }

    public void makeBounceFromHorizontal() {
        velocityY = -velocityY;
    }
}
