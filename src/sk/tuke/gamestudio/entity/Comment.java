package sk.tuke.gamestudio.entity;


import javax.persistence.*;


@NamedQueries({
        @NamedQuery(name = "Comment.getCommentByGame",
                query = "SELECT c FROM Comment c WHERE c.game=:game "),

        @NamedQuery(name = "Comment.getAllComments",
                query = "SELECT c FROM Comment c ORDER BY c.game"),
        @NamedQuery(name ="Comment.getCommentByID",
        query = "SELECT c FROM Comment c WHERE c.ident=:id"),
        @NamedQuery(name = "Comment.getCommentsByUser",
        query = "SELECT c FROM Comment c WHERE c.username=:username")

})

@Entity
public class Comment {

    @Id
    @GeneratedValue
    private int ident;
    private String username;
    private String comment;
    private String game;


    public Comment(){}

    public Comment(String username, String game, String comment ){
        this.setUsername(username);
        this.setGame(game);
        this.setComment(comment);
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public int getIdent() {
        return ident;
    }

    public void setIdent(int ident) {
        this.ident = ident;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "ident=" + ident +
                ", username='" + username + '\'' +
                ", comment='" + comment + '\'' +
                ", game='" + game + '\'' +
                '}';
    }
}
