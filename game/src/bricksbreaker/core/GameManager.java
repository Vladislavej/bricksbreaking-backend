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
    private List<Comment> commentList;
    private GameUI gameUI;
    private String player;
    private String game;

    public GameManager(GameUI gameUI) {
        this.gameUI = gameUI;
        this.player = "Player";
        this.game = "Bricks Breaking";
        gameUI.setPlayer(player);

        this.scoreServiceJDBC = new ScoreServiceJDBC();
        this.commentServiceJDBC = new CommentServiceJDBC();
        this.ratingServiceJDBC = new RatingServiceJDBC();
        this.topScores = scoreServiceJDBC.getTopScores(game);

        mainMenu(gameUI.showMainMenu());
    }
    private Field initialiazeField() {
        int[] fieldSpec = gameUI.getFieldSpecs();
        this.numColors = fieldSpec[2];
        return new Field(fieldSpec[0],fieldSpec[1]);
    }
    public void prepareGame(String gameMode) {
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

        gameLoop();

        gameOver();

        if(Objects.equals(gameMode, "classic")) {
            this.topScores = scoreServiceJDBC.getTopScores(game);
            Score finalScore = new Score(game, player, score, new Date());
            scoreServiceJDBC.addScore(finalScore);
            topScores = scoreServiceJDBC.getTopScores(game);
            gameUI.showHighScores(topScores);
        }

        if(gameUI.playAgain()) { play(gameMode); } else { mainMenu(gameUI.showMainMenu()); }
    }

    public void calculateStats() {
        score += field.getScoreThisMove() * field.getBrokenBricks();

        if(field.getBrokenBricks() == 1) { lives -= 1; }
        if(lives < 1) { field.setGameState(GameState.FAILED); }

        field.setBrokenBricks(0);
        field.setScoreThisMove(0);
    }

    public void mainMenu(int i) {
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
                player = gameUI.changePlayerName();
                break;
            case 5:
                System.exit(0);
                return;
            case 6:
                commentList = commentServiceJDBC.getComments(game);
                String getComment = gameUI.addComment(commentList);
                if(getComment != null) {
                    Comment comment = new Comment(game, player, getComment, new Date());
                    commentServiceJDBC.addComment(comment);
                }
                break;
            case 7:
                gameUI.passLastRating(ratingServiceJDBC.getRating(game,player));
                gameUI.passAverageRating(ratingServiceJDBC.getAverageRating(game));
                int getRating = gameUI.addRating();
                if(getRating != -1) {
                    Rating rating = new Rating(game, player, getRating, new Date());
                    ratingServiceJDBC.setRating(rating);
                }
                break;
            case 8:
                gameUI.showHelp();
                break;
        }
        mainMenu(gameUI.showMainMenu());
    }

    public String getPlayer() {
        return player;
    }

    public ScoreServiceJDBC getScoreServiceJDBC() {
        return scoreServiceJDBC;
    }

    public void setScoreServiceJDBC(ScoreServiceJDBC scoreServiceJDBC) {
        this.scoreServiceJDBC = scoreServiceJDBC;
    }

    public CommentServiceJDBC getCommentServiceJDBC() {
        return commentServiceJDBC;
    }

    public void setCommentServiceJDBC(CommentServiceJDBC commentServiceJDBC) {
        this.commentServiceJDBC = commentServiceJDBC;
    }

    public RatingServiceJDBC getRatingServiceJDBC() {
        return ratingServiceJDBC;
    }

    public void setRatingServiceJDBC(RatingServiceJDBC ratingServiceJDBC) {
        this.ratingServiceJDBC = ratingServiceJDBC;
    }

    public Field getField() {
        return field;
    }

    public int getLives() {
        return lives;
    }

    private void gameLoop() {
        do {
            gameUI.showStats(score, lives);
            gameUI.showField();

            int[] coordinates = gameUI.handleMove();
            if(coordinates == null) { mainMenu(gameUI.showMainMenu()); }
            field.breakTile(coordinates[0], coordinates[1]);

            calculateStats();
            field.unite();
            field.updateGameState();

        } while(field.getGameState() == GameState.PLAYING);
    }

    private void gameOver() {
        gameUI.showStats(score, lives);
        gameUI.showField();

        if(field.getGameState() == GameState.SOLVED) {
            gameUI.showWin();
        } else if(field.getGameState() == GameState.FAILED){
            gameUI.showFail();
        }
    }

    public int getScore() {
        return score;
    }

    public GameUI getGameUI() {
        return gameUI;
    }
}