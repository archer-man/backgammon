package backgammon.model;

public enum Side {
    WHITE(1, true, 15, 0),
    BLACK(-1, false, 15, 0);

    public final int moveDirection;
    public int checkerLeftOnBoard;
    public int sideScore;
    public boolean currentSide;

    Side(int i, boolean currentSide, int leftCheckers, int score) {
        this.moveDirection = i;
        this.currentSide = currentSide;
        this.checkerLeftOnBoard = leftCheckers;
        this.sideScore = score;
    }
}
