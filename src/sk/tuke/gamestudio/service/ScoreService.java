package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Score;

import java.util.List;

public interface ScoreService {
    void addScore(Score score) throws ScoreException;

    List<Score> getBestScoresForGame(String game) throws ScoreException;
    List<Score> getScoresByUser(String name);
    Score getScore(Integer id);
    void removeScore (Integer id);
}
