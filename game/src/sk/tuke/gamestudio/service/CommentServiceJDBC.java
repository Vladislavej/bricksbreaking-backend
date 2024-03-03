package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Comment;

import java.util.List;

public class CommentServiceJDBC implements CommentService {
    @Override
    public void addComment(Comment comment) throws CommentException {

    }

    @Override
    public List<Comment> getComments(String game) throws CommentException {
        return null;
    }

    @Override
    public void reset() throws CommentException {

    }
}
