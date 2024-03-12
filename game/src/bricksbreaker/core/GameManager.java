package bricksbreaker.core;

import bricksbreaker.ui.GameUI;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.CommentServiceJDBC;
import sk.tuke.gamestudio.service.RatingServiceJDBC;
import sk.tuke.gamestudio.service.ScoreServiceJDBC;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class GameManager {
    private Field field;
    private int score;
    private int lives;
    private int numColors;
    private ScoreServiceJDBC scoreServiceJDBC;
    private CommentServiceJDBC commentServiceJDBC;
    private RatingServiceJDBC ratingServiceJDBC;
    private List<Score> topScores;
    private GameUI gameUI;
    private String player;
    private String game;

    public GameManager(GameUI gameUI) {
        this.gameUI = gameUI;
        this.player = "player";
        this.game = "Bricks Breaking";

        this.scoreServiceJDBC = new ScoreServiceJDBC();
        this.commentServiceJDBC = new CommentServiceJDBC();
        this.ratingServiceJDBC = new RatingServiceJDBC();

        this.topScores = scoreServiceJDBC.getTopScores(game);

        mainMenu(gameUI.mainMenu());
    }
    private Field initialiazeField() {
        int[] fieldSpec = gameUI.getFieldSpecs();
        this.numColors = fieldSpec[2];
        return new Field(fieldSpec[0],fieldSpec[1]);
    }
    private void prepareGame(String gameMode) {
        field = null;
        while (field == null) {
            if(Objects.equals(gameMode, "custom")) {
                field = initialiazeField();
            } else if (Objects.equals(gameMode, "classic")) {
                field = new Field(10,10);
                numColors = 4;
            }
        }
        gameUI.setField(field);
        this.field.generate(numColors);
        lives = 3;
        field.setBrokenBricks(0);
        field.setScoreThisMove(0);
        score = 0;
        field.setGameState(GameState.PLAYING);
    }
    public void play(String gameMode) {
        prepareGame(gameMode);
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

        if(Objects.equals(gameMode, "classic")) {

            Score finalScore = new Score(game, player, score, new Date());
            scoreServiceJDBC.addScore(finalScore);
        }
        topScores = scoreServiceJDBC.getTopScores(game);

        gameUI.showHighScores(topScores);

        if(gameUI.playAgain()) { play(gameMode); } else { mainMenu(gameUI.mainMenu()); }
    }

    private void calculateStats() {
        score += field.getScoreThisMove() * field.getBrokenBricks();

        if(field.getBrokenBricks() == 1) { lives -= 1; }
        if(lives < 1) { field.setGameState(GameState.FAILED); }

        field.setBrokenBricks(0);
        field.setScoreThisMove(0);
    }

    private void mainMenu(int i) {
        if(field != null) {
            score = 0;
            field = null;
        }
        switch (i) {
            case 1:
                play("classic");
            case 2:
                play("custom");
            case 3:
                gameUI.showHighScores(topScores);
                break;
            case 4:
                player = gameUI.playerName();
                break;
            case 5:
                System.exit(0);
                return;
            case 6:
                String getComment = gameUI.getComment();
                if(getComment != " ") {
                    Comment comment = new Comment(game, player, gameUI.getComment(), new Date());
                    commentServiceJDBC.addComment(comment);
                }
                break;
            case 7:
                Rating rating = new Rating(game,player,gameUI.getRating(),new Date());
                ratingServiceJDBC.setRating(rating);
                break;
        }
        mainMenu(gameUI.mainMenu());
    }

    public String getPlayer() {
        return player;
    }
}
