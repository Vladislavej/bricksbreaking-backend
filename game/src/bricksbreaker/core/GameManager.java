package bricksbreaker.core;

import bricksbreaker.ui.GameUI;
import bricksbreaker.ui.console.ConsoleUI;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.ScoreServiceJDBC;

import java.util.Date;
import java.util.List;

public class GameManager {
    private Field field;
    private int score;
    private int lives;
    private int numColors;
    private ScoreServiceJDBC scoreServiceJDBC;
    private GameUI gameUI;
    private String player;
    private String game;

    public GameManager(GameUI gameUI) {
        this.scoreServiceJDBC = new ScoreServiceJDBC();
        this.gameUI = gameUI;
        this.player = "player";
        this.game = "Bricks Breaking";

        play();
    }
    private Field initialiazeField() {
        int[] fieldSpec = gameUI.getFieldSpecs();
        this.numColors = fieldSpec[2];
        return new Field(fieldSpec[0],fieldSpec[1]);
    }
    private void prepareGame() {
        field = null;
        while (field == null) { field = initialiazeField(); }
        gameUI.setField(field);
        this.field.generate(numColors);
        lives = 3;
        field.setBrokenBricks(0);
        field.setScoreThisMove(0);
        score = 0;
        field.setGameState(GameState.PLAYING);
    }
    public void play() {
        prepareGame();
        do {
            gameUI.showStats(score, lives);
            gameUI.showField();

            int[] coordinates = gameUI.handleMove();
            field.breakTile(coordinates[0], coordinates[1]);

            calculateStats();
            field.unite();
            field.updateGameState();
        } while(field.getGameState() == GameState.PLAYING);

        if(field.getGameState() == GameState.SOLVED) {
            System.out.println("Solved!");
        } else if(field.getGameState() == GameState.FAILED){
            System.out.println("Failed!");
        }

        gameUI.showStats(score, lives);
        gameUI.showField();

        Score finalScore = new Score(game, player, score, new Date());
        scoreServiceJDBC.addScore(finalScore);
        List<Score> topScores = scoreServiceJDBC.getTopScores(game);

        gameUI.showHighScores(topScores);

        if(gameUI.playAgain()) { play(); }
    }

    private void calculateStats() {
        score += field.getScoreThisMove() * field.getBrokenBricks();

        if(field.getBrokenBricks() == 1) { lives -= 1; }
        if(lives < 1) { field.setGameState(GameState.FAILED); }

        field.setBrokenBricks(0);
        field.setScoreThisMove(0);
    }
}
