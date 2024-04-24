package sk.tuke.gamestudio.server.webservice;

import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.game.Field;
import sk.tuke.gamestudio.game.WebGameManager;

@RestController
@RequestMapping("/api/bricksbreaking/field")
@CrossOrigin(origins = "http://localhost:3000") // Update with your frontend origin
public class BricksBreakingRest {
    private WebGameManager gameManager = new WebGameManager();
    @GetMapping
    public Field getField() {
        gameManager.prepareGame("classic");
        return gameManager.getField();
    }

    @GetMapping("/newGame")
    public Field newGame() {
        gameManager.play("classic");
        return gameManager.getField();
    }

    @GetMapping("/breakbrick")
    public Field breakBrick(@RequestParam int x, @RequestParam int y) {
        gameManager.gameLoop(x, y);
        return gameManager.getField();
    }
}
