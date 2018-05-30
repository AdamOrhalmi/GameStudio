package sk.tuke.gamestudio.service;



import sk.tuke.gamestudio.client.Game;
import sk.tuke.gamestudio.entity.Comment;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class CommentServiceJPA implements CommentService {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void addComment(Comment comment){
        entityManager.persist(comment);
    }

    @Override
    public List getCommentsByGame(String game) {
        return entityManager.createNamedQuery("Comment.getCommentByGame")
                .setParameter("game", game).getResultList();
    }

    @Override
    public List getAllComments() {
        return entityManager.createNamedQuery("Comment.getAllComments")
                .getResultList();
    }

    @Override
    public List getCommentsByUser(String username) {
        return entityManager.createNamedQuery("Comment.getCommentsByUser")
                .setParameter("username", username)
                .getResultList();
    }

    @Override
    public void editComment(Comment comment) {
        Comment dbComment = entityManager.merge(comment);
        dbComment.setComment(comment.getComment());
        System.out.println(">>>>>>>>>>>> " + dbComment);
    }

    public Comment getComment(int id){
       return (Comment) entityManager.createNamedQuery("Comment.getCommentByID")
                .setParameter("id", id).getSingleResult();
    }


    @Override
    public void deleteComment(Comment comment) {
        entityManager.remove(entityManager.merge(comment));
    }


}
