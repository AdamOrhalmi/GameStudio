package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.client.Game;
import sk.tuke.gamestudio.entity.Comment;

import java.util.List;

public interface CommentService {
    void addComment (Comment comment);
    List getCommentsByGame(String game);
    List getAllComments();
    List getCommentsByUser(String username);
    void editComment (Comment comment);
    Comment getComment (int id);
    void deleteComment (Comment comment);


}
