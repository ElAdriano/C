package game;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Bonus {

    public enum Type {
        SPEEDUP, SPEEDDOWN, BSIZEUP, BSIZEDOWN, PSIZEUP, PSIZEDOWN, EXTRABALLS
    }

    private ImageView rect;
    private int velocity = 1;
    private Type type;

    public Bonus(Type type, double x, double y, AnchorPane field) {
        rect = new ImageView();
        rect.setLayoutX(x);
        rect.setLayoutY(y);
        rect.setFitWidth(30);
        rect.setFitHeight(12);

        this.type = type;
        switch (type) {
            case BSIZEDOWN:
                rect.getStyleClass().add("bsizeDOWN");
                break;

            case BSIZEUP:
                rect.getStyleClass().add("bsizeUP");
                break;

            case EXTRABALLS:
                rect.getStyleClass().add("extraBALLS");
                break;

            case PSIZEDOWN:
                rect.getStyleClass().add("psizeDOWN");
                break;

            case PSIZEUP:
                rect.getStyleClass().add("psizeUP");
                break;

            case SPEEDDOWN:
                rect.getStyleClass().add("speedDOWN");
                break;

            case SPEEDUP:
                rect.getStyleClass().add("speedUP");
                break;
        }
        field.getChildren().add(rect);
        rect.toFront();
    }

    public Rectangle getRectangle() {
        Rectangle rectangle = new Rectangle(rect.getFitWidth(), rect.getFitHeight());
        rectangle.setLayoutX(rect.getLayoutX());
        rectangle.setLayoutY(rect.getLayoutY());
        rectangle.setFill(Color.BLACK);
        return rectangle;
    }

    public Bounds getBounds() {
        return rect.getBoundsInParent();
    }

    public void setY(double y) {
        rect.setLayoutY(y);
    }

    public Type getType() {
        return type;
    }

    public double getVelocityY() {
        return velocity;
    }

    public double getX() {
        return rect.getLayoutX();
    }

    public double getY() {
        return rect.getLayoutY();
    }

    public double getNextY() {
        return rect.getLayoutX() + velocity;
    }

    public double getWidth() {
        return rect.getFitWidth();
    }

    public double getHeight() {
        return rect.getFitHeight();
    }

    public void removeFromFiedl(AnchorPane field) {
        field.getChildren().remove(rect);
    }

}
