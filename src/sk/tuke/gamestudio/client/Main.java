package sk.tuke.gamestudio.client;

import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.kamene.Kamene;
import sk.tuke.gamestudio.minesweeper.Minesweeper;
import sk.tuke.gamestudio.pexeso.Pexeso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    private static RatingRestServiceClient ratingService = new RatingRestServiceClient();
    private static CommentRestServiceClient commentService = new CommentRestServiceClient();
    private static ScoreRestServiceClient scoreService = new ScoreRestServiceClient();
    private static boolean firstGamePlayed =false;
    private static Game game;


    public static void main(String[] args) {
        System.out.println("welcome to GameStudio!");
        System.out.println("enter your name: ");
        String username = readLine();
        System.out.println("what would you like to play?");
       while(true) {
           int i=1;
           for (Games g: Games.values()){
               System.out.println(i+". "+g.toString());
               i++;
           }

           System.out.println(i+ ". View tables\nAnything else: Exit");
           int s = Integer.parseInt(readLine());

           switch(s) {
               case 1:
                   game = new Minesweeper(username);
                   firstGamePlayed=true;
                   break;
               case 2:
                   game = new Kamene(username);
                   firstGamePlayed = true;
                   break;
               case 3:
                   game = new Pexeso(username);
                   firstGamePlayed = true;
                   break;
               case 4:
                   Tableviewer.additionalChoices();
                   break;
               default:
               System.out.println("See you some other time!");
               System.exit(0);
           }
           if(firstGamePlayed) {
               System.out.println("Thank you for playing! would you like to leave us a comment? y/n");
               String c = readLine();
               if (c.equals("y")) {
                   System.out.println("write your comment here: ");
                   c = readLine();
                   Comment comment = new Comment(username, game.getGameName(), c);
                   commentService.addComment(comment);

               }
               System.out.println("Would you like to rate our game? y/n");
               c = readLine();

               if (c.equals("y")) {
                   System.out.println("enter your rating (1-10) : ");
                   c = readLine();
                   int rate = Integer.parseInt(c);
                   Rating rating = new Rating(username, game.getGameName(), rate);

                   ratingService.addRating(rating);
               }
           }

           firstGamePlayed = false;

           System.out.println("Thank you for playing! would you like to play another game?");
       }

    }





    private static String readLine() {
        try {
            return input.readLine();
        } catch (IOException e) {
            return null;
        }
    }


}
