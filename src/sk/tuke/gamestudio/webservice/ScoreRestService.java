package sk.tuke.gamestudio.webservice;

import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.ScoreException;
import sk.tuke.gamestudio.service.ScoreService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/score")
public class ScoreRestService {
    @EJB
    private ScoreService scoreService;
//JSON- JavaScript Object Notation
    @POST
    @Consumes("application/json")
    public Response addScore(Score score) throws ScoreException {
        scoreService.addScore(score);
        return Response.ok().build();
    }

    @GET
    @Path("/{game}")
    @Produces("application/json")
    public List<Score> getBestScoresForGame(@PathParam("game") String game) throws ScoreException {
        return scoreService.getBestScoresForGame(game);
    }

    @GET
    @Path("/idget/{id}")
    @Produces("application/json")
    public Response getScore (@PathParam("id") Integer id){
        Score s = scoreService.getScore(id);
        if (s == null){
            return Response.status(404).build();
        }
        else return Response.ok(s).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response removeScore (@PathParam("id") Integer id){
       scoreService.removeScore(id);
       return Response.ok().build();

    }
    @GET
    @Path("/byuser/{user}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Score> getScoresByUser(@PathParam("user")String user){
        return scoreService.getScoresByUser(user);
    }




}

