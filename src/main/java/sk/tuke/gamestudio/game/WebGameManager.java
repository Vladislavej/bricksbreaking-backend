package sk.tuke.gamestudio.game;

import lombok.Getter;

public class WebGameManager {
    @Getter
    private Field field;
    @Getter
    private int score;
    @Getter
    private int lives;
    private int numColors;

    public WebGameManager() {
    }
    public void prepareGame(String gameMode) {
        field = null;
        while (field == null) {
            field = new Field(10,10);
            numColors = 4;
        }
        this.field.generate(numColors);
        lives = 3;
        field.setBrokenBricks(0);
        field.setScoreThisMove(0);
        score = 0;
        field.setGameState(GameState.PLAYING);
    }
    public void play(String gameMode) {
        prepareGame(gameMode);
    }

    public void calculateStats() {
        score += field.getScoreThisMove() * field.getBrokenBricks();

        if(field.getBrokenBricks() == 1) { lives -= 1; }
        if(lives < 1) { field.setGameState(GameState.FAILED); }

        field.setBrokenBricks(0);
        field.setScoreThisMove(0);
    }

    public void gameLoop(int x, int y) {
        field.breakTile(x, y);

        calculateStats();
        field.unite();
        field.updateGameState();
    }
}