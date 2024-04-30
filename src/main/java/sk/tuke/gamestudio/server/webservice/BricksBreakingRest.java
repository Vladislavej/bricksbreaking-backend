package sk.tuke.gamestudio.server.webservice;

import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.game.Field;
import sk.tuke.gamestudio.game.GameState;
import sk.tuke.gamestudio.game.WebGameManager;

@RestController
@RequestMapping("/api/bricksbreaking/field")
@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.1.56:3000"})
public class BricksBreakingRest {
    private WebGameManager gameManager = new WebGameManager();
    @GetMapping
    public Field getField() {
        return gameManager.getField();
    }

    @GetMapping("/newgame")
    public Field newGame(@RequestParam int difficulty) {
        gameManager.play(difficulty);
        return gameManager.getField();
    }

    @GetMapping("/customgame")
    public Field customGame(@RequestParam int numColors, @RequestParam int rows, @RequestParam int cols) {
        gameManager.prepareGame(3, rows, cols, numColors);
        return gameManager.getField();
    }

    @GetMapping("/breakbrick")
    public void breakBrick(@RequestParam int x, @RequestParam int y) {
        if(gameManager.getField().getGameState() == GameState.PLAYING) {
            gameManager.gameLoop(x, y);
        }
    }

    @GetMapping("/score")
    public int getScore(){
        return gameManager.getScore();
    }

    @GetMapping("/lives")
    public int getLives(){
        return gameManager.getLives();
    }

    @GetMapping("/gamestate")
    public GameState getGameState(){ return gameManager.getField().getGameState(); }
}
