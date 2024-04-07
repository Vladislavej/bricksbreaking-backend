package sk.tuke.gamestudio.game;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.*;
import sk.tuke.gamestudio.game.ui.GameUI;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class GameManager {
    private Field field;
    private int score;
    private int lives;
    private int numColors;
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private CommentService commentService;
    private List<Score> topScores;
    private List<Comment> commentList;
    private GameUI gameUI;
    private String player;
    private String game;

    public GameManager(GameUI gameUI) {
        this.gameUI = gameUI;
        this.player = "Player";
        this.game = "bricksbreaking";
        gameUI.setPlayer(player);
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
            this.topScores = scoreService.getTopScores(game);
            Score finalScore = new Score(game, player, score, new Date());
            scoreService.addScore(finalScore);
            topScores = scoreService.getTopScores(game);
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
                topScores = scoreService.getTopScores(game);
                gameUI.showHighScores(topScores);
                break;
            case 4:
                player = gameUI.changePlayerName();
                break;
            case 5:
                System.exit(0);
                return;
            case 6:
                commentList = commentService.getComments(game);
                String getComment = gameUI.addComment(commentList);
                if(getComment != null) {
                    Comment comment = new Comment(game, player, getComment, new Date());
                    commentService.addComment(comment);
                }
                break;
            case 7:
                gameUI.passLastRating(ratingService.getRating(game,player));
                gameUI.passAverageRating(ratingService.getAverageRating(game));
                int getRating = gameUI.addRating();
                if(getRating != -1) {
                    Rating rating = new Rating(game, player, getRating, new Date());
                    ratingService.setRating(rating);
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

    public void setGameUI(GameUI gameUI) {
        this.gameUI = gameUI;
    }

    public void execute() {
        topScores = scoreService.getTopScores(game);
        mainMenu(gameUI.showMainMenu());
    }
}