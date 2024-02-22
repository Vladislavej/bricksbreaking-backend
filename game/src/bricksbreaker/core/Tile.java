package bricksbreaker.core;

public class Tile {
    private TileColor color;
    private int brickScore;
    private boolean visited;

    public Tile(TileColor color) {
        this.color = color;
        brickScore = this.getColor().getScore();
        visited = false;
    }

    public TileColor getColor() {
        return color;
    }

    public void breakNeighbours(int x, int y, Field field) {
        if (visited) return;
        visited = true;

        Tile[][] tiles = field.getTiles();

        //left
        if (x - 1 >= 0 && tiles[y][x - 1] != null && tiles[y][x - 1].getColor() == color) {
            field.breakTile(x - 1, y);
        }
        //right
        if (x + 1 < field.getCols() && tiles[y][x + 1] != null && tiles[y][x + 1].getColor() == color) {
            field.breakTile(x + 1, y);
        }
        //up
        if (y - 1 >= 0 && tiles[y - 1][x] != null && tiles[y - 1][x].getColor() == color) {
            field.breakTile(x, y - 1);
        }
        //down
        if (y + 1 < field.getRows() && tiles[y + 1][x] != null && tiles[y + 1][x].getColor() == color) {
            field.breakTile(x, y + 1);
        }
    }
}