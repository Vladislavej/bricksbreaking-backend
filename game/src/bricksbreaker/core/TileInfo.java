package bricksbreaker.core;

public enum TileInfo {
    R(255,0,0, 20),
    G(0,255,0 ,10),
    B(0,0,255 ,5);

    private final int r;
    private final int g;
    private final int b;
    private final int score;

    TileInfo(int r, int g, int b, int score) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}