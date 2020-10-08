package backgammon.model;

public enum Dice {
    ONE(1, "dice/1.png"),
    TWO(2, "dice/2.png"),
    THREE(3, "dice/3.png"),
    FOUR(4, "dice/4.png"),
    FIVE(5, "dice/5.png"),
    SIX(6, "dice/6.png");

    private final int number;
    private final String path;

    Dice(int number, String path) {
        this.number = number;
        this.path = path;
    }

    public int getNumber() {
        return number;
    }

    public String getPath() {
        return path;
    }

}
