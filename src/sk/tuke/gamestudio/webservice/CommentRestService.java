package sk.tuke.gamestudio.webservice;


import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.service.CommentService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/comment")
public class CommentRestService {
@EJB
private CommentService commentService;


    @POST
    @Path("/new")
    @Produces(MediaType.APPLICATION_JSON)
    public Comment addComment (Comment comment){

    commentService.addComment(comment);
    return comment;
    }

    @GET
    @Path("/byGame/{game}")
    @Produces(MediaType.APPLICATION_JSON)
    public List getCommentsByGame (@PathParam("game") String game){
        return commentService.getCommentsByGame(game);
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List getAllComments (){
       return commentService.getAllComments();
    }

}
