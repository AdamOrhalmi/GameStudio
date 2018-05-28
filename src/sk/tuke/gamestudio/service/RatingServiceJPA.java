package sk.tuke.gamestudio.service;


import sk.tuke.gamestudio.entity.Rating;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class RatingServiceJPA implements RatingService{

    @PersistenceContext
    private EntityManager entityManager;


    public void addRating (Rating rating){
        List<Rating> ratingList = getRating();
        for (Rating r:ratingList){
            if (r.getGame().equals(rating.getGame())&&r.getUsername().equals(rating.getUsername())){
//                r.setRating(rating.getRating());
//                entityManager.merge(r);
                entityManager.createNamedQuery("Rating.updateRating")
                        .setParameter("rating", rating.getRating())
                        .setParameter("game",rating.getGame())
                        .setParameter("username",rating.getUsername()
                        );
                return;
            }
        }
        entityManager.persist(rating);
    }

    public List getRating (){
        return entityManager.createNamedQuery("Rating.getAllRating")
                .getResultList();
    }

    public String getAvgRating(String game){
        return entityManager.createNamedQuery("Rating.getAvgGameRating")
                .setParameter("game", game).getSingleResult().toString();
    }

    public int setRating (Rating rating){

        String s= "Rating.getExactRating";
        Rating r = (Rating) entityManager.createNamedQuery(s)
                .setParameter("game", rating.getGame())
                .setParameter("username", rating.getUsername()).getSingleResult();
        if(r.equals(null)){
            entityManager.persist(rating);

        }else {entityManager.merge(rating);}
        return 1;
    }


}
