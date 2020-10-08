package backgammon.model;

public class ResultOfMove {

    private MoveType moveType;
    private Checker checker;

    public ResultOfMove(MoveType moveType, Checker checker) {
        this.moveType = moveType;
        this.checker = checker;
    }

    public ResultOfMove(MoveType moveType) {
        this(moveType, null);
    }

    public MoveType getMoveType() {
        return moveType;
    }

    public Checker getChecker() {
        return checker;
    }

}
