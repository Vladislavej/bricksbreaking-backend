package bricksbreaker.core;

public class Tile {
    private TileColor color;
    private int brickScore;

    public Tile(TileColor color) {
        this.color = color;
        brickScore = this.getColor().getScore();
    }

    public TileColor getColor() {
        return color;
    }
}
