package backgammon.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Point extends Rectangle {

    private int pointNumber;
    private List<Checker> checkerList = new ArrayList<>();

    public Point(Color color, int x, int y, int number) {
        setHeight(200);
        setWidth(42);
        setFill(color);
        this.pointNumber = number;
        relocate(x, y);
    }

    public int getPointNumber() {
        return pointNumber;
    }

    public List<Checker> getCheckerList() {
        return checkerList;
    }

    public boolean isEmpty() {
        return checkerList.isEmpty();
    }

    public Checker getChecker() {
        if (isEmpty()) return null;
        return checkerList.get(checkerList.size() - 1);
    }

    public void putChecker(Checker checker) {
        checkerList.add(checker);
    }

    public int getCheckerCount() {
        return checkerList.size();
    }

    public void removeChecker() {
        checkerList.remove(checkerList.size() - 1);
    }

    public void changeCheckersSize(boolean applyToOldPoint) {
        int multiplier = 40;

        if (!applyToOldPoint && getCheckerCount() >= 5) {
            multiplier = 160 / getCheckerCount();
        } else if (applyToOldPoint && getCheckerCount() > 5) {
            multiplier = 160 / (getCheckerCount() - 1);
        }

        int checkerCount = this.getCheckerCount();
        for (int i = 0; i < checkerCount; i++) {
            Checker checker = this.getCheckerList().get(i);
            int x = (int) this.getLayoutX() + 1;
            int y;
            if (this.getPointNumber() < 13) {
                y = (int) (this.getLayoutY() + i * multiplier);
            } else {
                y = 410 - (i * multiplier);
            }
            checker.moveCoord(x, y);
        }
    }
}
