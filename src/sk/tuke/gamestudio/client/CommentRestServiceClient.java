package sk.tuke.gamestudio.client;

import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.service.CommentService;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

public class CommentRestServiceClient implements CommentService {

    private static final String URL = "http://localhost:8080/gamestudio_war_exploded/api/comment";

    @Override
    public void addComment(Comment comment) {

        try {
            Client client = ClientBuilder.newClient();
            Response response = client.target(URL + "/new")
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(comment, MediaType.APPLICATION_JSON), Response.class);

        } catch (Exception e) {
            System.err.println("Error uploading comment"+ e.getMessage());

        }
    }

    @Override
    public List<Comment> getCommentsByGame(String game) {
        try {
            Client client = ClientBuilder.newClient();
            return client.target(URL)
                    .path("/byGame/" + game)
                    .request(MediaType.APPLICATION_JSON)
                    .get(new GenericType<List<Comment>>() {
                    });
        } catch (Exception e) {
            System.err.println("Error downloading comments"+ e.getMessage());
            return null;
        }

    }

    @Override
    public List<Comment> getAllComments() {
        try {
            Client client = ClientBuilder.newClient();
            return client.target(URL)
                    .path("/all")
                    .request(MediaType.APPLICATION_JSON)
                    .get(new GenericType<List<Comment>>() {
                    });
        } catch (Exception e) {
            System.err.println("Error downloading comments"+ e.getMessage());
            return null;
        }
    }

    @Override
    public List<Comment> getCommentsByUser(String username) {
        try {
            Client client = ClientBuilder.newClient();
            return client.target(URL)
                    .path("/byUser/" + username)
                    .request(MediaType.APPLICATION_JSON)
                    .get(new GenericType<List<Comment>>() {
                    });
        } catch (Exception e) {
            System.err.println("Error downloading comments"+ e.getMessage());
            return null;
        }
    }

    @Override
    public void editComment(Comment comment) {

        try {
            Client client = ClientBuilder.newClient();
            Response response = client.target(URL)
                    .path("/edit")
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(comment, MediaType.APPLICATION_JSON), Response.class);
        } catch (Exception e) {
            System.err.println("Error editing comment"+ e.getMessage());

        }

    }

    @Override
    public Comment getComment(int id) {
        try {
            Client client = ClientBuilder.newClient();
            return client.target(URL)
                    .path("/"+id)
                    .request(MediaType.APPLICATION_JSON)
                    .get(new GenericType<Comment>() {
                    });
        } catch (Exception e) {
             System.err.println("Error downloading comment"+ e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteComment(Comment comment ) {

        try {
            Client client = ClientBuilder.newClient();
            Response response = client.target(URL)
                    .path("/" + comment.getIdent())
                    .request(MediaType.APPLICATION_JSON)
                    .delete(Response.class);
        } catch (Exception e) {
            System.err.println("Error deleting comment"+ e.getMessage());

        }


    }

    public void printComments(List<Comment> commentList) {
        if (commentList.isEmpty()) {

            return;
        }
        System.out.println("User comments: \n");
        for (int i = 0; i < commentList.size(); i++) {
            String gamename = commentList.get(i).getGame();
            gamename = gamename.substring(0, 1).toUpperCase() + gamename.substring(1);
            System.out.println((i+1) + ". User " + commentList.get(i).getUsername()
                    + "\n       commented on game " + gamename + ": \n           "
                    + commentList.get(i).getComment()
                    +"\n        COMMENTID: "+commentList.get(i).getIdent());
        }
    }

}
