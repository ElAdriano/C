package main;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameScreenController implements Initializable {

    @FXML
    private Circle ballCircle;
    @FXML
    private AnchorPane field;
    @FXML
    private Rectangle paddleRectangle;
    @FXML
    private Rectangle blurRectangle;
    @FXML
    private Label Label1;
    @FXML
    private Label Label2;
    @FXML
    private Label lifesLabel;
    @FXML
    private Label timeLabel;

    private Paddle paddle;
    private List<Ball> balls;
    private List<Brick> bricks;
    private List<Bonus> bonuses;
    private CollisionManager collisionManager;

    private Ball attachedBall;

    private final double defaultBallRadius = 10.0;

    private long time;
    private int lifes;
    private long startTime;
    private int duration = 0;
    private long timeOffset = 0;

    private boolean isGamePaused = false;
    private boolean hasGameStarted = false;

    private enum GameState {
        WON, LOST, PLAYING, RESET, PAUSED
    }

    @FXML
    public void handleSceneClick() {
        if (!hasGameStarted) {
            startTime = System.currentTimeMillis();
            hasGameStarted = true;
        }

        if (attachedBall != null) {
            attachedBall = null;
            time = System.currentTimeMillis();
            Loop();
        }

        field.getScene().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.P) {
                isGamePaused = !isGamePaused;
                blurRectangle.setVisible(isGamePaused);
                Label1.setText("Press P to resume");
                Label1.setVisible(isGamePaused);
                Label2.setVisible(isGamePaused);
            }
        });
    }

    private boolean checkForBonusCatch(Bonus bonus) {
        Bounds boundsA = paddle.getRectangle().getBoundsInParent();
        Bounds boundsB = bonus.getBounds();

        if (boundsA.intersects(boundsB)) {
            switch (bonus.getType()) {
                case PSIZEDOWN:
                    paddle.setX( paddle.getX() + paddle.getWidth() * 0.33 / 2 );
                    paddle.setWidth(paddle.getWidth() * 0.67);
                    break;

                case PSIZEUP:
                    paddle.setX( paddle.getX() - paddle.getWidth() * 0.5 / 2 );
                    paddle.setWidth(paddle.getWidth() * 1.5);
                    break;

                case BSIZEDOWN:
                    for (Ball ball : balls) {
                        ball.setRadius(ball.getRadius() * 0.5);
                    }
                    break;

                case BSIZEUP:
                    for (Ball ball : balls) {
                        ball.setRadius(ball.getRadius() * 2);
                    }
                    break;

                case SPEEDDOWN:
                    for (Ball ball : balls) {
                        ball.setVelocityX(ball.getVelocityX() * 0.5);
                        ball.setVelocityY(ball.getVelocityY() * 0.5);
                    }
                    break;

                case SPEEDUP:
                    for (Ball ball : balls) {
                        ball.setVelocityX(ball.getVelocityX() * 2);
                        ball.setVelocityY(ball.getVelocityY() * 2);
                    }
                    break;

                case EXTRABALLS: {
                    double x, y;
                    List<Ball> ballsToAdd = new ArrayList<>();
                    for (Ball ball : balls) {
                        x = ball.getVelocityX();
                        y = ball.getVelocityY();

                        Ball firstNewBall = setUpBall(ball.getX(), ball.getY());
                        Ball secondNewBall = setUpBall(ball.getX(), ball.getY());
                        
                        firstNewBall.setRadius(ball.getRadius());
                        secondNewBall.setRadius(ball.getRadius());

                        firstNewBall.setVelocityX(x * Math.cos(Math.toRadians(120)) - y * Math.sin(Math.toRadians(120)));
                        firstNewBall.setVelocityY(x * Math.sin(Math.toRadians(120)) + y * Math.cos(Math.toRadians(120)));

                        secondNewBall.setVelocityX(x * Math.cos(Math.toRadians(-120)) - y * Math.sin(Math.toRadians(-120)));
                        secondNewBall.setVelocityY(x * Math.sin(Math.toRadians(-120)) + y * Math.cos(Math.toRadians(-120)));

                        ballsToAdd.add(firstNewBall);
                        ballsToAdd.add(secondNewBall);
                    }
                    for (Ball ball : ballsToAdd) {
                        balls.add(ball);
                    }
                    ballsToAdd.clear();
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private Ball setUpBall(double x, double y) {
        double angle = Math.random() * 90 - 45;
        Circle newBallCircle = new Circle(defaultBallRadius);
        newBallCircle.getStyleClass().add("ball");
        Ball ball = new Ball(newBallCircle, 2* Math.sin(Math.toRadians(angle)), -2*Math.cos(Math.toRadians(angle)));
        field.getChildren().add(newBallCircle);
        ball.setX(x);
        ball.setY(y);
        return ball;
    }

    private void goBackToMenu() {
        try {
            Stage stage = (Stage) field.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("Templates/MainMenu.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException ex) {
            System.err.println("Error during loading main menu scene ");
        }
    }

    private void Loop() {
        long currentTime = System.currentTimeMillis();
        if (duration < (duration = (int) (currentTime - startTime - timeOffset) / 1000)) {
            timeLabel.setText((((int) duration) / 60) + ":" + (duration % 60 < 10 ? "0" : "") + (((int) duration) % 60));
        }

        switch (getGameState()) {
            case WON: {
                Label1.setText("You Won!");
                Label1.setVisible(true);
                blurRectangle.setVisible(true);
                PauseTransition delay = new PauseTransition(Duration.seconds(3));
                delay.setOnFinished(event -> goBackToMenu());
                delay.play();
                break;
            }

            case PAUSED: {
                PauseTransition delay = new PauseTransition(Duration.seconds(0.001));
                delay.setOnFinished(event -> Loop());
                delay.play();
                System.gc();
                timeOffset += System.currentTimeMillis() - time;
                time = System.currentTimeMillis();
                break;
            }

            case PLAYING: {
                PauseTransition delay = new PauseTransition(Duration.seconds(0.0005));
                delay.setOnFinished(event -> Loop());
                delay.play();

                System.gc();

                double deltaTime = System.currentTimeMillis() - time;
                time = System.currentTimeMillis();

                List<Bonus> removableBonuses = new ArrayList<>();
                for (Bonus bonus : bonuses) {
                    if (!checkForBonusCatch(bonus)) {
                        if (bonus.getY() > field.getHeight()) {
                            removableBonuses.add(bonus);
                        }
                        bonus.setY(bonus.getY() + bonus.getVelocityY() * deltaTime / 10);
                    } else {
                        removableBonuses.add(bonus);
                    }
                }

                List<Ball> removableBalls = new ArrayList<>();

                for (Ball ball : balls) {

                    if (ball.getY() > field.getHeight()) {
                        field.getChildren().remove(ball.getCircle());
                        removableBalls.add(ball);
                        break;
                    }

                    for (Bonus bonus : removableBonuses) {
                        bonus.removeFromFiedl(field);
                        bonuses.remove(bonus);
                    }

                    removableBonuses.clear();

                    List<Brick> knockedBricks = collisionManager.checkForCollisions(bricks, ball);

                    for (Brick brick : knockedBricks) {
                        brick.decreaseHealthPoints();
                        if (brick.getHealthPoints() <= 0) {
                            if (Math.random() < 0.1) {
                                double ran = Math.random() * 7;

                                if (ran < 1) {
                                    bonuses.add(new Bonus(Bonus.Type.BSIZEDOWN, brick.getX() + brick.getWidth() / 2, brick.getY()+ brick.getHeight() / 2, field));
                                } else if (ran < 2) {
                                    bonuses.add(new Bonus(Bonus.Type.BSIZEUP, brick.getX() + brick.getWidth() / 2, brick.getY()+ brick.getHeight() / 2, field));
                                } else if (ran < 3) {
                                    bonuses.add(new Bonus(Bonus.Type.EXTRABALLS, brick.getX() + brick.getWidth() / 2, brick.getY()+ brick.getHeight() / 2, field));
                                } else if (ran < 4) {
                                    bonuses.add(new Bonus(Bonus.Type.PSIZEDOWN, brick.getX() + brick.getWidth() / 2, brick.getY()+ brick.getHeight() / 2, field));
                                } else if (ran < 5) {
                                    bonuses.add(new Bonus(Bonus.Type.PSIZEUP, brick.getX() + brick.getWidth() / 2, brick.getY()+ brick.getHeight() / 2, field));
                                } else if (ran < 6) {
                                    bonuses.add(new Bonus(Bonus.Type.SPEEDDOWN, brick.getX() + brick.getWidth() / 2, brick.getY()+ brick.getHeight() / 2, field));
                                } else {
                                    bonuses.add(new Bonus(Bonus.Type.SPEEDUP, brick.getX() + brick.getWidth() / 2, brick.getY()+ brick.getHeight() / 2, field));
                                }
                            }

                            field.getChildren().remove(brick.getRectangle());
                            bricks.remove(brick);
                        }
                    }

                    ball.setX(ball.getX() + ball.getVelocityX() * deltaTime / 10);
                    ball.setY(ball.getY() + ball.getVelocityY() * deltaTime / 10);
                }

                for (Ball ball : removableBalls) {
                    balls.remove(ball);
                }

                break;
            }

            case RESET: {
                if (--lifes <= 0) {
                    Label1.setText("You Lost");
                    Label1.setVisible(true);
                    blurRectangle.setVisible(true);
                    PauseTransition delay = new PauseTransition(Duration.seconds(3));
                    delay.setOnFinished(event -> goBackToMenu());
                    delay.play();
                } else {
                    double paddleCenter = paddle.getX() + paddle.getWidth() / 2;
                    paddle.setWidth(61.0);
                    paddle.setX( paddleCenter - paddle.getWidth() / 2);
                    Ball ball = setUpBall(paddle.getX() + paddle.getWidth() / 2, 361.0);
                    attachedBall = ball;
                    balls.add(ball);
                    
                }
                lifesLabel.setText("Lifes: " + lifes);
                break;
            }
        }
    }

    private GameState getGameState() {
        if (bricks.isEmpty()) {
            return GameState.WON;
        }
        if (lifes <= 0) {
            return GameState.LOST;
        }
        if (isGamePaused) {
            return GameState.PAUSED;
        }
        if (balls.size() <= 0) {
            return GameState.RESET;
        }
        return GameState.PLAYING;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        paddle = new Paddle(paddleRectangle);
        balls = new ArrayList<>();
        bonuses = new ArrayList<>();
        bricks = new ArrayList<>();

        double angle = Math.random() * 90 - 45;
        Ball ball = new Ball(ballCircle, 2* Math.sin(Math.toRadians(angle)), 2* -Math.cos(Math.toRadians(angle)));
        attachedBall = ball;
        balls.add(ball);

        lifes = 3;
        lifesLabel.setText("Lifes: " + lifes);

        for (int i = 0; i < field.getChildren().size(); i++) {
            Node child = field.getChildren().get(i);
            if ((child instanceof Rectangle && !(child == paddleRectangle) && !(child == blurRectangle))) {
                bricks.add(new Brick((Rectangle) child, child.getStyleClass().get(0).charAt(2) - '0'));
            }
        }
        collisionManager = new CollisionManager(paddle, field);
        
        field.onMouseMovedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (getGameState() == GameState.PLAYING) {
                    paddle.setX(event.getSceneX() - paddle.getWidth() / 2);
                    if (attachedBall != null) {
                        attachedBall.setX(paddle.getX() + paddle.getWidth() / 2);
                    }
                }
            }
        });
    }
}
