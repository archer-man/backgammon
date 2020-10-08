package backgammon.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Checker extends Circle {

    private Side side;
    private Point point;
    private Bar bar;
    private double mouseX, mouseY;
    private double oldX, oldY;

    public Checker(Side side, Point point) {
        this.side = side;
        setRadius(20);
        setFill(side == Side.WHITE ? (Color.WHITE) : (Color.BLACK));
        setStroke(side == Side.WHITE ? (Color.BLACK) : (Color.WHITE));
        setStrokeWidth(0.5);
        moveToPoint(point);

        setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });
        setOnMouseDragged(e -> {
            if (this.point == null && this.bar != null) {
                relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
            } else if (this.point != null && this.point.getChecker() == this && this.point.getPointNumber() != 25 && this.point.getPointNumber() != 0) {
                relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
            }
        });
    }

    public Side getSide() {
        return side;
    }

    public Point getPoint() {
        return point;
    }

    public Bar getBar() {
        return bar;
    }

    public void moveCoord(int x, int y) {
        oldX = x;
        oldY = y;
        relocate(oldX, oldY);
    }

    public void undoMoveCoord() {
        relocate(oldX, oldY);
    }

    public void moveToPoint(Point point) {
        Point oldPoint = null;
        if (this.point != null) {
            oldPoint = this.point;
            this.point.removeChecker();
        }
        if (this.bar != null) {
            this.bar.removeChecker();
            this.bar = null;
        }

        this.point = point;
        int x = (int) point.getLayoutX() + 1;
        int y;
        int radiusMultiplier = 40;

        if (point.getCheckerCount() >= 5) {
            radiusMultiplier = 160 / (point.getCheckerCount());
            this.point.changeCheckersSize(false);
        }
        if (oldPoint != null && oldPoint.getCheckerCount() >= 5) {
            oldPoint.changeCheckersSize(true);
        }
        if (point.getPointNumber() < 13) {
            y = (int) (point.getLayoutY() + point.getCheckerCount() * radiusMultiplier);
        } else {
            y = 410 - (point.getCheckerCount() * radiusMultiplier);
        }
        point.putChecker(this);
        moveCoord(x, y);
        this.toFront();
    }

    public void moveToBar(Bar bar) {
        this.point.removeChecker();
        this.point = null;
        int x = (int) bar.getLayoutX();
        int y = (int) bar.getLayoutY();
        bar.setChecker(this);
        this.bar = bar;
        moveCoord(x, y);
        this.toFront();
    }

    public boolean isFirstCheckerOnPointOrBar() {
        if (this.point == null && this.bar != null) {
            return this.bar.getChecker() == this;
        }
        return this.point.getChecker() == this;
    }
}
