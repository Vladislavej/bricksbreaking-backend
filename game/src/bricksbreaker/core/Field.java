package bricksbreaker.core;

import java.util.Random;

public class Field  {
    private int rows;
    private int cols;
    private Brick[][] bricks;
    public Field(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        bricks = new Brick[rows][cols];
    }
    public Brick[][] getBricks() {
        return bricks;
    }

    public void generateField() {
        Random random = new Random();
        BrickColor[] colors = BrickColor.values();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                BrickColor randomColor = colors[random.nextInt(colors.length)];
                this.bricks[i][j] = new Brick(randomColor);
            }
        }
    }

    public boolean breakTile(int x, int y) {
        if(bricks[x][y] == null) { return false; }
        BrickColor brickColor = bricks[x][y].getColor();
        bricks[x][y] = null;
        checkNeigbours(x,y, brickColor);
        System.out.println("Destroyed tile at " + x + " " + y + " of color = " + brickColor);
        return true;
    }

    private void checkNeigbours(int x , int y, BrickColor brickColor) {
        //left
        if (y - 1 >= 0 && bricks[x][y - 1] != null && bricks[x][y - 1].getColor() == brickColor) { breakTile(x, y - 1); }
        //top
        if (x - 1 >= 0 && bricks[x - 1][y] != null && bricks[x - 1][y].getColor() == brickColor) { breakTile(x - 1, y); }
        //right
        if (x + 1 < rows && bricks[x + 1][y] != null && bricks[x + 1][y].getColor() == brickColor) { breakTile(x + 1, y); }
        //bottom
        if (y + 1 < cols && bricks[x][y + 1] != null && bricks[x][y + 1].getColor() == brickColor) { breakTile(x, y + 1); }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
}
