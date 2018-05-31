package sk.tuke.gamestudio.service;



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
    public List<Comment> getCommentsByGame(String game) {
        return entityManager.createNamedQuery("Comment.getCommentByGame", Comment.class)
                .setParameter("game", game).getResultList();
    }

    @Override
    public List<Comment> getAllComments() {
        return entityManager.createNamedQuery("Comment.getAllComments", Comment.class)
                .getResultList();
    }

    @Override
    public List<Comment> getCommentsByUser(String username) {
        return entityManager.createNamedQuery("Comment.getCommentsByUser", Comment.class)
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
       return entityManager.createNamedQuery("Comment.getCommentByID", Comment.class)
                .setParameter("id", id).getSingleResult();
    }


    @Override
    public void deleteComment(Comment comment) {
        entityManager.remove(entityManager.merge(comment));
    }


}
