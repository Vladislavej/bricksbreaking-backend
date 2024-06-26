package sk.tuke.gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.1.56:3000", "http://192.168.33.12:3000/"})
public class CommentServiceRest {
    @Autowired
    private CommentService commentService;
    @PostMapping
    public void addComment(@RequestBody Comment comment){
        commentService.addComment(comment);
    }
    @GetMapping("/{game}")
    public List<Comment> getComments(@PathVariable String game){
        return commentService.getComments(game);
    }
}