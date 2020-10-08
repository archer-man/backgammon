package backgammon.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Bar extends Rectangle {

    private List<Checker> checkerList = new ArrayList<>();

    public Bar(double x, double y) {
        setWidth(42);
        setHeight(42);
        setLayoutX(x);
        setLayoutY(y);
        setFill(Color.LIGHTGRAY);
    }

    public void setChecker(Checker checker) {
        checkerList.add(checker);
    }

    public Checker getChecker() {
        return checkerList.get(checkerList.size() - 1);
    }

    public void removeChecker() {
        checkerList.remove(checkerList.size() - 1);
    }

    public boolean isEmpty() {
        return checkerList.isEmpty();
    }
}
