package main;

import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class CollisionManager {

    public enum CollisionType {
        NONE, VERTICAL, HORIZONTAL, CORNER
    }

    private boolean proceededHorizontalBounce;
    private boolean proceededVerticalBounce;
    private final Paddle paddle;
    private final AnchorPane field;
    private Rectangle cornerCollisionRect;
    private List<Brick> knockedBricks;
    private Ball ball;

    public CollisionManager(Paddle paddle, AnchorPane field) {
        this.paddle = paddle;
        this.field = field;
        knockedBricks = new ArrayList<>();
    }

    public List<Brick> checkForCollisions(List<Brick> bricks, Ball ball) {
        this.ball = ball;
        proceededHorizontalBounce = false;
        proceededVerticalBounce = false;
        cornerCollisionRect = null;

        knockedBricks.clear();

        if (willBallCollideWithTop()) {
            ball.makeBounceFromHorizontal();
        }

        if (willBallCollideWithWall()) {
            ball.makeBounceFromVertical();
        }

        Circle nextCircle = ball.getNextCircle();
        switch (checkRectangleCircleIntersection(paddle.getRectangle(), nextCircle)) {
            case HORIZONTAL:
                manageCollision(CollisionType.HORIZONTAL, ball, paddle.getRectangle());
                double angle = (ball.getX() - paddle.getX() - (paddle.getWidth() / 2)) / paddle.getWidth() * 60;
                double velX = ball.getVelocityX();
                double velY = ball.getVelocityY();
                ball.setVelocityX(velX * Math.cos(Math.toRadians(angle)) - velY * Math.sin(Math.toRadians(angle)));
                ball.setVelocityY(velX * Math.sin(Math.toRadians(angle)) + velY * Math.cos(Math.toRadians(angle)));
                break;
            case CORNER:
                manageCollision(CollisionType.CORNER, ball, paddle.getRectangle());
                break;
            case VERTICAL:
                manageCollision(CollisionType.VERTICAL, ball, paddle.getRectangle());
                break;
            default:
                break;
        }

        for (Brick brick : bricks) {
            if (manageCollision(brick.getRectangle(), ball)) {
                knockedBricks.add(brick);
            }
        }
        if (cornerCollisionRect != null && !proceededHorizontalBounce && !proceededVerticalBounce) {
            makeCornerBounce(cornerCollisionRect, nextCircle);
        }

        return knockedBricks;
    }

    public void manageCollision(CollisionType type, Ball ball, Rectangle rect) {
        switch (type) {
            case HORIZONTAL: {
                if (!proceededHorizontalBounce) {
                    ball.makeBounceFromHorizontal();
                    proceededHorizontalBounce = true;
                }
                break;
            }

            case VERTICAL: {
                if (!proceededVerticalBounce) {
                    ball.makeBounceFromVertical();
                    proceededVerticalBounce = true;
                }
                break;
            }

            case CORNER: {
                cornerCollisionRect = rect;
                break;
            }

            case NONE:
                break;

        }
    }

    public boolean manageCollision(Rectangle rect, Ball ball) {
        this.ball = ball;
        Circle circle = ball.getNextCircle();
        switch (checkRectangleCircleIntersection(rect, circle)) {
            case HORIZONTAL: {
                if (!proceededHorizontalBounce) {
                    ball.makeBounceFromHorizontal();
                    proceededHorizontalBounce = true;
                }
                break;
            }

            case VERTICAL: {
                if (!proceededVerticalBounce) {
                    ball.makeBounceFromVertical();
                    proceededVerticalBounce = true;
                }
                break;
            }

            case CORNER: {
                cornerCollisionRect = rect;
                break;
            }

            case NONE:
                return false;

        }
        return true;
    }

    private void makeCornerBounce(Rectangle rect, Circle circle) {
        if (circle.getLayoutX() <= rect.getLayoutX()) {
            if (circle.getLayoutY() <= rect.getLayoutY()) {
                calculateCornerBounce(rect.getLayoutX(), rect.getLayoutY());
            } else {
                calculateCornerBounce(rect.getLayoutX(), rect.getLayoutY() + rect.getHeight());
            }
        } else {
            if (circle.getLayoutY() <= rect.getLayoutY()) {
                calculateCornerBounce(rect.getLayoutX() + rect.getWidth(), rect.getLayoutY());
            } else {
                calculateCornerBounce(rect.getLayoutX() + rect.getWidth(), rect.getLayoutY() + rect.getHeight());
            }
        }
    }

    private void calculateCornerBounce(double cornerX, double cornerY) {
        double x = ball.getX() - cornerX;
        double y = ball.getY() - cornerY;
        double c = -2 * (ball.getVelocityX() * x + ball.getVelocityY() * y) / (x * x + y * y);
        ball.setVelocityX(ball.getVelocityX() + c * x);
        ball.setVelocityY(ball.getVelocityY() + c * y);
    }

    public CollisionType checkRectangleCircleIntersection(Rectangle rect, Circle circle) {
        double rectCenterX = rect.getLayoutX() + rect.getWidth() / 2;
        double rectCenterY = rect.getLayoutY() + rect.getHeight() / 2;

        double distanceX = abs(circle.getLayoutX() - rectCenterX);
        double distanceY = abs(circle.getLayoutY() - rectCenterY);

        if (distanceX > (rect.getWidth() / 2 + circle.getRadius())) {
            return CollisionType.NONE;
        }
        if (distanceY > (rect.getHeight() / 2 + circle.getRadius())) {
            return CollisionType.NONE;
        }

        if (distanceX <= (rect.getWidth() / 2)) {
            return CollisionType.HORIZONTAL;
        }
        if (distanceY <= (rect.getHeight() / 2)) {
            return CollisionType.VERTICAL;
        }

        double cornerDistance = (distanceX - rect.getWidth() / 2) * (distanceX - rect.getWidth() / 2)
                + (distanceY - rect.getHeight() / 2) * (distanceY - rect.getHeight() / 2);

        if (cornerDistance <= (circle.getRadius() * circle.getRadius())) {
            return CollisionType.CORNER;
        }

        return CollisionType.NONE;
    }

    private boolean willBallCollideWithWall() {
        if (ball.getNextX() - ball.getRadius() < 0 || ball.getNextX() + ball.getRadius() > field.getWidth()) {
            return true;
        }
        return false;
    }

    private boolean willBallCollideWithTop() {
        if (ball.getNextY() - ball.getRadius() < 0) {
            return true;
        }
        return false;
    }
}
