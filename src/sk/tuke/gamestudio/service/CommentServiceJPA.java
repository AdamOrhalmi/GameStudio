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


}
