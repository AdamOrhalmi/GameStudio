package sk.tuke.gamestudio.webservice;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.service.RatingService;

import javax.ejb.EJB;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/rating")
public class RatingRestService {
   @EJB
  private RatingService ratingService;

   @POST
   @Path("/new")
   @Produces(MediaType.APPLICATION_JSON)
    public Rating addRating(Rating rating){
       ratingService.addRating(rating);
       return rating;
   }

   @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List getRating (){
       return ratingService.getRating();
   }

    @GET
    @Path("/ratingof/{game}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAvgRating (@PathParam("game") String game){
       String o= ratingService.getAvgRating(game);
      if(o==null)return "failed."; else
       return o;
    }
}