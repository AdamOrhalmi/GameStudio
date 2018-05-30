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
    @Path("/byUser/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public List getCommentsByUser (@PathParam("username") String username){
        return commentService.getCommentsByUser(username);
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List getAllComments (){
       return commentService.getAllComments();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteComment (@PathParam("id") int id){
        commentService.deleteComment(commentService.getComment(id));
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Comment getComment (@PathParam("id") int id){
        return commentService.getComment(id);
    }

    @POST
    @Path("/edit")
    @Produces(MediaType.APPLICATION_JSON)
    public void editComment (Comment comment ){
        commentService.editComment(comment);
    }


}
