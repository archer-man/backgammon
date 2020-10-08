package backgammon.controller;

import backgammon.model.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static backgammon.model.Side.WHITE;
import static backgammon.model.Side.BLACK;

public class Controller implements Initializable {

    public boolean dicesRolled = false;
    public final ArrayList<Integer> diceNumbers = new ArrayList<>();
    public int diceNumberCount;
    public Dice leftDice, rightDice;
    private Image leftDiceImage, rightDiceImage;
    private final Group pointGroup = new Group();
    private final Group checkerGroup = new Group();
    public final List<Point> pointList = new ArrayList<>();
    public final Bar blackBar = new Bar(294, 158);
    public final Bar whiteBar = new Bar(294, 250);
    Side whiteSide = WHITE;
    Side blackSide = BLACK;

    @FXML
    private Button rollDiceButton;

    @FXML
    private ImageView leftDiceIV, rightDiceIV;

    @FXML
    private Pane leftPane;

    @FXML
    private Circle currentSideCircle;

    @FXML
    private Label whiteScore, blackScore;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rollDiceButton.setOnMouseClicked(e -> rollDices());
        leftPane.getChildren().addAll(whiteBar, blackBar);
        leftPane.getChildren().addAll(pointGroup, checkerGroup);
        drawPoints();
        drawCheckers();
        fillCurrentSideCircle();
    }

    void drawPoints() {

        Point point = new Point(Color.LIGHTGRAY, 630, 0, 0);
        pointGroup.getChildren().add(point);
        pointList.add(point);

        point = new Point(Color.valueOf("#d79800"), 581, 0, 1);
        pointGroup.getChildren().add(point);
        pointList.add(point);

        point = new Point(Color.valueOf("#f29800"), 532, 0, 2);
        pointGroup.getChildren().add(point);
        pointList.add(point);

        point = new Point(Color.valueOf("#d79800"), 483, 0, 3);
        pointGroup.getChildren().add(point);
        pointList.add(point);

        point = new Point(Color.valueOf("#f29800"), 434, 0, 4);
        pointGroup.getChildren().add(point);
        pointList.add(point);

        point = new Point(Color.valueOf("#d79800"), 385, 0, 5);
        pointGroup.getChildren().add(point);
        pointList.add(point);

        point = new Point(Color.valueOf("#f29800"), 336, 0, 6);
        pointGroup.getChildren().add(point);
        pointList.add(point);

        point = new Point(Color.valueOf("#d79800"), 252, 0, 7);
        pointGroup.getChildren().add(point);
        pointList.add(point);

        point = new Point(Color.valueOf("#f29800"), 203, 0, 8);
        pointGroup.getChildren().add(point);
        pointList.add(point);

        point = new Point(Color.valueOf("#d79800"), 154, 0, 9);
        pointGroup.getChildren().add(point);
        pointList.add(point);

        point = new Point(Color.valueOf("#f29800"), 105, 0, 10);
        pointGroup.getChildren().add(point);
        pointList.add(point);

        point = new Point(Color.valueOf("#d79800"), 56, 0, 11);
        pointGroup.getChildren().add(point);
        pointList.add(point);

        point = new Point(Color.valueOf("#f29800"), 7, 0, 12);
        pointGroup.getChildren().add(point);
        pointList.add(point);

        point = new Point(Color.valueOf("#d79800"), 7, 250, 13);
        pointGroup.getChildren().add(point);
        pointList.add(point);

        point = new Point(Color.valueOf("#f29800"), 56, 250, 14);
        pointGroup.getChildren().add(point);
        pointList.add(point);

        point = new Point(Color.valueOf("#d79800"), 105, 250, 15);
        pointGroup.getChildren().add(point);
        pointList.add(point);

        point = new Point(Color.valueOf("#f29800"), 154, 250, 16);
        pointGroup.getChildren().add(point);
        pointList.add(point);

        point = new Point(Color.valueOf("#d79800"), 203, 250, 17);
        pointGroup.getChildren().add(point);
        pointList.add(point);

        point = new Point(Color.valueOf("#f29800"), 252, 250, 18);
        pointGroup.getChildren().add(point);
        pointList.add(point);

        point = new Point(Color.valueOf("#d79800"), 336, 250, 19);
        pointGroup.getChildren().add(point);
        pointList.add(point);

        point = new Point(Color.valueOf("#f29800"), 385, 250, 20);
        pointGroup.getChildren().add(point);
        pointList.add(point);

        point = new Point(Color.valueOf("#d79800"), 434, 250, 21);
        pointGroup.getChildren().add(point);
        pointList.add(point);

        point = new Point(Color.valueOf("#f29800"), 483, 250, 22);
        pointGroup.getChildren().add(point);
        pointList.add(point);

        point = new Point(Color.valueOf("#d79800"), 532, 250, 23);
        pointGroup.getChildren().add(point);
        pointList.add(point);

        point = new Point(Color.valueOf("#f29800"), 581, 250, 24);
        pointGroup.getChildren().add(point);
        pointList.add(point);

        point = new Point(Color.LIGHTGRAY, 630, 250, 25);
        pointGroup.getChildren().add(point);
        pointList.add(point);

    }

    public void initializeChecker(Point point, Side side) {
        Checker checker = new Checker(side, point);
        checker.setOnMouseReleased(e -> {
            if (checker.isFirstCheckerOnPointOrBar()) {
                try {
                    int newPointIndex = toBoard(checker.getLayoutX(), checker.getLayoutY());

                    Point newPoint = pointList.get(newPointIndex);
                    ResultOfMove result = tryMove(checker, newPoint);

                    switch (result.getMoveType()) {
                        case NONE:
                            checker.undoMoveCoord();
                            break;
                        case NORMAL:
                            checker.moveToPoint(newPoint);
                            if (--diceNumberCount == 0)
                                otherPlayersTurn();
                            break;
                        case KILL:
                            Checker checkerToBeKilled = result.getChecker();
                            checkerToBeKilled.moveToBar(checkerToBeKilled.getSide() == WHITE ? (whiteBar) : (blackBar));
                            checker.moveToPoint(newPoint);
                            if (--diceNumberCount == 0)
                                otherPlayersTurn();
                            break;
                    }
                } catch (Exception ex) {
                    checker.undoMoveCoord();
                    ex.printStackTrace();
                }
            }
            if (diceNumberCount > 0 && !checkIfNextMoveAvailable()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Нету доступных ходов");
                alert.setHeaderText(null);
                alert.setContentText("Пропустите ход");
                alert.showAndWait();
                otherPlayersTurn();
                diceNumbers.clear();
            }
            if (checker.getSide().sideScore == 15) {
                String player;
                if (checker.getSide() == WHITE) {
                    player = "Белые ";
                } else {
                    player = "Чёрные ";
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Победа");
                alert.setHeaderText(null);
                alert.setContentText(player + "победили.");
                alert.showAndWait();
            }
        });
        checkerGroup.getChildren().add(checker);
    }

    void drawCheckers() {
        Point point = pointList.get(1);
        for (int i = 0; i < 2; i++) {
            initializeChecker(point, WHITE);
        }

        point = pointList.get(6);
        for (int i = 0; i < 5; i++) {
            initializeChecker(point, BLACK);
        }

        point = pointList.get(8);
        for (int i = 0; i < 3; i++) {
            initializeChecker(point, BLACK);
        }

        point = pointList.get(12);
        for (int i = 0; i < 5; i++) {
            initializeChecker(point, WHITE);
        }

        point = pointList.get(13);
        for (int i = 0; i < 5; i++) {
            initializeChecker(point, BLACK);
        }

        point = pointList.get(17);
        for (int i = 0; i < 3; i++) {
            initializeChecker(point, WHITE);
        }

        point = pointList.get(19);
        for (int i = 0; i < 5; i++) {
            initializeChecker(point, WHITE);
        }

        point = pointList.get(24);
        for (int i = 0; i < 2; i++) {
            initializeChecker(point, BLACK);
        }
    }

    private void rollDices() {
        int randomNum = ThreadLocalRandom.current().nextInt(1, 7);
        switch (randomNum) {
            case 1: {
                leftDice = Dice.ONE;
                break;
            }
            case 2: {
                leftDice = Dice.TWO;
                break;
            }
            case 3: {
                leftDice = Dice.THREE;
                break;
            }
            case 4: {
                leftDice = Dice.FOUR;
                break;
            }
            case 5: {
                leftDice = Dice.FIVE;
                break;
            }
            case 6: {
                leftDice = Dice.SIX;
                break;
            }
        }
        randomNum = ThreadLocalRandom.current().nextInt(1, 7);
        switch (randomNum) {
            case 1: {
                rightDice = Dice.ONE;
                break;
            }
            case 2: {
                rightDice = Dice.TWO;
                break;
            }
            case 3: {
                rightDice = Dice.THREE;
                break;
            }
            case 4: {
                rightDice = Dice.FOUR;
                break;
            }
            case 5: {
                rightDice = Dice.FIVE;
                break;
            }
            case 6: {
                rightDice = Dice.SIX;
                break;
            }
        }

        try {
            leftDiceImage = new Image(leftDice.getPath());
            rightDiceImage = new Image(rightDice.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }

        leftDiceIV.setImage(leftDiceImage);
        rightDiceIV.setImage(rightDiceImage);

        dicesRolled = true;
        rollDiceButton.setVisible(false);
        int leftDiceNumber = leftDice.getNumber();
        int rightDiceNumber = rightDice.getNumber();

        MediaPlayer player = new MediaPlayer(new Media(Objects.requireNonNull(getClass().getClassLoader().getResource("roll.wav")).toExternalForm()));
        player.play();

        if (leftDiceNumber == rightDiceNumber) {
            for (int i = 0; i < 4; i++)
                diceNumbers.add(leftDiceNumber);
        } else {
            diceNumbers.add(leftDiceNumber);
            diceNumbers.add(rightDiceNumber);
        }
        diceNumberCount = diceNumbers.size();
    }

    private HashMap<Point, Integer> getAllowedMoves(Checker checker) {
        HashMap<Point, Integer> pointsAllowed = new HashMap<>();
        for (int i : diceNumbers) {
            int n;
            if (checker.getBar() != null) {
                int starting = (checker.getSide() == whiteSide) ? (0) : (25);
                n = starting + (checker.getSide().moveDirection * i);
            } else {
                n = checker.getPoint().getPointNumber() + (checker.getSide().moveDirection * i);
            }
            if (n >= 25 && canGoToHome(checker.getSide())) {
                n = 25;
                Point p = pointList.get(n);
                pointsAllowed.put(p, i);
            } else if (n <= 0 && canGoToHome(checker.getSide())) {
                n = 0;
                Point p = pointList.get(n);
                pointsAllowed.put(p, i);
            } else if (n > 0 && n < 25) {
                Point p = pointList.get(n);
                if (p.getCheckerList().size() < 2 || p.getChecker().getSide() == checker.getSide()) {
                    pointsAllowed.put(p, i);
                }
            }
        }
        return pointsAllowed;
    }

    public ResultOfMove tryMove(Checker checker, Point newPoint) {

        Bar checkersBar = (checker.getSide() == BLACK) ? (blackBar) : (whiteBar);

        HashMap<Point, Integer> pointsAllowed = getAllowedMoves(checker);

        if (pointsAllowed.isEmpty()) {
            return new ResultOfMove(MoveType.NONE);
        }

        if (checker.getSide().currentSide && dicesRolled && checker.getPoint() != newPoint) {
            if (pointsAllowed.containsKey(newPoint)) {
                if (checkersBar.isEmpty()) {
                    if ((newPoint.getPointNumber() == 25 || newPoint.getPointNumber() == 0) && canGoToHome(checker.getSide())) {
                        diceNumbers.remove(pointsAllowed.get(newPoint));
                        checker.getSide().checkerLeftOnBoard--;
                        updateScore(checker);
                        return new ResultOfMove(MoveType.NORMAL);
                    } else if (newPoint.isEmpty() || newPoint.getChecker().getSide() == checker.getSide()) {
                        diceNumbers.remove(pointsAllowed.get(newPoint));
                        return new ResultOfMove(MoveType.NORMAL);
                    } else if (newPoint.getCheckerCount() == 1 && newPoint.getChecker().getSide() != checker.getSide()) {
                        diceNumbers.remove(pointsAllowed.get(newPoint));
                        return new ResultOfMove(MoveType.KILL, newPoint.getChecker());
                    } else {
                        return new ResultOfMove(MoveType.NONE);
                    }
                } else {
                    if (checker.getBar() != null) {
                        if (newPoint.isEmpty() || newPoint.getChecker().getSide() == checker.getSide()) {
                            diceNumbers.remove(pointsAllowed.get(newPoint));
                            return new ResultOfMove(MoveType.NORMAL);
                        } else if (newPoint.getCheckerCount() == 1 && newPoint.getChecker().getSide() != checker.getSide()) {
                            diceNumbers.remove(pointsAllowed.get(newPoint));
                            return new ResultOfMove(MoveType.KILL, newPoint.getChecker());
                        } else {
                            return new ResultOfMove(MoveType.NONE);
                        }
                    } else {
                        return new ResultOfMove(MoveType.NONE);
                    }
                }
            }
        }
        return new ResultOfMove(MoveType.NONE);
    }

    private boolean checkIfNextMoveAvailable() {
        Side side = (whiteSide.currentSide) ? (WHITE) : (BLACK);
        Bar bar = (side == WHITE) ? (whiteBar) : (blackBar);
        if (!bar.isEmpty()) {
            if (getAllowedMoves(bar.getChecker()).isEmpty()) {
                return false;
            }
        }

        for (Point p : pointList) {
            if (!p.isEmpty() && p.getChecker().getSide() == side) {
                if (!getAllowedMoves(p.getChecker()).isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean canGoToHome(Side side) {
        List<Point> list;
        if (side == WHITE) {
            list = pointList.subList(19, 25);
        } else {
            list = pointList.subList(1, 7);
        }
        int count = 0;
        for (Point p : list) {
            if (p.getChecker() != null && p.getChecker().getSide() == side) {
                for (Checker c : p.getCheckerList()) {
                    count++;
                }
            }
        }

        return count == side.checkerLeftOnBoard;
    }

    private void otherPlayersTurn() {
        whiteSide.currentSide = !whiteSide.currentSide;
        blackSide.currentSide = !blackSide.currentSide;

        dicesRolled = false;
        rollDiceButton.setVisible(true);
        fillCurrentSideCircle();
    }

    private void fillCurrentSideCircle() {
        Color color;
        if (whiteSide.currentSide) {
            color = Color.WHITE;
        } else {
            color = Color.BLACK;
        }
        currentSideCircle.setFill(color);
    }

    private void updateScore(Checker checker) {
        checker.getSide().sideScore++;
        if (whiteSide.currentSide) {
            whiteScore.setText("" + checker.getSide().sideScore);
        } else {
            blackScore.setText("" + checker.getSide().sideScore);
        }
    }

    public int toBoard(double x, double y) throws Exception {
        int pointNumber = -1;
        if (y >= 0 && y <= 200) {
            if (x >= 7 && x <= 49) {
                pointNumber = 12;
            } else if (x >= 56 && x <= 98) {
                pointNumber = 11;
            } else if (x >= 105 && x <= 147) {
                pointNumber = 10;
            } else if (x >= 154 && x <= 196) {
                pointNumber = 9;
            } else if (x >= 203 && x <= 245) {
                pointNumber = 8;
            } else if (x >= 252 && x <= 294) {
                pointNumber = 7;
            } else if (x >= 336 && x <= 378) {
                pointNumber = 6;
            } else if (x >= 385 && x <= 427) {
                pointNumber = 5;
            } else if (x >= 434 && x <= 476) {
                pointNumber = 4;
            } else if (x >= 483 && x <= 525) {
                pointNumber = 3;
            } else if (x >= 532 && x <= 574) {
                pointNumber = 2;
            } else if (x >= 581 && x <= 623) {
                pointNumber = 1;
            }
            if (x >= 630 && x <= 672) {
                pointNumber = 0;
            }
        } else if (y >= 250 && y <= 450) {
            if (x >= 7 && x <= 49) {
                pointNumber = 13;
            } else if (x >= 56 && x <= 98) {
                pointNumber = 14;
            } else if (x >= 105 && x <= 147) {
                pointNumber = 15;
            } else if (x >= 154 && x <= 196) {
                pointNumber = 16;
            } else if (x >= 203 && x <= 245) {
                pointNumber = 17;
            } else if (x >= 252 && x <= 294) {
                pointNumber = 18;
            } else if (x >= 336 && x <= 378) {
                pointNumber = 19;
            } else if (x >= 385 && x <= 427) {
                pointNumber = 20;
            } else if (x >= 434 && x <= 476) {
                pointNumber = 21;
            } else if (x >= 483 && x <= 525) {
                pointNumber = 22;
            } else if (x >= 532 && x <= 574) {
                pointNumber = 23;
            } else if (x >= 581 && x <= 623) {
                pointNumber = 24;
            } else if (x >= 630 && x <= 672) {
                pointNumber = 25;
            }
        }
        if (pointNumber == -1) throw new Exception("Выход за границы поля");
        return pointNumber;
    }
}
