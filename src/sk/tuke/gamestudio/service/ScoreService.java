package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Score;

import java.util.List;

public interface ScoreService {
    void addScore(Score score) throws ScoreException;

    List getBestScoresForGame(String game) throws ScoreException;
    Score getScore(Integer id );
    Score editScore(Score score);
    void removeScore (Integer id);
}
