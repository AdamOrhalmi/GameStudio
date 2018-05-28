package sk.tuke.gamestudio.entity;


import javax.persistence.*;
import java.io.Serializable;

@NamedQueries({
        @NamedQuery(name = "Rating.getAllRating",
                query = "SELECT r FROM Rating r"),
        @NamedQuery(name = "Rating.getAvgGameRating",
                query = "SELECT avg(r.rating) FROM Rating r WHERE r.game=:game"),
        @NamedQuery(name = "Rating.getExactRating",
                query = "SELECT r FROM Rating r WHERE r.game=:game AND r.username =:username"),
        @NamedQuery(name = "Rating.updateRating",
                query = "UPDATE Rating r SET r.rating=:rating WHERE r.game=:game AND r.username=:username ")

})
@Entity @IdClass(RatingID.class)
public class Rating {


    @Id
    private String username;
    @Id
    private String game;

    private int rating;


    public Rating(){}
    public Rating(String username, String game, int rating){
       this.setUsername(username);
       this.setGame(game);
        this.setRating(rating);
    }


    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }
}

