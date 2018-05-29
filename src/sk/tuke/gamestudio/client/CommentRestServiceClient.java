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
            throw new RuntimeException("Error uploading comment", e);
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
            throw new RuntimeException("Error loading score", e);
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
            throw new RuntimeException("Error loading score", e);
        }
    }

    @Override
    public void editComment(Comment comment) {

        try {
            Client client = ClientBuilder.newClient();
            Response response = client.target(URL)
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(comment, MediaType.APPLICATION_JSON), Response.class);
        } catch (Exception e) {
            throw new RuntimeException("Error saving score", e);
        }

    }

    @Override
    public Comment getComment(int id) {
        return null;
    }

    @Override
    public void deleteComment(int id) {

        try {
            Client client = ClientBuilder.newClient();
            Response response = client.target(URL).path("/"+id)
                    .request(MediaType.APPLICATION_JSON)
                    .delete();
        } catch (Exception e) {
            throw new RuntimeException("Error saving score", e);
        }


    }

    public void printComments(List<Comment> commentList) {
        if (commentList.isEmpty()) {
            System.out.println("This bitch empty!");
            return;
        }
        System.out.println("User comments: ");
        for (int i = 0; i < commentList.size(); i++) {
            String gamename = commentList.get(i).getGame();
            gamename = gamename.substring(0, 1).toUpperCase() + gamename.substring(1);
            System.out.println("User " + commentList.get(i).getUsername() + "\n       commented on game " + gamename + ": \n           " + commentList.get(i).getComment());
        }
    }

}
//todo implementovat delet na score, comment, edit na comment, vymysliet este dajaky mdb