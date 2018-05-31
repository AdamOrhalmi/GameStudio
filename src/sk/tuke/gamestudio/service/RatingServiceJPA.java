package sk.tuke.gamestudio.service;


import sk.tuke.gamestudio.entity.Rating;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class RatingServiceJPA implements RatingService {

    @PersistenceContext
    private EntityManager entityManager;

    @Resource(lookup = "jms/ratingChangedQueue")
    private Queue queue;

    @Inject
    private JMSContext context;

    public void addRating(Rating rating) {
        Rating r;
        try {
            r = (Rating) entityManager.createNamedQuery("Rating.getExactRating")
                    .setParameter("game", rating.getGame())
                    .setParameter("username", rating.getUsername())
                    .getSingleResult();
        } catch (NoResultException e) {
            entityManager.persist(rating);
            return;
        }
        r.setRating(rating.getRating());
        entityManager.merge(r);
        String text = "rating changed for " + r.getUsername() + "'s comment on " + r.getGame() + ": " + r.getRating();
        context.createProducer().send(queue, context.createTextMessage(text));
    }

    public List<Rating> getRating() {
        return entityManager.createNamedQuery("Rating.getAllRating")
                .getResultList();
    }

    public String getAvgRating(String game) {
        return entityManager.createNamedQuery("Rating.getAvgGameRating")
                .setParameter("game", game).getSingleResult().toString();
    }


}
