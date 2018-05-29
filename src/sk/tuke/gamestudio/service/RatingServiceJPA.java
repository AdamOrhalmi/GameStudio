package sk.tuke.gamestudio.service;


import sk.tuke.gamestudio.entity.Rating;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class RatingServiceJPA implements RatingService{

    @PersistenceContext
    private EntityManager entityManager;


    public void addRating (Rating rating){
        Rating r;
        try {
            r = (Rating) entityManager.createNamedQuery("Rating.getExactRating")
                    .setParameter("game", rating.getGame())
                    .setParameter("username", rating.getUsername())
                    .getSingleResult();
        }catch (NoResultException e){
            entityManager.persist(rating);
            return;
        }
        r.setRating(rating.getRating());
        entityManager.merge(r);
    }

    public List getRating (){
        return entityManager.createNamedQuery("Rating.getAllRating")
                .getResultList();
    }

    public String getAvgRating(String game){
        return entityManager.createNamedQuery("Rating.getAvgGameRating")
                .setParameter("game", game).getSingleResult().toString();
    }



}
