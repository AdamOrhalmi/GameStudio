
package sk.tuke.gamestudio.client;

import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.ScoreService;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

public class ScoreRestServiceClient implements ScoreService {
    private static final String URL = "http://localhost:8080/gamestudio_war_exploded/api/score";

    @Override
    public void addScore(Score score) {
        try {
            Client client = ClientBuilder.newClient();
            Response response = client.target(URL)
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(score, MediaType.APPLICATION_JSON), Response.class);
        } catch (Exception e) {
            throw new RuntimeException("Error saving score", e);
        }
    }

    @Override
    public List<Score> getBestScoresForGame(String game) {
        try {
            Client client = ClientBuilder.newClient();
            return client.target(URL)
                    .path("/" + game)
                    .request(MediaType.APPLICATION_JSON)
                    .get(new GenericType<List<Score>>() {
                    });
        } catch (Exception e) {
            throw new RuntimeException("Error loading score", e);
        }
    }

    @Override
    public List<Score> getScoresByUser(String name) {
        try {
            Client client = ClientBuilder.newClient();
            return client.target(URL)
                    .path("/byuser/" + name)
                    .request(MediaType.APPLICATION_JSON)
                    .get(new GenericType<List<Score>>() {
                    });
        } catch (Exception e) {
            throw new RuntimeException("Error loading score", e);
        }
    }

    @Override
    public Score getScore(Integer id) {
        try {
            Client client = ClientBuilder.newClient();
            return client.target(URL)
                    .path("/idget/" + id)
                    .request(MediaType.APPLICATION_JSON)
                    .get(new GenericType<Score>() {
                    });
        } catch (Exception e) {
            throw new RuntimeException("Error loading score", e);
        }
    }

    public void printBestScores(String gameName) {
        List<Score> highScores = getBestScoresForGame(gameName);
        System.out.println(" HALL OF FAME- " + gameName);
        for (int i = 0; i < highScores.size(); i++) {
            System.out.println((i + 1) + ". " + highScores.get(i).getPlayer());
            System.out.println("   ------------> " + highScores.get(i).getPoints() + " points");
        }
    }


    @Override
    public void removeScore(Integer id) {
        try {
            Client client = ClientBuilder.newClient();
            Response response = client.target(URL).path("/"+id)
                    .request(MediaType.APPLICATION_JSON)
                    .delete();
        } catch (Exception e) {
            throw new RuntimeException("Error saving score", e);
        }

    }
}
