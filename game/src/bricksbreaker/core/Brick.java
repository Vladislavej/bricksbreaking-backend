package bricksbreaker.core;

public class Brick {
    private BrickColor color;
    private int brickScore;
    public Brick(BrickColor color) {
        this.color = color;
        brickScore = this.getColor().getScore();
    }

    public BrickColor getColor() {
        return color;
    }
}
