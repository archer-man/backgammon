package backgammon.controller;

import backgammon.model.Checker;
import backgammon.model.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

import backgammon.model.*;

class ControllerTest {

    Controller controller = new Controller();
    Point newPoint;
    Checker checker;

    @BeforeEach
    void init() {
        controller.drawPoints();
        controller.drawCheckers();
    }

    @Test
    void testForIllegalMove() {
        newPoint = controller.pointList.get(25);
        checker = controller.pointList.get(24).getChecker();
        assertEquals("NONE", controller.tryMove(checker, newPoint).getMoveType().toString());
    }

    @Test
    void testForEligibleMoves() {
        controller.leftDice = Dice.FOUR;
        controller.rightDice = Dice.ONE;
        controller.dicesRolled = true;
        controller.diceNumbers.add(controller.leftDice.getNumber());
        controller.diceNumbers.add(controller.rightDice.getNumber());
        controller.diceNumberCount = controller.diceNumbers.size();
        newPoint = controller.pointList.get(5);
        checker = controller.pointList.get(1).getChecker();
        assertEquals("NORMAL", controller.tryMove(checker, newPoint).getMoveType().toString());
        checker = controller.pointList.get(1).getChecker();
        newPoint = controller.pointList.get(2);
        assertEquals("NORMAL", controller.tryMove(checker, newPoint).getMoveType().toString());
    }

    @Test
    void testWhetherMoveIsIllegalWhenBarIsFull() {
        controller.leftDice = Dice.FIVE;
        controller.rightDice = Dice.THREE;
        controller.dicesRolled = true;
        controller.diceNumbers.add(controller.leftDice.getNumber());
        controller.diceNumbers.add(controller.rightDice.getNumber());
        controller.diceNumberCount = controller.diceNumbers.size();
        checker = controller.pointList.get(1).getChecker();
        checker.moveToBar(controller.whiteBar);
        checker = controller.pointList.get(1).getChecker();
        newPoint = controller.pointList.get(3);
        assertEquals("NONE", controller.tryMove(checker, newPoint).getMoveType().toString());
        checker = controller.whiteBar.getChecker();
        assertEquals("NORMAL", controller.tryMove(checker, newPoint).getMoveType().toString());
    }
}