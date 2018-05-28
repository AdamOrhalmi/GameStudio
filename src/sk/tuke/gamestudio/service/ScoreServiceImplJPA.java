package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Score;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
;

@Stateless
public class ScoreServiceImplJPA implements ScoreService {
    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private JMSContext context;

    @Resource(lookup = "jms/achievementQueue")
    private Queue queue;

    @Override
    public void addScore(Score score) throws ScoreException {
        if (score.getPoints() > 5000) {
            String text = "congratulations for your very high score! " + score.getPoints();
            context.createProducer().send(queue, context.createTextMessage(text));
        }
        entityManager.persist(score);

    }

    @Override
    public List getBestScoresForGame(String game) throws ScoreException {
        return entityManager.createNamedQuery("Score.getBestScoresForGame")
                .setParameter("game", game).setMaxResults(10).getResultList();
    }


    public Score getScore(Integer id) {
        return entityManager.find(Score.class, id);
    }

    public Score editScore(Score score) {

        return entityManager.merge(score);

    }

    public void removeScore(Integer id) {
        entityManager.remove(getScore(id));
    }

}
