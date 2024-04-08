package sk.tuke.gamestudio.server.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bricksbreaking")
public class GameController {

    @RequestMapping
    public String bricksbreaking() {
        return "index";
    }
}
